package com.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUsernameAndPostId(String username, Integer postId);
}

