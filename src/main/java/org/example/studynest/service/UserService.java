package org.example.studynest.service;

import org.example.studynest.dto.request.CreateUserRequestDTO;
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

    public UserResponseDTO register(CreateUserRequestDTO createUserRequestDTO) {
        if (userRepository.existsByEmail(createUserRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(createUserRequestDTO.getEmail());
        }
        User user = new User();



        userRepository.save(user);

    }

    public UserResponseDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundByEmailException(email));
        return UserMapper.toDTO(user);
    }
}
