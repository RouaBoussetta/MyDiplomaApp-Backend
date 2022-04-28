package tn.uae.nawanera.spring.services;

import org.springframework.web.multipart.MultipartFile;

import tn.uae.nawanera.spring.entities.Document;

public interface IDocumentService {
 
	 

	String addDocument(Document document, MultipartFile file);

}
