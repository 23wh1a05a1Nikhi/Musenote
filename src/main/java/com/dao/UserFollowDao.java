package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.UserFollow;
import com.model.UserFollowId;

@Service
public class UserFollowDao {

    @Autowired
    private UserFollowRepository userFollowRepo;

    public int getFollowerCount(String username) {
        return userFollowRepo.countFollowers(username);
    }

    public int getFollowingCount(String username) {
        return userFollowRepo.countFollowing(username);
    }
    
    public boolean isFollowing(String follower, String followee) {
        return userFollowRepo.existsById(new UserFollowId(follower, followee));
    }

    public void followUser(String follower, String followee) {
        if (!isFollowing(follower, followee)) {
            userFollowRepo.save(new UserFollow(follower, followee));
        }
    }

    public void unfollowUser(String follower, String followee) {
        UserFollowId id = new UserFollowId(follower, followee);
        if (userFollowRepo.existsById(id)) {
            userFollowRepo.deleteById(id);
        }
    }
}
