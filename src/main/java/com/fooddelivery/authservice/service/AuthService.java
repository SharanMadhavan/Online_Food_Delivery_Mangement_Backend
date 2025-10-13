package com.fooddelivery.authservice.service;

import com.fooddelivery.authservice.dto.SignupRequest;
import com.fooddelivery.authservice.dto.LoginRequest;
import com.fooddelivery.authservice.dto.AuthResponse;
import com.fooddelivery.authservice.dto.UserResponse;

public interface AuthService {
    UserResponse signup(SignupRequest request);
    AuthResponse login(LoginRequest request);
    UserResponse getCurrentUser(String token);
}
