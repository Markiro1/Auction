package com.ashapiro.auction.service.impl;

import com.ashapiro.auction.dto.jwt.JwtRequestDto;
import com.ashapiro.auction.dto.jwt.JwtResponseDto;
import com.ashapiro.auction.exceptions.auth.ErrorResponse;
import com.ashapiro.auction.service.AuthService;
import com.ashapiro.auction.userDetails.UserDetailsImpl;
import com.ashapiro.auction.utils.JwtTokenUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public ResponseEntity<?> createAuthToken(JwtRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password()
                    ));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenUtils.generateToken((UserDetailsImpl) userDetails);
            return ResponseEntity.ok(new JwtResponseDto(token));
        } catch (BadCredentialsException e) {
            return new ResponseEntity(new ErrorResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid email/password"), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public List<String> getRoles(String token) {
        return jwtTokenUtils.getRoles(token);
    }

    @Override
    public void logout(HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
    }
}
