package com.demo.blog.services;
import java.util.List;

import com.demo.blog.payload.CategoryDTO;
import com.demo.blog.payload.CategoryResponse;
/**
 * interface contains all category api service abstract methods
 * @author Samundar Singh Rathore
 *
 */
public interface CategoryService {
	
	/**
	 * by this method we can create categories
	 * @param categoryDTO
	 * @return
	 */
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	
	/**
	 * by this method we can update our category
	 * @param categoryId
	 * @param categoryDTO
	 * @return
	 */
	CategoryDTO updateCategoryById(Integer categoryId,CategoryDTO categoryDTO);
	
	/**
	 * get category by id
	 * @param categoryId
	 * @return
	 */
	CategoryDTO getCategoryById(Integer categoryId);
	
	/**
	 * get all categories
	 * @return
	 */
	CategoryResponse getAllCategory(Integer pageNumber,Integer pageSize);
	
	/**
	 * delete category by id
	 * @param categoryId
	 */
	void deleteCategory(Integer categoryId);
	
	

}
