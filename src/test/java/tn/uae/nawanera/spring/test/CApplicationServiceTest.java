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

@RunWith(SpringRunner.class)

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
 
public class CApplicationServiceTest {

	MockMvc mockMvc;

	 
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setupMockMvc() {

		 
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
	}
	
	

	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testApply() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"<<pdf data>>".getBytes(StandardCharsets.UTF_8));
		
		mockMvc.perform(multipart("/api/application/apply/1").file(file))
		.andExpect(status().isOk());

		
	MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/auth/activateAccount/5") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testBGetAllVacancyApplications() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/application/getAllApplicationsByVacancy/2") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	/*
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testCAcceptInternApplication() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/vacancy/accept-application/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	*/
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testDGetAllVacanciesAppliedByIntern() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/application/get-vacancies-By-intern/5") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testEGetAllInternsAppliesForVacancy() throws Exception {
 		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/application/getAllInternByVacancy/2") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	

/*
	 @Test
	 @Order(1)
	 public void testApply() throws Exception {
		  vacancy = vacancyService.getVacancyById(1);
			 
intern=userService.getUserById(5);
			app = new Application(intern, "dfyhg");
			applyService.apply(app, vacancy.getId());
	 }

	@Test
	@Order(2)
	public void testFindApplicationsByVacancy() {
		vacancy = vacancyService.getVacancyById(1);
		List<Application> apps = applyService.findApplicationsByVacancy(vacancy);
		assertThat(apps.size()).isPositive();
	}

	@Test
	
	@Order(3)
	public void testGetAllInternByVacancy() {

		List<User> interns = applyService.getAllInternByVacancy(1);
		assertThat(interns.size()).isPositive();
	}

	@Test
	@Order(4)
	public void testFindVacanciesByIntern() {

		List<Vacancy> vacancies = applyService.findVacanciesByIntern(5);
		assertThat(vacancies.size()).isPositive();
	}

	@Test
	@Order(5)
	public void testAcceptApplication() throws Exception {

		app = applyService.acceptApplication(1);

		assertThat(app.getIsAffected()).isNotNull();
	}
	
	*/

}
