package org.example.studynest.controller;

import jakarta.validation.Valid;
import org.example.studynest.dto.request.LoginUserDTO;
import org.example.studynest.dto.request.RegisterUserDTO;
import org.example.studynest.dto.response.UserResponseDTO;
import org.example.studynest.security.JwtService;
import org.example.studynest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(
            @Valid @RequestBody RegisterUserDTO dto
    ) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginUserDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(request.getEmail());

        return Map.of("accessToken", token);
    }
}


