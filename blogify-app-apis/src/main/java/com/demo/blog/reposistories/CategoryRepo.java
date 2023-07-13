package com.demo.blog.reposistories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.blog.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
	Boolean existsByCategoryTitle(String categoryTitle);

}
