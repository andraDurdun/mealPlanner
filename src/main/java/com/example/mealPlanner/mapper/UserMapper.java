package com.example.mealPlanner.mapper;

import com.example.mealPlanner.dto.PageResponseDto;
import com.example.mealPlanner.dto.UserDto;
import com.example.mealPlanner.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    public PageResponseDto<UserDto> toDto(Page<User> users) {
        List<UserDto> userDtos = users.getContent().stream()
                .map(this::toDto)
                .toList();
        return new PageResponseDto<>(userDtos, users.getNumber(), users.getNumberOfElements(), users.getTotalElements());

    }
}
