package tn.uae.nawanera.spring.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.SkillAssessment;
import tn.uae.nawanera.spring.entities.User;

@Repository
public interface SkillAssessmentRepository extends JpaRepository<SkillAssessment,Integer>{
	
	SkillAssessment findById(int id);
	@Query("Select s FROM SkillAssessment s where s.isPublished=true")

	List<SkillAssessment> retrieveAllPublishedSkillAssessment();

	@Transactional
	@Modifying
	@Query("delete from SkillAssessment a where  a.id=:id")
	void deleteSK(@Param("id") int id);

	
	List<SkillAssessment> findByCreatedBy(User createdBy);
	
	
	SkillAssessment findByTitle(String title);
	 


}
