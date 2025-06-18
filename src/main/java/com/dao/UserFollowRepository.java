package com.dao;

import com.model.UserFollow;
import com.model.UserFollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserFollowRepository extends JpaRepository<UserFollow, UserFollowId> {

    // Count followers of a user (how many people follow this user)
    @Query("SELECT COUNT(f) FROM UserFollow f WHERE f.following = :username")
    int countFollowers(@Param("username") String username);

    // Count following of a user (how many people this user follows)
    @Query("SELECT COUNT(f) FROM UserFollow f WHERE f.userRegUserName = :username")
    int countFollowing(@Param("username") String username);

    // Optional: get follower list
    @Query("SELECT f.userRegUserName FROM UserFollow f WHERE f.following = :username")
    List<String> getFollowers(@Param("username") String username);

    // Optional: get following list
    @Query("SELECT f.following FROM UserFollow f WHERE f.userRegUserName = :username")
    List<String> getFollowing(@Param("username") String username);
}
