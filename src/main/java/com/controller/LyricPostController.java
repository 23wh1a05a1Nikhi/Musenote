package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.LyricPostDao;
import com.model.LyricPost;
@CrossOrigin("http://localhost:3000")
@RestController
public class LyricPostController {
	@Autowired
	LyricPostDao postDao;
	
	@PostMapping("addPost")
	public LyricPost addPost(@RequestBody LyricPost post) {
		return postDao.addPost(post);
	}
	
	
	@GetMapping("getAllPosts")
	public List<LyricPost> getAllPosts(){
		return postDao.getAllPosts();
	}
	
	@GetMapping("getPostsByTags/{tag}")
	public List<LyricPost> getPostsByTags(@PathVariable("tag") String tagName){
		return postDao.getPostsByTags(tagName);
	}
	
	@GetMapping("getPostsByGenre/{genre}")
	public List<LyricPost> getPostsByGenre(@PathVariable("genre") String gnName){
		return postDao.getPostsByGenre(gnName);
	}
	
	@GetMapping("getPostsByUser/{userName}")
	public List<LyricPost> getPostsByUser(@PathVariable("userName") String userName){
		return postDao.getPostsByUser(userName);
	}
	
}
