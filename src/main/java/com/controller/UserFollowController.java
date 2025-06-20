package com.controller;

import com.dao.UserFollowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
public class UserFollowController {

    @Autowired
    private UserFollowDao followDao;

    @GetMapping("followCount/{username}")
    public Map<String, Integer> getFollowCounts(@PathVariable String username) {
        int followers = followDao.getFollowerCount(username);
        int following = followDao.getFollowingCount(username);

        Map<String, Integer> response = new HashMap<>();
        response.put("followers", followers);
        response.put("following", following);
        return response;
    }
    
    @GetMapping("/isFollowing/{follower}/{followee}")
    public Map<String, Boolean> isFollowing(@PathVariable String follower, @PathVariable String followee) {
        boolean isFollowing = followDao.isFollowing(follower, followee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("following", isFollowing);
        return response;
    }

    @PostMapping("/follow")
    public Map<String, String> follow(@RequestBody Map<String, String> body) {
        String follower = body.get("follower");
        String followee = body.get("followee");

        followDao.followUser(follower, followee);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Followed successfully");
        return response;
    }

    @PostMapping("/unfollow")
    public Map<String, String> unfollow(@RequestBody Map<String, String> body) {
        String follower = body.get("follower");
        String followee = body.get("followee");

        followDao.unfollowUser(follower, followee);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Unfollowed successfully");
        return response;
    }
    
    @GetMapping("/getFollowers/{username}")
    public List<String> getFollowers(@PathVariable String username) {
        return followDao.getFollowers(username);
    }

    @GetMapping("/getFollowing/{username}")
    public List<String> getFollowing(@PathVariable String username) {
        return followDao.getFollowing(username);
    }

}
