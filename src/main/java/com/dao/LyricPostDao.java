package com.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.LyricPost;

@Service
public class LyricPostDao {
	@Autowired
	LyricPostRepository postRepo;
	
	public LyricPost addPost(LyricPost post) {
		return postRepo.save(post);
	}
	
	public LyricPost getPostByID(int postID) {
		return postRepo.getByID(postID);
	}
	
	public List<LyricPost> getAllPosts() {
		return postRepo.findAll();
	}

	public List<LyricPost> getPostsByTags(String tagName) {
		// TODO Auto-generated method stub
		return postRepo.getByTag(tagName);
	}

	public List<LyricPost> getPostsByGenre(String gnName) {
		// TODO Auto-generated method stub
		return postRepo.getByGenre(gnName);
	}

	public List<LyricPost> getPostsByUser(String userName) {
		// TODO Auto-generated method stub
		return postRepo.getByUser(userName);
	}
	
	
}
