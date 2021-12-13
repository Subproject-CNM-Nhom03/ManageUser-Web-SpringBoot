package com.example.chat.service;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
}
