package coms309.cyhelp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import coms309.cyhelp.model.Category;
import coms309.cyhelp.repository.CategoryRepository;

@RestController
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;

	/**
	 * Gets all of the category in the repository.
	 * @return
	 */
	@GetMapping("/categories")
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	/**
	 * Returns the category with the specified id. 
	 * @param id
	 * @return
	 */
	@GetMapping("/categories/{id}")
	public Category getCategory(@PathVariable int id) {
		return categoryRepository.findById(id);
	}
	
}
