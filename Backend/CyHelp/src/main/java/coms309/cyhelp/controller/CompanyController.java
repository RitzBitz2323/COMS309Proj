package coms309.cyhelp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Company;
import coms309.cyhelp.service.CompanyService;
import io.swagger.annotations.Api;

@Api(value = "CompanyController", description = "Rest Controller for the Company Entity.")
@RestController
public class CompanyController {

	@Autowired
	CompanyService companyService;
	
	@PostMapping("/company")
	public @ResponseBody Company addCompany(@RequestBody Company company) {
		if(company == null) return null;
		return companyService.addCompany(company);
	}
	
	@GetMapping("/company/{id}/employees")
	public @ResponseBody List<Actor> getEmployees(@PathVariable int id) {
		return companyService.getEmployees(id);
	}
	
	@GetMapping("/company/{id}")
	public @ResponseBody Company getCompany(@PathVariable int id) {
		return companyService.getCompany(id);
	}
	
	@GetMapping("/companies")
	public @ResponseBody List<Company> getCompanies() {
		return companyService.getCompanies();
	}
	
	@PostMapping("/company/{id}/join")
	public @ResponseBody Actor addEmployee(@PathVariable int id, @RequestBody Actor actor) {
		return companyService.addEmployee(id, actor);
	}
}
