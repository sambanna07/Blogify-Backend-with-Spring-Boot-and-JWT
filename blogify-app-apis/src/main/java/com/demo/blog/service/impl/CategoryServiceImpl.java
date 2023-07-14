package com.demo.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.blog.entity.Category;
import com.demo.blog.exceptions.AlreadyExistException;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.exceptions.ServiceInternalException;
import com.demo.blog.payload.CategoryDTO;
import com.demo.blog.reposistories.CategoryRepo;
import com.demo.blog.services.CategoryService;

/**
 * implementation of CategoryService method
 * 
 * @author Samundar Singh Rathore
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Boolean existCategory = this.categoryRepo.existsByCategoryTitle(categoryDTO.getCategoryTitle());
		System.out.println(existCategory);
		if (existCategory) {
			throw new AlreadyExistException(760, "category", "title", categoryDTO.getCategoryTitle());
		}

		try {
			Category category = this.modelMapper.map(categoryDTO, Category.class);
			Category saveCategory = this.categoryRepo.save(category);
			return this.modelMapper.map(saveCategory, CategoryDTO.class);

		} catch (Exception e) {
			throw new ServiceInternalException(750,
					"internal problem in category service:createCategory method" + e.getMessage());
		}

	}

	@Override
	public CategoryDTO updateCategoryById(Integer categoryId, CategoryDTO categoryDTO) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(701, "category", "id", categoryId));
		try {
			category.setCategoryTitle(categoryDTO.getCategoryTitle());
			category.setCategoryDescription(categoryDTO.getCategoryDescription());

			Category updateCategory = this.categoryRepo.save(category);

			return this.modelMapper.map(updateCategory, CategoryDTO.class);
		} catch (Exception e) {
			throw new ServiceInternalException(750,
					"internal problem in category service:updateCategoryById method" + e.getMessage());
		}

	}

	@Override
	public CategoryDTO getCategoryById(Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(702, "category", "id", categoryId));

		try {
			return this.modelMapper.map(category, CategoryDTO.class);
		} catch (Exception e) {
			throw new ServiceInternalException(750,
					"internal problem in category service:getCategoryById method" + e.getMessage());
		}
	}

	@Override
	public List<CategoryDTO> getCategories() {
		List<Category> listOfCategories = this.categoryRepo.findAll();
		if (listOfCategories.size() == 0) {
			throw new ResourceNotFoundException(703, "categories");
		}
		try {
			List<CategoryDTO> listOfCategoriesDto = listOfCategories.stream()
					.map((category) -> this.modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
			return listOfCategoriesDto;
		} catch (Exception e) {
			throw new ServiceInternalException(750,
					"internal problem in category service:getCategories method" + e.getMessage());
		}

	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Boolean categoryExists = this.categoryRepo.existsById(categoryId);

		try {
			if (categoryExists) {
				this.categoryRepo.deleteById(categoryId);
			}
		} catch (Exception e) {
			throw new ServiceInternalException(750,
					"internal problem in category service:deleteCategory method" + e.getMessage());
		}

		if (!categoryExists) {
			throw new ResourceNotFoundException(704, "category", "id", categoryId);
		}
	}

}
