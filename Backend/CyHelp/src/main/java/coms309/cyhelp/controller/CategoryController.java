package coms309.cyhelp.controller;

/**
* This is the Tags model class.
* @author Ritvik Ambekar, Brandon Schumacher
* 
*/
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.cyhelp.model.Category;
import coms309.cyhelp.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(value="Category Controller", description = "Rest controller for Category entity")
@RestController
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	/**
	 * Gets all of the category in the repository.
	 * @return list of categories
	 */
	@ApiOperation(value="To obtain the list of all categories within the database.")
	@GetMapping("/categories")
	public @ResponseBody List<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}
	
	/**
	 * Returns the category with the specified id. 
	 * @param id
	 * @return category
	 */
	@ApiOperation(value="To obtain a specific category by an input of ID within the database.")
	@GetMapping("/categories/{id}")
	public @ResponseBody Category getCategory(@PathVariable int id) {
		return categoryService.getCategory(id);
	}
	
}
