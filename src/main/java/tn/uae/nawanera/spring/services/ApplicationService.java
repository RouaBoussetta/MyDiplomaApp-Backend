package tn.uae.nawanera.spring.services;

 import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.config.FileUploadUtil;
import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;
import tn.uae.nawanera.spring.repositories.ApplicationRepository;
import tn.uae.nawanera.spring.repositories.INotificationRepository;
import tn.uae.nawanera.spring.repositories.SkillAssessmentRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;
import tn.uae.nawanera.spring.repositories.VacancyRepository;
@Slf4j
@Service
public class ApplicationService implements IApplicationService {

	@Autowired
	ApplicationRepository applicationRepository;
	@Autowired
	VacancyRepository vacancyRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	IUserservice userService;
	@Autowired
	INotificationRepository iNotificationRepository;
	@Autowired
	SkillAssessmentRepository skillAssessmentRepository;
	@Autowired
	INotificationService inotifService;
	@Override
	public Application applyById(Application app,  MultipartFile file,int idvacancy)  {

 		Vacancy v = vacancyRepository.findById(idvacancy);
 		try {
			FileUploadUtil.saveFile(file);
			app.setCv(file.getOriginalFilename());
		} catch (IOException e) {
			log.info("e :", e);
		}
		
 		app.setVacancy(v);
		app.setIntern(userService.currentUser());
 		app.setIsAffected(false);
 		
 		applicationRepository.save(app);
 		inotifService.addNotification(app.getIntern(), userService.currentUser(), "New Internship Vacancy Application", "Apply for "+app.getVacancy().getTitle()+" internship which you posted.");

		return  app;

	}
	
	@Override
	public Application applyByTitle(Application app,  MultipartFile file,String titleVacancy)  {

 		Vacancy v = vacancyRepository.findByTitle(titleVacancy);
 		try {
			FileUploadUtil.saveFile(file);
			app.setCv(file.getOriginalFilename());
		} catch (IOException e) {
			log.info("e :", e);
		}
		
 		app.setVacancy(v);
		app.setIntern(userService.currentUser());
 		app.setIsAffected(false);
 		
 		applicationRepository.save(app);
 		inotifService.addNotification(app.getIntern(), userService.currentUser(), "New Internship Vacancy Application", "Apply for "+app.getVacancy().getTitle()+" internship which you posted.");

		return  app;

	}
	
	@Override
	public List<User> getAllInternByVacancy(int vacancyId) {

		return applicationRepository.getAllInternByVacancy(vacancyId);
	}
	
	@Override
	public List<User> getAllInternByVacancyTitle(String title) {

		return applicationRepository.getAllInternByVacancyTitle(title);
	}


	@Override
	public List<Vacancy> findVacanciesByIntern(int internId) {

		return applicationRepository.findVacanciesByIntern(internId);
	}
	
	@Override
	public List<Vacancy> findVacanciesByInternUsername(String username) {

		return applicationRepository.findVacanciesByInternUsername(username);
	}
	
	@Override
	public List<Application> retreiveApplicantSkillAssessments() {
	
		 return applicationRepository.getApplicantApplications(userService.currentUser() );
	}

	@Override
	public List<Application> findApplicationsByVacancy(Vacancy vacancy) {

		 

		return applicationRepository.getApplicationByVacancy(vacancy);
	}
	
	@Override
	public List<Application> findApplicationsByVacancyTitle(String title) {

		 

		return applicationRepository.getApplicationByVacancyTitle(title);
	}
	
	@Override
	public Application findApplicationById(int id) {

 
		return applicationRepository.findById(id);
	}
	
	@Override
	public Application findApplicationByVacancy(int id) {
 		Vacancy v = vacancyRepository.findById(id);

 
		return applicationRepository.findByVacancy(v);
	}

	@Override
	public Application acceptApplication(int idapp)    {
 	 

		Application app = applicationRepository.findById(idapp);
 		app.setIsAffected(true);
		userService.acceptIntern(app);
	 
		return applicationRepository.save(app);
	}

	@Override
	public void declineApplication(int id) {
		Application a = applicationRepository.findById(id) ;
	 
		applicationRepository.delete(a);

		
	}

	@Override
	public List<Vacancy> retreiveAllAssignedVacancies() {
		
		List<User> interns=userRepository.findTrainerInterns(userService.currentUser().getUsername());
		List<Vacancy> vacancies=new ArrayList<>();
		for (User u :interns) {
			Application app=applicationRepository.getAffectedApplication(u);
			vacancies.add(app.getVacancy());

		}
		return vacancies;
 	 
	}


	@Override
	public Boolean isAppliedByIntern(int idIntern,int idVacancy) {
		User intern=userRepository.findById(idIntern);
		Vacancy vacancy=vacancyRepository.findById(idVacancy);
		
 		return  applicationRepository.isAppliedByUserAndVacancy(intern,vacancy)!=null;
 		
	}

	@Override
	public List<Application> retreiveVacancyApplications() {
 		return applicationRepository.getVacancyApplications(userService.currentUser());
	}

}
