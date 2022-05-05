package tn.uae.nawanera.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Liking;
import tn.uae.nawanera.spring.entities.TrainingCourse;

@Repository
public interface ILikingRepository extends JpaRepository<Liking, Integer>{

	Liking findById(int id);
	@Query("SELECT l FROM Liking l WHERE l.user.id =:id order by l.likeDate desc")
	public List<Liking> getLikesByUserId(@Param("id")int id);

	public List<Liking> findByTrainingCourse(TrainingCourse tc);

	
	@Query("select count(l) from Liking l where l.user.id =:idu and l.trainingCourse.id =:idtc")
	public int isLikeExists(@Param("idu")int idu, @Param("idtc")int idtc);
	
	
 	@Query("SELECT l FROM Liking l WHERE l.trainingCourse.id =:id order by l.likeDate desc")
	public List<Liking> getLikesByTc(@Param("id")int id);
 
	
	

}
