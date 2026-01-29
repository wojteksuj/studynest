package org.example.studynest.mapper;

import org.example.studynest.dto.response.UserResponseDTO;
import org.example.studynest.entity.User;

public class UserMapper {
    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRegistrationDate(user.getRegistrationDate());
        return dto;
    }
}
