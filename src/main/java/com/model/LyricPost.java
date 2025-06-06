package com.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class LyricPost {
	
	@Id@GeneratedValue
	int postId;
	String title;
	String content;
	String genre;
	String tag1;
	String tag2;
	
	
	@ManyToOne
	@JoinColumn(name = "userName")
	UserReg userreg;
	
	public LyricPost() {
		
	}
	public LyricPost(int postId, String title, String content, String genre, String tag1, String tag2) {
		super();
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.genre = genre;
		this.tag1 = tag1;
		this.tag2 = tag2;
		
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getTag1() {
		return tag1;
	}
	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}
	public String getTag2() {
		return tag2;
	}
	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}
	
	public UserReg getUserreg() {
		return userreg;
	}
	public void setUserreg(UserReg userreg) {
		this.userreg = userreg;
	}
	
	
	
	
}
