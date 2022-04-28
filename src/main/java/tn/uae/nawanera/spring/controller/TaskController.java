package tn.uae.nawanera.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.uae.nawanera.spring.entities.Task;
import tn.uae.nawanera.spring.entities.TaskStatus;
import tn.uae.nawanera.spring.payload.response.MessageResponse;
import tn.uae.nawanera.spring.services.ITaskService;

@RestController
@RequestMapping("/api/project/tasks")
public class TaskController {

	@Autowired
	ITaskService itaskService;

	@PreAuthorize("hasAuthority('TRAINER')")

	@PostMapping("/add-project-task/{idproject}")
	public ResponseEntity<MessageResponse> addProjectTask(@RequestBody Task task,@PathVariable("idproject")int idproject) {
		if (task == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: please add values!"));
		}

		if (task.getTaskName().equals("")) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: please add task name  !"));
		}

		itaskService.addProjectTask(task,idproject);
		return ResponseEntity.ok(new MessageResponse("Task added successfully!"));
	}

	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')")

	@PutMapping("/Update-project-task/{idtask}")

	public Task updateProjectTask(@RequestBody Task task, @PathVariable("idtask") int idtask) {

		return itaskService.updateProjectTask(task, idtask);
	}

	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@GetMapping("/retreiveAllProjectTasks/{idProject}")
	public List<Task> getAllProjectTasks(@PathVariable("idProject") int idProject) {
		return itaskService.retreiveAllProjectTasks(idProject);
	}

	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@GetMapping("/retreive-Project-Task-details/{idTask}")
	public Task getProjectTaskDetails(@PathVariable("idTask") int idTask) {
		return itaskService.retreiveTaskDetails(idTask);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/count-Project-Tasks/{idProject}")
	public int countProjectTasks(@PathVariable("idProject") int idProject) {
		return itaskService.countProjectTasks(idProject);
	}

	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@GetMapping("/retreiveAllProjectTasks-byStatus/{status}")
	public List<Task> retreiveAllProjectTasksByStatus(@PathVariable("status") TaskStatus status) {
		return itaskService.retreiveAllProjectTasksByStatus(status);
	}

	@PreAuthorize("hasAuthority('TRAINER')")
	@PutMapping("/evaluate-project-task/{idProject}")
	public void evaluateProjectTask(@RequestBody Task task, @PathVariable("idProject") int idProject) {

		itaskService.evaluateProjectTask(task, idProject);
	}

	@PreAuthorize("hasAuthority('TRAINER') ")
	@PutMapping("/assign-task/{task}/to-intern/{idIntern}")
	public void assignTask(@PathVariable("task") int  task, @PathVariable("idIntern") int idIntern) {

		itaskService.assignTaskToIntern(task, idIntern);
	}

	@PreAuthorize("hasAuthority('TRAINER')")
	@PutMapping("/disassociate-task/{task}/from-intern/{idIntern}")
	public void disassociateTask(@PathVariable("task") int task, @PathVariable("idIntern") int idIntern) {

		itaskService.disassociateTaskFromIntern(task, idIntern);
	}

	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@GetMapping("/count-intern-Tasks/{idintern}")
	public int countInternTasks(@PathVariable("idintern") int idintern) {
		return itaskService.countAssignedTasksByIntern(idintern);
	}

	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@GetMapping("/retreive-intern-Tasks/{idproject}/{idintern}")
	public List<Task> retreiveInternTasks(@PathVariable("idproject") int idproject,@PathVariable("idintern") int idintern) {
		return itaskService.retreiveAssignedTasksByIntern(idproject,idintern);
	}

	@PreAuthorize("hasAuthority('TRAINER') ")
	@DeleteMapping("/remove-project-task/{idTask}")
	public void removeProjectTask( @PathVariable("idTask") int idTask) {
		itaskService.removeProjectTask( idTask);
	}
}
