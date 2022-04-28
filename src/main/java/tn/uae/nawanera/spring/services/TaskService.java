package tn.uae.nawanera.spring.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uae.nawanera.spring.entities.Project;
import tn.uae.nawanera.spring.entities.Task;
import tn.uae.nawanera.spring.entities.TaskStatus;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.repositories.ApplicationRepository;
import tn.uae.nawanera.spring.repositories.ProjectRepository;
import tn.uae.nawanera.spring.repositories.TaskRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;

@Service
public class TaskService implements ITaskService {

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	TaskRepository taskRepository;
	@Autowired
	ApplicationRepository appRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public Task addProjectTask(Task t, int idproject) {
		Project p = projectRepository.findById(idproject).get();
		t.setProject(p);
		t.setCreatedAt(LocalDateTime.now());
		t.setStatus(TaskStatus.TODO);

		return taskRepository.save(t);
	}

	@Override
	public void removeProjectTask(int idTask) {

		Task t = taskRepository.findById(idTask);

		taskRepository.delete(t);

	}

	@Override
	public Task updateProjectTask(Task ta, int idTask) {

		Task task = taskRepository.findById(idTask);
		if (!ta.getTaskName().equals(task.getTaskName()))
			task.setTaskName(ta.getTaskName());

		if (!ta.getDeadline().equals(task.getDeadline()))
			task.setDeadline(ta.getDeadline());

		if (!ta.getStatus().equals(task.getStatus()))
			task.setStatus(ta.getStatus());

		if (!ta.getTaskIssue().equals(task.getTaskIssue()))
			task.setTaskIssue(ta.getTaskIssue());
		task.setUpdatedAt(LocalDateTime.now());
		return taskRepository.save(task);

	}

	@Override
	public Task retreiveTaskDetails(int idTask) {

		return taskRepository.findById(idTask);
	}

	@Override
	public List<Task> retreiveAllProjectTasks(int idProject) {
		Project p = projectRepository.findById(idProject).get();

		return taskRepository.findByProject(p);
	}

	@Override
	public int countProjectTasks(int idProject) {
		Project p = projectRepository.findById(idProject).get();
		List<Task> tasks = taskRepository.findByProject(p);
		return tasks.size();
	}

	@Override
	public List<Task> retreiveAllProjectTasksByStatus(TaskStatus status) {

		return taskRepository.findByStatus(status);
	}

	@Override
	public List<Task> retreiveAllProjectTasksByDeadline(Date deadline) {

		return taskRepository.findByDeadline(deadline);
	}

	@Override
	public void evaluateProjectTask(Task ta, int project) {
		Project p = projectRepository.findById(project).get();

		List<Task> tasks = taskRepository.findByProject(p);

		for (Task task : tasks) {
			if (task.getId() == ta.getId()) {

				ta.setTaskName(task.getTaskName());

				ta.setDeadline(task.getDeadline());

				if (ta.getStatus() == null)
					ta.setStatus(task.getStatus());

				else
					ta.setStatus(ta.getStatus());
				if (ta.getTaskIssue() == null)
					ta.setTaskIssue(task.getTaskIssue());

				else
					ta.setTaskIssue(ta.getTaskIssue());

				ta.setCreatedAt(task.getCreatedAt());
				ta.setProject(p);
				ta.setUpdatedAt(LocalDateTime.now());

				taskRepository.save(ta);
			}

		}

	}

	@Override
	public void assignTaskToIntern(int t, int intern) {
		Task task = taskRepository.findById(t);
		User i = userRepository.findById(intern);
		Set<User> interns = new HashSet<>();
		interns.add(i);
		task.setUsers(interns);
		task.setAssignedAt(LocalDateTime.now());
		taskRepository.save(task);

	}

	@Override
	public void disassociateTaskFromIntern(int t, int intern) {
		Task task = taskRepository.findById(t);
		Set<User> interns = task.getUsers();
		for (User u : interns) {
			if (u.getId() == intern) {
				interns.remove(u);
				task.setUsers(interns);
				task.setAssignedAt(LocalDateTime.now());
			}
		}

		taskRepository.save(task);
	}

	@Override
	public int countAssignedTasksByIntern(int idIntern) {

		List<Task> tasks = new ArrayList<>();

		User intern = userRepository.findById(idIntern);
		List<User> users = userRepository.findAll();

		for (User u : users) {
			if (intern.equals(u)) {
				tasks.addAll(u.getTasks());
			}
		}

		return tasks.size();
	}

	@Override
	public List<Task> retreiveAssignedTasksByIntern(int idProject, int idIntern) {

		User intern = userRepository.findById(idIntern);
		Project project = projectRepository.findById(idProject).get();

		List<Task> tasks = new ArrayList<>();

		List<Task> projectTasks = project.getTasks();
		for (Task t : projectTasks) {
			if (t.getUsers().contains(intern)) {
				tasks.add(t);
			}
		}
		return tasks;
	}

 

}
