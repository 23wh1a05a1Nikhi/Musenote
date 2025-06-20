package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.model.PostLike;

import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByUsernameAndPostId(String username, Integer postId);

    @Modifying
    @Transactional
    @Query("DELETE FROM PostLike pl WHERE pl.username = :username AND pl.postId = :postId")
    void deleteByUsernameAndPostId(@Param("username") String username, @Param("postId") Integer postId);
}
