package com.ohgiraffers.quote_collection_spring.domain.category.controller;

import com.ohgiraffers.quote_collection_spring.global.common.ResponseList;
import com.ohgiraffers.quote_collection_spring.global.common.ResponseSingle;
import com.ohgiraffers.quote_collection_spring.domain.category.dto.PeriodDTO;
import com.ohgiraffers.quote_collection_spring.domain.category.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/period")
public class PeriodController {
    private final PeriodService periodService;

    @Autowired
    public PeriodController(PeriodService periodService) {
        this.periodService = periodService;
    }

    // 전체 시대 조회
    @GetMapping
    public ResponseEntity<ResponseList<PeriodDTO>> findAllPeriods() {
        List<PeriodDTO> periods = periodService.findAllPeriods();

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), periods));
    }

    // 시대명으로 시대 조회
    @GetMapping("/name")
    public ResponseEntity<ResponseList<PeriodDTO>> findPeriodByName(@RequestParam String keyword) {
        List<PeriodDTO> periods = periodService.findAllPeriodsByName(keyword.trim());

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), periods));
    }

    // 시대 ID 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSingle<PeriodDTO>> findPeriodById(@PathVariable int id) {
        PeriodDTO periodDTO = periodService.findPeriodById(id);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), periodDTO));
    }

    // 시대 등록
    @PostMapping
    public ResponseEntity<ResponseSingle<String>> createPeriod(@RequestBody PeriodDTO periodDTO) {
        periodService.savePeriod(periodDTO);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "시대 등록 성공"));
    }

    // 시대 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseSingle<String>> updatePeriod(@PathVariable int id, @RequestBody PeriodDTO periodDTO) {
        periodService.modifyPeriod(id, periodDTO);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "시대 수정 성공"));
    }

    // 시대 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseSingle<String>> deletePeriod(@PathVariable int id) {
        periodService.deletePeriod(id);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "시대 삭제 성공"));
    }
}
