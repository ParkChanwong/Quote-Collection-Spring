package com.ohgiraffers.quote_collection_spring.controller.category;

import com.ohgiraffers.quote_collection_spring.common.ResponseList;
import com.ohgiraffers.quote_collection_spring.common.ResponseSingle;
import com.ohgiraffers.quote_collection_spring.dto.category.PeriodDTO;
import com.ohgiraffers.quote_collection_spring.service.category.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 시대 ID 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSingle> findPeriodById(@PathVariable int id) {
        PeriodDTO periodDTO = periodService.findPeriodById(id);

        ResponseSingle responseSingle = new ResponseSingle(
                HttpStatus.OK.value(),
                periodDTO
        );

        return new ResponseEntity<>(responseSingle, HttpStatus.OK);
    }

    // 시대 등록
    @PostMapping
    public ResponseEntity<ResponseSingle> createPeriod(@RequestBody PeriodDTO periodDTO) {
        periodService.savePeriod(periodDTO);

        ResponseSingle responseSingle = new ResponseSingle(
                HttpStatus.OK.value(),
                "시대 등록 성공"
        );

        return new ResponseEntity<>(responseSingle, HttpStatus.OK);
    }
}
