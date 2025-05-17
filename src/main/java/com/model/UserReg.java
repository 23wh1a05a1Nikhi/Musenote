package com.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class UserReg {
	
	@Id
	String userName;
	@Column(unique = true)
	String mail;
	String password;
	String bio;
	int followerCount = 0; 
	
	@ElementCollection
	List<String> following;
	
	@JsonIgnore
	@OneToMany(mappedBy = "userreg")
	List<LyricPost> lyricList = new ArrayList<LyricPost>();

	public UserReg() {
		
	}

	public UserReg(String userName, String mail, String password, String bio, int followerCount ) {
		super();
		this.userName = userName;
		this.mail = mail;
		this.password = password;
		this.bio = bio;
		this.followerCount = followerCount;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public int getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<LyricPost> getLyricList() {
		return lyricList;
	}

	public void setLyricList(List<LyricPost> lyricList) {
		this.lyricList = lyricList;
	}

	public List<String> getFollowing() {
		return following;
	}

	public void setFollowing(List<String> following) {
		this.following = following;
	}
	
	
	
	
}
