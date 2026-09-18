package com.ohgiraffers.quote_collection_spring.domain.account.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "quote_user")
public class AccountEntity {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_pw")
    private String userPw;

    @Column(name = "user_auth")
    private int auth;

    public AccountEntity() {}

    public AccountEntity(int id, String userId, String userPw, int auth) {
        this.id = id;
        this.userId = userId;
        this.userPw = userPw;
        this.auth = auth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userPw='" + userPw + '\'' +
                ", auth='" + auth + '\'' +
                '}';
    }
}
