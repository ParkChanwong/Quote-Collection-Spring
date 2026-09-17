package com.ohgiraffers.quote_collection_spring.controller.category;

import com.ohgiraffers.quote_collection_spring.common.ResponseList;
import com.ohgiraffers.quote_collection_spring.common.ResponseSingle;
import com.ohgiraffers.quote_collection_spring.dto.category.FieldDTO;
import com.ohgiraffers.quote_collection_spring.service.category.FieldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/field")
public class FieldController {
    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    // 전체 분야 조회
    @GetMapping
    public ResponseEntity<ResponseList<FieldDTO>> findAllFields() {
        List<FieldDTO> fields = fieldService.findAllFields();

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), fields));
    }

    // 분야명으로 분야 조회
    @GetMapping("/name")
    public ResponseEntity<ResponseList<FieldDTO>> findAllFieldsByName(@RequestParam String keyword) {
        List<FieldDTO> fields = fieldService.findAllFieldsByName(keyword.trim());

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), fields));
    }

    // 분야 ID 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSingle<FieldDTO>> findFieldById(@PathVariable int id) {
        FieldDTO fieldDTO = fieldService.findFieldById(id);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), fieldDTO));
    }

    // 분야 등록
    @PostMapping
    public ResponseEntity<ResponseSingle<String>> saveField(@RequestBody FieldDTO fieldDTO) {
        fieldService.saveField(fieldDTO);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "분야 등록 성공"));
    }

    // 분야 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseSingle<String>> modifyField(@PathVariable int id, @RequestBody FieldDTO fieldDTO) {
        fieldService.modifyField(id, fieldDTO);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "분야 수정 성공"));
    }

    // 분야 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseSingle<String>> deleteField(@PathVariable int id) {
        fieldService.deleteField(id);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "분야 삭제 성공"));
    }
}
