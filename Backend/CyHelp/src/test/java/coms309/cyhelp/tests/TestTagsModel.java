package coms309.cyhelp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import coms309.cyhelp.model.Tags;

public class TestTagsModel {
	@Test
	public void testAddTag() {
		
		Tags newTag = new Tags("Broken mirror");
		
		assertEquals(0, newTag.getId());
		assertEquals("Broken mirror", newTag.getTagName());
	}
	
}
