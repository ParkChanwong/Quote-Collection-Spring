package com.ohgiraffers.quote_collection_spring.service.quote;

import com.ohgiraffers.quote_collection_spring.dto.quote.QuoteResponseDTO;
import com.ohgiraffers.quote_collection_spring.entity.quote.QuoteEntity;
import com.ohgiraffers.quote_collection_spring.repository.quote.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuoteService {
    private static final Sort QUOTE_SORT = Sort.by("quote", "themeName", "personName");

    private final QuoteRepository quoteRepository;

    @Autowired
    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    private QuoteResponseDTO convertToDTO(QuoteEntity quoteEntity) {
        return new QuoteResponseDTO(quoteEntity.getId(), quoteEntity.getPerson().getName(), quoteEntity.getTheme().getName(), quoteEntity.getQuote());
    }

    // 전체 명언 조회
    public List<QuoteResponseDTO> findAllQuotes() {
        List<QuoteEntity> quotes = quoteRepository.findAll(QUOTE_SORT);

        return quotes.stream().map(this::convertToDTO).toList();
    }


}
