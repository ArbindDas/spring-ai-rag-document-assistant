
package com.JSR.spring_ai_rag_document_assistant.service;

import com.JSR.spring_ai_rag_document_assistant.dto.*;
import com.JSR.spring_ai_rag_document_assistant.entity.User;
import com.JSR.spring_ai_rag_document_assistant.entity.UserProfile;
import com.JSR.spring_ai_rag_document_assistant.enums.RoleName;
import com.JSR.spring_ai_rag_document_assistant.exception.*;
import com.JSR.spring_ai_rag_document_assistant.repository.UserRepository;
import com.JSR.spring_ai_rag_document_assistant.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    @Transactional
    public AuthResponse signup(SignupRequest request) {
        // Check if email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered: " + request.getEmail());
        }

        // Check if username exists
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new UsernameAlreadyExistsException("Username taken: " + request.getUserName());
        }

        // Set default role if none provided
        Set<RoleName> roles = (request.getRoles() == null || request.getRoles().isEmpty())
                ? Set.of(RoleName.ROLE_USER)
                : request.getRoles();

        // Build user profile
        UserProfile profile = UserProfile.builder()
                .fullName(request.getFullName())
                .country(request.getCountry())
                .avatarUrl(request.getAvatarUrl())
                .build();

        // Build user
        User user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .emailVerified(false)
                .isActive(true)
                .profile(profile)
                .build();

        profile.setUser(user);
        User savedUser = userRepository.save(user);

        // Generate token using UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
        String token = jwtService.generateToken(userDetails);

        return buildAuthResponse(savedUser, token);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        } catch (DisabledException e) {
            throw new AccountDisabledException("Account is disabled");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        // Update last login
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Generate token using UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return buildAuthResponse(user, token);
    }

    private AuthResponse buildAuthResponse(User user, String token) {
        AuthResponse response = new AuthResponse();
        response.setUserName(user.getUserName());
        response.setEmail(user.getEmail());
        response.setRoles(user.getRoles());
        response.setAccessToken(token);
        response.setTokenType("Bearer");
        response.setLastLogin(user.getLastLogin());

        if (user.getProfile() != null) {
            response.setFullName(user.getProfile().getFullName());
            response.setAvatarUrl(user.getProfile().getAvatarUrl());
            response.setCountry(user.getProfile().getCountry());
        }

        return response;
    }
}