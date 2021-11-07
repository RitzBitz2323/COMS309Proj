package coms309.cyhelp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import coms309.cyhelp.model.Category;
import coms309.cyhelp.repository.CategoryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(value="Category Controller", description = "Rest controllery for Category entity")
@RestController
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;

	/**
	 * Gets all of the category in the repository.
	 * @return
	 */
	@ApiOperation(value="To obtain the list of all categories within the database.")
	@GetMapping("/categories")
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	/**
	 * Returns the category with the specified id. 
	 * @param id
	 * @return
	 */
	@ApiOperation(value="To obtain a specific category by an input of ID within the database.")
	@GetMapping("/categories/{id}")
	public Category getCategory(@PathVariable int id) {
		return categoryRepository.findById(id);
	}
	
}
