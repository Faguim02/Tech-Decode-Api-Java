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
    @Column(name = "description", length = 1000)
    private String description;
    private String font;
    private String date_at;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_at() {
        return date_at;
    }

    public void setDate_at(String data_at) {
        this.date_at = data_at;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", description='" + description + '\'' +
                ", font='" + font + '\'' +
                ", date_at='" + date_at + '\'' +
                ", comments=" + comments +
                '}';
    }
}
