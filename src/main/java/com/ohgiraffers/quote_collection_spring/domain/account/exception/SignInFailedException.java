package com.ohgiraffers.quote_collection_spring.domain.account.exception;

public class SignInFailedException extends RuntimeException {
    public SignInFailedException(String message) {
        super(message);
    }
}
