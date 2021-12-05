package coms309.cyhelp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms309.cyhelp.model.Tags;
import coms309.cyhelp.repository.TagsRepository;

@Service
public class TagsService {

	@Autowired
	TagsRepository tagsRepository;	
	
	public List<Tags> getAllTags() {
		return tagsRepository.findAll();
	}
		
	public Tags getTag(int id) {
		return tagsRepository.findById(id);
	}
	
	public Tags createTag(Tags newTag) {
		
		// if tag is empty will return null
		if(newTag == null) return null;
				
		Tags tag = new Tags(newTag.getTagName());
		tagsRepository.save(tag);
		
		return tag;
	}
	
	public HashMap<String, String> deleteTag(Tags tag) {
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		if(tag == null) {
			result.put("message", "that tag doesn't exist.");
			return result;
		}
		
		return deleteById(tag.getId());
	}
	
	public HashMap<String, String> deleteById(int id) {

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
