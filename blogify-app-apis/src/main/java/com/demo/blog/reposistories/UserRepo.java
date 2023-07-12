package com.demo.blog.reposistories;
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

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

}
