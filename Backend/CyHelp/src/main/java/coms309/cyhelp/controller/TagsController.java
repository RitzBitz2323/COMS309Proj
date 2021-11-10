package coms309.cyhelp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.cyhelp.model.Tags;
import coms309.cyhelp.service.TagsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* This is the Tags model class.
* @author Ritvik Ambekar, Brandon Schumacher
* 
*/

@RestController
@Api(value="Tags Controller", description = "Rest controller for Tags entity")
public class TagsController {
	
	@Autowired
	TagsService tagsService;
	

	/**
	 * Gets all of the tags in the repository.
	 * @return list of tags
	 */
	@ApiOperation(value="To obtain the list of all tags within the database.")
	@GetMapping("/tags")
	public @ResponseBody List<Tags> getAllTags() {
		return tagsService.getAllTags();
	}
	
	/**
	 * Returns the tag with the specified id. 
	 * @param id
	 * @return tag
	 */
	@ApiOperation(value="To obtain a specific tag by an input of ID within the database.")
	@GetMapping("/tags/{id}")
	public Tags getTag(@PathVariable int id) {
		return tagsService.getTag(id);
	}
	
	/**
	 * Create a tag with specified string
	 * @param tag
	 * @return String
	 */
	@ApiOperation(value="To create a specific tag by an input of a already created tag.")
	@PostMapping("/tags")
	public @ResponseBody Tags createTag(@RequestBody Tags input_tag) {
		return tagsService.createTag(input_tag);
	}
	
	/**
	 * Deletes a tag based on the tag provided by
	 * the RequestBody.
	 * @param tag
	 * @return String
	 */
	@ApiOperation(value="To delete a specific tag by speciying the tag to delete.")
	@DeleteMapping("/tags")
	public Map<String, String> deleteByRequestBody(@RequestBody Tags tag) {
		return tagsService.deleteTag(tag);
	}
	
	
	/**
	 * Deletes the tag with the specified id.
	 * @param id
	 * @return if it is success or it doesnt exist
	 */
	@ApiOperation(value="To delete a specific tag by speciying the id in which the tag is to delete.")
	@DeleteMapping("/tags/{id}")
	public Map<String, String> deleteByPathVariable(@PathVariable int id) {
		return tagsService.deleteById(id);
	}
	
	
	
}
