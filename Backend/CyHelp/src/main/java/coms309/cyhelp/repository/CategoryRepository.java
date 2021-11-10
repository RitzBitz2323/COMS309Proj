package coms309.cyhelp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import coms309.cyhelp.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	Category findById(int id);
	Category findByTitle(String title);
	
}