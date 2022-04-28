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

import tn.uae.nawanera.spring.entities.Certificate;
import tn.uae.nawanera.spring.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CertificateServiceTest {
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
 	public void testAAddCCertificate() throws Exception {
		String certificate = "{  \"title\":\"certificate 1\",\"description\":\"tfyghjiklpjhgfdtyuiokjftyuioklhgftyuiokjhgyuio\"}";

		MockMultipartFile stamp = new MockMultipartFile("stamp", "image.jpg", MediaType.IMAGE_JPEG_VALUE,
				"".getBytes(StandardCharsets.UTF_8));
		
		MockMultipartFile signature = new MockMultipartFile("signature", "image.jpg", MediaType.IMAGE_JPEG_VALUE,
				"".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		Certificate result = objectMapper.readValue(String.valueOf(certificate), Certificate.class);

		MockMultipartFile metadata = new MockMultipartFile("c", "certificate", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/certificate/add-Certificate/5").file(stamp).file(signature).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testBdisplaycertificateDetailsById() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/certificate/display-certificate-details/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
 	public void testCdisplayCertificatesByHr() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/certificate/display-Certificates-by-attributer/4") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
 	public void testDdisplayCertificatesByProject() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/certificate/display-Certificates-by-project/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testEdisplayOwnCertificates() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/certificate/display-own-Certificates") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testFdisplayOwnHrCertificates() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/certificate/display-own-hr-Certificates") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
 	public void testGdeleteCertificate() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/certificate/delete-certificate/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	/*
	@Autowired
	ICertificateService certificateService;
	
	Certificate c;
	@Autowired
	IUserservice userService;
	
	
	User intern,hr;
	
	/*
	@Before
	public void setUp() throws Exception {

		intern=userService.getUserById(5);
		hr=userService.getUserById(2);
		c = new Certificate();
		c.setId(1);
		c.setDescription("kjshfkshfkdjf");
		c.setHrmanager(hr);
		c.setIntern(intern);
		c.setTitle("jghezjkhfkhfkrh");
		 
		certificateService.addCertificateDetails(c);

	}
	
	@Test
	public void testRetrieveAllCertificate() {

		List<Certificate> certifs = certificateService.retrieveAllCertificate();
		assertThat(certifs.size()).isEqualTo(1);
	}
	
	
	@Test
	public void testDisplayCertificateDetailsById() {

		c = certificateService.displayCertificateDetailsById(1);
		assertThat(c ).isNotNull();
	}
	
	
	@Test
	public void testDisplayCertificatesByAttributer() {

		List<Certificate> certifs = certificateService.displayCertificatesByAttributer(2);
		assertThat(certifs.size()).isEqualTo(1);
	}
	
	
	
	@Test
	public void testRetrieveAllCertificateOfProject() {

		List<Certificate> certifs = certificateService.retrieveAllCertificateOfProject(1);
		assertThat(certifs.size()).isZero();
	}
	
	
	@Test
	public void testRemoveCertificate() {

		certificateService.removeCertificate(1);
		List<Certificate> certifs = certificateService.retrieveAllCertificate();
		assertThat(certifs.size()).isZero();
	}
	
	*/

}
