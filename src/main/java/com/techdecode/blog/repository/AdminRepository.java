package com.techdecode.blog.repository;

import com.techdecode.blog.models.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<AdminModel, UUID> {
    Optional<AdminModel> findByEmail(String email);
}
