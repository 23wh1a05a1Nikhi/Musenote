package com.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
@Entity
@Table(name = "user_follow")
@IdClass(UserFollowId.class)
public class UserFollow {

    @Id
    @Column(name = "user_reg_user_name")
    private String userRegUserName;

    @Id
    @Column(name = "following")
    private String following;

    // Constructors
    public UserFollow() {}

    public UserFollow(String userRegUserName, String following) {
        this.userRegUserName = userRegUserName;
        this.following = following;
    }

    // Getters and Setters
    public String getUserRegUserName() {
        return userRegUserName;
    }

    public void setUserRegUserName(String userRegUserName) {
        this.userRegUserName = userRegUserName;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }
}
