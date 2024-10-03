package io.hhplus.cleancode.presentation.controller;


import io.hhplus.cleancode.domain.dto.SugangDto;
import io.hhplus.cleancode.domain.service.SugangService;
import io.hhplus.cleancode.presentation.HttpDto.SugangHistoryResponse;
import io.hhplus.cleancode.presentation.HttpDto.SugangRequest;
import io.hhplus.cleancode.presentation.HttpDto.SugangScheduleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/sugang")
public class SugangController {

    @Autowired
    SugangService sugangService;


    @PostMapping("/apply")
    public String apply(@RequestBody SugangRequest sugangRequest) {

        String result = sugangService.apply(new SugangDto(sugangRequest.getSugangId(),sugangRequest.getStudentId(),0,sugangRequest.getClassDate(),null,null));

        return result;
    }

    @GetMapping("/getClassAvail/{classDate}")
    public List<SugangScheduleResponse> getClassAvail(@PathVariable("classDate") String classDate) {
        List<SugangDto> sugangInsertDtoList = sugangService.getClassAvail(new SugangDto(0L,0L,0L,classDate,null,null));


        return  sugangInsertDtoList.stream()
                .map(item -> {
                    SugangScheduleResponse sugangResponse = new SugangScheduleResponse(
                            item.getSugangId(),
                            item.getStudentId(),
                            item.getAvailNum(),
                            item.getClassDate(),
                            item.getClassName(),
                            item.getTeacher(),
                            "success"
                    );

                    return sugangResponse;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/getUserClassApply/{studentId}")
    public List<SugangHistoryResponse> getUserClassApply(@PathVariable("studentId") Long studentId) {
        List<SugangDto> sugangInsertDtoList = sugangService.getClassApplyHistory(new SugangDto(0L,studentId,0L,null,null,null));
        return sugangInsertDtoList.stream()
                .map(item -> {
                    SugangHistoryResponse sugangResponse = new SugangHistoryResponse(
                            item.getSugangId(),
                            item.getStudentId(),
                            item.getAvailNum(),
                            item.getClassDate(),
                            item.getClassName(),
                            item.getTeacher(),
                            "success"
                    );

                    return sugangResponse;
                })
                .collect(Collectors.toList());
    }

    //특강 신청 : studentId , 강의id , 날짜 (스트링8자)
    //특강신청완료 : 조회 : 특정 studentId => json 배열로 리턴 (날짜,특강id,특강명)
    //특강신청가능 : 조회 : 날짜별로 현재 신청가능 특강 목록 => json 배열로 (날짜,특강id,특강명) 


}
