package com.ohgiraffers.quote_collection_spring.repository.quote;

import com.ohgiraffers.quote_collection_spring.entity.quote.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<QuoteEntity, Integer> {
}
