package tn.uae.nawanera.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Demo;
import tn.uae.nawanera.spring.entities.Task;

@Repository
public interface DemoRepository extends JpaRepository<Demo, Integer>{

	List<Demo> findByTask(Task t);
	
	@Query("Select d FROM Demo d where d.task.id= :id")
Demo findDemoByTask(@Param("id")int id);

}
