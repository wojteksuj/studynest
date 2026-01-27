package org.example.studynest.controller;

import jakarta.validation.Valid;
import org.example.studynest.dto.request.CreateUserRequestDTO;
import org.example.studynest.dto.response.UserResponseDTO;
import org.example.studynest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studynest/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/by-email")
    public UserResponseDTO getUserByEmail(@RequestParam String email) {
        return userService.getByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(
            @Valid @RequestBody CreateUserRequestDTO dto
    ) {
        return userService.create(dto);
    }
}
