package com.demo.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.blog.entity.Comments;
import com.demo.blog.entity.Post;
import com.demo.blog.entity.User;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.payload.CommentsDTO;
import com.demo.blog.reposistories.CommentsRepo;
import com.demo.blog.reposistories.PostRepo;
import com.demo.blog.reposistories.UserRepo;
import com.demo.blog.services.CommentsService;

/**
 * all implementation of comment service interface
 * 
 * @author Samundar Singh Rathore
 *
 */
@Service
public class CommentsServiceImpl implements CommentsService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentsRepo commentsRepo;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * implementation of save comment
	 * @param comment dto resource
	 * @param Integer postId
	 * @param Integer userId
	 * @return saved comment dto resource
	 * 
	 */
	@Override
	public CommentsDTO saveComment(CommentsDTO commentsDTO, Integer postId, Integer userId) {
		Post post = this.postRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(650, "Post", "postid", userId));
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(651, "User", "userid", userId));

		String commentContent = commentsDTO.getContent();
		boolean commentExists = user.getComments().stream()
				.anyMatch(comment -> comment.getContent().equals(commentContent));

		if (commentExists) {
			throw new ResourceNotFoundException(652,
					"There is already a comment with the same content. Please provide a different comment.");
		}

		Comments comment = this.modelMapper.map(commentsDTO, Comments.class);
		comment.setPost(post);
		comment.setUser(user);
		Comments savedComment = this.commentsRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentsDTO.class);
	}

	/**
	 * implementation of update the comment resource
	 * @param commentdto resource
	 * @param Integer commentiD
	 * @return Updated commentdto resource
	 */
	@Override
	public CommentsDTO updateComment(CommentsDTO commentsDTO, Integer commentId) {
		Comments comment = this.commentsRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(653, "Comment", "commentid", commentId));
		comment.setContent(commentsDTO.getContent());
		Comments updatedComment = this.commentsRepo.save(comment);
		return this.modelMapper.map(updatedComment, CommentsDTO.class);
	}

	/**
	 * implementation of delete the post based on comment
	 * @param Integer commentId
	 */
	@Override
	public void deleteComment(Integer commentId) {
		Comments comment = this.commentsRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(654, "Comment", "commentid", commentId));
		this.commentsRepo.delete(comment);
	}

}
