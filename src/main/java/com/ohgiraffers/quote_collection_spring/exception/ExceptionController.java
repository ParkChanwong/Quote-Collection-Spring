package com.ohgiraffers.quote_collection_spring.exception;

import com.ohgiraffers.quote_collection_spring.common.ResponseError;
import com.ohgiraffers.quote_collection_spring.exception.category.field.DuplicateFieldException;
import com.ohgiraffers.quote_collection_spring.exception.category.field.EmptyFieldException;
import com.ohgiraffers.quote_collection_spring.exception.category.field.NotFoundFieldException;
import com.ohgiraffers.quote_collection_spring.exception.category.country.DuplicateCountryException;
import com.ohgiraffers.quote_collection_spring.exception.category.country.EmptyCountryException;
import com.ohgiraffers.quote_collection_spring.exception.category.country.NotFoundCountryException;
import com.ohgiraffers.quote_collection_spring.exception.category.period.DuplicatePeriodException;
import com.ohgiraffers.quote_collection_spring.exception.category.period.EmptyPeriodException;
import com.ohgiraffers.quote_collection_spring.exception.category.period.NotFoundPeriodException;
import com.ohgiraffers.quote_collection_spring.exception.category.theme.DuplicateThemeException;
import com.ohgiraffers.quote_collection_spring.exception.category.theme.EmptyThemeException;
import com.ohgiraffers.quote_collection_spring.exception.category.theme.NotFoundThemeException;
import com.ohgiraffers.quote_collection_spring.exception.person.EmptyPersonException;
import com.ohgiraffers.quote_collection_spring.exception.person.NotFoundPersonException;
import com.ohgiraffers.quote_collection_spring.exception.quote.DuplicateQuoteException;
import com.ohgiraffers.quote_collection_spring.exception.quote.EmptyQuoteException;
import com.ohgiraffers.quote_collection_spring.exception.quote.NotFoundQuoteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
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
            EmptyQuoteException.class
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
            DuplicateQuoteException.class
    })
    public ResponseEntity<ResponseError> duplicateException(Exception e) {
        ResponseError responseError = new ResponseError(
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        );

        return new ResponseEntity<>(responseError, HttpStatus.CONFLICT);
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
