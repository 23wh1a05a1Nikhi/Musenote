package com.model;

import java.io.Serializable;
import java.util.Objects;

public class UserFollowId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userRegUserName;
    private String following;

    public UserFollowId() {}

    public UserFollowId(String userRegUserName, String following) {
        this.userRegUserName = userRegUserName;
        this.following = following;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFollowId)) return false;
        UserFollowId that = (UserFollowId) o;
        return Objects.equals(userRegUserName, that.userRegUserName) &&
               Objects.equals(following, that.following);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRegUserName, following);
    }
}
