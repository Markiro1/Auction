package com.ashapiro.auction.service;

import com.ashapiro.auction.dto.jwt.JwtRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthService {

    ResponseEntity<?> createAuthToken(JwtRequestDto request);

    List<String> getRoles(String token);

    void logout(HttpServletResponse response, HttpServletRequest request);
}
