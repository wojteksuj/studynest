package org.example.studynest.mapper;

import org.example.studynest.dto.response.UserInfoResponseDTO;
import org.example.studynest.entity.User;

public class UserInfoMapper {
    public static UserInfoResponseDTO toDTO(User user) {
        UserInfoResponseDTO dto = new UserInfoResponseDTO();
        dto.setUsername(user.getUsername());
        return dto;
    }
}
