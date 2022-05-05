package tn.uae.nawanera.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.entities.TrainingCourse;
import tn.uae.nawanera.spring.payload.response.MessageResponse;
import tn.uae.nawanera.spring.services.ITrainingCourse;
@Slf4j
@RestController
@RequestMapping("/api/course-training")
public class TrainingCourseController {
	
	@Autowired
	ITrainingCourse itrainingService;
	
	@PreAuthorize("hasAuthority('TRAINER')")
	@PostMapping("/add-training-course")
	public ResponseEntity<MessageResponse> createTrainingCourse(@RequestPart String tc,
			@RequestParam(value = "file", required = false) MultipartFile file)   {
		
		
		TrainingCourse course = new TrainingCourse();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			course = objectMapper.readValue(tc, TrainingCourse.class);
		} catch (JsonProcessingException e) {
			log.info("e :", e);

		}
 

	
 		itrainingService.addTrainingCourse(course,file);
 		return ResponseEntity.ok(new MessageResponse("Course  successfully published!"));
	}
	
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping("/retrieve-all-training-courses")
	public List<TrainingCourse>  getAllTrainingCourses() {
		return itrainingService.getAllTrainingCourses();
	}
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping("/count-training-courses")
	public int  countTrainingCourses() {
		return itrainingService.countTrainingCourses();
	}
	
	@PreAuthorize("permitAll()")
	@GetMapping("/retrieve-all-training-courses-by-trainer/{id}")
	public List<TrainingCourse>  getTrainingCoursesByUserId(@PathVariable("id") int id)   {
		return itrainingService.getTrainingCoursesByUserId(id);
	}
	
	@PreAuthorize("permitAll()")
	@GetMapping("/count-training-courses-by-trainer/{id}")
	public int  countTrainingCoursesByUserId(@PathVariable("id") int id)  {
		return itrainingService.countTrainingCourses();
	}
	
	@PreAuthorize("hasAuthority('TRAINER')")
	@GetMapping("/retrieve-all-own-training-courses")
	public List<TrainingCourse>  getMyTrainingCourses()  {
		return itrainingService.getMyTrainingCourses();
	}
	
	
	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@GetMapping("/retrieve-training-course-details/{id}")
	public TrainingCourse  getTrainingCourseDetails(@PathVariable("id") int id)  {
		return itrainingService.getTrainingCourseById(id);
	}
	
	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@GetMapping("/retrieve-training-course-by-comment/{id}")
	public TrainingCourse  getTrainingCourse(@PathVariable("id") int id)  {
		return itrainingService.getTrainingCoursesByComment(id);
	}
	
	@PreAuthorize("hasAuthority('TRAINER')")
	@GetMapping("/count-all-own-training-courses")
	public int  countMyTrainingCourses()  {
		return itrainingService.getMyTrainingCourses().size();
	}
	
	@PreAuthorize("hasAuthority('TRAINER')")
	@DeleteMapping("/delete-training-course/{id}")  
	public void deleteTrainingCourse(@PathVariable("id") int id)  
	{  
		itrainingService.deleteTrainingCourse(id);
	}
	
	
	@PreAuthorize("permitAll()")
	@GetMapping("/get-training-courses-by-project/{id}")
	public List<TrainingCourse>  getTrainingCoursesByProject(@PathVariable("id") int id)  {
		return itrainingService.getTrainingCoursesByProject(id);
	}

	
	@PutMapping("/update-tc/{id}")
	public TrainingCourse update(@PathVariable("id") int id,@RequestBody TrainingCourse tc)   {

		return itrainingService.update(id, tc);
	}
}
