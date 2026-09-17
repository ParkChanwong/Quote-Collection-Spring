package com.ohgiraffers.quote_collection_spring.repository.category;

import com.ohgiraffers.quote_collection_spring.entity.category.CountryEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {
    // 국가명 조회
    List<CountryEntity> findByNameContaining(String name, Sort sort);

    // 동일 국가 체크
    boolean existsByName(String name);
}
