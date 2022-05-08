package tn.uae.nawanera.spring.services;

import java.util.List;

import tn.uae.nawanera.spring.entities.Project;

public interface IProjectService {

	 
 
	Project displayVacancyProjectDetailsByVacancy(int vacancy);

	Project displayVacancyProjectDetailsById(int id);

	List<Project> retrieveAllProject();

	List<Project> retrieveProjectsByTrainer(int trainerId);

	Project addVacancyProjectDetails(Project p) ;

	List<Project> retrieveOwnProjects() ;

	void removeProject(int projectId);

	int countProjectInterns(int id);
 
	Project update(int id, Project p);

	 

	List<Project> getInternProjects();

	int countProjects();

}
