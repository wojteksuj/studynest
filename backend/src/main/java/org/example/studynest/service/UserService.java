package org.example.studynest.service;

import org.example.studynest.config.PasswordEncoderConfig;
import org.example.studynest.dto.request.RegisterUserDTO;
import org.example.studynest.dto.response.UserInfoResponseDTO;
import org.example.studynest.dto.response.UserResponseDTO;
import org.example.studynest.entity.User;
import org.example.studynest.exception.DuplicateFieldsException;
import org.example.studynest.exception.UsernameNotFoundByEmailException;
import org.example.studynest.mapper.UserInfoMapper;
import org.example.studynest.mapper.UserMapper;
import org.example.studynest.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;

    public UserService(UserRepository userRepository, PasswordEncoderConfig passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoderConfig = passwordEncoder;
    }

    public UserResponseDTO register(RegisterUserDTO registerUserDTO) {

        if (userRepository.existsByUsername(registerUserDTO.getUsername())) {
            throw new DuplicateFieldsException("username", "Username already exists");
        }

        if (userRepository.existsByEmail(registerUserDTO.getEmail())) {
            throw new DuplicateFieldsException("email", "Email already exists");
        }

        User user = new User(
                registerUserDTO.getUsername(),
                registerUserDTO.getEmail(),
                passwordEncoderConfig.passwordEncoder().encode(registerUserDTO.getPassword())
        );
        User savedUser = userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }

    public UserResponseDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundByEmailException(email));
        return UserMapper.toDTO(user);
    }

    public UserInfoResponseDTO getUserInfo(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(userId.toString()));
        return UserInfoMapper.toDTO(user);
    }
}
