package tn.uae.nawanera.spring.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.config.FileUploadUtil;
import tn.uae.nawanera.spring.entities.Assessment;
import tn.uae.nawanera.spring.entities.Notice;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.repositories.AssessmentRepository;
import tn.uae.nawanera.spring.repositories.ProjectRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;
@Slf4j
@Service
public class AssessmentService implements IAssessmentService {
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AssessmentRepository assessmentRepository;

	@Autowired
	IUserservice iuserService;

	@Override
	public Assessment addAssessment(Assessment assessment, MultipartFile signature, MultipartFile stamp, int intern) {
		User i=userRepository.findById(intern);
		assessment.setIntern(i);
		
		try {
			FileUploadUtil.saveFile(signature);
			assessment.setSignature(signature.getOriginalFilename());

		} catch (IOException e) {
			log.info("e:"+e);
		}
		
		try {
			FileUploadUtil.saveFile(stamp);
			assessment.setStamp(stamp.getOriginalFilename());

		} catch (IOException e) {
			log.info("e:"+e);
		}
		 
 		assessment.setTrainer(iuserService.currentUser());

		return assessmentRepository.save(assessment);

	}

	@Override
	public List<Assessment> displayAssessmentByIntern(int idIntern) {
		User intern = userRepository.findById(idIntern);
		return assessmentRepository.findByIntern(intern);
	}

	public Assessment displayAssessmentById(int id) {
		return assessmentRepository.findById(id) ;

	}

	@Override
	public List<Assessment> retrieveAllAssessments() {

		return assessmentRepository.findAll();
	}

	@Override
	public List<Assessment> retrieveInternAssessement()  {

		return assessmentRepository.findByIntern(iuserService.currentUser());
	}
	
	@Override
	public List<Assessment> retrieveTrainerAssessement()  {

		return assessmentRepository.findByTrainer(iuserService.currentUser());
	}

	@Override
	public void removeAssessment(int assessmentId) {
		assessmentRepository.deleteById(assessmentId);
	}

	@Override
	public List<Assessment> displayAssessmentsByNotice(Notice notice) {
		return assessmentRepository.findByfinalNotice(notice);
	}

	@Override
	public int countAllAssessment() {
		List<Assessment> assessments = assessmentRepository.findAll();
		return assessments.size();
	}

	@Override
	public List<Assessment> retrieveAllConclusive() {
		return assessmentRepository.findByfinalNotice(Notice.CONCLUSIVE);
	}

	@Override
	public List<Assessment> retrieveAllInconclusive() {
		return assessmentRepository.findByfinalNotice(Notice.INCONCLUSIVE);
	}

	@Override
	public Assessment update(Assessment assessment, int id) {
		 Assessment exist=assessmentRepository.findById(id) ;
		 
 			 exist.setFinalNotice(assessment.getFinalNotice());
		 
		 
		 if (!exist.getRemark().equals(assessment.getRemark())) {
			 exist.setRemark(assessment.getRemark());
		 }
		 
		return assessmentRepository.save(exist);
	}

}
