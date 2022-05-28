package tn.uae.nawanera.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.entities.Demo;
import tn.uae.nawanera.spring.payload.response.MessageResponse;
import tn.uae.nawanera.spring.services.IDemoService;
@Slf4j
@RestController
@RequestMapping("/api/project/task/demo")
public class DemoController {
	
	@Autowired
	IDemoService idemoService;
	
	@PreAuthorize("hasAuthority('INTERN')")
	@PostMapping(value = "/add-task-demo/{idtask}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MessageResponse> addTaskDemo( @RequestPart String d,@RequestParam(value = "file", required = true) MultipartFile file, @PathVariable("idtask")int idtask)  {
		Demo demo=new Demo();
		ObjectMapper objectMapper= new ObjectMapper();
		try {
            demo=objectMapper.readValue(d,Demo.class);
        } catch (JsonProcessingException e) {
			log.info("e :", e);
        }
		idemoService.addDemoDetails(demo,file, idtask);
		
		System.out.println(demo.getCategory()); 
		return ResponseEntity.ok(new MessageResponse("Task Demo successfully Registred!"));
	}

	@PreAuthorize("hasAuthority('INTERN')")
	@GetMapping(value = "/retreiveOwnDemos")
	@ResponseBody
	public List<Demo> retreiveOwnDemos() {

		return idemoService.retrieveOwnDemos();
	}
	
	@PreAuthorize("hasAuthority('INTERN') or hasAuthority('TRAINER')")
	@GetMapping(value = "/retreiveAllDemos")
	@ResponseBody
	public List<Demo> retreiveAllDemos() {

		return idemoService.retrieveAllDemos();
	}
	
	@PreAuthorize("hasAuthority('INTERN') or hasAuthority('TRAINER')")

	@GetMapping(value = "/retreiveAllDemos-by-task/{taskId}")
	@ResponseBody
	public List<Demo> retreiveAllDemosbyTask(@PathVariable("taskId")int taskId) {

		return idemoService.retrieveDemosByTask(taskId);
	}
	
	@PreAuthorize("hasAuthority('INTERN') or hasAuthority('TRAINER')")
	@GetMapping(value = "/display-demo-details/{demoId}")
	@ResponseBody
	public Demo  displayDemoDetails(@PathVariable("demoId")int demoId) {

		return idemoService.displayDemoDetailsById(demoId);
	}
	
	@PreAuthorize("hasAuthority('INTERN') or hasAuthority('TRAINER')")
	@GetMapping(value = "/display-demo-details-by-task/{taskId}")
	@ResponseBody
	public Demo  displayDemoDetailsByTask(@PathVariable("taskId")int taskId) {

		return idemoService.displayDemoByTask(taskId);
	}
	
	@PreAuthorize("hasAuthority('INTERN')")
	@DeleteMapping("/delete-task-demo/{id}")  
	public void deleteTaskDemo(@PathVariable("id") int id)   
	{  
		idemoService.removeDemo(id);
	}

}
