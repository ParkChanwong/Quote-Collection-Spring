package com.quotehunters.quotecollection.domain.mypage.bookmark.controller;

import com.quotehunters.quotecollection.domain.mypage.bookmark.dto.BookmarkDTO;
import com.quotehunters.quotecollection.domain.mypage.bookmark.entity.BookmarkEntity;
import com.quotehunters.quotecollection.domain.mypage.bookmark.service.BookmarkService;
import com.quotehunters.quotecollection.global.common.ResponseList;
import com.quotehunters.quotecollection.global.common.ResponseSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/mypage")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    // 내 전체 북마크
    @GetMapping("/bookmark")
    public ResponseEntity<ResponseList<BookmarkDTO>> findAllBookmarks(@AuthenticationPrincipal Jwt jwt) {
        int myId = Integer.parseInt(Objects.requireNonNull(jwt.getSubject()));
        List<BookmarkDTO> bookmarks = bookmarkService.findAllBookmarks(myId);

        return ResponseEntity.ok(new ResponseList<>(HttpStatus.OK.value(), bookmarks));
    }

    // 북마크 취소
    @DeleteMapping("/bookmark/{bookmarkId}")
    public ResponseEntity<ResponseSingle<String>> bookmarkCancel(@AuthenticationPrincipal Jwt jwt, @PathVariable int bookmarkId) {
        int myId = Integer.parseInt(Objects.requireNonNull(jwt.getSubject()));

        bookmarkService.bookmarkCancel(myId, bookmarkId);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "북마크가 취소되었습니다."));
    }
}
