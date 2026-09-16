package com.ohgiraffers.quote_collection_spring.exception.category.country;

public class NotFoundCountryException extends RuntimeException {
    public NotFoundCountryException(String message) {
        super(message);
    }

    public NotFoundCountryException() {
        super("존재하지 않은 국가입니다.");
    }
}
