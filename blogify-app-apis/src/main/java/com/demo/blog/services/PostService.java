package com.demo.blog.services;
import java.util.List;

import com.demo.blog.payload.PostDTO;
import com.demo.blog.payload.PostResponse;
/**
 * service interface for post
 * @author Samundar Singh Rathore
 *
 */
public interface PostService {
	/**
	 * method for create post object in data-base
	 * @param postDTO
	 * @return Single PostDTO object
	 */
	PostDTO createPost(PostDTO postDTO,Integer UserId,Integer CategoryId);
	
	/**
	 * method for update post object in data-base
	 * @param postDTO
	 * @param postId
	 * @return Single PostDTO object
	 */
	PostDTO updatePost(PostDTO postDTO,Integer postId);
	
	/**
	 * method for delete post object in data-base
	 * @param postId
	 */
	void deletePostById(Integer postId);
	
	/**
	 * Retrieve single post based on id
	 * @param postId
	 * @return single PostDTO object
	 */
	PostDTO getPostById(Integer postId);
	
	/**
	 * fetch all available posts in database
	 * @return list of PostDTO objects
	 */
	PostResponse getAllPost(Integer pageNumber,Integer pageSize);
	
	/**
	 * fetch all post related to that category
	 * @param categoryId
	 * @return list of PostDTO objects
	 */
	List<PostDTO> getPostsByCategoryID(Integer categoryId);
	
	/**
	 * fetch all post related to that user
	 * @param userId
	 * @return list of PostDTO objects
	 */
	List<PostDTO> getPostsByUserId(Integer userId);
	

}
