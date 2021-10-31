package coms309.cyhelp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import coms309.cyhelp.model.Tags;
import coms309.cyhelp.model.Category;
import coms309.cyhelp.controller.*;

public class TestTagsModel {
	@Test
	public void testAddTag() {
		
		Tags newTag = new Tags("Broken mirror");
		
		assertEquals(1, newTag.getId());
		assertEquals("Broken mirror", newTag.getTagName());
	}
	
}
