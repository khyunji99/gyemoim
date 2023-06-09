package com.team.gyemoim.service;

import com.team.gyemoim.dto.board.*;
import com.team.gyemoim.mapper.BoardMapper;
import com.team.gyemoim.vo.AttachedVO;
import com.team.gyemoim.vo.BoardVO;
import com.team.gyemoim.vo.PageVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;
    private final String uploadPath; // FileUploadConfig 에 있는 uploadPath 주입


    /* (Create) */

    // 첨부파일 조회하기
    @Override
    public AttachedVO getAttachedById(int bid) throws Exception {
        return boardMapper.getAttachedById(bid);
    }
    // 첨부파일 수정
    @Override
    public void updateAttached(AttachedVO attached) throws Exception {
        boardMapper.updateAttached(attached);
    }
    // 첨부파일 삭제
    @Override
    public void deleteAttached(int attachedID) throws Exception {
        boardMapper.deleteAttached(attachedID);
    }


    // 게시글 생성하고 생성된 게시글의 번호 bid 반환
    @Override
    @Transactional
    public int writePost(BoardWriteDTO boardWriteDTO, MultipartFile file) throws Exception {
        // BoardWriteDTO 이용하여 BoardVO 생성하고 DB에 게시글 저장
        BoardVO boardVO = new BoardVO();
        boardVO = boardVO.dtoToVO(boardWriteDTO);
        int bid = boardMapper.getBid();
        boardVO.setBid(bid);
        System.out.println(boardVO);

        boardMapper.insert(boardVO); // 게시글 저장 후 고유 식별자 반환

        // 첨부파일 존재하면 첨부파일 저장
        if (file != null && !file.isEmpty()) {
            // AttachedVO 객체 생성 및 정보 저장
            AttachedVO attachedVO = new AttachedVO();
            attachedVO = attachedVO.dtoToVO(bid,file);
            attachedVO.setFilePath(saveFile(file)); // 업로드 경로에 파일 저장

            // AttachedVO 객체를 데이터베이스에 저장 (첨부파일 생성)
            System.out.println(attachedVO);
            boardMapper.saveAttached(attachedVO);
        }

        return boardVO.getBid(); // 게시글 고유 식별자 반환
    }



    // 파일 실제 업로드 경로에 저장하는 로직
    // 'file' 객체에서 파일 이름 얻어와 경로를 생성하고 해당 경로에 파일을 저장한다.
    private String saveFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFileName);
        String fileName = LocalDateTime.now().toString().replace(":", "-") + "." + extension;
        String filePath = uploadPath + File.separator + fileName; // File.separator 사용하여 경로 구분자 설정
        File dest = new File(filePath);
        file.transferTo(dest);

        return filePath;
    }

    /* (Read) BoardServiceImpl
    // 검색 해당하는 게시글 갯수 구하기 (사용 o)
    @Override
    public int searchCountBoard(PageVO spv) throws Exception {
        System.out.println("BoardServiceImpl.searchCountBoard_검색한 게시글 갯수 : " + boardMapper.searchCountBoard(spv));
        return boardMapper.searchCountBoard(spv);
    }*/

    // 검색에 해당하는 게시글 리스트 조회하기 (사용 o)
    @Override
    public List<BoardVO> searchList(BoardListDTO dto) throws Exception {
        System.out.println("******************** 게시글 리스트 searchList 서비스 성공 :D ********************");
        System.out.println("가져오는 게시글 종류: " + dto.getType());
        return boardMapper.searchList(dto);
    }



    // 게시글 총 갯수 구하기
//    @Override
//    public int countBoard() throws Exception {
//        System.out.println("BoardServiceImpl.countBoard_게시글 총 갯수 : " + boardMapper.countBoard());
//        return boardMapper.countBoard();
//    }



    /* 게시글 조회하기 */
    @Override
    public List<BoardVO> selectBoard() throws Exception {
        System.out.println("BoardSereviceImpl.selectBoard_게시글 조회 : " + boardMapper.selectBoard());
        return boardMapper.selectBoard();
    }

    // 특정 게시글 상세보기
    /*@Override
    public BoardVO readDetail(BoardReadCountDTO boardReadCountDTO) throws Exception {
        // 로그인 한 사용자의 조회수만 카운팅
        if (boardReadCountDTO.getReaderUno() != null) {
            Integer result = boardMapper.createBoardRecordCountHistory(boardReadCountDTO); // 조회수 히스토리 처리 (insert: 1, update: 2)

            // 특정 게시글을 로그인 한 사용자가 처음 읽은 경우일 때 (result == 1 인 경우)
            if (result == 1) {
                Integer updatedRecordCount = boardMapper.updateViewCnt(boardReadCountDTO.getBoardBid()); // 조회수 증가
            }
        }

        return boardMapper.readDetail(boardReadCountDTO.getBoardBid());
    }
*/

    @Override
    public BoardVO readDetail(int bid) throws Exception {
        //boardMapper.updateViewCnt(bid); // 조회수 올리기

        return boardMapper.readDetail(bid);
    }

    // 조회수 올리기
    @Override
    public void updateViewCnt(int bid) throws Exception {
        boardMapper.updateViewCnt(bid);
    }


    /* (Update) */
    // 수정 페이지 불러오기
    @Override
    public BoardVO modify(int bid) throws Exception {
        System.out.println("서비스 bid: " + bid);
        return boardMapper.modify(bid);
    }


    // 게시글 및 첨부파일 수정하기
    @Override
    public void modifyUpdate(BoardModifyDTO boardModifyDTO) throws Exception {
        // 글 수정하기
        boardMapper.modifyUpdate(boardModifyDTO);
        System.out.println("BoardServiceImpl.modifyUpdate 수정 왜 안돼 악 " + boardModifyDTO);
        System.out.println("BoardServiceImpl.modifyUpdate 수정 서비스야 bid 좀 가져와줘.. " + boardModifyDTO.getBid());

        /* 첨부파일
        MultipartFile newFile = boardModifyDTO.getUploadFile();

        if (!newFile.isEmpty() && newFile != null) {// 업로드된 파일이 있는 경우
            UUID uid = UUID.randomUUID();
            String savedName = uid.toString() + "_" + newFile.getOriginalFilename();
            // savedName 은 유니크 네임
            newFile.transferTo(new File(uploadPath + filePath + savedName)); // 서버에 파일 저장

            // savedName 을 modifyDTO 에 넣어주기
            boardModifyDTO.setFileName(savedName);

            // new 첨부파일 추가
            boardMapper.addAttachedUpdate(boardModifyDTO);

         */
        }



    /* 수정 페이지 첨부파일 불러오기
    @Override
    public AttachedVO attached(int bid) throws Exception {
        return boardMapper.attached(bid);
    }

    // old 첨부파일 삭제
    @Override
    public AttachedVO deleteFile(String fileName) throws Exception {
        try {
            Path file = Paths.get(uploadPath + filePath + fileName);
            Files.deleteIfExists(file);
        } catch (Exception e) {
            System.out.println("deleteFile 에러발생 !!!!!!! :0 ");
        }
        return null;
    }
     */




    /* 글 삭제 (Delete) BoardDeleteServiceImpl */
    @Override
    public void delete(BoardDeleteDTO boardDeleteDTO) throws Exception {
        boardMapper.delete(boardDeleteDTO);
    }

}
