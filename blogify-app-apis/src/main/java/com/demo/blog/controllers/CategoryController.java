package com.demo.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.demo.blog.payload.CategoryDTO;
import com.demo.blog.payload.CategoryResponse;
import com.demo.blog.services.CategoryService;

/**
 * it handle all request related to category
 * 
 * @author samundar singh rathore
 *
 */
@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * EndPoint for creating resource of category
	 * 
	 * @param categoryDTO
	 * @return newly saved category resource with id
	 */
	@PostMapping(value = "/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws Exception {
		CategoryDTO savedCategory = this.categoryService.createCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(savedCategory, HttpStatus.CREATED);
	}

	/**
	 * EndPoint for getting resource based on categoryId
	 * 
	 * @param categoryId
	 * @return category resource based on provided categoryId
	 */
	@GetMapping(value = "/{id}/")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable(value = "id") Integer categoryId) throws Exception {
		CategoryDTO getCategory = this.categoryService.getCategoryById(categoryId);
		return new ResponseEntity<CategoryDTO>(getCategory, HttpStatus.FOUND);
	}

	/**
	 * EndPoint for getting all saved categories resource
	 * 
	 * @return list of all saved resources
	 */
	@GetMapping(value = "/categories")
	public ResponseEntity<CategoryResponse> getCategories(@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,@RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize) throws Exception {
		CategoryResponse response= this.categoryService.getAllCategory(pageNumber, pageSize);
		return new ResponseEntity<CategoryResponse>(response, HttpStatus.FOUND);
	}

	/**
	 * EndPoint for update category resource
	 * 
	 * @param categoryDTO
	 * @param categoryId
	 * @return updated category resource
	 */
	@PutMapping(value = "/{id}/")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO,
			@PathVariable(value = "id") Integer categoryId) throws Exception {
		CategoryDTO updatedCategory = this.categoryService.updateCategoryById(categoryId, categoryDTO);
		return new ResponseEntity<CategoryDTO>(updatedCategory, HttpStatus.ACCEPTED);
	}

	/**
	 * EndPoint for delete resource by id
	 * 
	 * @param categoryId
	 * @return response deleted successfully
	 */
	@DeleteMapping(value = "/{id}/")
	public ResponseEntity<String> deleteCategory(@PathVariable(value = "id") Integer categoryId) throws Exception {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<String>("deleted successfully", HttpStatus.OK);
	}

}
