package com.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(length = 300)
    private String content;

    @ManyToOne
    @JoinColumn(name = "postId")
    private LyricPost post;

    @ManyToOne
    @JoinColumn(name = "userName")
    private UserReg user;

    public Comment() {}

    public Comment(String content, LyricPost post, UserReg user) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.post = post;
        this.user = user;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LyricPost getPost() {
        return post;
    }

    public void setPost(LyricPost post) {
        this.post = post;
    }

    public UserReg getUser() {
        return user;
    }

    public void setUser(UserReg user) {
        this.user = user;
    }
}