package com.example.mealPlanner.service;

import com.example.mealPlanner.dto.authentication.JwtAuthenticationResponse;
import com.example.mealPlanner.dto.authentication.SignInRequest;
import com.example.mealPlanner.dto.authentication.SignUpRequest;
import com.example.mealPlanner.entity.User;

public interface AuthenticationService {

    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);

    User getAuthenticatedUser();
}
