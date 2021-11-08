package coms309.cyhelp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import coms309.cyhelp.model.Category;
import coms309.cyhelp.repository.CategoryRepository;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EntityScan(basePackages = {"coms309.cyhelp.model"})
@EnableJpaRepositories(basePackages = {"coms309.cyhelp.repository"})
@ComponentScan(basePackages = {"coms309.cyhelp.controller","coms309.cyhelp.service"})
public class CyHelpApplication {

	public static void main(String[] args) {
		SpringApplication.run(CyHelpApplication.class, args);
	}
	
	@Bean
	CommandLineRunner initCategory(CategoryRepository catRepo) {
		return args ->{
		
			String[] categories = {
					"None",
					"Plumber",
					"Electrical and Electronic Installer and Repairer",
					"Automotive Mechanic",
					"Computer and Office Machine Technician",
					"Heating, Air Conditioning, and Refrigeration Mechanic",
					"Home Appliance Repairer"
			};
		
			for(String category : categories) {
				Category cat = new Category(category);
				if(catRepo.findByTitle(cat.getTitle()) == null) 
					catRepo.save(cat);
			}
			
		};
	}
	
	@Bean
    public Docket getAPIdocs() {
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build(); 
    }

}