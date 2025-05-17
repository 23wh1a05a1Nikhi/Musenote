package com.dao;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.model.UserReg;
@Service
public class UserRegDao {
	
	@Autowired
	UserRegRepository userRepo;
	public UserReg addUser(UserReg user) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String encryptPass = bcrypt.encode(user.getPassword());
		user.setPassword(encryptPass);
		return userRepo.save(user);
	}
	public UserReg getUserByName(String name) {
		// TODO Auto-generated method stub
		return userRepo.findByName(name).orElse(null);
	}
	public UserReg addFollowing(UserReg user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}
	public String UsersLogin(String userName, String password) {
		// TODO Auto-generated method stub
		
		
		Optional<UserReg> optionalUser = userRepo.findByName(userName);

		if (!optionalUser.isPresent()) {
			return "Invalid user";
		}

		UserReg user = optionalUser.get();
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

		if (bcrypt.matches(password, user.getPassword())) {
			return "Valid";
		}
		return "Invalid pass";
		
		
	}
	
	
	
	

}
