package tn.uae.nawanera.spring.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Comment;
import tn.uae.nawanera.spring.entities.TrainingCourse;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Integer> {
	
	Comment findById(int id);
	Comment findByCommentContent(String commentContent);
	@Query("SELECT c FROM Comment c WHERE c.user.id =:id")
	List<Comment> getCommentsByUserId(@Param("id") int id);

	 

	@Query("SELECT c FROM Comment c WHERE c.commentContent LIKE %?1%")

	List<Comment> findCommentsByTextContaining(String pattern);

	List<Comment> getCommentsByTrainingCourse(TrainingCourse tc );


	@Query("SELECT c FROM Comment c WHERE c.trainingCourse.id =:id")
	List<Comment> getCommentsBytcId(@Param("id")int id);
	@Transactional
	@Modifying
	@Query("delete from Comment c where  c.id=:id")
	void deleteComment(@Param("id") int id);


	@Query("SELECT c.trainingCourse FROM Comment c WHERE c.id =:id")
	TrainingCourse findTrainingCourse(@Param("id") int id);
}
