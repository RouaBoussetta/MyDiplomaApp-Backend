package tn.uae.nawanera.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Project;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{

 
	List<Project> findByTrainer(User trainer);
 
	Project findByVacancy(Vacancy v);

	 
 
}
