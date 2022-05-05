package tn.uae.nawanera.spring.services;

 import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;

public interface IApplicationService {
 
 	 public Application acceptApplication(  int app  )  ;
	public List<User> getAllInternByVacancy(int vacancyId);
	public List<Vacancy> findVacanciesByIntern(int internId);
	
	public List<Application> findApplicationsByVacancy(Vacancy vacancy);
 
	void declineApplication(int id);

 
	Application findApplicationById(int id);
 	List<Application> retreiveApplicantSkillAssessments();
	List<Vacancy> retreiveAllAssignedVacancies();
	
 	Boolean isAppliedByIntern(int idIntern, int idVacancy);
	List<Application> findApplicationsByVacancyTitle(String title);
	List<Vacancy> findVacanciesByInternUsername(String username);
	List<User> getAllInternByVacancyTitle(String title);
	Application applyByTitle(Application app, MultipartFile file, String titleVacancy);
	Application applyById(Application app, MultipartFile file, int idvacancy);
	Application findApplicationByVacancy(int idvacancy);
 	
	
 

}
