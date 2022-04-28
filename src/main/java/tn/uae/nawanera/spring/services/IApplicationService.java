package tn.uae.nawanera.spring.services;

 import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.Notification;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;

public interface IApplicationService {
 
 	 public Application acceptApplication(  int app  )  ;
	public List<User> getAllInternByVacancy(int vacancyId);
	public List<Vacancy> findVacanciesByIntern(int internId);
	
	public List<Application> findApplicationsByVacancy(Vacancy vacancy);
 
	void declineApplication(int id);

 
	Application findApplicationById(int id);
	Application apply(Application app, MultipartFile file, int idvacancy) ;
	Notification addAppNotif(Application app);
	List<Application> retreiveApplicantSkillAssessments();
	List<Vacancy> retreiveAllAssignedVacancies();
 	
	
 

}
