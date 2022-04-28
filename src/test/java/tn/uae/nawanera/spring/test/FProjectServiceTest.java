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
public class FProjectServiceTest {

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
 	public void testACreateProjectDetails() throws Exception {
		String project="{\r\n"
				+ "    \"title\":\"project1\",\r\n"
				+ "    \"description\":\"In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content.\",\r\n"
				+ "    \"vacancy\":{\r\n"
				+ "        \"id\":1\r\n"
				+ "    }\r\n"
				+ "}";
		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/project/add-project") .content(project)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testBGetVacancyProject() throws Exception {
	 
		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/retreive-project-by-vacancy/1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testCRetreiveProjectDetails() throws Exception {
	 
		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/retreive-project-details/1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
 	public void testDRetreiveAllProjects() throws Exception {
	 
		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/retreiveAllprojects")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testEgetOwnProjects() throws Exception {
	 
		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/retreive-own-projects")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	/*
	
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testFdeleteProject() throws Exception {
	 
		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/project/delete-project/1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	*/
	
	/*
	@Before
	public void setUp() throws Exception {

		trainer=userService.getUserById(4);
		v=vacancyService.getVacancyById(1);
		p = new Project();
		p.setId(1);

		p.setTitle("testttt");
		p.setDescription("uyghijojojdoodk");
		p.setVacancy(v);
		p.setTrainer(trainer);

		projectService.addVacancyProjectDetails(p);

	}
	
	
	@Test
	public void testDisplayVacancyProjectDetailsByVacancy() {

		 p = projectService.displayVacancyProjectDetailsByVacancy(1);
		assertThat(p).isNotNull();
	}
	
	@Test
	public void testDisplayVacancyProjectDetailsById() {

		 p = projectService.displayVacancyProjectDetailsById(1);
		assertThat(p).isNotNull();
	}
	
	
	@Test
	public void testRetrieveAllProject() {

		List<Project> p = projectService.retrieveAllProject();
		assertThat(p.size()).isNotZero();
	}
	
	
	@Test
	public void testRetrieveProjectsByTrainer() {

		List<Project> p = projectService.retrieveProjectsByTrainer(4);
		assertThat(p.size()).isNotZero();
	}
	
	*/
	 
}
