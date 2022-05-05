package tn.uae.nawanera.spring.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tn.uae.nawanera.spring.entities.Certificate;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.repositories.CertificateRepository;
import tn.uae.nawanera.spring.repositories.ProjectRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;


@Service
public class CertificateService implements ICertificateService{
	@Autowired
	UserRepository userRepository;
	@Autowired
	CertificateRepository certificateRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	IUserservice iuserService;
	 
	@Override
	
	public Certificate addCertificateDetails(Certificate certificate, int idIntern, MultipartFile stamp, MultipartFile signature)  {
		
		User intern=userRepository.findById(idIntern);
		
		certificate.setIntern(intern);
		certificate.setHrmanager(iuserService.currentUser());
		certificate.setSignature(signature.getOriginalFilename());
		certificate.setStamp(stamp.getOriginalFilename());
		certificate.setAttributedAt(LocalDateTime.now());
		
		
		return certificateRepository.save(certificate);
	}
	@Override
	public Certificate displayCertificateDetailsById(int id) {
 		return certificateRepository.findById(id);
	}
	@Override
	public List<Certificate> displayCertificatesByAttributer(int hrId) {
		User hrmanager=userRepository.findById(hrId);

		return certificateRepository.findByHrmanager(hrmanager);
	}
	@Override
	public List<Certificate> retrieveAllCertificate() {
 		return certificateRepository.findAll();
	}
	
 
	@Override
	public List<Certificate> displayOwnHrCertificates()  {
		return certificateRepository.findByHrmanager(iuserService.currentUser());
	}
	@Override
	public String downloadCertificate(int id) {

		Certificate certificate=certificateRepository.findById(id) ;
		certificate.setDownloadedAt(LocalDateTime.now());
		certificateRepository.save(certificate);
		return "this certificate was successfully downloaded!";
	}
	@Override
	public void removeCertificate(int id) {
		Certificate certificate=certificateRepository.findById(id) ;
		certificateRepository.delete(certificate);

	}

	 

}
