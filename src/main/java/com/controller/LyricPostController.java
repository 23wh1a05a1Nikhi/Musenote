package com.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import com.dao.LyricPostDao;
import com.dao.LyricPostRepository;
import com.dao.UserRegRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dao.PostLikeRepository;
import com.model.LyricPost;
import com.model.PostLike;
import com.model.UserReg;
import com.util.JwtUtil;

@CrossOrigin("http://localhost:3000")
@RestController
public class LyricPostController {

	@Autowired
	private LyricPostDao postDao;
	
	@Autowired
	private UserRegRepository userRepo;
	
	@Autowired
	private LyricPostRepository postRepo;
	
	@Autowired
	private PostLikeRepository likeRepo;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	// ✅ Create a post
	@PostMapping("/addPost")
	public ResponseEntity<?> addPost(@RequestBody LyricPost post, Principal principal) {
	    try {
	        String username = principal.getName();
	        UserReg user = userRepo.findByName(username)
	                .orElseThrow(() -> new RuntimeException("User not found"));
	
	        post.setUserreg(user);
	        post.setLikes(0); // Set default likes to 0
	        postRepo.save(post);
	
	        return ResponseEntity.ok("Post created successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Failed to create post: " + e.getMessage());
	    }
	}
	
	// ✅ Like a post
	@PostMapping("/likePost/{postId}")
	public ResponseEntity<String> toggleLikePost(
	        @PathVariable Integer postId,
	        @RequestHeader("Authorization") String authHeader) {

	    try {
	        String token = authHeader.replace("Bearer ", "");
	        String username = jwtUtil.extractUsername(token);

	        Optional<LyricPost> optPost = postRepo.findById(postId);
	        if (optPost.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
	        }

	        LyricPost post = optPost.get();

	        // Check if the user already liked the post
	        if (likeRepo.existsByUsernameAndPostId(username, postId)) {
	            // Unlike
	            likeRepo.deleteByUsernameAndPostId(username, postId);
	            post.setLikes(Math.max((post.getLikes() != null ? post.getLikes() : 1) - 1, 0));
	            postRepo.save(post);
	            return ResponseEntity.ok("Post unliked");
	        } else {
	            // Like
	            PostLike like = new PostLike(username, postId);
	            likeRepo.save(like);
	            post.setLikes((post.getLikes() != null ? post.getLikes() : 0) + 1);
	            postRepo.save(post);
	            return ResponseEntity.ok("Post liked");
	        }

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error toggling like: " + e.getMessage());
	    }
	}

	
	@GetMapping("/getPostById/{postID}")
	public LyricPost getPostByID(@PathVariable("postID") int postID) {
	    return postDao.getPostByID(postID);
	}
	
	@GetMapping("/getPostsByTags/{tag}")
	public List<LyricPost> getPostsByTags(@PathVariable("tag") String tagName) {
	    return postDao.getPostsByTags(tagName);
	}
	
	@GetMapping("/getPostsByGenre/{genre}")
	public List<LyricPost> getPostsByGenre(@PathVariable("genre") String gnName) {
	    return postDao.getPostsByGenre(gnName);
	}
	
	@GetMapping("/getPostsByUser/{userName}")
	public List<LyricPost> getPostsByUser(@PathVariable("userName") String userName) {
	    return postDao.getPostsByUser(userName);
	}
	
	@GetMapping("/getPostsByTitle/{title}")
	public List<LyricPost> getPostsByTitle(@PathVariable("title") String title) {
		return postDao.getPostsByTitle(title);
	}
	
	@PostMapping("/addPostWithAudio")
	public ResponseEntity<?> addPostWithAudio(
	    @RequestPart("post") String postJson,
	    @RequestPart(value = "file", required = false) MultipartFile file,
	    Principal principal
	) throws IOException {
	    // Convert raw JSON string to LyricPost object
	    ObjectMapper objectMapper = new ObjectMapper();
	    LyricPost post = objectMapper.readValue(postJson, LyricPost.class);

	    // Save audio file if present
	    if (file != null && !file.isEmpty()) {
	        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
	        Path uploadPath = Paths.get("uploads/" + fileName);
	        Files.write(uploadPath, file.getBytes());
	        post.setAudioFileName(fileName);
	    }
	    // ✅ Associate user with the post
	    String username = principal.getName();
	    UserReg user = userRepo.findByName(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));
	    post.setUserreg(user);
	    post.setLikes(0);
	  
	    LyricPost savedPost = postDao.addPost(post);
	    return ResponseEntity.ok(savedPost);
	}
	
	@GetMapping("/audio/{fileName}")
	public ResponseEntity<Resource> serveAudio(@PathVariable String fileName) throws IOException {
	    Path path = Paths.get("uploads/" + fileName);
	    if (!Files.exists(path)) {
	        return ResponseEntity.notFound().build();
	    }
	    Resource resource = new UrlResource(path.toUri());
	    return ResponseEntity.ok()
	            .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
	            .body(resource);
	}


    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<String> deletePostById(
            @PathVariable Integer postId,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtUtil.extractUsername(token);
            Optional<LyricPost> optionalPost = postRepo.findById(postId);
            if (optionalPost.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
            }
            LyricPost post = optionalPost.get();
            if (!post.getUserreg().getUserName().equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You can only delete your own posts");
            }
            postRepo.deleteById(postId);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting post: " + e.getMessage());
        }
    }

    @GetMapping("/getAllPosts")
    public List<LyricPost> getAllPosts() {
        return postDao.getAllPosts();
    }
    
    @GetMapping("/isPostLiked/{postId}")
    public ResponseEntity<Boolean> isPostLiked(
            @PathVariable Integer postId,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        boolean liked = likeRepo.existsByUsernameAndPostId(username, postId);
        return ResponseEntity.ok(liked);
    }
}
