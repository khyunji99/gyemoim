package com.team.gyemoim.service;

import com.team.gyemoim.dto.board.*;
import com.team.gyemoim.vo.AttachedVO;
import com.team.gyemoim.vo.BoardVO;
import com.team.gyemoim.vo.PageVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {

    /* 게시글 생성 C */
    int writePost(BoardWriteDTO boardWrite, MultipartFile file) throws Exception; // 첨부파일 업로드 포함 게시글 작성하기
    /* 게시글 조회 R */
    List<BoardVO> searchList(BoardListDTO dto) throws Exception; // 검색 후 검색에 해당하는 게시글 리스트로 조회하기 (페이징 동시에 검색)
    //BoardVO readDetail(BoardReadCountDTO boardReadCountDTO) throws Exception; // 특정 게시글 상세보기(+ 조회수 올리기)
    BoardVO readDetail(int bid, boolean increaseViews) throws Exception; // 특정 게시글 상세보기
    //void updateViewCnt(int bid) throws Exception; // 조회수 올리기
    List<BoardVO> selectBoard() throws Exception; //게시글 조회하기

    /* 게시글 삭제 D */
    void delete(BoardDeleteDTO boardDeleteDTO) throws Exception; // 게시글 삭제하기

    /* 게시글 수정 U */
    // 수정페이지 불러오기
    BoardVO modify(int bid) throws Exception; // 수정 전 원래 글 정보 끌고오기
    // 게시글 수정하기
    void modifyUpdate(BoardModifyDTO boardModifyDTO) throws Exception; // 게시글 정보 수정하기


    /* 첨부파일 */
    // 첨부파일 상세보기
    AttachedVO getAttachedById(int attachedID) throws Exception;



}
