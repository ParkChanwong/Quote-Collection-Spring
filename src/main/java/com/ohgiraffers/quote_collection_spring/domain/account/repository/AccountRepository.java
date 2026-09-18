package com.ohgiraffers.quote_collection_spring.domain.account.repository;

import com.ohgiraffers.quote_collection_spring.domain.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    // id 찾기
    Optional<AccountEntity> findByUserId(String userId);
}
