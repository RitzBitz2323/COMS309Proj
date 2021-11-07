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
@ComponentScan(basePackages = {"coms309.cyhelp.controller"})
public class CyHelpApplication {

	public static void main(String[] args) {
		SpringApplication.run(CyHelpApplication.class, args);
	}
	
	@Bean
	CommandLineRunner initCategory(CategoryRepository catRepo) {
		return args ->{
		Category cat0 = new Category("None");
		Category cat1 = new Category("Plumber");
		Category cat2 = new Category("Electrical and Electronic Installer and Repairer");
		Category cat3 = new Category("Automotive Mechanic");
		Category cat4 = new Category("Computer and Office Machine Technician");
		Category cat5 = new Category("Heating, Air Conditioning, and Refrigeration Mechanic");
		Category cat6 = new Category("Home Appliance Repairer");
		
		catRepo.save(cat0);
		catRepo.save(cat1);
		catRepo.save(cat2);
		catRepo.save(cat3);
		catRepo.save(cat4);
		catRepo.save(cat5);
		catRepo.save(cat6);
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