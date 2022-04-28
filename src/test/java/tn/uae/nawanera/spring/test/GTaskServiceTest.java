package tn.uae.nawanera.spring.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import tn.uae.nawanera.spring.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GTaskServiceTest {
	
	
	MockMvc mockMvc;

	@Autowired
	UserRepository userRepository;
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setupMockMvc() {

		 
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
	}
	
  
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testACreateProjectTask() throws Exception {
		String task="{ \"taskName\":\"Project Specification\",\"deadline\":\"2022-06-01\" }";
		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/project/tasks/add-project-task/1") .content(task)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
 
	
	
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testBupdateProjectTask() throws Exception {
		String task="{ \"taskName\":\"updated\", \"status\": \"TODO\",\"taskIssue\": \"\",\"deadline\":\"2022-06-17\"}";
		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/project/tasks/Update-project-task/1") .content(task)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testCupdateProjectTask() throws Exception {
		String task="{ \"taskName\":\"updated\", \"status\": \"DOING\",\"taskIssue\": \"\",\"deadline\":\"2022-06-17\"}";
		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/project/tasks/Update-project-task/1") .content(task)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testDgetAllProjectTasks() throws Exception {
 		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/tasks/retreiveAllProjectTasks/1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testEgetProjectTaskDetails() throws Exception {
 		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/tasks/retreive-Project-Task-details/1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser 
 	public void testFcountProjectTasks() throws Exception {
 		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/tasks/count-Project-Tasks/1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testGretreiveAllProjectTasksByStatus() throws Exception {
 		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/tasks/retreiveAllProjectTasks-byStatus/DOING")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
 
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testHassignTask() throws Exception {
 		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/project/tasks/assign-task/1/to-intern/5")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	 
	
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testIdisassociateTask() throws Exception {
 		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/project/tasks/disassociate-task/1/from-intern/5")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
 
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testJcountInternTasks() throws Exception {
 		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/tasks/count-intern-Tasks/5")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testKretreiveInternTasks() throws Exception {
 		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/tasks/retreive-intern-Tasks/5")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	/*
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testLremoveProjectTask() throws Exception {
 		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/project/tasks/remove-project-task/1/1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	*/

	
	
	
	
	
	
	
	
	/*
	@Before
	public void setUp() throws Exception {

		p = projectService.displayVacancyProjectDetailsById(1);

		task = new Task();
		task.setId(1);

		task.setTaskName("hjfgdjhfjdh");
		task.setStatus(TaskStatus.TODO);
		task.setProject(p);
		task.setDeadline(Date.valueOf("2022-03-16"));
		taskService.addProjectTask(task);

	}
	
	
	@Test
	public void testRetreiveAllProjectTasks() {
 
		List<Task> tasks = taskService.retreiveAllProjectTasks(1);
		assertThat(tasks.size()).isNotZero();
	}
	
	
	@Test
	public void testCountProjectTasks() {
 
		 int tasks = taskService.countProjectTasks(1);
		assertThat(tasks).isNotZero();
	}
	
	
	@Test
	public void testRetreiveAllProjectTasksByStatus() {
 
		 List<Task> tasks = taskService.retreiveAllProjectTasksByStatus(TaskStatus.TODO);
		assertThat(tasks).isNotNull();
	}
	
	
	@Test
	public void testRetreiveAllProjectTasksByDeadline() {
 
		 List<Task> tasks = taskService.retreiveAllProjectTasksByDeadline(Date.valueOf("2022-03-16"));
		assertThat(tasks).isNotNull();
	}
	
	
	
	@Test
	public void testAssignTaskToIntern() {
 task=taskService.retreiveTaskDetails(1);
		   taskService.assignTaskToIntern(task, 5);
		   
		   
 	}
	
	
	@Test
	public void testUpdateProjectTask() {
		
		task=taskService.retreiveTaskDetails(1);
 Task updated=taskService.updateProjectTask(task,1);
	assertThat(task.getId()).isEqualTo(updated.getId());

		   
		   
 	}
	
	*/
	
	

	
	

	
	

}
