package tn.uae.nawanera.spring.services;

import java.util.List;

import tn.uae.nawanera.spring.entities.Degree;
import tn.uae.nawanera.spring.entities.Status;
import tn.uae.nawanera.spring.entities.Vacancy;
import tn.uae.nawanera.spring.entities.VacancyCategory;

public interface IVacancyService {

	public Vacancy createVacancy(Vacancy vacancy);

	public List<Vacancy> retreiveAllVacancies();

	public Vacancy getVacancyById(int id);
	public Vacancy getVacancyByCompany(String companyName);
	public Vacancy getVacancyByTitle(String title);

	public void deleteVacancyById(int vacancyId);

	int countVacancies();

	public List<Vacancy> getVacanciesPostedBy(String username);

	public List<Vacancy> getVacanciesByCategory(VacancyCategory category);

	public List<Vacancy> getVacanciesByStatus(Status status);

	public List<Vacancy> getVacanciesByDegree(Degree degree);

	public List<Vacancy> getPaidVacancies();

	public List<Vacancy> getActiveVacancies();

	public List<Vacancy> getClosedVacancies();

	public List<Vacancy> getDraftVacancies();

	public List<Vacancy> getArchievedVacancies();

	public List<Vacancy> getOwnVacancies();

	int countActiveVacancies();

	int countDraftVacancies();

	int countClosedVacancies();

	int countArchievedVacancies();

	public List<Vacancy> retrieveCompanyVacancies(String companyName);

	public List<Vacancy> retrieveCompanyVacancies();

	List<Vacancy> retrieveTrainerVacancies();

	Vacancy updateVacancy(Vacancy vacancy, int id);

	List<Vacancy> getCompanyVacanciesByCategory(String companyname, VacancyCategory category);

	public List<Vacancy> retrieveVacanciesTrainedBy();

	// List<Vacancy> retrieveVacanciesAsc();

}
