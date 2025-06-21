package com.controller;

import com.model.Comment;
import com.model.LyricPost;
import com.model.UserReg;
import com.util.JwtUtil;
import com.dao.CommentDao;
import com.dao.LyricPostDao;
import com.dao.UserRegDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private LyricPostDao postDao;

    @Autowired
    private UserRegDao userDao;
    
    @Autowired
    private JwtUtil jwtutil;
    
    @PostMapping("/addComment/{postId}")
    public Comment addComment(@PathVariable int postId,
                              @RequestBody Map<String, String> body,
                              @RequestHeader("Authorization") String authHeader) {
        String content = body.get("content");

        String jwt = authHeader.substring(7); // remove "Bearer "
        String username = jwtutil.extractUsername(jwt); // your existing JwtService

        UserReg user = userDao.getUserByName(username);
        LyricPost post = postDao.getPostByID(postId);

        return commentDao.addComment(new Comment(content, post, user));
    }

    

    @GetMapping("/commentsByPost/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable int postId) {
        LyricPost post = postDao.getPostByID(postId);
        return commentDao.getCommentsByPost(post);
    }
}
