package com.quotehunters.quotecollection.domain.category.controller;

import com.quotehunters.quotecollection.global.common.ResponseList;
import com.quotehunters.quotecollection.global.common.ResponsePage;
import com.quotehunters.quotecollection.global.common.ResponseSingle;
import com.quotehunters.quotecollection.domain.category.dto.ThemeDTO;
import com.quotehunters.quotecollection.domain.category.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theme")
public class ThemeController {
    private final ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    // 전체 주제 조회
    @GetMapping(params = "!page")
    public ResponseEntity<ResponseList<ThemeDTO>> findAllThemes() {
        List<ThemeDTO> themes = themeService.findAllThemes();

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), themes));
    }

    // 전체 분야 조회 - 페이지네이션
    @GetMapping(params = "page")
    public ResponseEntity<ResponsePage<ThemeDTO>> findAllThemesByPage(
            @PageableDefault(
                    size = 10,
                    sort = {"name"}
            )
            Pageable pageable
    ) {
        Page<ThemeDTO> themes = themeService.findAllThemesByPage(pageable);

        return ResponseEntity.ok(new ResponsePage<>(
                HttpStatus.OK.value(),
                themes.getContent(),
                themes.getTotalElements(),
                themes.getTotalPages()
        ));
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
