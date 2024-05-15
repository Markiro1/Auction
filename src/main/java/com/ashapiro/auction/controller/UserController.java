package com.ashapiro.auction.controller;

import com.ashapiro.auction.dto.user.AddUserDto;
import com.ashapiro.auction.dto.user.UserDto;
import com.ashapiro.auction.dto.user.UserRegistrationDto;
import com.ashapiro.auction.exceptions.validation.RegisterValidationException;
import com.ashapiro.auction.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public String signUp(@Valid UserRegistrationDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", new RegisterValidationException().getMessage());
            return "redirect:/auth/register";
        }
        userService.save(userDto);
        return "redirect:/auth/login";
    }

    @GetMapping("/admin/add")
    public String addUserPage(@ModelAttribute("message") String message, Model model) {
        model.addAttribute("userForm", new AddUserDto());
        if (message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "user/addUser";
    }

    @PostMapping("/admin/add")
    public String addUser(AddUserDto addUserDto, RedirectAttributes redirectAttributes) {
        String message = userService.saveByAdmin(addUserDto);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users/admin/add";
    }

    @GetMapping("/admin/all")
    public String getAll(@ModelAttribute("message") String message, Model model) {
        List<UserDto> users = userService.getAll();
        model.addAttribute("users", users);
        if (message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "user/allUsers";
    }

    @DeleteMapping("/admin/delete/{userId}")
    public String getInfo(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        String message = userService.deleteUserById(userId);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users/admin/all";
    }
}
