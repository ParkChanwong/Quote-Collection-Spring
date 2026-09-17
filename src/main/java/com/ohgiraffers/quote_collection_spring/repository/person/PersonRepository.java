package com.ohgiraffers.quote_collection_spring.repository.person;

import com.ohgiraffers.quote_collection_spring.entity.person.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {
}
