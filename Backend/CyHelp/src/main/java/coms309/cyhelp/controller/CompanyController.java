package coms309.cyhelp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
		return company; //change 
	}
	
}
