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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.repositories.UserRepository;

@RunWith(SpringRunner.class)

@SpringBootTest
@FixMethodOrder(MethodSorters.DEFAULT)

public class MyDiplomaAppApplicationTest {

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
	public void testAsignup() throws Exception {

		String admin = "{\"firstname\":\"Roua\",\"lastname\":\"Boussetta\",\"username\":\"Roua_Boussetta\",\"companyName\":\"myDiploma\",\"email\":\"myDiploma@gmail.com\",\"password\":\"azerty123\",\"role\":{\"id\":1,\"roleType\":\"ADMINISTRATOR\"}}";

		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"<<pdf data>>".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		User result = objectMapper.readValue(String.valueOf(admin), User.class);

		MockMultipartFile metadata = new MockMultipartFile("u", "admin", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/auth/signup").file(file).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void testSignupIntern() throws Exception {

		String intern = "{\"firstname\":\"Noureddine\",\"lastname\":\"Rezgui\",\"username\":\"Noureddine_Rezgui\",\"email\":\"noureddine@gmail.com\",\"password\":\"azerty123\",\"role\":{\"id\":5,\"roleType\":\"INTERN\"}}";

		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"<<pdf data>>".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		User result = objectMapper.readValue(String.valueOf(intern), User.class);

		MockMultipartFile metadata = new MockMultipartFile("u", "intern", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/auth/signup").file(file).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void testBsignin() throws Exception {
		String logged = "{\"username\":\"Roua_Boussetta\",\"password\":\"azerty123\"}";
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signin").content(logged)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test

	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
	public void testCreateCompanies() throws Exception {

		String company = "{\"firstname\":\"Nawanera\",\"lastname\":\"LLC\",\"username\":\"NawaneraLLC\",\"companyName\":\"NawaneraLLC\",\"email\":\"nawanera@gmail.com\",\"password\":\"azerty123\",\"role\":{\"id\":2,\"roleType\":\"COMPANY\"}}";

		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"<<pdf data>>".getBytes(StandardCharsets.UTF_8));
		ObjectMapper objectMapper = new ObjectMapper();
		User result = objectMapper.readValue(String.valueOf(company), User.class);

		MockMultipartFile metadata = new MockMultipartFile("u", "company", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
		mockMvc.perform(multipart("/api/user/addCompany").file(file).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "NawaneraLLC", password = "azerty123", authorities = "COMPANY")
	public void testDCreateHrManager() throws Exception {

		String hr = "{\"firstname\":\"Daryll\",\"lastname\":\"See\",\"username\":\"Daryl_See\",\"email\":\"daryl@gmail.com\",\"password\":\"azerty123\"}";

		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"<<pdf data>>".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		User result = objectMapper.readValue(String.valueOf(hr), User.class);

		MockMultipartFile metadata = new MockMultipartFile("u", "hr", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/user/addManager").file(file).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "Daryl_See", password = "daryl", authorities = "HR_MANAGER")
	public void testECreateTrainer() throws Exception {

		String trainer = "{\"firstname\":\"Ahmed\",\"lastname\":\"ahmed\",\"username\":\"Ahmed_Ahmed\",\"email\":\"ahmed@gmail.com\",\"password\":\"azerty123\"}";

		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"<<pdf data>>".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		User result = objectMapper.readValue(String.valueOf(trainer), User.class);

		MockMultipartFile metadata = new MockMultipartFile("u", "trainer", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/user/addTrainer").file(file).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
/*
	@Test
	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
	public void testFGetCurrentUserTest() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/current");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("********************" + result.getResponse().getContentAsString());

		assertEquals("Roua_Boussetta", result.getResponse().getContentAsString());
	}
*/
	@Test
	@WithMockUser(username = "Daryl_See", password = "azerty123", authorities = "HR_MANAGER")
	public void testGEditProfile() throws Exception {

		String hr = "{\"firstname\":\"Daryll\",\"lastname\":\"See\",\"username\":\"Daryl_See\",\"email\":\"daryl.see@gmail.com\",\"password\":\"daryl\"}";
		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"<<pdf data>>".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		User result = objectMapper.readValue(String.valueOf(hr), User.class);

		MockMultipartFile metadata = new MockMultipartFile("u", "hr", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/user/edit-profile/4").file(file).file(metadata)
				.accept(MediaType.APPLICATION_JSON).with(new RequestPostProcessor() {
					@Override
					public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
						request.setMethod("PUT");
						return request;
					}
				})).andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "Roua_Boussetta", password = "azerty123", authorities = "ADMINISTRATOR")
	public void testBGetAllUsers() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/findall")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	/*
	 * @Test
	 * 
	 * @WithMockUser(username = "Roua_Boussetta", password = "azerty123",
	 * authorities = "ADMINISTRATOR") public void testHLockCompanyService() throws
	 * Exception { MockHttpServletRequestBuilder requestBuilder =
	 * MockMvcRequestBuilders.put("/api/user/lock-company-service/2")
	 * .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
	 * 
	 * MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	 * 
	 * assertEquals(200, result.getResponse().getStatus());
	 * 
	 * }
	 * 
	 * 
	 * 
	 * @Test
	 * 
	 * @WithMockUser(username = "Roua_Boussetta", password = "azerty123",
	 * authorities = "ADMINISTRATOR") public void testHUnLockCompanyService() throws
	 * Exception { MockHttpServletRequestBuilder requestBuilder =
	 * MockMvcRequestBuilders.put("/api/user/unlock-company-service/2")
	 * .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
	 * 
	 * MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	 * 
	 * assertEquals(200, result.getResponse().getStatus());
	 * 
	 * }
	 * 
	 */

}
