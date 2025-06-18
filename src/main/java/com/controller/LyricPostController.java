package com.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.LyricPostDao;
import com.dao.LyricPostRepository;
import com.dao.UserRegDao;
import com.dao.UserRegRepository;
import com.model.LyricPost;
import com.model.UserReg;
@CrossOrigin("http://localhost:3000")
@RestController
public class LyricPostController {
	@Autowired
	LyricPostDao postDao;
	
	@Autowired
	UserRegRepository userRepo;
	
	@Autowired
	LyricPostRepository postRepo;
	
	@PostMapping("/addPost")
	public ResponseEntity<?> addPost(@RequestBody LyricPost post, Principal principal) {
	    String username = principal.getName(); // comes from token
	    UserReg user = userRepo.findByName(username)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    post.setUserreg(user);  // <-- set the user
	 

	    postRepo.save(post);
	    return ResponseEntity.ok("Post created successfully");
	}
	
	/*@PostMapping("addPost")
	public LyricPost addPost(@RequestBody LyricPost post) {
		return postDao.addPost(post);
	}*/
	
	
	@GetMapping("getAllPosts")
	public List<LyricPost> getAllPosts(){
		return postDao.getAllPosts();
	}
	
	@GetMapping("getPostById/{postID}")
	public LyricPost getPostByID(@PathVariable("postID") int postID) {
		return postDao.getPostByID(postID);
	}
	
	@GetMapping("getPostsByTags/{tag}")
	public List<LyricPost> getPostsByTags(@PathVariable("tag") String tagName) {
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
