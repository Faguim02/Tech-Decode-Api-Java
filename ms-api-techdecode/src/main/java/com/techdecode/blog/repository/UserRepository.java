package com.techdecode.blog.repository;

import com.techdecode.blog.models.UserModel;
import com.techdecode.blog.models.roles.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserDetails findByEmail(String email);
    List<UserModel> findByUserRole(UserRole userRole);
}
