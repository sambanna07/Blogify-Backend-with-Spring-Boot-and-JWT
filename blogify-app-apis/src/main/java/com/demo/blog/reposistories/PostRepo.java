package com.demo.blog.reposistories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.blog.entity.Category;
import com.demo.blog.entity.Post;
import com.demo.blog.entity.User;
import com.demo.blog.payload.PostDTO;

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
	
	/**
	 * checking data with that title post is available or not
	 * @param postTitle
	 * @return Post resource if post title is already in database otherwise null
	 */
	Post findByPostTitle(String postTitle);
	
	/**
	 * searching the post based on post_title
	 * @param title
	 * @return Related postdtos based on keyword
	 */
	@Query("select p from Post p where p.postTitle like :key ")
	List<Post> searchPostByTitle(@Param("key")String title);
	

}
