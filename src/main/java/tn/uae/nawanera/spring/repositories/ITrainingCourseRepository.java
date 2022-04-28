package tn.uae.nawanera.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Project;
import tn.uae.nawanera.spring.entities.TrainingCourse;
 
 
@Repository
public interface ITrainingCourseRepository extends JpaRepository<TrainingCourse,Integer>{

 
	List<TrainingCourse> findByPublishedBy(String username);
	
	@Query("select DISTINCT tc from TrainingCourse tc "
			+ "join tc.comments c "
			+ "join c.user u "
			+ "where u.id=:id")
	List<TrainingCourse> getAllTrainingCoursesCommentedByUser(@Param("id")int id);

	@Query("select DISTINCT tc from TrainingCourse tc "
			+ "join tc.likes l "
			+ "join l.user u "
			+ "where u.id=:id")
	List<TrainingCourse> getAllTrainingCoursesLikedByUser(@Param("id")int id);
	
	
	@Query("SELECT tc FROM TrainingCourse tc WHERE tc.description LIKE %?1% OR tc.type LIKE %?1% order by tc.publishedAt desc")
	List<TrainingCourse> findtcByTextContaining(String pattern);

	List<TrainingCourse> findByProject(Project project);
	
	
}
