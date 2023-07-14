package com.demo.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.blog.payload.PostDTO;
import com.demo.blog.services.PostService;

@RestController
@RequestMapping(value = "api/post")
public class PostController {

	@Autowired
	private PostService postService;

	/**
	 * Endpoint for creating a new post resource
	 * 
	 * @param userId     The ID of the user
	 * @param categoryId The ID of the category
	 * @param postDTO    The post data
	 * @return The created post object with ID
	 */
	@RequestMapping(value = "/userid/{userId}/categoryid/{categoryId}/")
	public ResponseEntity<PostDTO> createPost(@PathVariable(value = "userId") Integer userId,
			@PathVariable(value = "categoryId") Integer categoryId, @Valid @RequestBody PostDTO postDTO) throws Exception{
		PostDTO newPost = this.postService.createPost(postDTO, userId, categoryId);
		return new ResponseEntity<PostDTO>(newPost, HttpStatus.CREATED);

	}

	/**
	 * Endpoint for getting post resource by postId
	 * 
	 * @param postId
	 * @return Post resource of provided postId
	 */
	@GetMapping(value = "/{postid}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable(value = "postid") Integer postId)throws Exception {
		PostDTO post = this.postService.getPostById(postId);
		return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
	}

	/**
	 * Endpoint for getting all post resource
	 * 
	 * @return All Post resource saved in database
	 */
	@GetMapping(value = "/")
	public ResponseEntity<List<PostDTO>> getAllPost()throws Exception {
		List<PostDTO> posts = this.postService.getAllPost();
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}

	/**
	 * Endpoint for getting post resource of individual user
	 * 
	 * @param userId
	 * @return List of Post resource of provided userId
	 */
	@GetMapping(value = "/user/{userid}/")
	public ResponseEntity<List<PostDTO>> getPostByUserId(@PathVariable(value = "userid") Integer userId)throws Exception {
	    List<PostDTO> postOfUser = this.postService.getPostsByUserId(userId);
	    return new ResponseEntity<List<PostDTO>>(postOfUser, HttpStatus.OK);
	}
	
	/**
	 * Endpoint for getting post resource of individual category
	 * 
	 * @param userId
	 * @return List of Post resource of provided userId
	 */
	@GetMapping(value = "/category")
	public ResponseEntity<List<PostDTO>> getPostByCategoryId(@RequestParam(value = "categoryid") Integer categoryId) {
	    List<PostDTO> postOfCategory = this.postService.getPostsByCategoryID(categoryId);
	    return new ResponseEntity<List<PostDTO>>(postOfCategory, HttpStatus.OK);
	}
	
	/**
	 * EndPoint for update the post resource based on id
	 * @param postDTO
	 * @param postId
	 * @return updated post resource
	 */
	@PutMapping(value = "/{postid}")
	public ResponseEntity<PostDTO> updatePostById(@Valid @RequestBody PostDTO postDTO,@PathVariable(value = "postid") Integer postId){
		PostDTO updatedPost=this.postService.updatePost(postDTO, postId);
		return new ResponseEntity<PostDTO>(updatedPost,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{postid}/")
	public ResponseEntity<String> deletePostById(@PathVariable(value="postid") Integer postId){
		this.postService.deletePostById(postId);
		return new ResponseEntity<String>("post deleted",HttpStatus.OK);
	}

}
