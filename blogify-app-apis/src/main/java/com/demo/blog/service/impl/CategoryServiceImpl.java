package com.demo.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.blog.entity.Category;
import com.demo.blog.exceptions.AlreadyExistException;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.exceptions.ServiceInternalException;
import com.demo.blog.payload.CategoryDTO;
import com.demo.blog.payload.CategoryResponse;
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
		Optional<Category> categoryByTitle=Optional.ofNullable(this.categoryRepo.findByCategoryTitle(categoryDTO.getCategoryTitle()));
		if(categoryByTitle.isPresent()) {
			Category categoryOptional= categoryByTitle.get();
			if(categoryOptional.getCategoryId()!=categoryId) {
				throw new AlreadyExistException(750,"Category", "category title",categoryDTO.getCategoryTitle());
			}
		}
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
	public CategoryResponse getAllCategory(Integer pageNumber,Integer pageSize) {
		Pageable pageable=PageRequest.of(pageNumber,pageSize);
		Page<Category> pageData = this.categoryRepo.findAll(pageable);

		if (pageData.getTotalElements() == 0) {
			throw new ResourceNotFoundException(703, "There is no category available in database");
		}
		if (pageData.getTotalElements() != 0 && pageData.isEmpty()==true) {
			throw new ResourceNotFoundException(703, "You are on wrong page,Please go to right page");
		}
		try {
			CategoryResponse category=new CategoryResponse();
			List<Category> content = pageData.getContent();
			System.out.println(content);
			List<CategoryDTO> listOfCategoriesDto = content.stream()
					.map((cat) -> this.modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
			//set content
			category.setCategoryContent(listOfCategoriesDto);
			//set page number
			category.setPageNumber(pageData.getNumber());
			//set number of elements in that page
			category.setNumberOfElements(pageData.getNumberOfElements());
			//set total elements in data base
			category.setTotalElements(pageData.getTotalElements());
			//set total pages
			category.setTotalpages(pageData.getTotalPages());
			//set that is first page or not
			category.setFirstPage(pageData.isFirst());
			//set that is last page or not
			category.setLastPage(pageData.isLast());
			
			return category;
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
