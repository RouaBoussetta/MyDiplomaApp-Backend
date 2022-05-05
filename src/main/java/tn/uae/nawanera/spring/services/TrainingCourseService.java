package tn.uae.nawanera.spring.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.config.FileUploadUtil;
import tn.uae.nawanera.spring.entities.Project;
import tn.uae.nawanera.spring.entities.TrainingCourse;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.repositories.ICommentRepository;
import tn.uae.nawanera.spring.repositories.ILikingRepository;
import tn.uae.nawanera.spring.repositories.ITrainingCourseRepository;
import tn.uae.nawanera.spring.repositories.ProjectRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;
@Slf4j
@Service
public class TrainingCourseService implements ITrainingCourse {
	@Autowired
	ITrainingCourseRepository trainingCourseRepository;
	@Autowired
	UserRepository  userRepository;
	
	@Autowired
	ILikingRepository  likngRepository;
	
	@Autowired
	ICommentRepository  commentRepository;
	
	@Autowired
	 ProjectRepository  projectRepository;
	@Autowired
	INotificationService inotifService;
	
	@Autowired
	IUserservice iuserService;
	@Override
	public TrainingCourse addTrainingCourse(TrainingCourse trainingcourse ,MultipartFile file) {

		trainingcourse.setPublishedAt(LocalDateTime.now());
		trainingcourse.setPublishedBy(iuserService.currentUser().getUsername());
		try {
			FileUploadUtil.saveFile(file);
			trainingcourse.setCourse(file.getOriginalFilename());
		} catch (IOException e) {
			log.info("e :", e);

		}
		trainingCourseRepository.save(trainingcourse);
		List<User> users=userRepository.findAllByCompanyName(iuserService.currentUser().getCompanyName());
		for (User u:users) {
			inotifService.addNotification(u, iuserService.currentUser(), "Training Course", "Post a New Training Course.");
		}
 		return trainingcourse;
	}

	@Override
	public String deleteTrainingCourse(int id)   { 
		TrainingCourse tc=trainingCourseRepository.getById(id);
		 trainingCourseRepository.delete(tc);
			return "Training Course deleted successfully";
	}

	

	@Override
	public List<TrainingCourse> getAllTrainingCourses() {
	
		return   trainingCourseRepository.findAll();
	}

 
	@Override
	public int countTrainingCourses() {
		List <TrainingCourse>tcs= trainingCourseRepository.findAll();
		return tcs.size();
	}

	@Override
	public List<TrainingCourse> getTrainingCoursesByUserId(int id) {
		
		User trainer=userRepository.findById(id);
 		return trainingCourseRepository.findByPublishedBy(trainer.getUsername());
	}
	@Override
	public TrainingCourse getTrainingCoursesByComment(int id) {
		
		
 		return commentRepository.findTrainingCourse(id);
	}
	@Override
	public int countTrainingCoursesByUser(int id) {
		User trainer=userRepository.findById(id);
		List<TrainingCourse>list=  trainingCourseRepository.findByPublishedBy(trainer.getUsername());	
 		 return list.size();
	}
	
	@Override
	public List<TrainingCourse> getMyTrainingCourses()  {
 
		return  trainingCourseRepository.findByPublishedBy(iuserService.currentUser().getUsername());	
		 
	}
	
	@Override
	public TrainingCourse updateTrainingCourse(TrainingCourse tc)  {
		TrainingCourse t=trainingCourseRepository.getById(tc.getId());
		
			return trainingCourseRepository.save(t);
	 

	 
	}

	@Override
	public TrainingCourse getTrainingCourseById(int id) {
		return trainingCourseRepository.findById(id) ;
	}

	@Override
	public List<TrainingCourse> searchTrainingCourses(String pattern) {
		 return trainingCourseRepository.findtcByTextContaining(pattern);
	}

	@Override
	public List<TrainingCourse> getTrainingCoursesCommentedByUser(int id) {
		 
		return trainingCourseRepository.getAllTrainingCoursesCommentedByUser(id);
	}

	@Override
	public List<TrainingCourse> getTrainingCoursesLikedByUser(int id) {
 	 	return trainingCourseRepository.getAllTrainingCoursesLikedByUser(id);

	}

 

	@Override
	public TrainingCourse mostLikedTrainingCourse()   {
 
		int max=0;
		TrainingCourse mostliked= new TrainingCourse();
		for(TrainingCourse tc: trainingCourseRepository.findAll()){
			int nblikes=(likngRepository.getLikesByTc(tc.getId())).size();
			if (max<nblikes){
				max=nblikes;
				mostliked=tc;
			}
		}
		return mostliked;
	}

	@Override
	public TrainingCourse mostCommentedTrainingCourse() {
		int max=0;
		TrainingCourse mostcommented= new TrainingCourse();
		for(TrainingCourse tc: trainingCourseRepository.findAll()){
			int nbcomments=(commentRepository.getCommentsBytcId(tc.getId())).size();
			if (max<nbcomments){
				max=nbcomments;
				mostcommented=tc;
			}
		}
		return mostcommented;
	}

	@Override
	public List<TrainingCourse> getTrainingCoursesByProject(int id) {
		
		Project project=projectRepository.findById(id) ;
 		return trainingCourseRepository.findByProject(project);
	}

	@Override
	public TrainingCourse update(int id, TrainingCourse tc) {
		
		TrainingCourse exist=trainingCourseRepository.findById(id) ;
		
		if(!tc.getTitle().equals(exist.getTitle())) {
			exist.setTitle(tc.getTitle());
			
		}
		
		if(!tc.getDescription().equals(exist.getDescription())) {
			exist.setDescription(tc.getDescription());
			
		}
		exist.setUpdatedAt(LocalDateTime.now());
	 
		return trainingCourseRepository.save(exist);
	}


}
