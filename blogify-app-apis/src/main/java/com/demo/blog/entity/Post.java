package com.demo.blog.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * this class entity class of post
 * @author Samundar Singh Rathore
 *
 */
@Entity
@Table(name = "blog_posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Integer postId;
	
	@Column(name = "post_title",length = 100,nullable = false)
	private String postTitle;
	
	@Column(name = "post_content",nullable = false,length=5000)
	private String postContent;
	
	@Column(name = "post_image",nullable = false)
	private String postImage;
	
	@Column(name="creation_date")
	private Date postCreationDate;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<Comments> commnets=new HashSet<>();

}
