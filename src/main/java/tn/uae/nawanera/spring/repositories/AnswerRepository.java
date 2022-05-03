package tn.uae.nawanera.spring.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

 import tn.uae.nawanera.spring.entities.Answer;
import tn.uae.nawanera.spring.entities.Question;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Integer>{
	@Transactional
	@Modifying
	@Query("delete from Answer a where  a.question.id=:id")
	void deleteAnswer(@Param("id") int id);
	
	
	
	@Query("SELECT  a From Answer a join a.question q join q.skillAssessment s  WHERE s.id =:id ")

	 
	List<Answer> findbySkillAssessment(@Param("id")int id);



	List<Answer> findByQuestion(Question q);
	Answer findById(int id);



	int countByQuestion(Question question);



	Answer findByText(String a);




	
	

}
