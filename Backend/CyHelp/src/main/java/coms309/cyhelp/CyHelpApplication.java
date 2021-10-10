package coms309.cyhelp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import coms309.cyhelp.model.Category;
import coms309.cyhelp.repository.CategoryRepository;

@SpringBootApplication
public class CyHelpApplication {

	public static void main(String[] args) {
		SpringApplication.run(CyHelpApplication.class, args);
	}
	
	@Bean
	CommandLineRunner initCategory(CategoryRepository catRepo) {
		return args ->{
		Category cat1 = new Category("Plumber");
		Category cat2 = new Category("Electrical and Electronic Installer and Repairer");
		Category cat3 = new Category("Automotive Mechanic");
		Category cat4 = new Category("Computer and Office Machine Technician");
		Category cat5 = new Category("Heating, Air Conditioning, and Refrigeration Mechanic");
		Category cat6 = new Category("Home Appliance Repairer");
	
		catRepo.save(cat1);
		catRepo.save(cat2);
		catRepo.save(cat3);
		catRepo.save(cat4);
		catRepo.save(cat5);
		catRepo.save(cat6);
		};
	}
}