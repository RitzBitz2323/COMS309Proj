package coms309.cyhelp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Category;
import coms309.cyhelp.model.Tags;
import coms309.cyhelp.model.Ticket;
import coms309.cyhelp.repository.TagsRepository;

@RestController
public class TagsController {
	@Autowired
	TagsRepository tagsRepository;

	

	/**
	 * Gets all of the tags in the repository.
	 * @return
	 */
	@GetMapping("/tags")
	public List<Tags> getAllTags() {
		return tagsRepository.findAll();
	}
	
	/**
	 * Returns the tag with the specified id. 
	 * @param id
	 * @return
	 */
	@GetMapping("/tags/{id}")
	public Tags getTag(@PathVariable int id) {
		return tagsRepository.findById(id);
	}
	
	/**
	 * Create a tag with specified string
	 * @param name
	 * @return String
	 */
	
	
	/**
	 * Deletes a tag based on the tag provided by
	 * the RequestBody.
	 * @param tag
	 * @return String
	 */
	@DeleteMapping("/tags")
	public Map<String, String> deleteByRequestBody(@RequestBody Tags tag) {
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		if(tag == null) {
			result.put("message", "that tag doesn't exist.");
			return result;
		}
		
		return deleteByPathVariable(tag.getId());
	}
	
	
	/**
	 * Deletes the tag with the specified id.
	 * @param id
	 * @return
	 */
	@DeleteMapping("/tags/{id}")
	public Map<String, String> deleteByPathVariable(@PathVariable int id) {
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		Tags tag = tagsRepository.findById(id);
		if(tag == null) {
			result.put("message", "that tag doesn't exist.");
			return result;
		}
		
		tagsRepository.delete(tag);
		result.put("message", "success");
		
		return result;
	}
	
	
	
}
