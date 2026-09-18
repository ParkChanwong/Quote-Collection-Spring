package com.quotehunters.quotecollection.domain.account.controller;

import com.quotehunters.quotecollection.domain.account.dto.AccountDTO;
import com.quotehunters.quotecollection.domain.account.service.AccountService;
import com.quotehunters.quotecollection.global.common.ResponseSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<ResponseSingle<String>> signIn(
            @RequestBody AccountDTO accountDTO,
            @RequestParam(defaultValue = "1") int auth
    ) {
        String token = accountService.signIn(accountDTO, auth);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), token));
    }

    @PostMapping("/signup/user")
    public ResponseEntity<ResponseSingle<String>> userSignUp(@RequestBody AccountDTO accountDTO) {
        accountService.userSignUp(accountDTO);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "회원가입 성공"));
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<ResponseSingle<String>> adminSignUp(@RequestBody AccountDTO accountDTO) {
        accountService.adminSignUp(accountDTO);

        return ResponseEntity.ok(new ResponseSingle<>(HttpStatus.OK.value(), "회원가입 성공"));
    }
}
