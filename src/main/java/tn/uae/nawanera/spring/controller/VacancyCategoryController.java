package tn.uae.nawanera.spring.controller;

import java.util.List;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.uae.nawanera.spring.entities.VacancyCategory;
import tn.uae.nawanera.spring.services.VacancyCategoryService;

@RestController
@RequestMapping("/api/vacancy-categories")
public class VacancyCategoryController {

	
	@Autowired
	VacancyCategoryService categoryservice;
	
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PostMapping("/add")
	public String addCategory(@RequestBody VacancyCategory category) {

		
		categoryservice.createCateogry(category);

		return category.getCategory()+" was successfully added";
	}
	
	@PermitAll
	@GetMapping("/retrieve-all-vacancy-categories")
	 
	public List<VacancyCategory> getAll( ) {

		return categoryservice.retreiveAllCategories();

	}
	
	@PermitAll
	@GetMapping("/retrieve-vacancy-category/{id}")
	@ResponseBody

	public  VacancyCategory  getCategory(@PathVariable("id") int id) {

 
		return categoryservice.getCategoryById(id);

	}
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@DeleteMapping("/remove-vacancy-category/{id}")
	public String removeAnswer(@PathVariable("id") int id){

		 categoryservice.removeCategory(id);
		return "category was successfuly removed  ";
	}

	
	
}
