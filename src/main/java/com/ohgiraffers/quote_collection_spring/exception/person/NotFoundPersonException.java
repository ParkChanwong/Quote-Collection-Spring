package com.ohgiraffers.quote_collection_spring.exception.person;

public class NotFoundPersonException extends RuntimeException {
    public NotFoundPersonException(String message) {
        super(message);
    }

    public NotFoundPersonException() {
        super("존재하지 않은 인물입니다.");
    }
}
