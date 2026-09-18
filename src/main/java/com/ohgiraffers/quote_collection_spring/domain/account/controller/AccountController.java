package com.ohgiraffers.quote_collection_spring.domain.account.controller;

import com.ohgiraffers.quote_collection_spring.domain.account.dto.AccountDTO;
import com.ohgiraffers.quote_collection_spring.domain.account.dto.TokenDTO;
import com.ohgiraffers.quote_collection_spring.domain.account.service.AccountService;
import com.ohgiraffers.quote_collection_spring.global.common.ResponseSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseSingle<String>> signIn(@RequestBody AccountDTO accountDTO) {
        String token = accountService.signIn(accountDTO);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), token));
    }

    @PostMapping("/signup/user")
    public ResponseEntity<ResponseSingle<String>> userSignUp(@RequestBody AccountDTO accountDTO) {
        accountService.userSignUp(accountDTO);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "회원가입 성공"));
    }
}
