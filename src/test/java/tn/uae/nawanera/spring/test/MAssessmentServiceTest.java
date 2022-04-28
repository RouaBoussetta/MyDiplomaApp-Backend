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

import tn.uae.nawanera.spring.entities.Assessment;
import tn.uae.nawanera.spring.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MAssessmentServiceTest {
	
	
	
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
 	public void testAaddAssessment() throws Exception {
		String assessment = "{\"finalNotice\":\"CONCLUSIVE\",\"remark\":\"dyfghioplkjhghuiokjhgyuikjhgj\"}";

		MockMultipartFile stamp = new MockMultipartFile("stamp", "image.jpg", MediaType.IMAGE_JPEG_VALUE,
				"".getBytes(StandardCharsets.UTF_8));
		
		MockMultipartFile signature = new MockMultipartFile("signature", "image.jpg", MediaType.IMAGE_JPEG_VALUE,
				"".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		Assessment result = objectMapper.readValue(String.valueOf(assessment), Assessment.class);

		MockMultipartFile metadata = new MockMultipartFile("a", "assessment", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/project/assessment/add-assessment/intern=/5").file(stamp).file(signature).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testBdisplayassessmentDetailsById() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/assessment/display-assessment-details/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testCdisplayassessmentsByIntern() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/assessment/display-assessments-by-intern/5") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	@Test
	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
 	public void testDdisplayAllAssessments() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/assessment/display-all-assessments") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testEdisplayOwnAssessments() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/assessment/display-own-assessments") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
 	public void testFdisplayConclusiveAssessment() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/assessment/display-Conclusive-assessment") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	@Test
	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
 	public void testGdisplayInconclusiveAssessment() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/assessment/display-Inconclusive-assessment") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	@Test
	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
 	public void testHcountConclusiveAssessment() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/assessment/count-Conclusive-assessment") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
 	public void testIcountInconclusiveAssessment() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/assessment/count-Inconclusive-assessment") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
 	public void testJdisplayAssessmentByNotice() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/assessment/display-assessments-by-notice/CONCLUSIVE") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testKdeleteAssessment() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/project/assessment/delete-assessment/2") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	
/*	
	@Autowired
	IAssessmentService assessmentService;
	@Autowired
	IUserservice userService;
	
	Assessment a;
	User intern,trainer;
	
	/*
	@Before
	public void setUp() throws Exception {

		intern=userService.getUserById(5);
		trainer=userService.getUserById(4);
		a = new Assessment();
		a.setId(1);
		 a.setIntern(intern);
		 a.setRemark("kghuhgerjgor");
		 a.setTrainer(trainer);
		 a.setFinalNotice(Notice.CONCLUSIVE);
		 
		assessmentService.addAssessment(a);

	}

	
	@Test
	public void testRetrieveAllAssessments() {

		List<Assessment> assessments = assessmentService.retrieveAllAssessments();
		assertThat(assessments.size()).isPositive();
	}
	
	
	@Test
	public void testRetrieveAllConclusive() {

		List<Assessment> assessments = assessmentService.retrieveAllConclusive();
		assertThat(assessments.size()).isEqualTo(1);
	}
	
	
	@Test
	public void testRetrieveAllInConclusive() {

		List<Assessment> assessments = assessmentService.retrieveAllInconclusive();
		assertThat(assessments.size()).isEqualTo(0);
	}
	
	

	@Test
	public void testCountAllAssessment() {

		int assessments = assessmentService.countAllAssessment();
		assertThat(assessments).isEqualTo(1);
	}
	
	
	@Test
	public void testDisplayAssessmentsByNotice() {

		List<Assessment> assessments = assessmentService.displayAssessmentsByNotice(Notice.CONCLUSIVE);
		assertThat(assessments.size()).isEqualTo(1);
	}
	
	
	@Test
	public void testDisplayAssessmentById() {

		 Assessment  assessments = assessmentService.displayAssessmentById(1);
		assertThat(assessments ).isNotNull();
	}
	
	
	@Test
	public void testDisplayAssessmentByIntern() {

		List<Assessment>  assessments = assessmentService.displayAssessmentByIntern(5);
		assertThat(assessments.size()).isEqualTo(1);
	}
	
	
	@Test
	public void testRemoveAssessment() {

		assessmentService.removeAssessment(1);
		List<Assessment>  assessments = assessmentService.retrieveAllAssessments();
		assertThat(assessments.size()).isEqualTo(0);
	}
	*/}
