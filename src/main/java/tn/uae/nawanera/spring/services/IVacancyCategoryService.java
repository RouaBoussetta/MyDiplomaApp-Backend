package tn.uae.nawanera.spring.services;

import java.util.List;

 import tn.uae.nawanera.spring.entities.VacancyCategory;

public interface IVacancyCategoryService {
	public VacancyCategory createCateogry(VacancyCategory c);

	public List<VacancyCategory> retreiveAllCategories();

	public VacancyCategory getCategoryById(int id);

	public void removeCategory(int id);
}
