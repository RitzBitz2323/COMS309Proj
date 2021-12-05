package coms309.cyhelp.service;

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
			actorRepository.save(companyUser);
			
			company.setUserId(companyUser.getId());
			
			companyRepository.save(company);
			
			return company;
		}
		
		return null;
	}
	
	public Actor findByUsername(String username) {
		return actorRepository.findByUsername(username);
	}
}
