package com.techdecode.blog.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "category")
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    @OneToMany(mappedBy = "category")
    private List<PostModel> postModels;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PostModel> getPostModels() {
        return postModels;
    }

    public void setPostModels(List<PostModel> postModels) {
        this.postModels = postModels;
    }
}
