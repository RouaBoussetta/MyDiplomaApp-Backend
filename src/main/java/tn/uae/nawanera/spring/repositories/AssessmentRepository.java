package tn.uae.nawanera.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Assessment;
import tn.uae.nawanera.spring.entities.Notice;
import tn.uae.nawanera.spring.entities.User;
 
@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Integer>{

	List<Assessment> findByIntern(User intern);

	List<Assessment> findByfinalNotice(Notice notice);

	List<Assessment> findByTrainer(User trainer);
	

}
