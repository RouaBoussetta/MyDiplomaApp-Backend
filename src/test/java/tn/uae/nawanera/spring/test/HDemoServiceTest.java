package tn.uae.nawanera.spring.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import tn.uae.nawanera.spring.entities.Demo;
import tn.uae.nawanera.spring.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HDemoServiceTest {
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
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testAUploadTaskDemo() throws Exception {
		String demo = "{\"category\":\"DOCUMENT\",\"description\":\"jnkjkjk\"}";

		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		Demo result = objectMapper.readValue(String.valueOf(demo), Demo.class);

		MockMultipartFile metadata = new MockMultipartFile("d", "demo", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/project/task/demo/add-task-demo/1").file(file).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testBretreiveOwnDemos() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/task/demo/retreiveOwnDemos") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testCretreiveAllDemos() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/task/demo/retreiveAllDemos") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testDretreiveAllDemosbyTask() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/task/demo/retreiveAllDemos-by-task/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testEdisplayDemoDetails() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/task/demo/display-demo-details/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testFdisplayDemoDetailsByTask() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/task/demo/display-demo-details-by-task/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testGdeleteTaskDemo() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/project/task/demo/delete-task-demo/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	/*
	@Autowired
	IDemoService demoService;
	
	
	@Autowired
	ITaskService taskService;
	
	
	Demo d;
	Task task;
	
	/*
	@Before
	public void setUp() throws Exception {

		task=taskService.retreiveTaskDetails(1);
		d = new Demo();
		d.setId(1);
		d.setCategory(DemoCategory.DOCUMENT);
		d.setDescription("jkhkjhkhjhoih");
		d.setTask(task);
		 
		demoService.addDemoDetails(d);

	}

	
	@Test
	public void testRetrieveAllDemos() {

		List<Demo> demos = demoService.retrieveAllDemos();
		assertThat(demos.size()).isPositive();
	}
	
	@Test
	public void testDisplayDemoDetailsById() {

		Demo demo = demoService.displayDemoDetailsById(1);
		assertThat(demo ).isNotNull();
	}
	
	
	@Test
	public void testRetrieveDemosByTask() {

		List<Demo> demos  = demoService.retrieveDemosByTask(1);
		assertThat(demos.size() ).isEqualTo(1);
	}
	
	
	@Test
	public void testRemoveDemo() {

		demoService.removeDemo(1);
		List<Demo> demos  = demoService.retrieveAllDemos();
		assertThat(demos.size() ).isZero();
	}

	*/

}
