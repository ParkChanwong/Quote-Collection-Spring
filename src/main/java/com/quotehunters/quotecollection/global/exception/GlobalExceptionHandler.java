package com.quotehunters.quotecollection.global.exception;

import com.quotehunters.quotecollection.domain.account.exception.*;
import com.quotehunters.quotecollection.domain.mypage.bookmark.exception.NotFoundBookmarkException;
import com.quotehunters.quotecollection.global.common.ResponseError;
import com.quotehunters.quotecollection.domain.category.exception.field.DuplicateFieldException;
import com.quotehunters.quotecollection.domain.category.exception.field.EmptyFieldException;
import com.quotehunters.quotecollection.domain.category.exception.field.NotFoundFieldException;
import com.quotehunters.quotecollection.domain.category.exception.country.DuplicateCountryException;
import com.quotehunters.quotecollection.domain.category.exception.country.EmptyCountryException;
import com.quotehunters.quotecollection.domain.category.exception.country.NotFoundCountryException;
import com.quotehunters.quotecollection.domain.category.exception.period.DuplicatePeriodException;
import com.quotehunters.quotecollection.domain.category.exception.period.EmptyPeriodException;
import com.quotehunters.quotecollection.domain.category.exception.period.NotFoundPeriodException;
import com.quotehunters.quotecollection.domain.category.exception.theme.DuplicateThemeException;
import com.quotehunters.quotecollection.domain.category.exception.theme.EmptyThemeException;
import com.quotehunters.quotecollection.domain.category.exception.theme.NotFoundThemeException;
import com.quotehunters.quotecollection.domain.person.exception.EmptyPersonException;
import com.quotehunters.quotecollection.domain.person.exception.NotFoundPersonException;
import com.quotehunters.quotecollection.domain.quote.exception.DuplicateQuoteException;
import com.quotehunters.quotecollection.domain.quote.exception.EmptyQuoteException;
import com.quotehunters.quotecollection.domain.quote.exception.NotFoundQuoteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // 존재하지 않음
    @ExceptionHandler({
            NotFoundCountryException.class,
            NotFoundPeriodException.class,
            NotFoundFieldException.class,
            NotFoundThemeException.class,
            NotFoundPersonException.class,
            NotFoundQuoteException.class,
            NotFoundBookmarkException.class,
            NotFoundUserException.class
    })
    public ResponseEntity<ResponseError> notFoundException(Exception e) {
        ResponseError responseError = new ResponseError(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );

        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

    // 필수값을 빈값으로 등록
    @ExceptionHandler({
            EmptyCountryException.class,
            EmptyPeriodException.class,
            EmptyFieldException.class,
            EmptyThemeException.class,
            EmptyPersonException.class,
            EmptyQuoteException.class,
            EmptyIdException.class,
            EmptyPasswordException.class
    })
    public ResponseEntity<ResponseError> emptyException(Exception e) {
        ResponseError responseError = new ResponseError(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    // 중복 등록
    @ExceptionHandler({
            DuplicateCountryException.class,
            DuplicatePeriodException.class,
            DuplicateFieldException.class,
            DuplicateThemeException.class,
            DuplicateQuoteException.class,
            DuplicateIdException.class
    })
    public ResponseEntity<ResponseError> duplicateException(Exception e) {
        ResponseError responseError = new ResponseError(
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        );

        return new ResponseEntity<>(responseError, HttpStatus.CONFLICT);
    }

    // 로그인 실패
    @ExceptionHandler(SignInFailedException.class)
    public ResponseEntity<ResponseError> loginFailed(Exception e) {
        ResponseError responseError = new ResponseError(
                HttpStatus.UNAUTHORIZED.value(),
                e.getMessage()
        );

        return new ResponseEntity<>(responseError, HttpStatus.UNAUTHORIZED);
    }

    // 잡지 못한 문제 통합
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleException(Exception e){
        ResponseError responseError = new ResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
        );

        return new ResponseEntity<>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
