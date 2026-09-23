package com.quotehunters.quotecollection.domain.quote.controller;

import com.quotehunters.quotecollection.global.common.ResponseList;
import com.quotehunters.quotecollection.global.common.ResponsePage;
import com.quotehunters.quotecollection.global.common.ResponseSingle;
import com.quotehunters.quotecollection.domain.quote.dto.QuoteRequestDTO;
import com.quotehunters.quotecollection.domain.quote.dto.QuoteResponseDTO;
import com.quotehunters.quotecollection.domain.quote.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quote")
public class QuoteController {
    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    // 전체 명언 조회
    @GetMapping(params = "!page")
    public ResponseEntity<ResponseList<QuoteResponseDTO>> findAllQuotes() {
        List<QuoteResponseDTO> quotes = quoteService.findAllQuotes();

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), quotes));
    }

    // 전체 명언 조회 - 페이지네이션
    @GetMapping(params = "page")
    public ResponseEntity<ResponsePage<QuoteResponseDTO>> findAllQuotesByPage(
            @PageableDefault(
                    size = 10,
                    sort = {"quote", "themeName", "personName"}
            ) Pageable pageable
    ) {
        Page<QuoteResponseDTO> quotes = quoteService.findAllQuotesByPage(pageable);

        return ResponseEntity.ok(new ResponsePage<>(HttpStatus.OK.value(), quotes.getContent(), quotes.getTotalElements(), quotes.getTotalPages()));
    }


    // 인물명으로 명언 조회
    @GetMapping("/person")
    public ResponseEntity<ResponseList<QuoteResponseDTO>> findQuotesByPerson(@RequestParam String keyword) {
        List<QuoteResponseDTO> quotes = quoteService.findAllQuotesByPersonName(keyword.trim());

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), quotes));
    }

    // 주제명으로 명언 조회
    @GetMapping("/theme")
    public ResponseEntity<ResponseList<QuoteResponseDTO>> findQuotesByTheme(@RequestParam String keyword) {
        List<QuoteResponseDTO> quotes = quoteService.findAllQuotesByThemeName(keyword.trim());

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), quotes));
    }

    // 키워드로 명언 조회
    @GetMapping("/content")
    public ResponseEntity<ResponseList<QuoteResponseDTO>> findQuotesByKeyword(@RequestParam String keyword) {
        List<QuoteResponseDTO> quotes = quoteService.findAllQuotesByKeyword(keyword.trim());

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), quotes));
    }

    // 명언 ID 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSingle<QuoteResponseDTO>> findQuoteById(@PathVariable int id) {
        QuoteResponseDTO quote = quoteService.findQuoteById(id);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), quote));
    }

    // 주제, 인물명 + 키워드 조회
    @GetMapping("/search")
    public ResponseEntity<ResponsePage<QuoteResponseDTO>> searchQuotes(
        @RequestParam String theme,
        @RequestParam String keyword,
        @PageableDefault(
                size = 10,
                sort = {"quote", "person.name", "theme.name", "id"}
        ) Pageable pageable
    ) {
        Page<QuoteResponseDTO> quotes =
                quoteService.searchQuotes(theme.trim(), keyword.trim(), pageable);

        return ResponseEntity.ok(
                new ResponsePage<>(
                        HttpStatus.OK.value(),
                        quotes.getContent(),
                        quotes.getTotalElements(),
                        quotes.getTotalPages()
                )
        );
    }

    // 명언 등록
    @PostMapping
    public ResponseEntity<ResponseSingle<String>> saveQuote(@RequestBody QuoteRequestDTO quoteRequestDTO) {
        quoteService.saveQuote(quoteRequestDTO);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "명언 등록 성공"));
    }

    // 명언 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseSingle<String>> modifyQuote(@PathVariable int id, @RequestBody QuoteRequestDTO quoteRequestDTO) {
        quoteService.modifyQuote(id, quoteRequestDTO);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "명언 수정 성공"));
    }

    // 명언 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseSingle<String>> deleteQuote(@PathVariable int id) {
        quoteService.deleteQuote(id);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "명언 삭제 성공"));
    }
}
