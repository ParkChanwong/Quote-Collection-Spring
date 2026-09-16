package com.ohgiraffers.quote_collection_spring.repository.category;

import com.ohgiraffers.quote_collection_spring.entity.category.PeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository extends JpaRepository<PeriodEntity, Integer> {
}
