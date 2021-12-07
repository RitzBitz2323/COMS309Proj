package coms309.cyhelp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Company;
import coms309.cyhelp.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CompanyController", description = "Rest Controller for the Company Entity.")
@RestController
public class CompanyController {

	@Autowired
	CompanyService companyService;
	
	@ApiOperation(value = "Add a new company to the repository.")
	@PostMapping("/company")
	public @ResponseBody Company addCompany(@RequestBody Company company) {
		if(company == null) return null;
		return companyService.addCompany(company);
	}
	
	@ApiOperation(value = "Gets a list of employees of a specified company.")
	@GetMapping("/company/{id}/employees")
	public @ResponseBody List<Actor> getEmployees(@PathVariable int id) {
		return companyService.getEmployees(id);
	}
	
	@ApiOperation(value = "Get the information of a certain company.")
	@GetMapping("/company/{id}")
	public @ResponseBody Company getCompany(@PathVariable int id) {
		return companyService.getCompany(id);
	}
	
	@ApiOperation(value = "Gets of a the companies.")
	@GetMapping("/companies")
	public @ResponseBody List<Company> getCompanies() {
		return companyService.getCompanies();
	}
	
	@ApiOperation(value = "Adds employees into companies.")
	@PostMapping("/company/{id}/join")
	public @ResponseBody Actor addEmployee(@PathVariable int id, @RequestBody Actor actor) {
		return companyService.addEmployee(id, actor);
	}
}
