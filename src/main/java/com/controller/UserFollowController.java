package com.controller;

import com.dao.UserFollowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
}
