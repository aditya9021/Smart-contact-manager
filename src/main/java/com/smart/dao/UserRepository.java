package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User , Integer>{
	
	//used to return user using email
	
	@Query(value = "select * from User e where u.email = :email", nativeQuery=true)
	public User getUserByUserName(@Param("email") String email);   
	}
