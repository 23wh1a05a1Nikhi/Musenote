package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
