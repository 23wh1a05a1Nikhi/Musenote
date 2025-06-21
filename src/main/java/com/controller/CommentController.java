package com.controller;

import com.model.Comment;
import com.model.LyricPost;
import com.model.UserReg;
import com.dao.CommentDao;
import com.dao.LyricPostDao;
import com.dao.UserRegDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private LyricPostDao postDao;

    @Autowired
    private UserRegDao userDao;

    @PostMapping("/addComment")
    public Comment addComment(@RequestParam String content,
                              @RequestParam int postId,
                              @RequestParam String userName) {
        LyricPost post = postDao.getPostByID(postId);
        UserReg user = userDao.getUserByName(userName); 

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setUser(user);

        return commentDao.addComment(comment);
    }

    @GetMapping("/commentsByPost/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable int postId) {
        LyricPost post = postDao.getPostByID(postId);
        return commentDao.getCommentsByPost(post);
    }
}
