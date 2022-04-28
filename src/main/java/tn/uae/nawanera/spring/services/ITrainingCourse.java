package tn.uae.nawanera.spring.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import tn.uae.nawanera.spring.entities.TrainingCourse;

 

public interface ITrainingCourse {
 
 	
	public String deleteTrainingCourse(int id)  ;
	
	public TrainingCourse updateTrainingCourse(TrainingCourse tc )  ;
	
	public List<TrainingCourse> getAllTrainingCourses();
	
	public TrainingCourse getTrainingCourseById(int id);
	
	public int countTrainingCourses();
	
	public List<TrainingCourse> getTrainingCoursesByUserId(int id);
	
	public int countTrainingCoursesByUser(int id);
    
	public List<TrainingCourse> searchTrainingCourses(String text);
	
	public List<TrainingCourse> getTrainingCoursesCommentedByUser(int id);
	
	public List<TrainingCourse> getTrainingCoursesLikedByUser(int id);
	
	TrainingCourse update(int id, TrainingCourse tc);
	
	public List<TrainingCourse> getMyTrainingCourses() ;
	
 
	public TrainingCourse mostLikedTrainingCourse() ;

	public TrainingCourse mostCommentedTrainingCourse() ;

	TrainingCourse addTrainingCourse(TrainingCourse trainingcourse, MultipartFile file);

	List<TrainingCourse> getTrainingCoursesByProject(int id);

	TrainingCourse getTrainingCoursesByComment(int id);
 

}
