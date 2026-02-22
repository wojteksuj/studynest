package org.example.studynest.controller;

import jakarta.validation.Valid;
import org.example.studynest.dto.request.RegisterUserDTO;
import org.example.studynest.dto.response.UserInfoResponseDTO;
import org.example.studynest.dto.response.UserResponseDTO;
import org.example.studynest.security.CustomUserDetails;
import org.example.studynest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserResponseDTO getUserByEmail(@RequestParam String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/username")
    public UserInfoResponseDTO getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.getUserInfo(userDetails.getId());
    }
}
