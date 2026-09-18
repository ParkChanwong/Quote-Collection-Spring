package com.ohgiraffers.quote_collection_spring.global.exception.quote;

public class EmptyQuoteException extends RuntimeException {
    public EmptyQuoteException(String message) {
        super(message);
    }

    public EmptyQuoteException() {
        super("명언은 비어있을 수 없습니다.");
    }
}
