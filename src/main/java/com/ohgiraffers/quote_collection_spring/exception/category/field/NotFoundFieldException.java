package com.ohgiraffers.quote_collection_spring.exception.category.field;

public class NotFoundFieldException extends RuntimeException {
    public NotFoundFieldException(String message) {
        super(message);
    }

    public NotFoundFieldException() {
        super("존재하지 않는 분야입니다.");
    }
}
