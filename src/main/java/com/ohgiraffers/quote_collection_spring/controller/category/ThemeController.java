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
    public ResponseEntity<ResponseList<ThemeDTO>> findAllThemes() {
        List<ThemeDTO> themes = themeService.findAllThemes();

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), themes));
    }

    // 주제명으로 주제 조회
    @GetMapping("/name")
    public ResponseEntity<ResponseList<ThemeDTO>> findAllThemesByName(@RequestParam String keyword) {
        List<ThemeDTO> themes = themeService.findAllThemesByName(keyword.trim());

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), themes));
    }

    // 주제 ID 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSingle<ThemeDTO>> findThemeById(@PathVariable int id) {
        ThemeDTO theme = themeService.findThemeById(id);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), theme));
    }

    // 주제 등록
    @PostMapping
    public ResponseEntity<ResponseSingle<String>> saveTheme(@RequestBody ThemeDTO theme) {
        themeService.saveTheme(theme);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "주제 등록 성공"));
    }

    // 주제 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseSingle<String>> modifyTheme(@PathVariable int id, @RequestBody ThemeDTO theme) {
        themeService.modifyTheme(id, theme);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "주제 수정 성공"));
    }

    // 주제 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseSingle<String>> deleteTheme(@PathVariable int id) {
        themeService.deleteTheme(id);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "주제 삭제 성공"));
    }
}
