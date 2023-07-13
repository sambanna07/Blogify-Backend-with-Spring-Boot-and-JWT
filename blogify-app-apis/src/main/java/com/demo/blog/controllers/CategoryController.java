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
import org.springframework.web.bind.annotation.RestController;

import com.demo.blog.payload.CategoryDTO;
import com.demo.blog.services.CategoryService;

/**
 * it handle all request related to category
 * @author samundar singh rathore
 *
 */
@RestController
@RequestMapping(value= "/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * for creating resource of category
	 * @param categoryDTO
	 * @return saved category object with id
	 */
	@PostMapping(value = "/")
	public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
		CategoryDTO savedCategory=this.categoryService.createCategory(categoryDTO);
		return new ResponseEntity<>(savedCategory,HttpStatus.CREATED);	
	}
	
	/**
	 * for getting resource based on id
	 * @param categoryId
	 * @return category object
	 */
	@GetMapping(value = "/{id}/")
	public ResponseEntity<?> getCategory(@PathVariable(value="id") Integer categoryId){
		CategoryDTO getCategory=this.categoryService.getCategoryById(categoryId);
		return new ResponseEntity<>(getCategory,HttpStatus.FOUND);
	}
	
	/**
	 * for getting all saved categories 
	 * @return
	 */
	@GetMapping(value="/")
	public ResponseEntity<?> getCategories(){
		List<CategoryDTO> listOfCategories=this.categoryService.getCategories();
		return new ResponseEntity<>(listOfCategories,HttpStatus.FOUND);
	}
	
	@PutMapping(value="/{id}/")
	public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO,@PathVariable(value="id") Integer categoryId){
		CategoryDTO updatedCategory=this.categoryService.updateCategoryById(categoryId, categoryDTO);
		return new ResponseEntity<>(updatedCategory,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping(value="/{id}/")
	public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Integer categoryId){
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>("deleted sucessfully",HttpStatus.OK);
	}
	
	

}
