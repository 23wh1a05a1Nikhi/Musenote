package com.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.UserRegDao;
import com.dao.UserRegRepository;
import com.dto.LoginResponse;
import com.model.UserLogin;
import com.model.UserReg;
import com.util.JwtUtil;
@CrossOrigin("http://localhost:3000")
@RestController
public class UserRegController {
	@Autowired
	UserRegDao userDao;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("addUser")
	public UserReg addUser(@RequestBody UserReg user) {
		return userDao.addUser(user);
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
	
	
}
