package tn.uae.nawanera.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.VacancyCategory;

@Repository
public interface IVacancyCategoryRepository extends JpaRepository<VacancyCategory, Integer>{
	
	VacancyCategory findById(int id);

}
