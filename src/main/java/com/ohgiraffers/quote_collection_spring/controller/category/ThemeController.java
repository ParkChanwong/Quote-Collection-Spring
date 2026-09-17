package com.ohgiraffers.quote_collection_spring.controller.category;

import com.ohgiraffers.quote_collection_spring.common.ResponseList;
import com.ohgiraffers.quote_collection_spring.common.ResponseSingle;
import com.ohgiraffers.quote_collection_spring.dto.category.ThemeDTO;
import com.ohgiraffers.quote_collection_spring.service.category.ThemeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theme")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    // 전체 주제 조회
    @GetMapping
    public ResponseEntity<ResponseList> findAllThemes() {
        List<ThemeDTO> themes = themeService.findAllThemes();

        ResponseList responseList = new ResponseList(
                HttpStatus.OK.value(),
                themes
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    // 주제명으로 주제 조회
    @GetMapping("/name")
    public ResponseEntity<ResponseList<ThemeDTO>> findAllThemesByName(@RequestParam String keyword) {
        List<ThemeDTO> themes = themeService.findAllThemesByName(keyword);

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), themes));
    }

    // 주제 ID 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSingle> findThemeById(@PathVariable int id) {
        ThemeDTO theme = themeService.findThemeById(id);

        ResponseSingle responseSingle = new ResponseSingle(
                HttpStatus.OK.value(),
                theme
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseSingle);
    }

    // 주제 등록
    @PostMapping
    public ResponseEntity<ResponseSingle> saveTheme(@RequestBody ThemeDTO theme) {
        themeService.saveTheme(theme);

        ResponseSingle responseSingle = new ResponseSingle(
                HttpStatus.OK.value(),
                "주제 등록 성공"
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseSingle);
    }

    // 주제 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseSingle> modifyTheme(@PathVariable int id, @RequestBody ThemeDTO theme) {
        themeService.modifyTheme(id, theme);

        ResponseSingle responseSingle = new ResponseSingle(
                HttpStatus.OK.value(),
                "주제 수정 성공"
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseSingle);
    }

    // 주제 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseSingle> deleteTheme(@PathVariable int id) {
        themeService.deleteTheme(id);

        ResponseSingle responseSingle = new ResponseSingle(
                HttpStatus.OK.value(),
                "주제 삭제 성공"
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseSingle);
    }
}
