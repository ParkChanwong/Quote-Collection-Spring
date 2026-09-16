package com.ohgiraffers.quote_collection_spring.exception.category.country;

import com.ohgiraffers.quote_collection_spring.common.ResponseError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)  // 일반 ExceptionController보다 먼저 체크하도록
@ControllerAdvice
public class CountryExceptionController {
    // 존재하지 않는 국가 id 조회 시
    @ExceptionHandler(NotFoundCountryException.class)
    public ResponseEntity<ResponseError> notFoundCountry(NotFoundCountryException e) {
        ResponseError responseError = new ResponseError(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );

        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

    // 빈 국가명 등록 시
    @ExceptionHandler(EmptyCountryException.class)
    public ResponseEntity<ResponseError> emptyCountry(EmptyCountryException e) {
        ResponseError responseError = new ResponseError(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    // 중복되는 국가명 등록 시
    @ExceptionHandler(DuplicateCountryException.class)
    public ResponseEntity<ResponseError> duplicateCountry(DuplicateCountryException e) {
        ResponseError responseError = new ResponseError(
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        );

        return new ResponseEntity<>(responseError, HttpStatus.CONFLICT);
    }
}
