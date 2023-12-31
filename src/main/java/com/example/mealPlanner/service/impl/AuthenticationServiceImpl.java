package com.example.mealPlanner.service.impl;

import com.example.mealPlanner.dto.authentication.JwtAuthenticationResponse;
import com.example.mealPlanner.dto.authentication.SignInRequest;
import com.example.mealPlanner.dto.authentication.SignUpRequest;
import com.example.mealPlanner.dto.authentication.UserPrincipalDto;
import com.example.mealPlanner.entity.Role;
import com.example.mealPlanner.entity.User;
import com.example.mealPlanner.exception.AuthenticationException;
import com.example.mealPlanner.exception.DuplicateResourceException;
import com.example.mealPlanner.mapper.UserPrincipalMapper;
import com.example.mealPlanner.repository.UserRepository;
import com.example.mealPlanner.service.AuthenticationService;
import com.example.mealPlanner.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserPrincipalMapper userPrincipalMapper;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        validateEmailIsNotUsed(request.getEmail());
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(userPrincipalMapper.toDto(user));
        return JwtAuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);
        return JwtAuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipalDto userPrincipalDto) {
            //todo add exception
            return userRepository.findById(userPrincipalDto.getId()).orElseThrow();
        }

        throw new AuthenticationException("User not authenticated");
    }

    private void validateEmailIsNotUsed(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateResourceException("User with email: " + email + " already exists.");
        }
    }
}
