package tn.uae.nawanera.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.Project;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{

Project findByTitle(String title);
	List<Project> findByTrainer(User trainer);
 
	Project findByVacancy(Vacancy v);
	Project findById(int id);

	@Query("select p from Project p "
			+ "where  "
			+ "p.trainer=:trainer")
	List<Project> isEvaluated( @Param("trainer")User trainer);
	
}
