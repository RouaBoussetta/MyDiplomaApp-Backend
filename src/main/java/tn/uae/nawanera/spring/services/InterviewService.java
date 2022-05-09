package tn.uae.nawanera.spring.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.Interview;
import tn.uae.nawanera.spring.repositories.ApplicationRepository;
import tn.uae.nawanera.spring.repositories.InterviewRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;
import tn.uae.nawanera.spring.repositories.VacancyRepository;
 
@Service
public class InterviewService implements IinterviewService{
	
	@Autowired
	InterviewRepository interviewRepository;
	@Autowired
	ApplicationRepository applicationRepository;
	@Autowired
	SkillAssessmentService saService;
	@Autowired
	VacancyRepository vacancyRepository;
	@Autowired
	UserRepository userRepository;
	
 
	@Override
	public Interview planifyInterview(Interview interview ,int idapp) throws IOException, GeneralSecurityException {
		
		Application app=applicationRepository.findById(idapp);
		
		interview.setApplication(app);
		interview.setCreatedAt(LocalDateTime.now());
		
		interviewRepository.save(interview);
		app.setInterviewPlanned(true);
		applicationRepository.save(app);
		
	//	saService.attacheSa(app.getIntern().getEmail(), "Interview", "You have An interview with "+app.getVacancy().getPostedby().getFirstname()+" "+app.getVacancy().getPostedby().getLastname());
		return interview;
	}
	@Override
	public List<Interview> retreiveInterviews() {
		
		return interviewRepository.findAll();
	}
	@Override
	public  Interview  getInterviewByApplication(int app) {
		Application a=applicationRepository.findById(app);
		
		return interviewRepository.findByApplication(a);
	}
	@Override
	public void rejectInterview (int id) {
		
		Interview intervieww=getInterviewByApplication(id);
		  interviewRepository.delete(intervieww);
	}
	
	
 

}
