package com.example.mealPlanner.service.impl;

import com.example.mealPlanner.dto.PageRequestDto;
import com.example.mealPlanner.dto.PageResponseDto;
import com.example.mealPlanner.dto.UserDto;
import com.example.mealPlanner.entity.Role;
import com.example.mealPlanner.entity.User;
import com.example.mealPlanner.exception.DuplicateResourceException;
import com.example.mealPlanner.exception.ResourceNotFoundException;
import com.example.mealPlanner.mapper.UserMapper;
import com.example.mealPlanner.repository.UserRepository;
import com.example.mealPlanner.service.AuthenticationService;
import com.example.mealPlanner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    @Value("${user.initial.password}")
    private String initialPassword;

    @Override
    public UserDto getUser() {
        User authenticatedUser = authenticationService.getAuthenticatedUser();
        return userMapper.toDto(authenticatedUser);
    }

    @Override
    public PageResponseDto<UserDto> getUsers(PageRequestDto pageRequestDto) {
        Page<User> users = userRepository.findAll(PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()));
        return userMapper.toDto(users);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        validateEmailIsNotUsed(userDto.getEmail());
        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(initialPassword))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        int deletedUsers = userRepository.deleteUserById(id);
        if (deletedUsers == 0) {
            throw new ResourceNotFoundException("User with id: " + id + " was not deleted.");
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        validateEmailIsNotUsed(userDto);
        User user = getUserById(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(userDto.getRole());
        return userMapper.toDto(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
    }

    private void validateEmailIsNotUsed(UserDto userDto) {
        Optional<User> user = userRepository.findByIdIsNotAndEmail(userDto.getId(), userDto.getEmail());
        if (user.isPresent()) {
            throw new DuplicateResourceException("User with email: " + userDto.getEmail() + " already exists.");
        }
    }

    private void validateEmailIsNotUsed(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateResourceException("User with email: " + email + " already exists.");
        }
    }
}
