package com.quotehunters.quotecollection.domain.account.service;

import com.quotehunters.quotecollection.domain.account.dto.AccountDTO;
import com.quotehunters.quotecollection.domain.account.entity.AccountEntity;
import com.quotehunters.quotecollection.domain.account.exception.DuplicateIdException;
import com.quotehunters.quotecollection.domain.account.exception.EmptyIdException;
import com.quotehunters.quotecollection.domain.account.exception.EmptyPasswordException;
import com.quotehunters.quotecollection.domain.account.exception.SignInFailedException;
import com.quotehunters.quotecollection.domain.account.repository.AccountRepository;
import com.quotehunters.quotecollection.global.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        } else if (accountRepository.existsByUserIdAndAuth(accountDTO.getUserId(), 1)) {
            throw new DuplicateIdException();
        } else if (accountDTO.getUserPw() == null || accountDTO.getUserPw().isBlank()) {
            throw new EmptyPasswordException();
        }

        AccountEntity account = convertToEntity(accountDTO);
        account.setAuth(1);

        accountRepository.save(account);
    }

    public void adminSignUp(AccountDTO accountDTO) {
        if (accountDTO.getUserId() == null || accountDTO.getUserId().isBlank()) {
            throw new EmptyIdException();
        } else if (accountRepository.existsByUserIdAndAuth(accountDTO.getUserId(), 0)) {
            throw new DuplicateIdException();
        } else if (accountDTO.getUserPw() == null || accountDTO.getUserPw().isBlank()) {
            throw new EmptyPasswordException();
        }

        AccountEntity account = convertToEntity(accountDTO);
        account.setAuth(0);

        accountRepository.save(account);
    }
}
