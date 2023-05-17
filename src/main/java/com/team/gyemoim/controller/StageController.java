package com.team.gyemoim.controller;

import com.team.gyemoim.dto.stage.ImportDTO;
import com.team.gyemoim.dto.stage.StageCreateDTO;
import com.team.gyemoim.dto.stage.StageParticipateDTO;
import com.team.gyemoim.service.StageCreateService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StageController {

    private final StageCreateService stageCreateService;


    //스테이지 생성하기
    @PostMapping(value ="/stageCreate")
    public void stageCreate(StageCreateDTO stageCreateDTO,StageParticipateDTO stageParticipateDTO,ImportDTO importDTO) throws Exception{
        System.out.println("[컨트롤러] 스테이지 생성 ");
        stageCreateService.stageCreate(stageCreateDTO);
        stageCreateService.stageParticipate(stageParticipateDTO);


    }
    //수령예정표 가져오기
    @GetMapping(value ="/stageCreate")

    public List<ImportDTO> importTableGet(@RequestParam BigDecimal pfRate){
        return stageCreateService.importGet(pfRate);
    }
    // 중복체크
    @PostMapping(value ="/checkPfName")
    public boolean checkPfName(@RequestParam("pfName") String pfName) {
         System.out.println("[컨트롤러] 중복체크 " + pfName);
         int count = stageCreateService.checkPfName(pfName);
        System.out.println("[컨트롤러] 중복체크 count " + count);
        return count>0;
    }



    // 참가자 테이블
    @PostMapping(value ="/stageAgree")
    public void stageCreate(StageParticipateDTO stageParticipateDTO) throws Exception{
        System.out.println("[컨트롤러] 스테이지 생성 ");
        stageCreateService.stageParticipate(stageParticipateDTO);
    }
    //스테이지 pfID 가져오기
    @GetMapping(value ="/stageAgree")
    public List<StageCreateDTO> stagePartIn1(@RequestParam("name") String pfName){
        return stageCreateService.stagePartIn1(pfName);
    }
    //스테이지 정보 가져오기
    @GetMapping(value ="/stageAgree2")
    public List<ImportDTO> stagePartIn2(@RequestParam("name") String pfName){
        return stageCreateService.stagePartIn2(pfName);
    }

    // U


    // D
}
