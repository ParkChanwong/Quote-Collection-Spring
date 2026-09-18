package com.ohgiraffers.quote_collection_spring.global.exception;

import com.ohgiraffers.quote_collection_spring.domain.account.exception.DuplicateIdException;
import com.ohgiraffers.quote_collection_spring.domain.account.exception.EmptyIdException;
import com.ohgiraffers.quote_collection_spring.domain.account.exception.EmptyPasswordException;
import com.ohgiraffers.quote_collection_spring.domain.account.exception.SignInFailedException;
import com.ohgiraffers.quote_collection_spring.global.common.ResponseError;
import com.ohgiraffers.quote_collection_spring.domain.category.exception.field.DuplicateFieldException;
import com.ohgiraffers.quote_collection_spring.domain.category.exception.field.EmptyFieldException;
import com.ohgiraffers.quote_collection_spring.domain.category.exception.field.NotFoundFieldException;
import com.ohgiraffers.quote_collection_spring.domain.category.exception.country.DuplicateCountryException;
import com.ohgiraffers.quote_collection_spring.domain.category.exception.country.EmptyCountryException;
import com.ohgiraffers.quote_collection_spring.domain.category.exception.country.NotFoundCountryException;
import com.ohgiraffers.quote_collection_spring.domain.category.exception.period.DuplicatePeriodException;
import com.ohgiraffers.quote_collection_spring.domain.category.exception.period.EmptyPeriodException;
import com.ohgiraffers.quote_collection_spring.domain.category.exception.period.NotFoundPeriodException;
import com.ohgiraffers.quote_collection_spring.domain.category.exception.theme.DuplicateThemeException;
import com.ohgiraffers.quote_collection_spring.domain.category.exception.theme.EmptyThemeException;
import com.ohgiraffers.quote_collection_spring.domain.category.exception.theme.NotFoundThemeException;
import com.ohgiraffers.quote_collection_spring.domain.person.exception.EmptyPersonException;
import com.ohgiraffers.quote_collection_spring.domain.person.exception.NotFoundPersonException;
import com.ohgiraffers.quote_collection_spring.domain.quote.exception.DuplicateQuoteException;
import com.ohgiraffers.quote_collection_spring.domain.quote.exception.EmptyQuoteException;
import com.ohgiraffers.quote_collection_spring.domain.quote.exception.NotFoundQuoteException;
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
            NotFoundQuoteException.class
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
