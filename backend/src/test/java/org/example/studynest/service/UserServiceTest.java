package org.example.studynest.service;

import org.example.studynest.config.PasswordEncoderConfig;
import org.example.studynest.dto.request.RegisterUserDTO;
import org.example.studynest.dto.response.UserInfoResponseDTO;
import org.example.studynest.dto.response.UserResponseDTO;
import org.example.studynest.entity.User;
import org.example.studynest.exception.DuplicateFieldsException;
import org.example.studynest.exception.UsernameNotFoundByEmailException;
import org.example.studynest.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoderConfig passwordEncoderConfig;

    @InjectMocks
    private UserService userService;

    private RegisterUserDTO buildRegisterDTO(String username, String email, String password) {
        RegisterUserDTO dto = new RegisterUserDTO();
        dto.setUsername(username);
        dto.setEmail(email);
        dto.setPassword(password);
        return dto;
    }

    @Test
    void register_savesUserAndReturnsDTO() {
        RegisterUserDTO dto = buildRegisterDTO("alice", "alice@example.com", "Secret1");

        when(userRepository.existsByUsername("alice")).thenReturn(false);
        when(userRepository.existsByEmail("alice@example.com")).thenReturn(false);
        when(passwordEncoderConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        UserResponseDTO result = userService.register(dto);

        assertEquals("alice", result.getUsername());
        assertEquals("alice@example.com", result.getEmail());
    }

    @Test
    void register_throwsDuplicateFieldsException_whenUsernameExists() {
        RegisterUserDTO dto = buildRegisterDTO("alice", "alice@example.com", "Secret1");

        when(userRepository.existsByUsername("alice")).thenReturn(true);

        DuplicateFieldsException ex = assertThrows(DuplicateFieldsException.class,
                () -> userService.register(dto));
        assertEquals("username", ex.getField());
    }

    @Test
    void register_throwsDuplicateFieldsException_whenEmailExists() {
        RegisterUserDTO dto = buildRegisterDTO("alice", "alice@example.com", "Secret1");

        when(userRepository.existsByUsername("alice")).thenReturn(false);
        when(userRepository.existsByEmail("alice@example.com")).thenReturn(true);

        DuplicateFieldsException ex = assertThrows(DuplicateFieldsException.class,
                () -> userService.register(dto));
        assertEquals("email", ex.getField());
    }

    @Test
    void getByEmail_returnsDTO_whenUserExists() {
        User user = new User("alice", "alice@example.com", "hash");

        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.of(user));

        UserResponseDTO result = userService.getByEmail("alice@example.com");

        assertEquals("alice", result.getUsername());
        assertEquals("alice@example.com", result.getEmail());
    }

    @Test
    void getByEmail_throws_whenUserNotFound() {
        when(userRepository.findByEmail("ghost@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundByEmailException.class,
                () -> userService.getByEmail("ghost@example.com"));
    }

    @Test
    void getUserInfo_returnsUsername_whenUserExists() {
        UUID userId = UUID.randomUUID();
        User user = new User("alice", "alice@example.com", "hash");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserInfoResponseDTO result = userService.getUserInfo(userId);

        assertEquals("alice", result.getUsername());
    }

    @Test
    void getUserInfo_throws_whenUserNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userService.getUserInfo(userId));
    }
}
