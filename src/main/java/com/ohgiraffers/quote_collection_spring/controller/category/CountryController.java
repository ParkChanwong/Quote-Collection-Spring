package com.ohgiraffers.quote_collection_spring.controller.category;

import com.ohgiraffers.quote_collection_spring.common.ResponseList;
import com.ohgiraffers.quote_collection_spring.common.ResponseSingle;
import com.ohgiraffers.quote_collection_spring.dto.category.CountryDTO;
import com.ohgiraffers.quote_collection_spring.service.category.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    // 전체 국가 조회
    @GetMapping
    public ResponseEntity<ResponseList> findAllCountries() {
        List<CountryDTO> countries = countryService.findAllCountries();

        ResponseList responseList = new ResponseList(
                HttpStatus.OK.value(),
                countries
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    // 국가 id 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSingle> findCountryById(@PathVariable int id){
        CountryDTO countryDTO = countryService.findCountryById(id);

        ResponseSingle responseSingle = new ResponseSingle(
                HttpStatus.OK.value(),
                countryDTO
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseSingle);
    }

    // 국가 등록
    @PostMapping
    public ResponseEntity<ResponseSingle> updateCountry(@RequestBody CountryDTO countryDTO){
        countryService.saveCountry(countryDTO);

        ResponseSingle responseSingle = new ResponseSingle(
                HttpStatus.OK.value(),
                "국가 등록 성공"
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseSingle);
    }

    // 국가 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseSingle> updateCountry(@PathVariable int id, @RequestBody CountryDTO countryDTO){
        countryService.modifyCountry(id, countryDTO);

        ResponseSingle responseSingle = new ResponseSingle(
                HttpStatus.OK.value(),
                "국가 수정 성공"
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseSingle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseSingle> deleteCountry(@PathVariable int id){
        countryService.deleteCountry(id);

        ResponseSingle responseSingle = new ResponseSingle(
                HttpStatus.OK.value(),
                "국가 삭제 성공"
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseSingle);
    }
}
