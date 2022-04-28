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

import tn.uae.nawanera.spring.entities.Project;
import tn.uae.nawanera.spring.entities.TrainingCourse;
import tn.uae.nawanera.spring.repositories.UserRepository;
import tn.uae.nawanera.spring.services.IProjectService;
import tn.uae.nawanera.spring.services.ITrainingCourse;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JTrainingCourseTest {

	
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
 	public void testACreateTrainingCourse() throws Exception {
		String course = "{ \"title\":\"course 1\",\"description\":\"azertyuiop\",\"type\":\"PDF\",\"project\":{\"id\":1}}";

		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"<<pdf data>>".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		TrainingCourse result = objectMapper.readValue(String.valueOf(course), TrainingCourse.class);

		MockMultipartFile metadata = new MockMultipartFile("course", "course", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/course-training/add-training-course").file(file).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
	
	
	@Test
	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
 	public void testBgetAllTrainingCourses() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/course-training/retrieve-all-training-courses") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	@Test
	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
 	public void testCcountTrainingCourses() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/course-training/count-training-courses") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testDgetTrainingCoursesByUserId() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/course-training/retrieve-all-training-courses-by-trainer/4") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testEcountTrainingCoursesByUserId() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/course-training/count-training-courses-by-trainer/4") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testFgetMyTrainingCourses() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/course-training/retrieve-all-own-training-courses") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testGcountMyTrainingCourses() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/course-training/count-all-own-training-courses") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	/*
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testHdeleteTrainingCourse() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/course-training/delete-training-course/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	*/
	
	
	
	
	@Autowired
	ITrainingCourse trainingCourseService;
	@Autowired
	IProjectService projectService;
	Project p;
	TrainingCourse tc;

	/*
	@Before
	public void setUp() throws Exception {

		p = projectService.displayVacancyProjectDetailsById(1);

		tc = new TrainingCourse();
		tc.setId(1);

		tc.setTitle("hgijdkjddkljlkjlklk");
		tc.setDescription("kdhfihfejfoejfo");
		tc.setType(CourseType.PDF);
		tc.setProject(p);

		trainingCourseService.addTrainingCourse(tc);

	}

	@Test
	public void testGetAllTrainingCourses() {

		List<TrainingCourse> tcs = trainingCourseService.getAllTrainingCourses();
		assertThat(tcs.size()).isNotZero();
	}

	@Test
	public void testCountTrainingCourses() {

		int tcs = trainingCourseService.countTrainingCourses();
		assertThat(tcs).isNotZero();
	}

	@Test
	public void testCountTrainingCoursesByUser() {

		int tcs = trainingCourseService.countTrainingCoursesByUser(4);
		assertThat(tcs).isZero();
	}

	@Test
	public void testUpdateTrainingCourse() throws Exception {
		tc = trainingCourseService.getTrainingCourseById(1);
		tc.setTitle("updated");
		TrainingCourse updated = trainingCourseService.updateTrainingCourse(tc);

		assertThat(tc.getId()).isEqualTo(updated.getId());
	}
	
	
	@Test
	public void testGetTrainingCourseById()   {
		tc = trainingCourseService.getTrainingCourseById(1);
		 

		assertThat(tc.getId()).isNotNull();
	}
	
	
	@Test
	public void testGetTrainingCoursesCommentedByUser()   {
		List<TrainingCourse>tc = trainingCourseService.getTrainingCoursesCommentedByUser(5);
		 

		assertThat(tc.size()).isZero();
	}
	
	
	@Test
	public void testGetTrainingCoursesLikedByUser()  {
		List<TrainingCourse>tc = trainingCourseService.getTrainingCoursesLikedByUser(4);
		 

		assertThat(tc.size()).isZero();
	}
	
	*/
	
	
	

}
