package tn.uae.nawanera.spring.controller;

import java.util.List;

import javax.annotation.security.PermitAll;

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
import org.springframework.web.bind.annotation.RestController;

import tn.uae.nawanera.spring.entities.Project;
import tn.uae.nawanera.spring.payload.response.MessageResponse;
import tn.uae.nawanera.spring.services.IApplicationService;
import tn.uae.nawanera.spring.services.IProjectService;
import tn.uae.nawanera.spring.services.IUserservice;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	IProjectService iprojectService;
	@Autowired
	IUserservice iuserService;

	@Autowired
	IApplicationService iApplicationService;

	@PreAuthorize("hasAuthority('TRAINER')")
	@PostMapping("/add-project")
	public ResponseEntity<MessageResponse> createProject(@RequestBody Project project)  {

		if (project == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: please add values!"));
		}

		if (project.getTitle().equals("")) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: please add project Title!"));
		}
		if (project.getDescription().equals("")) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: please add project description!"));
		}
	 

 
		iprojectService.addVacancyProjectDetails(project);
		
		return ResponseEntity.ok(new MessageResponse("project Published successfully!"));
	}
	
	
	
	
	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN') or hasAuthority('HR_MANAGER')")
	@GetMapping("/retreive-project-by-vacancy/{id}")
	public Project getvacancyProject(@PathVariable("id") int id) {
		return iprojectService.displayVacancyProjectDetailsByVacancy(id);
	}

	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN') or hasAuthority('HR_MANAGER')")
	@GetMapping("/retreive-project-details/{id}")
	public Project getvacancyProjectDetails(@PathVariable("id") int id) {
		return iprojectService.displayVacancyProjectDetailsById(id);
	} 
	
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR') ")
	@GetMapping("/retreiveAllprojects")
	public List<Project> getAllProjects() {
		return iprojectService.retrieveAllProject();
	}
	
	
	
	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')  ")
	@GetMapping("/retreive-own-projects")
	public List<Project> getOwnProjects()   {
		return iprojectService.retrieveOwnProjects();
	}
	@PreAuthorize("hasAuthority('TRAINER')")

	@DeleteMapping("/delete-project/{id}")  
	public void deleteProject(@PathVariable("id") int id)   
	{  
		iprojectService.removeProject(id);
	}
	
	
	@PermitAll
	@GetMapping("/{id}/count-project-interns")
	public int countProjectInterns(@PathVariable("id") int id)   {
		return iprojectService.countProjectInterns(id);
	}
	
	@PermitAll
	@GetMapping("/count-projects")
	public int countProjects()   {
		return iprojectService.countProjects();
	}
	
	@PreAuthorize("hasAuthority('TRAINER')")

	@PutMapping("/update-project/{id}")
	public Project update(@PathVariable("id") int id,@RequestBody Project p)   {

		return iprojectService.update(id, p);
	}
	
	@PreAuthorize("hasAuthority('INTERN') ")
	@GetMapping("/retreiveAllprojects-of-current-intern")
	public List<Project> getAllInternProjects() {
		return iprojectService.getInternProjects();
	}
	
	


}
