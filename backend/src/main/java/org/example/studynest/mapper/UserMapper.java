package org.example.studynest.mapper;

import org.example.studynest.dto.response.UserInfoResponseDTO;
import org.example.studynest.dto.response.UserResponseDTO;
import org.example.studynest.entity.User;

public class UserMapper {
    public static UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRegistrationDate(user.getRegistrationDate());
        return dto;
    }

    public static UserInfoResponseDTO toUserInfoResponseDTO(User user) {
        UserInfoResponseDTO dto = new UserInfoResponseDTO();
        dto.setUsername(user.getUsername());
        return dto;
    }
}
