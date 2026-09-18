package com.quotehunters.quotecollection.domain.mypage.bookmark.service;

import com.quotehunters.quotecollection.domain.mypage.bookmark.dto.BookmarkDTO;
import com.quotehunters.quotecollection.domain.mypage.bookmark.entity.BookmarkEntity;
import com.quotehunters.quotecollection.domain.mypage.bookmark.exception.NotFoundBookmarkException;
import com.quotehunters.quotecollection.domain.mypage.bookmark.repository.BookmarkRepository;
import com.quotehunters.quotecollection.domain.person.entity.PersonEntity;
import com.quotehunters.quotecollection.domain.person.exception.NotFoundPersonException;
import com.quotehunters.quotecollection.domain.person.repository.PersonRepository;
import com.quotehunters.quotecollection.domain.quote.entity.QuoteEntity;
import com.quotehunters.quotecollection.domain.quote.exception.NotFoundQuoteException;
import com.quotehunters.quotecollection.domain.quote.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkService {
    private static final Sort BOOKMARK_SORT = Sort.by("quote");

    private final BookmarkRepository bookmarkRepository;
    private final QuoteRepository quoteRepository;
    private final PersonRepository personRepository;

    @Autowired
    private BookmarkService(
            BookmarkRepository bookmarkRepository,
            QuoteRepository quoteRepository,
            PersonRepository personRepository
    ) {
        this.bookmarkRepository = bookmarkRepository;
        this.quoteRepository = quoteRepository;
        this.personRepository = personRepository;
    }

    private PersonEntity findPersonOrThrow(int personId) {
        return personRepository.findById(personId).orElseThrow(NotFoundPersonException::new);
    }

    private QuoteEntity findQuoteOrThrow(int quoteId) {
        return quoteRepository.findById(quoteId).orElseThrow(NotFoundQuoteException::new);
    }

    private BookmarkDTO convertToDTO(BookmarkEntity bookmarkEntity) {
        return new BookmarkDTO(
                bookmarkEntity.getId(),
                bookmarkEntity.getQuote().getPerson().getName(),
                bookmarkEntity.getQuote().getQuote()
        );
    }

    // 내 전체 북마크
    public List<BookmarkDTO> findAllBookmarks(int memberId) {
        List<BookmarkEntity> bookmarks = bookmarkRepository.findAllByAccountId(memberId, BOOKMARK_SORT);

        return bookmarks.stream().map(this::convertToDTO).toList();
    }

    // 북마크 취소
    public void bookmarkCancel(int memberId, int bookmarkId) {
        List<BookmarkEntity> bookmarks = bookmarkRepository.findAllByAccountId(memberId, BOOKMARK_SORT);

        BookmarkEntity bookmark = bookmarks.stream().filter(b -> b.getId() == bookmarkId)
                .findFirst()
                .orElseThrow(NotFoundBookmarkException::new);

        bookmarkRepository.delete(bookmark);
    }
}
