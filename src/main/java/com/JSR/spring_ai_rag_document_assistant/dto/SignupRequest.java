package com.JSR.spring_ai_rag_document_assistant.dto;

import com.JSR.spring_ai_rag_document_assistant.enums.RoleName;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one uppercase, one lowercase, one number, and one special character"
    )
    private String password;

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters")
    @Pattern(
            regexp = "^[a-zA-Z\\s]+$",
            message = "Full name must not contain numbers or special characters"
    )
    private String fullName;

    @Size(max = 100, message = "Country name must be less than 100 characters")
    private String country;

    @Size(max = 255, message = "Avatar URL must be less than 255 characters")
    private String avatarUrl;

    private Set<RoleName> roles; // Optional, default to ROLE_USER in service
}