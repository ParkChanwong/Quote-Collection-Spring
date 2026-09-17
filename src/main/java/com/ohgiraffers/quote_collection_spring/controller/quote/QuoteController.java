package com.ohgiraffers.quote_collection_spring.controller.quote;

import com.ohgiraffers.quote_collection_spring.common.ResponseList;
import com.ohgiraffers.quote_collection_spring.dto.quote.QuoteResponseDTO;
import com.ohgiraffers.quote_collection_spring.service.quote.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quote")
public class QuoteController {
    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping
    public ResponseEntity<ResponseList<QuoteResponseDTO>> findAllQuotes() {
        List<QuoteResponseDTO> quotes = quoteService.findAllQuotes();

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), quotes));
    }
}
