package tn.uae.nawanera.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.entities.Certificate;
import tn.uae.nawanera.spring.services.CertificateService;

@Slf4j
@RestController
@RequestMapping("/api/certificate")
public class CertificateController {
	
	@Autowired
	CertificateService certificateservice;

	
	@PostMapping(value = "/add-Certificate/{idIntern}" )
	public Certificate addCertificate(@RequestPart String c,@RequestParam(value = "signature", required = true) MultipartFile signature,@RequestParam(value = "stamp", required = true) MultipartFile stamp,@PathVariable("idIntern") int idIntern)  {

		Certificate certificate=new Certificate();
		ObjectMapper objectMapper= new ObjectMapper();
		try {
            certificate=objectMapper.readValue(c,Certificate.class);
        } catch (JsonProcessingException e) {
			log.info("e :", e);

        }
	return	certificateservice.addCertificateDetails(certificate ,idIntern,stamp,signature );

		 
	}
	
	
	@GetMapping(value = "/display-certificate-details/{id}")
	@ResponseBody
	public Certificate  displaycertificateDetailsById(@PathVariable("id")int id) {

		return certificateservice.displayCertificateDetailsById(id);
	}
	
	 
	@GetMapping(value = "/display-Certificates-by-attributer/{id}")
	@ResponseBody
	public List<Certificate>  displayCertificatesByHr(@PathVariable("id")int id) {

		return certificateservice.displayCertificatesByAttributer(id);
	}
	
	
	
	@GetMapping(value = "/display-all-certificates")
	@ResponseBody
	public List<Certificate>  displayAllCertificates( ) {

		return certificateservice.retrieveAllCertificate();
	}
	
	
	
 
	@GetMapping(value = "/display-own-hr-Certificates")
	@ResponseBody
	public List<Certificate>  displayOwnHrCertificates( )  {

		return certificateservice.displayOwnHrCertificates();
	}
	
	
	@GetMapping(value = "/display-intern-/{id}/certificates")
	@ResponseBody
	public List<Certificate>  displayInternCertificates(@PathVariable("id")int id )  {

		return certificateservice.displayCertificatesByIntern(id);
	}
	
	
	
	@PutMapping("/download-certificate/{id}")

	public String downloadCertificate(@PathVariable("id")int id) {
 		 
		return certificateservice.downloadCertificate(id);
	}
	
	@DeleteMapping("/delete-certificate/{id}")  
	public void deleteCertificate(@PathVariable("id") int id)   
	{  
		certificateservice.removeCertificate(id);
	}
	
}
