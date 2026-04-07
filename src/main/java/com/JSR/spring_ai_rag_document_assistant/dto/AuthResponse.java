package com.JSR.spring_ai_rag_document_assistant.dto;

import com.JSR.spring_ai_rag_document_assistant.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String userName;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String country;
    private Set<RoleName> roles;
    private String accessToken; // JWT token
    private String tokenType = "Bearer";
    private LocalDateTime lastLogin;
}