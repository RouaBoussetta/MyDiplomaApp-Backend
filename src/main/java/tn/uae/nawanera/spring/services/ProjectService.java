package tn.uae.nawanera.spring.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.var;
import tn.uae.nawanera.spring.entities.Project;
import tn.uae.nawanera.spring.entities.Task;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;
import tn.uae.nawanera.spring.repositories.ApplicationRepository;
import tn.uae.nawanera.spring.repositories.ProjectRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;
import tn.uae.nawanera.spring.repositories.VacancyRepository;

@Service
public class ProjectService implements IProjectService {
	@Autowired
	VacancyRepository vacancyRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	ApplicationRepository applicationRepository;
	@Autowired
	IUserservice iuserService;
	@Autowired
	ITaskService taskService;
	@Autowired
	IApplicationService iApplicationService;

	@Override
	public Project addVacancyProjectDetails(Project p) {

		Vacancy v = vacancyRepository.findById(p.getVacancy().getId());

		p.setVacancy(v);
		p.setTrainer(iuserService.currentUser());
		List<User> interns = iApplicationService.getAllInternByVacancy(p.getVacancy().getId());

		p.setAddedOn(LocalDate.now());
		p.setAddedAt(LocalTime.now());
	
		
		projectRepository.save(p);
		for (User u : interns) {
			iuserService.assignProjectToIntern(p.getId(), u.getId());
		}

		return p;
	}

	@Override
	public Project displayVacancyProjectDetailsByVacancy(int vacancy) {
		Vacancy v = vacancyRepository.findById(vacancy);

		return projectRepository.findByVacancy(v);
	}

	@Override
	public Project displayVacancyProjectDetailsById(int id) {

		return projectRepository.findById(id);
	}

	@Override
	public List<Project> retrieveAllProject() {
		return projectRepository.findAll();

	}

	@Override
	public List<Project> retrieveOwnProjects() {

		return projectRepository.findByTrainer(iuserService.currentUser());
	}

	@Override
	public List<Project> retrieveProjectsByTrainer(int trainerId) {
		User trainer = userRepository.findById(trainerId);
		return projectRepository.findByTrainer(trainer);
	}

	@Override
	public void removeProject(int projectId) {
		Project p = projectRepository.findById(projectId) ;
		List<Task> tasks = taskService.retreiveAllProjectTasks(projectId);
		for (Task t : tasks) {
			taskService.removeProjectTask(t.getId());
		}
		projectRepository.delete(p);
	}

	@Override
	public int countProjectInterns(int id) {
		Project p = projectRepository.findById(id) ;

		return p.getInterns().size();
	}

	@Override
	public int countProjects() {
		 
		return projectRepository.findAll().size();
	}

	
	
	@Override
	public Project update(int id, Project p) {

		Project existProject = projectRepository.findById(id) ;
		if (!p.getTitle().equals(existProject.getTitle()))
			existProject.setTitle(p.getTitle());

		if (!p.getDescription().equals(existProject.getDescription()))
			existProject.setDescription(p.getDescription());

		if (!p.getVacancy().equals(existProject.getVacancy()))
			existProject.setVacancy(p.getVacancy());

		existProject.setUpdatedOn(LocalDate.now());
		existProject.setUpdatedAt(LocalTime.now());
		return projectRepository.save(existProject);

	}
	
	
	
	@Override
	public List<Project> getInternProjects( ) {
		User intern=userRepository.findById(iuserService.currentUser().getId()) ;
		return intern.getInternProjects();
	}

	@Override
	public List<Project> getInternProjects(int idintern) {
		User intern=userRepository.findById(idintern) ;
		return intern.getInternProjects();
	}

	@Override
	public List<Project> getTrainerProjects(int idTrainer) {
		User trainer=userRepository.findById(idTrainer) ;
		return projectRepository.findByTrainer(trainer);
	}
	
	


	
	
	
	

}
