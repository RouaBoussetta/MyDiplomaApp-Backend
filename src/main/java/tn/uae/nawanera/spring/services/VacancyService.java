package tn.uae.nawanera.spring.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uae.nawanera.spring.entities.Degree;
import tn.uae.nawanera.spring.entities.Status;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;
import tn.uae.nawanera.spring.entities.VacancyCategory;
import tn.uae.nawanera.spring.repositories.UserRepository;
import tn.uae.nawanera.spring.repositories.VacancyRepository;

@Service
public class VacancyService implements IVacancyService {

	@Autowired
	VacancyRepository vacancyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	IUserservice iuserService;
	@Autowired
	INotificationService inotifService;

	@Override
	public Vacancy createVacancy(Vacancy vacancy) {
		if (Boolean.FALSE.equals(vacancy.getPaid())) {
			vacancy.setSalary(0);
		}

		vacancy.setCompanyName(iuserService.currentUser().getCompanyName());
		vacancy.setPostedby(iuserService.currentUser());
		vacancy.setPostedAt(LocalDateTime.now());
		vacancyRepository.save(vacancy);
		List<User> users=userRepository.findAllByCompanyName(iuserService.currentUser().getCompanyName());
		for (User u:users) {
			inotifService.addNotification(u, iuserService.currentUser(), "New Internship Vacancy", "Post a New Internship Vacancy.");
		}
		return vacancy;
	}

	@Override
	public List<Vacancy> retreiveAllVacancies() {

		return vacancyRepository.findAll();
	}

	@Override
	public Vacancy getVacancyById(int id) {

		return vacancyRepository.findById(id);
	}
	
	@Override
	public Vacancy getVacancyByCompany(String companyName) {
	 
		return vacancyRepository.findByCompanyName(companyName);
	}

	@Override
	public Vacancy updateVacancy(Vacancy vacancy,int id) {
		Vacancy existvacany = getVacancyById(id);

		if(!vacancy.getTitle().equals(existvacany.getTitle())) 
			existvacany.setTitle(vacancy.getTitle());
		
		
		if(!vacancy.getDescription().equals(existvacany.getDescription())) 
			existvacany.setDescription(vacancy.getDescription());
		
		if(!vacancy.getStatus().equals(existvacany.getStatus())) 
			existvacany.setStatus(vacancy.getStatus());
		
		if(!vacancy.getDegree().equals(existvacany.getDegree())) 
			existvacany.setDegree(vacancy.getDegree());
		
		if(!vacancy.getPaid().equals(existvacany.getPaid())) 
			existvacany.setPaid(vacancy.getPaid());
		
		
		if(vacancy.getSalary()!=existvacany.getSalary()) 
			existvacany.setSalary(vacancy.getSalary());
		
		
		
		if(!vacancy.getCategory().equals(existvacany.getCategory())) 
			existvacany.setCategory(vacancy.getCategory());
		
		
		if(!vacancy.getQualification().equals(existvacany.getQualification())) 
			existvacany.setQualification(vacancy.getQualification());
		
		existvacany.setUpdatedOn(LocalDateTime.now());
		vacancyRepository.save(existvacany);
		List<User> users=userRepository.findAllByCompanyName(iuserService.currentUser().getCompanyName());
		for (User u:users) {
			inotifService.addNotification(u, iuserService.currentUser(), "Update", "The Internship Vacancy which is titled "+existvacany.getTitle()+" was updated.");
		}
		return existvacany;
		

	}

	@Override
	public void deleteVacancyById(int vacancyId) {
		Vacancy v = vacancyRepository.findById(vacancyId);
		List<User> users=userRepository.findAllByCompanyName(iuserService.currentUser().getCompanyName());
		for (User u:users) {
			inotifService.addNotification(u, iuserService.currentUser(), "Removing Internship Vacancy", "The Internship Vacancy which is titled "+v.getTitle()+" was removed.");
		}
		
		vacancyRepository.delete(v);
	 

	}

	@Override
	public int countVacancies() {

 		return  vacancyRepository.findAll().size();
	}

	@Override
	public List<Vacancy> getVacanciesPostedBy(String username) {

		return vacancyRepository.findVacanciesPostedBy(username);
	}
	
	@Override
	public List<Vacancy> getOwnVacancies( ) {

		return vacancyRepository.findVacanciesPostedBy(iuserService.currentUser().getUsername());
	}


	@Override
	public List<Vacancy> getVacanciesByCategory(VacancyCategory category) {

		return vacancyRepository.findVacanciesByCategory(category);
	}
	
	@Override
	public List<Vacancy> getCompanyVacanciesByCategory(String companyname,VacancyCategory category) {

		return vacancyRepository.findCompanyVacanciesByCategory(companyname,category);
	}

	@Override
	public List<Vacancy> getVacanciesByStatus(Status status) {
		return vacancyRepository.findVacanciesByStatus(status);
	}

	@Override
	public List<Vacancy> getVacanciesByDegree(Degree degree) {

		return vacancyRepository.findVacanciesByDegree(degree);

	}

	@Override
	public List<Vacancy> getPaidVacancies() {

		return vacancyRepository.findPaidVacancies(true);
	}

	@Override
	public List<Vacancy> getActiveVacancies() {

		return vacancyRepository.findVacanciesByStatus(Status.ACTIVE);
	}

	@Override
	public List<Vacancy> getClosedVacancies() {

		return vacancyRepository.findVacanciesByStatus(Status.CLOSED);
	}

	@Override
	public List<Vacancy> getDraftVacancies() {

		return vacancyRepository.findVacanciesByStatus(Status.DRAFT);
	}

	@Override
	public List<Vacancy> getArchievedVacancies() {

		return vacancyRepository.findVacanciesByStatus(Status.ARCHEIVED);
	}

	@Override
	public int countActiveVacancies() {
		List<Vacancy> vacancies = vacancyRepository.findVacanciesByStatus(Status.ACTIVE);
		return vacancies.size();
	}

	@Override
	public int countDraftVacancies() {
		List<Vacancy> vacancies = vacancyRepository.findVacanciesByStatus(Status.DRAFT);
		return vacancies.size();
	}

	@Override
	public int countClosedVacancies() {
		List<Vacancy> vacancies = vacancyRepository.findVacanciesByStatus(Status.CLOSED);
		return vacancies.size();
	}

	@Override
	public int countArchievedVacancies() {
		List<Vacancy> vacancies = vacancyRepository.findVacanciesByStatus(Status.ARCHEIVED);
		return vacancies.size();
	}

	@Override
	public List<Vacancy> retrieveCompanyVacancies(String companyName) {

		return vacancyRepository.findVacanciesByCompanyName(companyName);
	}

	@Override
	public List<Vacancy> retrieveTrainerVacancies() {

		return vacancyRepository.findVacanciesByTrainedby(iuserService.currentUser());
	}

	@Override
	public List<Vacancy> retrieveCompanyVacancies() {

		return vacancyRepository.findVacanciesByCompanyName(iuserService.currentUser().getCompanyName());

	}

	@Override
	public List<Vacancy> retrieveVacanciesTrainedBy() {

		return vacancyRepository.findVacanciesByTrainedby(iuserService.currentUser());

	}

	 

	
	
}
