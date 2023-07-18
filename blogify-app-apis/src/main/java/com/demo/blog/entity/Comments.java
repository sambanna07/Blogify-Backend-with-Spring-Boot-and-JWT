package com.demo.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Comments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private int id;
	
	@Column(name = "comment_content")
	@Size(min =10,message = "for commnet minimum 10 words required")
	private String content;
	
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	

}
