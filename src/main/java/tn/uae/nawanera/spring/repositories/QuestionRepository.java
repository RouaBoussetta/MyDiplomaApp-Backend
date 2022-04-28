package tn.uae.nawanera.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

 import tn.uae.nawanera.spring.entities.Question;
import tn.uae.nawanera.spring.entities.SkillAssessment;

@Repository
public interface QuestionRepository  extends JpaRepository<Question, Integer> {


	int countBySkillAssessment(SkillAssessment skillAssessment);

	List<Question> findBySkillAssessment(SkillAssessment sk);

}
