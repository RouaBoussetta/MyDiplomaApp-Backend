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

public class BVacancyServiceTest {

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
 	public void testACreateCategory() throws Exception {
		String c="{\r\n"
				+ "    \"category\":\"Backend\"\r\n"
				+ "}";
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/vacancy-categories/add") .content(c)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testBCreateVacancy() throws Exception {
		String v="{\r\n"
				+ "\"title\":\"End-Of-Studies\",\r\n"
				+ "\"description\":\"Dear Young Talent, Are you seeking for an exciting end-of-studies internship in Tunisia: Sousse, Siliana or Jemmal? What a great timing, we are recruiting! \",\r\n"
				+ "\"qualification\":\"Must have strong oral and written communications skills, and be able to work effectively and build relationships with others.\",\r\n"
				+ "\"degree\":\"BACHELOR\",\r\n"
				+ "\"category\":{\"id\":1},\r\n"
				+ "\"paid\":\"false\",\r\n"
				+ "\"status\":\"ACTIVE\"\r\n"
				+ "}";
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/vacancy/addVacancy") .content(v)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testCreateVacancy() throws Exception {
		String v="{\r\n"
				+ "\r\n"
				+ "\"title\":\"vacancy 2\",\r\n"
				+ "\"description\":\"Dear Young Talent, Are you seeking for an exciting end-of-studies internship in Tunisia: Sousse, Siliana or Jemmal? What a great timing, we are recruiting! \",\r\n"
				+ "\"qualification\":\"Must have strong oral and written communications skills, and be able to work effectively and build relationships with others.\",\r\n"
				+ "\"degree\":\"BACHELOR\",\r\n"
				+ "\"category\":{\"id\":1},\r\n"
				+ "\"paid\":\"false\",\r\n"
				+ "\"status\":\"ACTIVE\"\r\n"
				+ "\r\n"
				+ "}";
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/vacancy/addVacancy") .content(v)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	 
	
	@Test
	@WithMockUser
 	public void testGetAllVacancies() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/retreiveAllvacancies") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser
 	public void testDFindVacancyById() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/findVacancyById/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testFindVacanciesByStatus() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/findVacanciesByStatus/ACTIVE") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	 
	
	@Test
	@WithMockUser
 	public void testEFindVacanciesArchieved() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/find-Archieved-Vacancies") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	@Test
	@WithMockUser
 	public void testFindVacanciesActive() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/find-Active-Vacancies") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	@Test
	@WithMockUser
 	public void testFindCompanyVacancies() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/find-company-vacancies/NawaneraLLC") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser
 	public void testFindPaidVacancies() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/find-paid-Vacancies") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testFupdateVacancy() throws Exception {
		
		String vacancy="{\r\n"
				+ "\"title\":\"End-Of-Studies Internships\",\r\n"
				+ "\"description\":\"  What a great timing, we are recruiting! Check out our opportunities in our internship catalog projects please check our LinkedIn post: https://bit.ly/3Ju046e and apply for the project you are interested for!\",\r\n"
				+ "\"qualification\":\"Must have strong oral and written communications skills, and be able to work effectively and build relationships with others.\",\r\n"
				+ "\"degree\":\"BACHELOR\",\r\n"
				+ "\"category\":{\"id\":1},\r\n"
				+ "\"paid\":\"true\",\r\n"
				+ "\"salary\":1000,\r\n"
				+ "\"status\":\"ACTIVE\"\r\n"
				+ "}";
		
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/vacancy/UpdateVacancy/1") .content(vacancy)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	
	@Test
	@WithMockUser
 	public void testcountVacancies() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/count-Vacancies") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser
 	public void testJcountActiveVacancies() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/count-active-Vacancies") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser
 	public void testcountclosedVacancies() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/count-closed-Vacancies") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser
 	public void testcountDraftVacancies() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/count-Draft-Vacancies") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	@Test
	@WithMockUser
 	public void testcountArchievedVacancies() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/count-Archieved-Vacancies") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")

 	public void testGgetcompanyVacancies() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/find-own-company-vacancies") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")

 	public void testHdeleteVacancy() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/vacancy/delete-vacancy/2") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
 
	
	
	
	
	
	
	
	
 
  
	
		
	
	

}
