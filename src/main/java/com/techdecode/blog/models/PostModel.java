package com.techdecode.blog.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "post")
public class PostModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String bannerUrl;
    private String font;
    private String data_at;

    @OneToMany(mappedBy = "post")
    private List<DescriptionPostModel> descriptionPost;

    @OneToMany(mappedBy = "post")
    private List<CommentModel> comments;

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

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getData_at() {
        return data_at;
    }

    public void setData_at(String data_at) {
        this.data_at = data_at;
    }

    public List<DescriptionPostModel> getDescriptionPost() {
        return descriptionPost;
    }

    public void setDescriptionPost(List<DescriptionPostModel> descriptionPost) {
        this.descriptionPost = descriptionPost;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }
}
