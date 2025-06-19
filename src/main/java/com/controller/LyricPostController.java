package com.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.dao.LyricPostDao;
import com.dao.LyricPostRepository;
import com.dao.UserRegRepository;
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
public ResponseEntity<String> likePost(
        @PathVariable Integer postId,
        @RequestHeader("Authorization") String authHeader) {

    try {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);

        if (likeRepo.existsByUsernameAndPostId(username, postId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User already liked this post");
        }

        Optional<LyricPost> optPost = postRepo.findById(postId);
        if (optPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }

        LyricPost post = optPost.get();
        post.setLikes((post.getLikes() == null ? 0 : post.getLikes()) + 1);
        postRepo.save(post);

        PostLike like = new PostLike(username, postId);
        likeRepo.save(like);

        return ResponseEntity.ok("Post liked");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error liking post: " + e.getMessage());
    }
}

@GetMapping("/getAllPosts")
public List<LyricPost> getAllPosts() {
    return postDao.getAllPosts();
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
}