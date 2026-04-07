package com.JSR.spring_ai_rag_document_assistant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String avatarUrl;
    private String country;

    // todo -> “ Who holds the foreign key → that’s the owning side.”
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false , unique = true)
    private  User user;
}
