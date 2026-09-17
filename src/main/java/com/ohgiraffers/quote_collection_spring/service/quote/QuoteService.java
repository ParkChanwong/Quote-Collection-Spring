package com.ohgiraffers.quote_collection_spring.service.quote;

import com.ohgiraffers.quote_collection_spring.dto.person.PersonRequestDTO;
import com.ohgiraffers.quote_collection_spring.dto.quote.QuoteRequestDTO;
import com.ohgiraffers.quote_collection_spring.dto.quote.QuoteResponseDTO;
import com.ohgiraffers.quote_collection_spring.entity.category.ThemeEntity;
import com.ohgiraffers.quote_collection_spring.entity.person.PersonEntity;
import com.ohgiraffers.quote_collection_spring.entity.quote.QuoteEntity;
import com.ohgiraffers.quote_collection_spring.exception.category.theme.NotFoundThemeException;
import com.ohgiraffers.quote_collection_spring.exception.person.NotFoundPersonException;
import com.ohgiraffers.quote_collection_spring.exception.quote.EmptyQuoteException;
import com.ohgiraffers.quote_collection_spring.repository.category.ThemeRepository;
import com.ohgiraffers.quote_collection_spring.repository.person.PersonRepository;
import com.ohgiraffers.quote_collection_spring.repository.quote.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {
    private static final Sort QUOTE_SORT = Sort.by("quote", "themeName", "personName");

    private final QuoteRepository quoteRepository;
    private final PersonRepository personRepository;
    private final ThemeRepository themeRepository;

    @Autowired
    public QuoteService(
            QuoteRepository quoteRepository,
            PersonRepository personRepository,
            ThemeRepository themeRepository
    ) {
        this.quoteRepository = quoteRepository;
        this.personRepository = personRepository;
        this.themeRepository = themeRepository;
    }

    private QuoteResponseDTO convertToDTO(QuoteEntity quoteEntity) {
        return new QuoteResponseDTO(
                quoteEntity.getId(),
                quoteEntity.getPerson().getName(),
                quoteEntity.getTheme().getName(),
                quoteEntity.getQuote()
        );
    }

    private PersonEntity findPersonOrThrow(int personId) {
        return personRepository
                .findById(personId)
                .orElseThrow(NotFoundPersonException::new);
    }

    private ThemeEntity findThemeOrThrow(int themeId) {
        return themeRepository
                .findById(themeId)
                .orElseThrow(NotFoundThemeException::new);
    }

    private QuoteEntity convertToEntity(
            QuoteRequestDTO quoteRequestDTO,
            PersonEntity personEntity,
            ThemeEntity themeEntity
    ) {
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setPerson(personEntity);
        quoteEntity.setTheme(themeEntity);
        quoteEntity.setQuote(quoteRequestDTO.getQuote());

        return quoteEntity;
    }

    // 전체 명언 조회
    public List<QuoteResponseDTO> findAllQuotes() {
        List<QuoteEntity> quotes = quoteRepository.findAll(QUOTE_SORT);

        return quotes.stream().map(this::convertToDTO).toList();
    }

    // 인물명으로 명언 조회
    public List<QuoteResponseDTO> findAllQuotesByPersonName(String personName) {
        List<QuoteEntity> quotes = quoteRepository.findAllQuotesByPersonNameContaining(personName, QUOTE_SORT);

        return quotes.stream().map(this::convertToDTO).toList();
    }

    // 주제명으로 명언 조회
    public List<QuoteResponseDTO> findAllQuotesByThemeName(String themeName) {
        List<QuoteEntity> quotes = quoteRepository.findAllQuotesByThemeNameContaining(themeName, QUOTE_SORT);

        return quotes.stream().map(this::convertToDTO).toList();
    }

    // 키워드로 명언 조회
    public List<QuoteResponseDTO> findAllQuotesByKeyword(String keyword) {
        List<QuoteEntity> quotes = quoteRepository.findAllQuotesByQuoteContaining(keyword, QUOTE_SORT);

        return quotes.stream().map(this::convertToDTO).toList();
    }

    // 명언 등록
    public void saveQuote(QuoteRequestDTO quoteDTO) {
        PersonEntity personEntity = findPersonOrThrow(quoteDTO.getPersonId());
        ThemeEntity themeEntity = findThemeOrThrow(quoteDTO.getThemeId());

        if (quoteDTO.getQuote() == null || quoteDTO.getQuote().isBlank()) {
            throw new EmptyQuoteException();
        }

        quoteRepository.save(convertToEntity(quoteDTO, personEntity, themeEntity));
    }
}
