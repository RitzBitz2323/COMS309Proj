package coms309.cyhelp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coms309.cyhelp.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Company findById(int id);
}
