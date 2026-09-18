package com.quotehunters.quotecollection.domain.quote.service;

import com.quotehunters.quotecollection.domain.quote.dto.QuoteRequestDTO;
import com.quotehunters.quotecollection.domain.quote.dto.QuoteResponseDTO;
import com.quotehunters.quotecollection.domain.category.entity.ThemeEntity;
import com.quotehunters.quotecollection.domain.person.entity.PersonEntity;
import com.quotehunters.quotecollection.domain.quote.entity.QuoteEntity;
import com.quotehunters.quotecollection.domain.category.exception.theme.NotFoundThemeException;
import com.quotehunters.quotecollection.domain.person.exception.NotFoundPersonException;
import com.quotehunters.quotecollection.domain.quote.exception.DuplicateQuoteException;
import com.quotehunters.quotecollection.domain.quote.exception.EmptyQuoteException;
import com.quotehunters.quotecollection.domain.quote.exception.NotFoundQuoteException;
import com.quotehunters.quotecollection.domain.category.repository.ThemeRepository;
import com.quotehunters.quotecollection.domain.person.repository.PersonRepository;
import com.quotehunters.quotecollection.domain.quote.repository.QuoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
            String content,
            PersonEntity personEntity,
            ThemeEntity themeEntity
    ) {
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setPerson(personEntity);
        quoteEntity.setTheme(themeEntity);
        quoteEntity.setQuote(content);

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

    // 명언 ID 단일 조회
    public QuoteResponseDTO findQuoteById(int quoteId) {
        QuoteEntity quoteEntity = quoteRepository.
                findById(quoteId).
                orElseThrow(NotFoundQuoteException::new);

        return convertToDTO(quoteEntity);
    }

    // 명언 등록
    @Transactional
    public void saveQuote(QuoteRequestDTO quoteDTO) {
        PersonEntity personEntity = findPersonOrThrow(quoteDTO.getPersonId());
        ThemeEntity themeEntity = findThemeOrThrow(quoteDTO.getThemeId());

        if (quoteDTO.getQuote() == null || quoteDTO.getQuote().isBlank()) {
            throw new EmptyQuoteException();
        }
        String content = quoteDTO.getQuote().trim();
        if (
            quoteRepository.existsByPersonIdAndThemeIdAndQuote(
                personEntity.getId(),
                themeEntity.getId(),
                content
            )
        ) {
            throw new DuplicateQuoteException();
        }

        quoteRepository.save(convertToEntity(content, personEntity, themeEntity));
    }

    // 명언 수정
    @Transactional
    public void modifyQuote(int id, QuoteRequestDTO quoteDTO) {
        QuoteEntity quote = quoteRepository.findById(id).orElseThrow(NotFoundQuoteException::new);

        PersonEntity person = findPersonOrThrow(quoteDTO.getPersonId());
        ThemeEntity theme = findThemeOrThrow(quoteDTO.getThemeId());

        if (quoteDTO.getQuote() == null || quoteDTO.getQuote().isBlank()) {
            throw new EmptyQuoteException();
        }
        String content = quoteDTO.getQuote().trim();
        if (
            quoteRepository.existsByPersonIdAndThemeIdAndQuote(
                person.getId(),
                theme.getId(),
                content
            )
        ) {
            throw new DuplicateQuoteException();
        }

        quote.setPerson(person);
        quote.setTheme(theme);
        quote.setQuote(content);
    }

    // 명언 삭제
    @Transactional
    public void deleteQuote(int id) {
        QuoteEntity quote = quoteRepository.findById(id).orElseThrow(NotFoundQuoteException::new);

        quoteRepository.delete(quote);
    }
}
