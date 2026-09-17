package com.ohgiraffers.quote_collection_spring.repository.quote;

import com.ohgiraffers.quote_collection_spring.entity.quote.QuoteEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<QuoteEntity, Integer> {
    // 인물명 조회
    List<QuoteEntity> findAllQuotesByPersonNameContaining(String personName, Sort sort);
}
