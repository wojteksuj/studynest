package org.example.studynest.service;

import org.example.studynest.dto.request.RegisterUserDTO;
import org.example.studynest.dto.response.UserResponseDTO;
import org.example.studynest.entity.User;
import org.example.studynest.exception.EmailAlreadyExistsException;
import org.example.studynest.exception.UsernameNotFoundByEmailException;
import org.example.studynest.mapper.UserMapper;
import org.example.studynest.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO register(RegisterUserDTO registerUserDTO) {
        if (userRepository.existsByEmail(registerUserDTO.getEmail())) {
            throw new EmailAlreadyExistsException(registerUserDTO.getEmail());
        }
        User user = new User(
                registerUserDTO.getUsername(),
                registerUserDTO.getEmail(),
                registerUserDTO.getPassword()
        );
        User savedUser = userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }

    public UserResponseDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundByEmailException(email));
        return UserMapper.toDTO(user);
    }
}
