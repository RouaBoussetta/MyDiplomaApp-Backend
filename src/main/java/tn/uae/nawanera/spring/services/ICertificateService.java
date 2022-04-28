package tn.uae.nawanera.spring.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import tn.uae.nawanera.spring.entities.Certificate;



public interface ICertificateService {
 
 
	Certificate addCertificateDetails(Certificate certificate, int idIntern, MultipartFile stamp, MultipartFile signature)  ;
	Certificate displayCertificateDetailsById(int id);
	List<Certificate> displayCertificatesByAttributer(int hrId);
	List<Certificate> displayOwnHrCertificates( )  ;
	List<Certificate> retrieveAllCertificate();
  	String downloadCertificate(int id);
	void removeCertificate(int id);

 
}
