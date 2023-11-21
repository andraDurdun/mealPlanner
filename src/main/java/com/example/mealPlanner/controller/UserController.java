package com.example.mealPlanner.controller;

import com.example.mealPlanner.dto.PageRequestDto;
import com.example.mealPlanner.dto.PageResponseDto;
import com.example.mealPlanner.dto.UserDto;
import com.example.mealPlanner.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserDto getUser() {
        return userService.getUser();
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping
    public PageResponseDto<UserDto> getUsers(@ModelAttribute @Valid PageRequestDto pageRequestDto) {
        return userService.getUsers(pageRequestDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return userService.updateUser(userDto);

    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
