package com.quotehunters.quotecollection.domain.quote.repository;

import com.quotehunters.quotecollection.domain.quote.entity.QuoteEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<QuoteEntity, Integer> {
    // 인물명 조회
    List<QuoteEntity> findAllQuotesByPersonNameContaining(String personName, Sort sort);

    // 주제명 조회
    List<QuoteEntity> findAllQuotesByThemeNameContaining(String themeName, Sort sort);

    // 키워드 조회
    List<QuoteEntity> findAllQuotesByQuoteContaining(String content, Sort sort);

    // 중복 체크
    boolean existsByPersonIdAndThemeIdAndQuote(int personId, int themeId, String quote);
}
