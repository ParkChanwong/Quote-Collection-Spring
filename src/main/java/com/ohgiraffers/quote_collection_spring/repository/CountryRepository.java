package com.ohgiraffers.quote_collection_spring.repository;

import com.ohgiraffers.quote_collection_spring.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
