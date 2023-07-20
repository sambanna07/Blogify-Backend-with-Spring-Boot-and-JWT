package com.demo.blog.reposistories;
import java.util.List;
import java.util.Optional;

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
	
	Optional<User> findByEmail(String email);
	
	List<User> findByNameContaining(String name);

}
