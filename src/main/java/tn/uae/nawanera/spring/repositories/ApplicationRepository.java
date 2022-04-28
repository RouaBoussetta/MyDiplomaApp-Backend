package tn.uae.nawanera.spring.repositories;

 
import java.util.List;
 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.SkillAssessment;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;

 
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>{
	@Query("select DISTINCT u from User u "
			+ "join u.applications a "
			+ "join a.vacancy v "
			+ "where v.id=:vacancyId")
	List<User> getAllInternByVacancy(@Param("vacancyId")int vacancyId);
	
	@Query("select DISTINCT u from User u "
			+ "join u.applications a "
			+ "join a.vacancy v "
			+ "where v.id=:vacancyId and a.isAffected=true")
	List<User> getAllAffectedInternByVacancy(@Param("vacancyId")int vacancyId);
	
	@Query("select DISTINCT v from Vacancy v"
			+ " join v.applications a"
			+ " join a.intern u  "
			+ "where u.id=:internId")

	List<Vacancy> findVacanciesByIntern(@Param("internId")int internId);
	

	@Query("select a from Application a "
			+ "where a.intern=:intern and "
			+ "a.vacancy=:vacancy ")
public Application getApplicationByInternAndVacancy(@Param("intern")User intern, @Param("vacancy")Vacancy vacancy);


	@Query("select DISTINCT a from Application a "
			+ "where  "
			+ "a.vacancy=:vacancy ")
	List<Application> getApplicationByVacancy( @Param("vacancy")Vacancy vacancy);

	Application findByVacancy(Vacancy vacancy);
	Application findById(int id);

	Application findByIntern(User intern);

	Application findBySkillAssessment(SkillAssessment sa);
	

	@Query("select DISTINCT a from Application a "
			+ "where  "
			+ "a.intern=:intern ")
	List<Application> getApplicantApplications(  @Param("intern")User intern);
	
	
	@Query("select a from Application a "
			+ "where  "
			+ "a.intern=:intern and a.isAffected=true")
	Application getAffectedApplication(  @Param("intern")User intern);
}
