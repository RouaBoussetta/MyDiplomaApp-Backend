package tn.uae.nawanera.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uae.nawanera.spring.entities.VacancyCategory;
import tn.uae.nawanera.spring.repositories.IVacancyCategoryRepository;

@Service
public class VacancyCategoryService implements IVacancyCategoryService{

	@Autowired
	IVacancyCategoryRepository categoryRepository;
	@Override
	public VacancyCategory createCateogry(VacancyCategory c) {
 
		return categoryRepository.save(c);
	}

	@Override
	public List<VacancyCategory> retreiveAllCategories() {
		List<VacancyCategory> categories = categoryRepository.findAll();

		if (!categories.isEmpty()) {
			return categories;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public VacancyCategory getCategoryById(int id) {
 
		return categoryRepository.findById(id);
	}

	@Override
	public void removeCategory(int id) {
		categoryRepository.deleteById(id);		
	}

	 

}
