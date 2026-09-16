package com.ohgiraffers.quote_collection_spring.repository.category;

import com.ohgiraffers.quote_collection_spring.entity.category.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<FieldEntity, Integer> {
    boolean existsByName(String name);
}
