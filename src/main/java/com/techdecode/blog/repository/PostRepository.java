package com.techdecode.blog.repository;

import com.techdecode.blog.models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostModel, UUID> {
    public List<PostModel> findByTitleContaining(String keyword);
}
