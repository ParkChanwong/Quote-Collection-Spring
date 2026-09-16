package com.ohgiraffers.quote_collection_spring.exception;

import com.ohgiraffers.quote_collection_spring.common.ResponseError;
import com.ohgiraffers.quote_collection_spring.exception.category.country.DuplicateCountryException;
import com.ohgiraffers.quote_collection_spring.exception.category.country.EmptyCountryException;
import com.ohgiraffers.quote_collection_spring.exception.category.country.NotFoundCountryException;
import com.ohgiraffers.quote_collection_spring.exception.category.period.DuplicatePeriodException;
import com.ohgiraffers.quote_collection_spring.exception.category.period.EmptyPeriodException;
import com.ohgiraffers.quote_collection_spring.exception.category.period.NotFoundPeriodException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    // 존재하지 않음
    @ExceptionHandler({
            NotFoundCountryException.class,
            NotFoundPeriodException.class
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
            EmptyPeriodException.class
    })
    public ResponseEntity<ResponseError> emptyCountry(Exception e) {
        ResponseError responseError = new ResponseError(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    // 중복 등록
    @ExceptionHandler({
            DuplicateCountryException.class,
            DuplicatePeriodException.class
    })
    public ResponseEntity<ResponseError> duplicateCountry(DuplicateCountryException e) {
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
