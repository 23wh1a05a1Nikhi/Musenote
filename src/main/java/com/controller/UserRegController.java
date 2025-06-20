package com.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.dao.LyricPostRepository;
import com.dao.PostLikeRepository;
import com.dao.UserFollowRepository;
import com.dao.UserRegDao;
import com.dao.UserRegRepository;
import com.dto.LoginResponse;
import com.model.UserLogin;
import com.model.UserReg;
import com.service.EmailService;
import com.util.JwtUtil;
@CrossOrigin("http://localhost:3000")
@RestController
public class UserRegController {
	@Autowired
	UserRegDao userDao;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PostLikeRepository postLikeRepository;

	@Autowired
	private UserFollowRepository userFollowRepository;

	@Autowired
	private LyricPostRepository lyricPostRepository;

	
	@PostMapping("addUser")
	public ResponseEntity<?> addUser(@RequestBody UserReg user) {
	    try {
	        System.out.println("Received user:");
	        System.out.println("Username: " + user.getUserName());
	        System.out.println("Email: " + user.getMail());
	        System.out.println("Password: " + user.getPassword());

	        UserReg savedUser = userDao.addUser(user);

	        emailService.sendWelcomeEmail(user.getMail(), user.getUserName());

	        return ResponseEntity.ok(savedUser);
	    } catch (Exception e) {
	        e.printStackTrace(); // for full stack trace
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("Registration failed: " + e.getMessage());
	    }
	}


	
	@GetMapping("getUserByName/{name}")
	public UserReg getUserByName(@PathVariable("name") String name) {
		return userDao.getUserByName(name);
	}
	
	@PutMapping("addFollowing")
	public UserReg addFollowing(@RequestBody UserReg user) {
		return userDao.addFollowing(user);
	}
	
	
	
	@PostMapping("UsersLogin")
	public Object UsersLogin(@RequestBody UserLogin user) {
	    String result = userDao.UsersLogin(user.getUserName(), user.getPassword());

	    if (result.equals("Valid")) {
	        String token = jwtUtil.generateToken(user.getUserName());
	        return new LoginResponse(token, user.getUserName());
	    } else {
	        return result;
	    }
	}
	
	@Autowired
    private UserRegRepository userRegRepository;
	@PutMapping("/updateBio")
    public ResponseEntity<String> updateBio(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String bio = body.get("bio");

        Optional<UserReg> userOptional = userRegRepository.findByName(username);
        if (userOptional.isPresent()) {
            UserReg user = userOptional.get();
            user.setBio(bio);
            userRegRepository.save(user);
            return ResponseEntity.ok("Bio updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
	
	@DeleteMapping("/deleteAccount/{username}")
	@Transactional
	public ResponseEntity<String> deleteAccount(
	        @PathVariable("username") String username,
	        @RequestHeader("Authorization") String authHeader) {

	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token.");
	    }

	    String token = authHeader.substring(7); // remove "Bearer "
	    String tokenUsername;
	    try {
	        tokenUsername = jwtUtil.extractUsername(token);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.");
	    }

	    if (!tokenUsername.equals(username)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to delete this account.");
	    }

	    try {
	        postLikeRepository.deleteByUsername(username);
	        userFollowRepository.deleteAllUserFollows(username);
	        lyricPostRepository.deleteByUserName(username);
	        userRegRepository.deleteById(username);

	        return ResponseEntity.ok("Account deleted successfully.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during account deletion.");
	    }
	}	
}
