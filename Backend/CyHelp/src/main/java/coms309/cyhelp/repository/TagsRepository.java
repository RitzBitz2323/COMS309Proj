package coms309.cyhelp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coms309.cyhelp.model.Tags;

public interface TagsRepository extends JpaRepository<Tags, Integer> {

		Tags findById(int id);
}