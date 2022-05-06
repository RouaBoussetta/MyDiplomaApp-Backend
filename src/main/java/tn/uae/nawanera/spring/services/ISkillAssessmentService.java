package tn.uae.nawanera.spring.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import tn.uae.nawanera.spring.entities.SkillAssessment;
import tn.uae.nawanera.spring.entities.skillassessment.Response;
import tn.uae.nawanera.spring.entities.skillassessment.Result;

public interface ISkillAssessmentService {

	SkillAssessment save(SkillAssessment skillAssessment);

	List<SkillAssessment> retreiveAll();
 
	List<SkillAssessment> retrieveAllPublished();

	List<SkillAssessment> retrieveAllByUser(int user);

	SkillAssessment find(int id);

	SkillAssessment update(int id, SkillAssessment sa);

	void deleteSkillAssessment(int sa);

	List<SkillAssessment> searchSkillAssessment(String query);

	Result checkInternAnswers(SkillAssessment sa, List<Response> answers);

	//void assignSAToIntern(int saId, int internId) throws GeneralSecurityException, IOException;

	void publishSkillAssessment(int skillassessment);

	List<SkillAssessment> retreiveOwnSkillAssessments();

	void publishSkillAssessment(String title);

 
	//void assignSAToIntern(String skTitle, String internUsername) throws GeneralSecurityException, IOException;

	SkillAssessment getSkillAssessmentByTitle(String string);

	 

 

 
}
