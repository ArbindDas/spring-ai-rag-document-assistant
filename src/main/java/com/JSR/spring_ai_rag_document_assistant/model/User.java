package com.JSR.spring_ai_rag_document_assistant.model;

import com.JSR.spring_ai_rag_document_assistant.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "app_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String userName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ElementCollection(fetch = FetchType.EAGER) // -> collection of basic types (not entities)
    // fetch = EAGER → load roles immediately with User
    @Enumerated(EnumType.STRING)  // → store enum names as string
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id")) // → separate table for the collection
    private Set<RoleName> roles = new HashSet<>(); // -> roles must be unique

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    // cascade = CascadeType.ALL = do all persistence operations (save, update, delete, etc.)
    // automatically on the related entity when done on the parent entity.
    private UserProfile profile;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


//    @PrePersist =>	Before the entity is saved for the first time (INSERT)
//    @PostPersist	 => After the entity is saved (INSERT)
//    @PreUpdate =>	Before the entity is updated (UPDATE)
//    @PostUpdate =>	After the entity is updated (UPDATE)
//    @PreRemove =>	Before the entity is deleted (DELETE)
//    @PostRemove =>	After the entity is deleted (DELETE)
//    @PostLoad =>	After the entity is loaded from DB (SELECT)
}