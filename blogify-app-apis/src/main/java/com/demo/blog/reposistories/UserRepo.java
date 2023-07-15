package com.demo.blog.reposistories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.blog.entity.User;


/**
 * 
 * @author samundar_singh_rathre
 *
 *userrepo class which extends the JpaRepository
 *
 *this give all the methods like save,findbyid.findall
 */


public interface UserRepo extends JpaRepository<User, Integer> {
	
	public boolean existsByEmail(String email);
	
	User findByEmail(String email);

}
