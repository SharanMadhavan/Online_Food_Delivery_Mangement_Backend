package com.fooddelivery.authservice.service;

import com.fooddelivery.authservice.dto.SignupRequest;
import com.fooddelivery.authservice.dto.LoginRequest;
import com.fooddelivery.authservice.dto.AuthResponse;
import com.fooddelivery.authservice.dto.UserResponse;
import com.fooddelivery.authservice.entity.User;
import com.fooddelivery.authservice.repository.UserRepository;
import com.fooddelivery.authservice.exception.AuthException;
import com.fooddelivery.authservice.config.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserResponse signup(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AuthException("Email already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getRole());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            throw new AuthException("Invalid email or password");
        }
        User user = userOpt.get();
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token, user.getEmail(), user.getName(), user.getRole());
    }

    @Override
    public UserResponse getCurrentUser(String token) {
        String email = jwtUtil.extractUsername(token);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AuthException("User not found"));
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
