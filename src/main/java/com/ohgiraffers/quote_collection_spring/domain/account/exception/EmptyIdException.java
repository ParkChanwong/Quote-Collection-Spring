package com.ohgiraffers.quote_collection_spring.domain.account.exception;

public class EmptyIdException extends RuntimeException {
    public EmptyIdException(String message) {
        super(message);
    }

    public EmptyIdException() {
        super("아이디는 비어있을 수 없습니다.");
    }
}
