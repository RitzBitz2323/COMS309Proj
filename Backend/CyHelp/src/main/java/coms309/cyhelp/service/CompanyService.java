package coms309.cyhelp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Company;
import coms309.cyhelp.repository.ActorRepository;
import coms309.cyhelp.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	ActorRepository actorRepository;
	
	public Company addCompany(Company company) {
		
		if(findByUsername(company.getUsername()) == null) {
			
			Actor companyUser = new Actor(Actor.COMPANY, "", "", company.getUsername(), company.getPassword());
			companyUser = actorRepository.save(companyUser);
			
			company.setUserId(companyUser.getId());
			companyRepository.save(company);
			
			companyUser.setCompany(company);
			companyUser = actorRepository.save(companyUser);
			
			return company;
		}
		
		return null;
	}
	
	public List<Company> getCompanies() {
		return companyRepository.findAll();
	}
	
	public Company getCompany(int id) {
		try {
			return companyRepository.findById(id);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Actor> getEmployees(int id) {
		try {
			Company company = getCompany(id);
			return company.getEmployees();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Actor addEmployee(int id, Actor actor) {
		try {
			Company company = getCompany(id);
			Actor tech = actorRepository.findById(actor.getId());
			
			if(tech.getUserType() == Actor.TECHNICIAN && company != null) {
				
				tech.setCompany(company);
				company.addEmployees(tech);
				
				companyRepository.save(company);
				actorRepository.save(tech);
				
				return tech;
			
			} else return null;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Actor findByUsername(String username) {
		return actorRepository.findByUsername(username);
	}
}
