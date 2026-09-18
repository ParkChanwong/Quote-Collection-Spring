package com.ohgiraffers.quote_collection_spring.global.exception.quote;

public class NotFoundQuoteException extends RuntimeException {
    public NotFoundQuoteException(String message) {
        super(message);
    }

    public NotFoundQuoteException() {
        super("존재하지 않은 명언입니다.");
    }
}
