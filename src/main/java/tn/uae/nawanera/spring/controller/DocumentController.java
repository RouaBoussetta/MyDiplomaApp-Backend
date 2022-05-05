package tn.uae.nawanera.spring.controller;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.entities.Document;
import tn.uae.nawanera.spring.services.IDocumentService;
@Slf4j
@RestController
@RequestMapping("/api/document")
public class DocumentController {

	
	@Autowired
	IDocumentService idocService;
	
	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('HR_MANAGER') or hasAuthority('INTERN')  ")
	@PostMapping("/add-doc")
	public String addDoc( @RequestPart String doc, @RequestPart(value = "file", required = false) MultipartFile file) {
		Document document=new Document();
		
		ObjectMapper objectMapper= new ObjectMapper();
		try {
			document=objectMapper.readValue(doc,Document.class);
        } catch (JsonProcessingException e) {
			log.info("e :", e);

        }
		 return idocService.addDocument(document,file);
	}
}
