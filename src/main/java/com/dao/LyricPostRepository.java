package com.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.model.LyricPost;

@Repository
public interface LyricPostRepository extends JpaRepository<LyricPost,Integer>{

	@Query("from LyricPost where postId = :postID")
	LyricPost getByID(@Param("postID") int postID);
	
	@Query("from LyricPost where tag1 = :tagName or tag2 = :tagName")
	List<LyricPost> getByTag(@Param("tagName") String tagName);
	
	@Query("from LyricPost where genre = :gnName")
	List<LyricPost> getByGenre(@Param("gnName") String gnName);
	
	
	@Query("from LyricPost where userreg.userName = :userName")
	List<LyricPost> getByUser(@Param("userName") String userName);
	
	@Query("from LyricPost where lower(title) like lower(concat('%', :title, '%'))")
	List<LyricPost> getByTitle(@Param("title") String title);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM LyricPost p WHERE p.userreg.userName = :username")
	void deleteByUserName(@Param("username") String username);


}
