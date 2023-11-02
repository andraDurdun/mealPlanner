package com.example.mealPlanner.service;

import com.example.mealPlanner.dto.PageRequestDto;
import com.example.mealPlanner.dto.PageResponseDto;
import com.example.mealPlanner.dto.UserDto;

public interface UserService {

    PageResponseDto<UserDto> getUsers(PageRequestDto pageRequestDto);

    UserDto updateUser(UserDto userDto);

    UserDto createUser(UserDto userDto);

    void deleteUser(Long id);
}
