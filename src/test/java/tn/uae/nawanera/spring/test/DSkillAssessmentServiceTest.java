package tn.uae.nawanera.spring.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
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
public class DSkillAssessmentServiceTest {

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
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testACreateSkillAssessment() throws Exception {
		String sa="{\"title\":\"test\",\r\n"
				+ "    \"description\":\"test\"}";
		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/skill-assessment/create-skill-assessment") .content(sa)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testBCreateQuestions() throws Exception {
		String q1="{\"text\":\" question 1\",\"questionScore\":1}";
		String q2="{\"text\":\" question 2\",\"questionScore\":1}";
		String q3="{\"text\":\" question 3\",\"questionScore\":1}";
		
 		MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders.post("/api/skill-assessment/question/new-question-of/1") .content(q1)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();

		assertEquals(200, result1.getResponse().getStatus());
		
		
 		MockHttpServletRequestBuilder requestBuilder2 = MockMvcRequestBuilders.post("/api/skill-assessment/question/new-question-of/1") .content(q2)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();

		assertEquals(200, result2.getResponse().getStatus());
		
		
 		MockHttpServletRequestBuilder requestBuilder3 = MockMvcRequestBuilders.post("/api/skill-assessment/question/new-question-of/1") .content(q3)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result3 = mockMvc.perform(requestBuilder3).andReturn();

		assertEquals(200, result3.getResponse().getStatus());

	}
 
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testCCreateAnswers() throws Exception {
		String a1="{ \"text\":\"answer 1 of q1\" }";
		String a2="{ \"text\":\"answer 2 of q1\" }";
		String a3="{ \"text\":\"answer 3 of q1\" }";
		
		
		String a11="{ \"text\":\"answer 1 of q2\" }";
		String a22="{\"text\":\" answer 2 of q2\" }";
		String a33="{\"text\":\" answer 3 of q2\" }";
		
 		MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders.post("/api/skill-assessment/question/answers/add-answer-to-question/1") .content(a1)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();

		assertEquals(200, result1.getResponse().getStatus());
		
		
 		MockHttpServletRequestBuilder requestBuilder2 = MockMvcRequestBuilders.post("/api/skill-assessment/question/answers/add-answer-to-question/1") .content(a2)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();

		assertEquals(200, result2.getResponse().getStatus());
		
		
 		MockHttpServletRequestBuilder requestBuilder3 = MockMvcRequestBuilders.post("/api/skill-assessment/question/answers/add-answer-to-question/1") .content(a3)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result3 = mockMvc.perform(requestBuilder3).andReturn();

		assertEquals(200, result3.getResponse().getStatus());
		
		
		
		
		
		
		
 		MockHttpServletRequestBuilder requestBuilder11 = MockMvcRequestBuilders.post("/api/skill-assessment/question/answers/add-answer-to-question/2") .content(a11)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result11 = mockMvc.perform(requestBuilder11).andReturn();

		assertEquals(200, result11.getResponse().getStatus());
		
		
 		MockHttpServletRequestBuilder requestBuilder22 = MockMvcRequestBuilders.post("/api/skill-assessment/question/answers/add-answer-to-question/2") .content(a22)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result22 = mockMvc.perform(requestBuilder22).andReturn();

		assertEquals(200, result22.getResponse().getStatus());
		
		
 		MockHttpServletRequestBuilder requestBuilder33 = MockMvcRequestBuilders.post("/api/skill-assessment/question/answers/add-answer-to-question/2") .content(a33)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result33 = mockMvc.perform(requestBuilder33).andReturn();

		assertEquals(200, result33.getResponse().getStatus());

	}
	
	
	 	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testDDeleteQuestion() throws Exception {
 
		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/skill-assessment/question/delete-question/3")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
 
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testESetCorrectAnswers() throws Exception {
 

		
 		MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders.put("/api/skill-assessment/question/1/set-correct-answer/2") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();

		assertEquals(200, result1.getResponse().getStatus());
		
		
 		MockHttpServletRequestBuilder requestBuilder2 = MockMvcRequestBuilders.put("/api/skill-assessment/question/2/set-correct-answer/4") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();

		assertEquals(200, result2.getResponse().getStatus());
		

	}
	
 
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testFPublishSA() throws Exception {
 

		
 		MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders.put("/api/skill-assessment/publish-SkillAssessment/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();

		assertEquals(200, result1.getResponse().getStatus());

		

	}
	  
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testGAssignSAToIntern() throws Exception {
 

		
 		MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders.put("/api/skill-assessment/assign-skill-assessment-to-intern/1/5") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();

		assertEquals(200, result1.getResponse().getStatus());

		

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testHStartSA() throws Exception {
 
				String submit="[{\"question\":1,\"selectedAnswer\":2},{\"question\":2,\"selectedAnswer\":4}]";
		
 		MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders.post("/api/skill-assessment/1/submitAnswers") .content(submit)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();

		assertEquals(200, result1.getResponse().getStatus());

		

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	@Before
	public void setUp() throws Exception {

		hr = userService.getUserById(2);
		sa = new SkillAssessment();
		sa.setId(1);
		sa.setDescription("sdfghjklm");
		sa.setTitle("skill 1");
		sa.setCreatedBy(hr);
		skillAssessementService.save(sa);
		
		 
	}

	@Test
	public void testRetreiveAll() {
		List<SkillAssessment> sas = skillAssessementService.retreiveAll();
		assertThat(sas.size()).isPositive();
	}

	@Test
	public void testRetrieveAllPublished() {
		List<SkillAssessment> sas = skillAssessementService.retrieveAllPublished();
		assertThat(sas.size()).isZero();
	}

	@Test
	public void testRetrieveAllByUser() {
		List<SkillAssessment> sas = skillAssessementService.retrieveAllByUser(2);
		assertThat(sas.size()).isPositive();
	}

	@Test
	public void testUpdate() {
		sa = skillAssessementService.find(1);

		sa.setTitle("updatedddd");
		SkillAssessment updated = skillAssessementService.update(sa);
		assertThat(sa.getId()).isEqualTo(updated.getId());
	}

	@Test
	public void testPublishSkillAssessment() {

		skillAssessementService.publishSkillAssessment(1);
		sa = skillAssessementService.find(1);

		assertThat(sa.getIsPublished()).isNotEqualTo(false);
	}
*/
	

	
}
