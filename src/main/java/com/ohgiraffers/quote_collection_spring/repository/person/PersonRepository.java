package com.ohgiraffers.quote_collection_spring.repository.person;

import com.ohgiraffers.quote_collection_spring.entity.person.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {
    // 국가명으로 인물 조회
    List<PersonEntity> findByCountryNameContaining(String countryName);

    // 시대명으로 인물 조회
    List<PersonEntity> findByPeriodNameContaining(String periodName);

    // 분야명으로 인물 조회
    List<PersonEntity> findByFieldNameContaining(String fieldName);

    // 인물명으로 인물 조회
    List<PersonEntity> findByNameContaining(String name);
}
