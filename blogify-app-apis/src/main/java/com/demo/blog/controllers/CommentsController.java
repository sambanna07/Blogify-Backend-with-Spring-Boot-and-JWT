package com.demo.blog.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Rest Controller for comment api
 * @author Samundar Singh rathore
 *
 */

import com.demo.blog.payload.CommentsDTO;
import com.demo.blog.services.CommentsService;

@RestController
@RequestMapping(value = "api")
public class CommentsController {
	
	@GetMapping(value = "/comment")
	public String test() {
		return "working";
	}

	@Autowired
	private CommentsService commentsService;

	/**
	 * End point for save the comment based on userid and post id
	 * 
	 * @param userId
	 * @param postId
	 * @param commentsDTO
	 * @return saved comment resource
	 */
	@PostMapping(value = "/user/{userId}/post/{postId}/comment/")
	public ResponseEntity<CommentsDTO> saveComment(@PathVariable(name = "userId") Integer userId,
			@PathVariable(name = "postId") Integer postId, @RequestBody @Valid CommentsDTO commentsDTO) {
		CommentsDTO savedComment = this.commentsService.saveComment(commentsDTO, postId, userId);
		return new ResponseEntity<CommentsDTO>(savedComment, HttpStatus.CREATED);
	}

	/**
	 * End point for updating the existing comment with the comment id
	 * @param commentId
	 * @param commentsDTO
	 * @return updated comment resource
	 */
	@PutMapping(value = "/commentId/{commentId}/")
	public ResponseEntity<CommentsDTO> updateComment(@PathVariable(name = "commentId") Integer commentId,
			@RequestBody CommentsDTO commentsDTO) {
		CommentsDTO updatedComment = this.commentsService.updateComment(commentsDTO, commentId);
		return new ResponseEntity<CommentsDTO>(updatedComment,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deletecomment/{commentId}/")
	public ResponseEntity<String> deleteComment(@PathVariable(name = "commentId")Integer commentId){
		this.commentsService.deleteComment(commentId);
		return new ResponseEntity<String>("comment deleted successfully",HttpStatus.OK);
	}

}
