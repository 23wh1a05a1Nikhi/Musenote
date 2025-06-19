package com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "post_like", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "post_id"}))
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(name = "post_id")
    private Integer postId;

    public PostLike() {}

    public PostLike(String username, Integer postId) {
        this.username = username;
        this.postId = postId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Integer getPostId() { return postId; }
    public void setPostId(Integer postId) { this.postId = postId; }
}
