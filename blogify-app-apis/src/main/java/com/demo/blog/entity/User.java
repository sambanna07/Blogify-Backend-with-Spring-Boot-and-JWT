package com.demo.blog.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.demo.blog.payload.CommentsDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id",nullable = false)
	private int id;	
	
	@Column(name="user_name")
	private String name;
	
	@Column(name="user_email")
	private String email;
	
	@Column(name="user_password")
	private String password;
	
	@Column(name="user_about")
	private String about;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Post> posts=new ArrayList<>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<Comments> comments=new HashSet<>();



}
