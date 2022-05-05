package tn.uae.nawanera.spring.services;

import java.util.Date;
import java.util.List;

import tn.uae.nawanera.spring.entities.Task;
import tn.uae.nawanera.spring.entities.TaskStatus;

public interface ITaskService {
 
  	public Task retreiveTaskDetails(int idTask);
  	public Task findbyTaskname(String task);
	public List<Task> retreiveAllProjectTasks(int idProject);
	public int  countProjectTasks(int idProject);
	public int  countAssignedTasksByIntern(int idIntern);
 	public List<Task> retreiveAllProjectTasksByStatus(TaskStatus status);
	
  	
	public List<Task> retreiveAllProjectTasksByDeadline(Date deadline);
	public  void evaluateProjectTask(Task t,int project);

 
	Task updateProjectTask(Task ta, int idTask);

	Task addProjectTask(Task t, int idproject);

	void disassociateTaskFromIntern(int t, int intern);

	void assignTaskToIntern(int t, int intern);

	void removeProjectTask(int idTask);

	List<Task> retreiveAssignedTasksByIntern(int idProject, int idIntern);

	


	

	

}
