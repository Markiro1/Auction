package com.ashapiro.auction.controller;

import com.ashapiro.auction.dto.contactUs.ContactUsDto;
import com.ashapiro.auction.dto.user.UpdateUserProfileDto;
import com.ashapiro.auction.dto.user.UserProfileDto;
import com.ashapiro.auction.service.HomeService;
import com.ashapiro.auction.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "home/home";
    }

    @GetMapping("/user")
    public String userHome() {
        return "home/home-user";
    }

    @GetMapping("/admin")
    public String adminHome() {
        return "home/home-admin";
    }

    @GetMapping("/contact-us")
    public String contactUs(Model model) {
        model.addAttribute("contactForm", new ContactUsDto());
        return "home/contact-us";
    }

    @PostMapping("/contact-us/send")
    public String sendContactUs(@ModelAttribute("contactForm") ContactUsDto contactUsDto) {
        homeService.saveContactUs(contactUsDto);
        return "home/contact-us";
    }

    @GetMapping("/user/profile/{userId}")
    public String profilePage(@PathVariable Long userId, Model model) {
        UserProfileDto userProfile = userService.getUserProfileByUserId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("profile", userProfile);
        model.addAttribute("updateProfile", new UpdateUserProfileDto());
        return "home/profile";
    }

    @PutMapping("/user/profile/save/{userId}")
    public String saveProfile(@PathVariable Long userId, UpdateUserProfileDto updateUserProfileDto) {
        homeService.updateProfile(userId, updateUserProfileDto);
        return "redirect:/user/profile/" + userId;
    }

}
