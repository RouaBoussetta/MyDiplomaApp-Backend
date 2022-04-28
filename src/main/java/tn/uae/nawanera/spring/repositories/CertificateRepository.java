package tn.uae.nawanera.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Certificate;
import tn.uae.nawanera.spring.entities.User;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {

	List<Certificate> findByHrmanager(User hrmanager);
 
}
