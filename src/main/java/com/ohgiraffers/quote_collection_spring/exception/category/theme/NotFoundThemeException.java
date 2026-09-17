package com.ohgiraffers.quote_collection_spring.exception.category.theme;

public class NotFoundThemeException extends RuntimeException {
    public NotFoundThemeException(String message) {
        super(message);
    }

    public NotFoundThemeException() {
        super("존재하지 않은 주제입니다.");
    }
}
