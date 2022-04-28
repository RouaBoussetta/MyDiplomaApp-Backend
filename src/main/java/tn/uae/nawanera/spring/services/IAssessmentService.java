package tn.uae.nawanera.spring.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import tn.uae.nawanera.spring.entities.Assessment;
import tn.uae.nawanera.spring.entities.Notice;

public interface IAssessmentService {

	List<Assessment> displayAssessmentByIntern(int intern);

	public Assessment displayAssessmentById(int id);

	List<Assessment> retrieveInternAssessement();

	List<Assessment> displayAssessmentsByNotice(Notice notice);

	public int countAllAssessment();

	public List<Assessment> retrieveAllAssessments();

	public List<Assessment> retrieveAllConclusive();

	public List<Assessment> retrieveAllInconclusive();

	void removeAssessment(int assessmentId);

	Assessment addAssessment(Assessment assessment, MultipartFile signature, MultipartFile stamp, int intern);
	Assessment update(Assessment assessment, int id);

	List<Assessment> retrieveTrainerAssessement();

}
