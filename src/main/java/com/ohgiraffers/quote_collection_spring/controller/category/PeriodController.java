package com.ohgiraffers.quote_collection_spring.controller.category;

import com.ohgiraffers.quote_collection_spring.common.ResponseList;
import com.ohgiraffers.quote_collection_spring.dto.category.PeriodDTO;
import com.ohgiraffers.quote_collection_spring.service.category.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/period")
public class PeriodController {
    private PeriodService periodService;

    @Autowired
    public PeriodController(PeriodService periodService) {
        this.periodService = periodService;
    }

    // 전체 시대 조회
    @GetMapping
    public ResponseEntity<ResponseList> findAllPeriods() {
        List<PeriodDTO> periods = periodService.findAllPeriods();

        ResponseList responseList = new ResponseList(
                HttpStatus.OK.value(),
                periods
        );

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
