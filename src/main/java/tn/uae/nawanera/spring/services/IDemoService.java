package tn.uae.nawanera.spring.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import tn.uae.nawanera.spring.entities.Demo;

public interface IDemoService {

	Demo displayDemoDetailsById(int id);

	List<Demo> retrieveAllDemos();

	List<Demo> retrieveDemosByTask(int task);

	List<Demo> retrieveOwnDemos();

	void removeDemo(int demoId);

	Demo displayDemoByTask(int task);

 
	Demo addDemoDetails(Demo demo, MultipartFile file, int idtask);

}
