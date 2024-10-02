package io.hhplus.cleancode.presentation.controller;


import io.hhplus.cleancode.domain.dto.SugangDto;
import io.hhplus.cleancode.domain.service.SugangService;
import io.hhplus.cleancode.presentation.HttpDto.SugangRequest;
import io.hhplus.cleancode.presentation.HttpDto.SugangResponse;
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

    @GetMapping("/insert/{studentId}/{sugangId}/{availNum}/{sugangName}/{classDate}")
    public String insert(@PathVariable("studentId") Long studentId,
                        @PathVariable("sugangId") Long sugangId,
                         @PathVariable("availNum") Long availNum,
                        @PathVariable("sugangName") String sugangName,
                        @PathVariable("classDate") String classDate) {
        sugangService.insert(new SugangDto(sugangId,studentId,availNum,classDate,sugangName));
        return null;
    }

    @PostMapping("/apply")
    public SugangResponse apply(@RequestBody SugangRequest sugangRequest) {

        sugangService.apply(new SugangDto(sugangRequest.getSugangId(),sugangRequest.getStudentId(),0,sugangRequest.getClassDate(),null));

        return new SugangResponse("success");
    }

    @GetMapping("/getClassAvail/{classDate}")
    public List<SugangResponse> getClassAvail(@PathVariable("classDate") String classDate) {
        List<SugangDto> sugangInsertDtoList = sugangService.getClassAvail(new SugangDto(0L,0L,0L,classDate,""));


        return  sugangInsertDtoList.stream()
                .map(item -> {
                    SugangResponse sugangResponse = new SugangResponse(
                            item.getSugangId(),
                            item.getStudentId(),
                            item.getAvailNum(),
                            item.getClassDate(),
                            item.getClassName(),
                            "success"
                    );

                    return sugangResponse;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/getUserClassApply/{studentId}")
    public List<SugangResponse> getUserClassApply(@PathVariable("studentId") Long studentId) {
        List<SugangDto> sugangInsertDtoList = sugangService.getClassApplyHistory(new SugangDto(0L,studentId,0L,"",""));
        return sugangInsertDtoList.stream()
                .map(item -> {
                    SugangResponse sugangResponse = new SugangResponse(
                            item.getSugangId(),
                            item.getStudentId(),
                            item.getAvailNum(),
                            item.getClassDate(),
                            item.getClassName(),
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
