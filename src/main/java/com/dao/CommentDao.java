package com.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Comment;
import com.model.LyricPost;
import com.dao.CommentRepository;

@Service
public class CommentDao {

    @Autowired
    CommentRepository commentRepo;

    public Comment addComment(Comment comment) {
        return commentRepo.save(comment);
    }

    public List<Comment> getCommentsByPost(LyricPost post) {
        return commentRepo.findByPost(post);
    }
}