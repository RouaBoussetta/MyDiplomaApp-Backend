package tn.uae.nawanera.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer>{

}
