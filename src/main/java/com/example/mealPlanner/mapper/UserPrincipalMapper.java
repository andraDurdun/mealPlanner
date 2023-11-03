package com.example.mealPlanner.mapper;

import com.example.mealPlanner.dto.authentication.UserPrincipalDto;
import com.example.mealPlanner.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPrincipalMapper {

    public UserPrincipalDto toDto(User user) {
        return UserPrincipalDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
