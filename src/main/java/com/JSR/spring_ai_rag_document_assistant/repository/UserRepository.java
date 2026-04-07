package com.JSR.spring_ai_rag_document_assistant.repository;

import com.JSR.spring_ai_rag_document_assistant.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUserName(String username);

    CriteriaBuilder findByUserName(String username);
}