package com.techdecode.blog.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "category")
public class CategoryModel {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    @OneToMany(mappedBy = "category")
    private List<PostModel> postModels;
}
