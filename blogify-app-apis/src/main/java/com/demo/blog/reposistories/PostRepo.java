package com.demo.blog.reposistories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.blog.entity.Category;
import com.demo.blog.entity.Post;
import com.demo.blog.entity.User;

public interface PostRepo extends JpaRepository<Post,Integer> {
	
	/**
	 * fetching the all post related to that user
	 * @param user
	 * @return list of posts related to that user
	 */
	List<Post> findByUser(User user);
	
	/**
	 * fetching the all post related to that category
	 * @param category
	 * @return list of posts related to that category
	 */
	List<Post> findByCategory(Category category);
	
	/**
	 * checking data with that title post is available or not
	 * @param postTitle
	 * @return true if post is already in database otherwise false
	 */
	Boolean existsByPostTitle(String postTitle);

}
