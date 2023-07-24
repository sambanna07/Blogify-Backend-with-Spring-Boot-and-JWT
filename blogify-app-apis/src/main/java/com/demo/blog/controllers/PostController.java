package com.demo.blog.controllers;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.blog.configurations.AppConstants;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.payload.PostDTO;
import com.demo.blog.payload.PostResponse;
import com.demo.blog.services.FileService;
import com.demo.blog.services.PostService;

@RestController
@RequestMapping(value = "api/post")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;
	
//	@Value("${application.image}")
	private String path="image/";


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
			@PathVariable(value = "categoryId") Integer categoryId, @Valid @RequestBody PostDTO postDTO)
			throws Exception {
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
	public ResponseEntity<PostDTO> getPostById(@PathVariable(value = "postid") Integer postId) throws Exception {
		PostDTO post = this.postService.getPostById(postId);
		return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
	}

	/**
	 * Endpoint for getting all post resource
	 * 
	 * @return All Post resource saved in database
	 */
	@GetMapping(value = "/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_POSTID, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir)
			throws Exception {
		PostResponse posts = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}

	/**
	 * Endpoint for getting post resource of individual user
	 * 
	 * @param userId
	 * @return List of Post resource of provided userId
	 */
	@GetMapping(value = "/user/{userid}/")
	public ResponseEntity<List<PostDTO>> getPostByUserId(@PathVariable(value = "userid") Integer userId)
			throws Exception {
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
	 * 
	 * @param postDTO
	 * @param postId
	 * @return updated post resource
	 */
	@PutMapping(value = "/{postid}")
	public ResponseEntity<PostDTO> updatePostById(@Valid @RequestBody PostDTO postDTO,
			@PathVariable(value = "postid") Integer postId) {
		PostDTO updatedPost = this.postService.updatePost(postDTO, postId);
		return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
	}

	/**
	 * EndPoin for delete the post resource based on id
	 * 
	 * @param postId
	 * @return success message
	 */
	@DeleteMapping(value = "/{postid}/")
	public ResponseEntity<String> deletePostById(@PathVariable(value = "postid") Integer postId) {
		this.postService.deletePostById(postId);
		return new ResponseEntity<String>("post deleted", HttpStatus.OK);
	}

	/**
	 * getting post based on keyword given by user
	 * 
	 * @param postTitle
	 * @return list of postDTOs
	 */
	@GetMapping("/search")
	public ResponseEntity<List<PostDTO>> seachPostsTtile(@RequestParam(value = "postTitle") String postTitle) {
		List<PostDTO> searchPostByTitle = this.postService.searchPostByTitle(postTitle);
		return new ResponseEntity<List<PostDTO>>(searchPostByTitle, HttpStatus.OK);
	}

	/**
	 * End Point for saving image based on postId
	 * 
	 * @param image
	 * @param postId
	 * @return Update PostDTO resource
	 */
	@PostMapping(value = "/image/upload/{postId}/")
	public ResponseEntity<PostDTO> uploadPostImage(@RequestBody MultipartFile image,
			@PathVariable(value = "postId") Integer postId) {
		PostDTO postDTO = this.postService.getPostById(postId);
		try {
			String fileName = this.fileService.uploadImage(path, image);
			postDTO.setPostImage(fileName);
			PostDTO updatedPost = this.postService.updatePost(postDTO, postId);
			return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			throw new ResourceNotFoundException(1000, "something wnet wrong in post controller");
		}

	}

	/**
	 * End point for getting image
	 * @param imageName
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public String getImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getFileResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		return "success";
	}

}
