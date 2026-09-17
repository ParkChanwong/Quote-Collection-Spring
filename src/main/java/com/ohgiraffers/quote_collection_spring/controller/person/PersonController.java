package com.ohgiraffers.quote_collection_spring.controller.person;

import com.ohgiraffers.quote_collection_spring.common.ResponseList;
import com.ohgiraffers.quote_collection_spring.dto.person.PersonResponseDTO;
import com.ohgiraffers.quote_collection_spring.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<ResponseList> findAllPersons() {
        List<PersonResponseDTO> persons = personService.findAllPersons();

        ResponseList<PersonResponseDTO> responseList = new ResponseList<>(
                HttpStatus.OK.value(),
                persons
        );

        return ResponseEntity.ok(responseList);
    }
}
