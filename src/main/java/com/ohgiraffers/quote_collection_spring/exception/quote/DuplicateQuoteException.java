package com.ohgiraffers.quote_collection_spring.exception.quote;

public class DuplicateQuoteException extends RuntimeException {
    public DuplicateQuoteException(String message) {
        super(message);
    }

    public DuplicateQuoteException() {
        super("이미 등록된 명언입니다.");
    }
}
