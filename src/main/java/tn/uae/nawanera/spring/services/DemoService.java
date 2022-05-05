package tn.uae.nawanera.spring.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.config.FileUploadUtil;
import tn.uae.nawanera.spring.entities.Demo;
import tn.uae.nawanera.spring.entities.Task;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.repositories.DemoRepository;
import tn.uae.nawanera.spring.repositories.ProjectRepository;
import tn.uae.nawanera.spring.repositories.TaskRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;
@Slf4j
@Service
public class DemoService implements IDemoService {
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	DemoRepository demoRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	IUserservice iuserService;
	
	@Override
	public Demo addDemoDetails(Demo demo,MultipartFile file, int idtask)  {
		Task task = taskRepository.findById(idtask);
		try {
			FileUploadUtil.saveFile(file);
			demo.setFile(file.getOriginalFilename());
		} catch (IOException e) {
			log.info("e :", e);

		}
	
		demo.setTask(task);
		demo.setAddedAt(LocalDateTime.now());

		return demoRepository.save(demo);
	}

	@Override
	public Demo displayDemoByTask(int task) {
		Task t = taskRepository.findById(task);
		
		List<Demo> demos=demoRepository.findByTask(t);
		
		 
		for(Demo d:demos) 
			if(d.getTask().equals(t)) 
				return demoRepository.findById(d.getId());
 
			
	 
		return demoRepository.findDemoByTask(task);
		
		 
	}

	@Override
	public Demo displayDemoDetailsById(int id) {

		return demoRepository.findById(id) ;
	}

	@Override
	public List<Demo> retrieveAllDemos() {

		return   demoRepository.findAll();
	}

	@Override
	public List<Demo> retrieveDemosByTask(int task) {
		Task t = taskRepository.findById(task);

		return   demoRepository.findByTask(t);
	}

	@Override
	public List<Demo> retrieveOwnDemos()   {
 
		 
		List<Demo> demos = new ArrayList<>();

		for (Demo d : demoRepository.findAll()) {
			List<User> interns = userRepository.findAll();
 			for (User u : interns) {
				if (u.getId() ==  iuserService.currentUser() .getId()) {

 				 	demos.addAll(demoRepository.findByTask(d.getTask()));
				}
			}

		}
		return demos;

	}

	@Override
	public void removeDemo(int demoId) {

		Demo demo=demoRepository.findById(demoId) ;
		
		demoRepository.delete(demo);

	}

	 

}
