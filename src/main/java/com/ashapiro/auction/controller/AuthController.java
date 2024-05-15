package com.ashapiro.auction.controller;

import com.ashapiro.auction.dto.jwt.JwtRequestDto;
import com.ashapiro.auction.dto.jwt.JwtResponseDto;
import com.ashapiro.auction.dto.user.UserLoginDto;
import com.ashapiro.auction.dto.user.UserRegistrationDto;
import com.ashapiro.auction.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new UserLoginDto());
        return "auth/login-form";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(response, request);
        return "redirect:/logo";
    }

    @PostMapping("/login")
    public String signIn(@ModelAttribute("user") JwtRequestDto jwtRequestDto, HttpServletResponse httpServletResponse) {
        ResponseEntity<?> response = authService.createAuthToken(jwtRequestDto);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            JwtResponseDto jwtResponseDto = (JwtResponseDto) response.getBody();
            String token = jwtResponseDto.token();
            List<String> roles = authService.getRoles(token);
            addCookie("token", token, httpServletResponse);
            if (roles.contains("ROLE_USER")) {
                return "redirect:/logo/user";
            } else {
                return "redirect:/logo/admin";
            }
        } else {
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/register")
    public String registrationForm(@ModelAttribute("error") String message, Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        if (message != null || !message.isEmpty()) {
            model.addAttribute("errorMessage", message);
        }
        return "auth/register-form";
    }

    private void addCookie(String name, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
