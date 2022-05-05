package tn.uae.nawanera.spring.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.config.FileUploadUtil;
import tn.uae.nawanera.spring.entities.Document;
import tn.uae.nawanera.spring.entities.Project;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.repositories.DocumentRepository;
import tn.uae.nawanera.spring.repositories.ProjectRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;
@Slf4j
@Service
public class DocumentService implements IDocumentService {

	@Autowired
	DocumentRepository docRepo;
	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	IUserservice iuserService;

	@Override
	public String addDocument(Document document, MultipartFile file) {

		Project p = projectRepository.findById(document.getProject().getId());

		List<User> interns = p.getInterns();
		User intern = new User();
		for (User i : interns) {
			if (iuserService.currentUser().getId() == i.getId()) {
				intern = i;
			}
		}
		
		 
		if (iuserService.currentUser().getId() == p.getVacancy().getPostedby().getId()
				|| iuserService.currentUser().getId() == p.getTrainer().getId()
				 || iuserService.currentUser().getId() == intern.getId()
				) {

			try {
				FileUploadUtil.saveFile(file);
				document.setDoc(file.getOriginalFilename());
			} catch (IOException e) {
				log.info("e :", e);

			}

			docRepo.save(document);
			return "the doccument was added successfully";

		} else {

			return "this doccument cannot be added successfully";
		}
	}

}
