package com.example.mealPlanner.service.impl;

import com.example.mealPlanner.dto.authentication.JwtAuthenticationResponse;
import com.example.mealPlanner.dto.authentication.SignInRequest;
import com.example.mealPlanner.dto.authentication.SignUpRequest;
import com.example.mealPlanner.entity.Role;
import com.example.mealPlanner.entity.User;
import com.example.mealPlanner.repository.UserRepository;
import com.example.mealPlanner.service.AuthenticationService;
import com.example.mealPlanner.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        String jwtToken = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
