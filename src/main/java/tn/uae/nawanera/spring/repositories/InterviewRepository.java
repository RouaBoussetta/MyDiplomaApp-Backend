package tn.uae.nawanera.spring.repositories;

 
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.Interview;
 
@Repository
public interface InterviewRepository extends JpaRepository<Interview, Integer>{

	Interview findByApplication(Application a);
	Interview findById(int id);
 


}
