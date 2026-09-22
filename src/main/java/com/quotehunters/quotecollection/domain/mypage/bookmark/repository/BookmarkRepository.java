package com.quotehunters.quotecollection.domain.mypage.bookmark.repository;

import com.quotehunters.quotecollection.domain.mypage.bookmark.entity.BookmarkEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Integer> {
    boolean existsByAccountIdAndQuoteId(int accountId, int quoteId);

    // 내 모든 북마크 가져오기
    List<BookmarkEntity> findAllByAccountId(int userId, Sort sort);

    Optional<BookmarkEntity> findByIdAndAccountId(int bookmarkId, int userId);
}
