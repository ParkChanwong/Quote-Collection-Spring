package com.ohgiraffers.quote_collection_spring.repository.category;

import com.ohgiraffers.quote_collection_spring.entity.category.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {
    boolean existsByName(String name);
}
