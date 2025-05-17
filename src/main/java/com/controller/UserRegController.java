package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.UserRegDao;
import com.model.UserLogin;
import com.model.UserReg;
@CrossOrigin("http://localhost:3000")
@RestController
public class UserRegController {
	@Autowired
	UserRegDao userDao;
	
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
	public String UsersLogin(@RequestBody UserLogin user) {
		return userDao.UsersLogin(user.getUserName(), user.getPassword());
		
	}
}
