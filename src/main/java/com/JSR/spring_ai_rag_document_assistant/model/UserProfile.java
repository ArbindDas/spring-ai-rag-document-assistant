package com.JSR.spring_ai_rag_document_assistant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String avtarUrl;
    private String country;

    // todo -> “ Who holds the foreign key → that’s the owning side.”
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false , unique = true)
    private  User user;
}
