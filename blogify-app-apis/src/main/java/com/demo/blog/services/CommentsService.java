package com.demo.blog.services;
/**
 * abstract method for comments related service logic 
 * @author Samundar Singh Rathore
 *
 */

import com.demo.blog.payload.CommentsDTO;

public interface CommentsService {
	
	
	/**
	 * abstract method for save comment resource 
	 * @param commentsDTO
	 * @param postId
	 * @param userId
	 * @return newly created comment resource
	 */
	public CommentsDTO saveComment(CommentsDTO commentsDTO,Integer postId,Integer userId);
	
	/**
	 * abstract method for updating comment resource
	 * based on id
	 * @param commentId
	 * @return update comment resource
	 */
	public CommentsDTO updateComment(CommentsDTO commentsDTO,Integer commentId);
	
	/**
	 * abstract method for deleting comment resource
	 * based on id
	 * @param commentId
	 */
	public void deleteComment(Integer commentId);
	
	
	
 
}
