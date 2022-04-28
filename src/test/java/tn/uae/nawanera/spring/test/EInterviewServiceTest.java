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

 
@RunWith(SpringRunner.class)

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EInterviewServiceTest {
	
	MockMvc mockMvc;

 
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setupMockMvc() {

		 
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
	}
 
 
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testAPlannifyInterview() throws Exception {
		String interview="{\"interviewDate\":\"2022-01-20\",\"interviewTime\":\"18:00:00\" }";
		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/interview/plannify/1") .content(interview)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testCAcceptIntern() throws Exception {
 		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/application/accept-application/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
 
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testDAffectTrainerToIntern() throws Exception {
 		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/user/affectTrainerToIntern/4/5") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
 	public void testBretreiveAllInterviews() throws Exception {
 		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/interview/retreiveAllInterviews") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	
	
	
	/*
	@Before
	public void setUp() throws Exception {

		
		app=appService.findApplicationById(1);
		i = new Interview();
		i.setId(1);
	i.setApplication(app);
	i.setInterviewDate(Date.valueOf("2022-03-16"));
	

		interviewService.planifyInterview(i);

	}
	
	@Test
	public void testGetAllInterviews() {

		List<Interview> interviews = interviewService.retreiveInterviews();
		assertThat(interviews.size()).isPositive();
	}
	
	*/
	

}
