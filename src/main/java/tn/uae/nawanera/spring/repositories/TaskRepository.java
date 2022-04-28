package tn.uae.nawanera.spring.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Project;
import tn.uae.nawanera.spring.entities.Task;
import tn.uae.nawanera.spring.entities.TaskStatus;
  
@Repository

public interface TaskRepository extends JpaRepository<Task, Integer>{

	Task findById(int id);
 	
	List<Task> findByProject(Project p);

	List<Task> findByStatus(TaskStatus status);

	List<Task> findByDeadline(Date deadline);
	
	 
 
	

}
