package com.ohgiraffers.quote_collection_spring.domain.account.service;

import com.ohgiraffers.quote_collection_spring.domain.account.dto.AccountDTO;
import com.ohgiraffers.quote_collection_spring.domain.account.entity.AccountEntity;
import com.ohgiraffers.quote_collection_spring.domain.account.exception.DuplicateIdException;
import com.ohgiraffers.quote_collection_spring.domain.account.exception.EmptyIdException;
import com.ohgiraffers.quote_collection_spring.domain.account.exception.EmptyPasswordException;
import com.ohgiraffers.quote_collection_spring.domain.account.exception.SignInFailedException;
import com.ohgiraffers.quote_collection_spring.domain.account.repository.AccountRepository;
import com.ohgiraffers.quote_collection_spring.global.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private AccountEntity convertToEntity(AccountDTO dto) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(dto.getUserId());
        accountEntity.setUserPw(passwordEncoder.encode(dto.getUserPw()));

        return accountEntity;
    }

    public String signIn(AccountDTO accountDTO) {
        AccountEntity account = accountRepository
                .findByUserId(accountDTO.getUserId())
                .orElseThrow(() -> new SignInFailedException("아이디가 틀렸습니다."));

        if (accountDTO.getUserId() == null || accountDTO.getUserId().isBlank()) {
            throw new EmptyIdException();
        } else if (accountDTO.getUserPw() == null || accountDTO.getUserPw().isBlank()) {
            throw new EmptyPasswordException();
        }

        if (!passwordEncoder.matches(accountDTO.getUserPw(), account.getUserPw())) {
            throw new SignInFailedException("비밀번호가 틀렸습니다.");
        }

        String role = switch (account.getAuth()) {
            case 0 -> "ADMIN";
            case 1 -> "USER";
            default -> null;
        };

        return jwtService.createAccessToken(account.getId(), role);
    }

    public void userSignUp(AccountDTO accountDTO) {
        if (accountDTO.getUserId() == null || accountDTO.getUserId().isBlank()) {
            throw new EmptyIdException();
        } else if (accountRepository.existsByUserId(accountDTO.getUserId())) {
            throw new DuplicateIdException();
        } else if (accountDTO.getUserPw() == null || accountDTO.getUserPw().isBlank()) {
            throw new EmptyPasswordException();
        }

        AccountEntity account = convertToEntity(accountDTO);
        account.setAuth(1);

        accountRepository.save(account);
    }
}
