package com.ohgiraffers.quote_collection_spring.repository.category;

import com.ohgiraffers.quote_collection_spring.entity.category.ThemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeRepository extends JpaRepository<ThemeEntity, Integer> {
    // 주제명 조회
    List<ThemeEntity> findByNameContaining(String name);

    // 중복 체크
    boolean existsByName(String name);
}
