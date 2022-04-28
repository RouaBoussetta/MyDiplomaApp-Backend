package tn.uae.nawanera.spring.repositories;

 import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.uae.nawanera.spring.entities.Degree;
 import tn.uae.nawanera.spring.entities.Status;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;
import tn.uae.nawanera.spring.entities.VacancyCategory;

public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
	
	 Vacancy findById(int id);
	@Query("Select v FROM Vacancy v where v.postedby.username= :postedby")
	List<Vacancy> findVacanciesPostedBy(@Param("postedby")String postedby);
	
 
	@Query("Select v FROM Vacancy v where v.category= :category")
	List<Vacancy> findVacanciesByCategory(@Param("category")VacancyCategory category);
	
	@Query("Select v FROM Vacancy v where v.category= :category and v.companyName=:companyName")
	List<Vacancy> findCompanyVacanciesByCategory(@Param("companyName")String companyName,@Param("category")VacancyCategory category);
	
	
	
	@Query("Select v FROM Vacancy v where v.status= :status")
	List<Vacancy> findVacanciesByStatus(@Param("status") Status status);

	@Query("Select v FROM Vacancy v where v.degree= :degree")
	List<Vacancy> findVacanciesByDegree(@Param("degree")Degree degree);
	
	@Query("Select v FROM Vacancy v where v.paid= :x")
	List<Vacancy> findPaidVacancies(@Param("x")boolean x);
	
	@Query("Select v FROM Vacancy v where v.postedby.companyName= :companyName")
	List<Vacancy> findVacanciesByCompanyName(@Param("companyName")String companyName);

	List<Vacancy> findVacanciesByTrainedby(User trainer);
	List<Vacancy> findVacanciesByPostedby(User hr);
	
	

 	 
	

	
	
}
