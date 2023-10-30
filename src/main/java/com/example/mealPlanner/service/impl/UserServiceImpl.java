package com.example.mealPlanner.service.impl;

import com.example.mealPlanner.repository.UserRepository;
import com.example.mealPlanner.service.AuthenticationService;
import com.example.mealPlanner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

}
