package com.demo.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demo.blog.entity.Category;
import com.demo.blog.entity.Post;
import com.demo.blog.entity.User;
import com.demo.blog.exceptions.AlreadyExistException;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.exceptions.ServiceInternalException;
import com.demo.blog.payload.PostDTO;
import com.demo.blog.payload.PostResponse;
import com.demo.blog.reposistories.CategoryRepo;
import com.demo.blog.reposistories.PostRepo;
import com.demo.blog.reposistories.UserRepo;
import com.demo.blog.services.PostService;

/**
 * This is implementation class PostService interface
 * 
 * @author Samundar Singh Rathore
 *
 */
@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	/**
	 * this method used for create new post resource
	 * 
	 * @exception if post already available with same title then it throw
	 *               AlreadyExistException
	 * @exception if there is any exception during execution of service class then
	 *               it throwServiceInternalException
	 * @return returns PostDTO object to controller
	 */
	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
		Boolean postExistWithTitle = this.postRepo.existsByPostTitle(postDTO.getPostTitle());
		if (postExistWithTitle) {
			throw new AlreadyExistException(801, "Post", "post_title", postDTO.getPostTitle());
		}
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(815, "user", "user id", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(816, "category", "category id", categoryId));
		try {
			Post post = this.modelMapper.map(postDTO, Post.class);
			post.setPostImage("default.png");
			post.setPostCreationDate(new Date());
			post.setUser(user);
			post.setCategory(category);
			Post savedPost = this.postRepo.save(post);
			return this.modelMapper.map(savedPost, PostDTO.class);
		} catch (Exception e) {
			throw new ServiceInternalException(802,
					"something went wrong in PostService:createPost method" + e.getMessage());
		}

	}

	/**
	 * this method used for update post resource
	 * 
	 * @exception if post already available with same title then it throw
	 *               AlreadyExistException
	 * @exception if there is any exception during execution of service class then
	 *               it throwServiceInternalException
	 * @return returns PostDTO object to controller
	 */
	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(804, "post", "post_id", postId));
		;
		Optional<Post> postByTitle = Optional.ofNullable(this.postRepo.findByPostTitle(postDTO.getPostTitle()));
		if (postByTitle.isPresent()) {
			if (postByTitle.get().getPostId() != postId) {
				throw new AlreadyExistException(812, "Post", "post title", postDTO.getPostTitle());
			}
		}
		try {
			post.setPostTitle(postDTO.getPostTitle());
			post.setPostContent(postDTO.getPostContent());
			post.setPostImage(postDTO.getPostImage());
			Post updatedPost = this.postRepo.save(post);
			return this.modelMapper.map(updatedPost, PostDTO.class);
		} catch (Exception e) {
			throw new ServiceInternalException(803,
					"something went wrong in PostService:updatePost method" + e.getMessage());
		}
	}

	/**
	 * this method used for delete the resource based on the postid
	 * 
	 * @exception if post not available with id then it throw
	 *               ResourceNotFoundException
	 * @exception if there is any exception during execution of service class then
	 *               it throwServiceInternalException
	 * @return void
	 */
	@Override
	public void deletePostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(805, "post", "post_id", postId));
		try {
			this.postRepo.delete(post);
		} catch (Exception e) {
			throw new ServiceInternalException(806,
					"\"something went wrong in PostService:deletePost method" + e.getMessage());
		}

	}

	/**
	 * this method used for get resource based on id
	 * 
	 * @exception if post not available with id then it throw
	 *               ResourceNotFoundException
	 * @exception if there is any exception during execution of service class then
	 *               it throwServiceInternalException
	 * @return returns PostDTO object to controller
	 */
	@Override
	public PostDTO getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(807, "post", "post_id", postId));
		try {
			return this.modelMapper.map(post, PostDTO.class);
		} catch (Exception e) {
			throw new ServiceInternalException(808,
					"something went wrong in PostService:getPostById method" + e.getMessage());
		}
	}

	/**
	 * this method used for get all post saved in data base
	 * 
	 * @exception if post not available then it throw ResourceNotFoundException
	 * @exception if there is any exception during execution of service class then
	 *               it throwServiceInternalException
	 * @return returns list of PostDTO object to controller
	 */
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		if (pagePost.isEmpty() && pagePost.isLast() == true) {
			throw new ResourceNotFoundException(809, "please give correct page number,In this there is no data");
		}
		if (pagePost.isEmpty() && pagePost.isFirst() == true) {
			throw new ResourceNotFoundException(809, "In database there is no post,please try after some time");
		}
		try {
			List<Post> posts = pagePost.getContent();
			List<PostDTO> listOfPostDTO = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
					.collect(Collectors.toList());
			PostResponse postResponse = new PostResponse();
			postResponse.setPostContent(listOfPostDTO);
			// current page number
			postResponse.setPageNumber(pagePost.getNumber());
			// current page size means elements in that particular page
			postResponse.setNumberOfElements(pagePost.getNumberOfElements());
			// total element in pageable object
			postResponse.setTotalElements(pagePost.getTotalElements());
			// total number of page
			postResponse.setTotalpages(pagePost.getTotalPages());
			// is this last page
			postResponse.setLastPage(pagePost.isLast());
			// is this is first page
			postResponse.setFirstPage(pagePost.isFirst());
			return postResponse;
		} catch (Exception e) {
			throw new ServiceInternalException(810,
					"something went wrong in PostService:getAllPost method" + e.getMessage());
		}
	}

	/**
	 * this method used for get resource based on category id
	 * 
	 * @exception if post not available with id then it throw
	 *               ResourceNotFoundException
	 * @exception if there is any exception during execution of service class then
	 *               it throwServiceInternalException
	 * @return returns list of PostDTO object to controller
	 */
	@Override
	public List<PostDTO> getPostsByCategoryID(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(816, "category", "category id", categoryId));
		List<Post> listOfPostByCategory = this.postRepo.findByCategory(category);
		if (listOfPostByCategory.size() == 0) {
			throw new ResourceNotFoundException(811,
					"for " + category.getCategoryTitle() + " ,there is no active post");
		}
		try {
			List<PostDTO> listOfPostDTOByCategory = listOfPostByCategory.stream()
					.map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
			return listOfPostDTOByCategory;
		} catch (Exception e) {
			throw new ServiceInternalException(812,
					"something went wrong in PostService:getPostsByCategory method" + e.getMessage());
		}
	}

	/**
	 * this method used for update post resource
	 * 
	 * @exception if post not available with id then it throw
	 *               ResourceNotFoundException
	 * @exception if there is any exception during execution of service class then
	 *               it throwServiceInternalException
	 * @return returns list of PostDTO object to controller
	 */
	@Override
	public List<PostDTO> getPostsByUserId(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(815, "User", "user id", userId));
		List<Post> listOfPostByUser = this.postRepo.findByUser(user);
		if (listOfPostByUser.size() == 0) {
			throw new ResourceNotFoundException(813, "yet " + user.getName() + " not post any blog");
		}
		try {
			List<PostDTO> listOfPostDTOByUser = listOfPostByUser.stream()
					.map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
			return listOfPostDTOByUser;
		} catch (Exception e) {
			throw new ServiceInternalException(814,
					"something went wrong in PostService:getPostsByUser method" + e.getMessage());
		}

	}

	/**
	 * this method used for search post resource based on keyword
	 * 
	 * @exception if post not available with keyword then it throw
	 *               ResourceNotFoundException
	 * @return returns list of PostDTO object to controller based on given keyword
	 */
	@Override
	public List<PostDTO> searchPostByTitle(String keyword) {
		List<Post> postBasedOnSearching = this.postRepo.searchPostByTitle("%"+keyword+"%");
		if(postBasedOnSearching.size()==0) {
			throw new ResourceNotFoundException(815,"there is no data with matching post title :"+keyword);
		}
		List<PostDTO> postDTOs = postBasedOnSearching.stream().map(post -> this.modelMapper.map(post,PostDTO.class)).collect(Collectors.toList());
		return postDTOs;
	}



}
