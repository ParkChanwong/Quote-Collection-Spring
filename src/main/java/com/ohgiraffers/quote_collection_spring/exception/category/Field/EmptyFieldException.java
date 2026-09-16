package com.ohgiraffers.quote_collection_spring.exception.category.Field;

public class EmptyFieldException extends RuntimeException {
    public EmptyFieldException(String message) {
        super(message);
    }

    public EmptyFieldException() {
        super("분야명은 비어있을 수 없습니다.");
    }
}
