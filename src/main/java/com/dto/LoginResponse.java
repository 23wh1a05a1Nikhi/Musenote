package com.dto;

public class LoginResponse {
    private String token;
    private String userName;

    public LoginResponse(String token, String userName) {
        this.token = token;
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public String getUserName() {
        return userName;
    }
}