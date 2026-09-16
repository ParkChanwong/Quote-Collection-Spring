package com.ohgiraffers.quote_collection_spring.controller.category;

import com.ohgiraffers.quote_collection_spring.common.ResponseList;
import com.ohgiraffers.quote_collection_spring.common.ResponseSingle;
import com.ohgiraffers.quote_collection_spring.dto.category.CountryDTO;
import com.ohgiraffers.quote_collection_spring.service.category.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    // 전체 국가 조회
    @GetMapping
    public ResponseEntity<ResponseList> findAllCountries() {
        List<CountryDTO> contries = countryService.findAllCountries();

        ResponseList responseList = new ResponseList(
                HttpStatus.OK.value(),
                contries
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSingle> findCountryById(@PathVariable int id){
        CountryDTO countryDTO = countryService.findCountryById(id);

        ResponseSingle responseSingle = new ResponseSingle(
                HttpStatus.OK.value(),
                countryDTO
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseSingle);
    }
}
