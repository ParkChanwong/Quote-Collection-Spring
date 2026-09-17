package com.ohgiraffers.quote_collection_spring.controller.person;

import com.ohgiraffers.quote_collection_spring.common.ResponseList;
import com.ohgiraffers.quote_collection_spring.common.ResponseSingle;
import com.ohgiraffers.quote_collection_spring.dto.person.PersonRequestDTO;
import com.ohgiraffers.quote_collection_spring.dto.person.PersonResponseDTO;
import com.ohgiraffers.quote_collection_spring.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // 전체 인물 조회
    @GetMapping
    public ResponseEntity<ResponseList<PersonResponseDTO>> findAllPersons() {
        List<PersonResponseDTO> persons = personService.findAllPersons();

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), persons));
    }

    // 국가명으로 인물 조회
    @GetMapping("/country")
    public ResponseEntity<ResponseList<PersonResponseDTO>> findPersonsByCountry(@RequestParam String keyword) {
        List<PersonResponseDTO> persons = personService.findAllPersonsByCountryName(keyword.trim());

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), persons));
    }

    // 시대명으로 인물 조회
    @GetMapping("/period")
    public ResponseEntity<ResponseList<PersonResponseDTO>> findPersonsByPeriod(@RequestParam String keyword) {
        List<PersonResponseDTO> persons = personService.findAllPersonsByPeriodName(keyword.trim());

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), persons));
    }

    // 분야명으로 인물 조회
    @GetMapping("/field")
    public ResponseEntity<ResponseList<PersonResponseDTO>> findPersonsByField(@RequestParam String keyword) {
        List<PersonResponseDTO> persons = personService.findAllPersonsByFieldName(keyword.trim());

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), persons));
    }

    // 인물명으로 인물 조회
    @GetMapping("/name")
    public ResponseEntity<ResponseList<PersonResponseDTO>> findPersonsByName(@RequestParam String keyword) {
        List<PersonResponseDTO> persons = personService.findAllPersonsByName(keyword);

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), persons));
    }

    // 인물 ID 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSingle<PersonResponseDTO>> findPersonById(@PathVariable int id) {
        PersonResponseDTO person = personService.findPersonById(id);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), person));
    }

    // 인물 등록
    @PostMapping
    public ResponseEntity<ResponseSingle<String>> savePerson(@RequestBody PersonRequestDTO person) {
        personService.savePerson(person);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "인물 등록 성공"));
    }

    // 인물 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseSingle<String>> updatePerson(
            @PathVariable int id,
            @RequestBody PersonRequestDTO person
    ) {
        personService.modifyPerson(id, person);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "인물 수정 성공"));
    }

    // 인물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseSingle<String>> deletePerson(@PathVariable int id) {
        personService.deletePerson(id);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "인물 삭제 성공"));
    }
}
