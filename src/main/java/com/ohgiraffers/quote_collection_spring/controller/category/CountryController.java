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
    public ResponseEntity<ResponseList<CountryDTO>> findAllCountries() {
        List<CountryDTO> countries = countryService.findAllCountries();

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), countries));
    }

    // 국가명으로 국가 조회
    @GetMapping("/name")
    public ResponseEntity<ResponseList<CountryDTO>> findAllCountriesByName(@RequestParam String keyword) {
        List<CountryDTO> countries = countryService.findAllCountriesByName(keyword.trim());

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), countries));
    }

    // 국가 id 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSingle<CountryDTO>> findCountryById(@PathVariable int id){
        CountryDTO countryDTO = countryService.findCountryById(id);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), countryDTO));
    }

    // 국가 등록
    @PostMapping
    public ResponseEntity<ResponseSingle<String>> updateCountry(@RequestBody CountryDTO countryDTO){
        countryService.saveCountry(countryDTO);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "국가 등록 성공"));
    }

    // 국가 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseSingle<String>> updateCountry(@PathVariable int id, @RequestBody CountryDTO countryDTO){
        countryService.modifyCountry(id, countryDTO);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "국가 수정 완료"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseSingle<String>> deleteCountry(@PathVariable int id){
        countryService.deleteCountry(id);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "국가 삭제 완료"));
    }
}
