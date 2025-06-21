package com.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.model.UserReg;

@Repository
public interface UserRegRepository extends JpaRepository<UserReg,String>{
	@Query("from UserReg where userName = :name")
	Optional<UserReg> findByName(@Param("name") String name);
	@Query("from UserReg where mail = :mail")
	Optional<UserReg> findByMail(@Param("mail") String mail);
	
	
	
}
