package com.example.mealPlanner.service;

import com.example.mealPlanner.dto.PageRequestDto;
import com.example.mealPlanner.dto.PageResponseDto;
import com.example.mealPlanner.dto.UserDto;
import com.example.mealPlanner.entity.User;

public interface UserService {

    UserDto getUser();

    PageResponseDto<UserDto> getUsers(PageRequestDto pageRequestDto);

    UserDto updateUser(UserDto userDto);

    UserDto createUser(UserDto userDto);

    void deleteUser(Long id);

    User getUserById(Long id);
}
