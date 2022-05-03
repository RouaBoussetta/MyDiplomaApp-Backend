package tn.uae.nawanera.spring.test;

import static org.assertj.core.api.Assertions.assertThat;
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

import tn.uae.nawanera.spring.entities.Answer;
import tn.uae.nawanera.spring.entities.Question;
import tn.uae.nawanera.spring.entities.SkillAssessment;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;
import tn.uae.nawanera.spring.entities.VacancyCategory;
import tn.uae.nawanera.spring.repositories.UserRepository;
import tn.uae.nawanera.spring.services.IAnswerService;
import tn.uae.nawanera.spring.services.IQuestionService;
import tn.uae.nawanera.spring.services.ISkillAssessmentService;
import tn.uae.nawanera.spring.services.IUserservice;
import tn.uae.nawanera.spring.services.IVacancyCategoryService;
import tn.uae.nawanera.spring.services.IVacancyService;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class MydiplomaAppTest {

	MockMvc mockMvc;

	@Autowired
	UserRepository userRepository;
	@Autowired
	IVacancyCategoryService icategory;

	@Autowired
	IVacancyService ivacancy;
	@Autowired
	IUserservice iuser;
	@Autowired
	IQuestionService iquestion;
	@Autowired
	IAnswerService ianswer;
	@Autowired
	ISkillAssessmentService isk;
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setupMockMvc() {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
	}
 
	@Test
	public void AAtestsignup() throws Exception {

		String admin = "{\"firstname\":\"Admin\",\"lastname\":\"admin\",\"username\":\"Admin_Admin\",\"companyName\":\"myDiploma\",\"email\":\"admin@gmail.com\",\"password\":\"azerty123\",\"role\":{\"id\":1,\"roleType\":\"ADMINISTRATOR\"}}";

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
	public void AtestSignupIntern() throws Exception {

		String intern = "{\"firstname\":\"intern\",\"lastname\":\"intern\",\"username\":\"Intern_Intern\",\"email\":\"boussettaroua@gmail.com\",\"password\":\"azerty123\",\"role\":{\"id\":5,\"roleType\":\"INTERN\"}}";

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
	public void Atestsignin() throws Exception {
		String logged = "{\"username\":\"Admin_Admin\",\"password\":\"azerty123\"}";
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/signin").content(logged)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void BtestCreateCompanies() throws Exception {

		String company = "{\"firstname\":\"company\",\"lastname\":\"company\",\"username\":\"Company_Company\",\"companyName\":\"Company\",\"email\":\"company@gmail.com\",\"password\":\"azerty123\",\"role\":{\"id\":2,\"roleType\":\"COMPANY\"}}";

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
	@WithMockUser(username = "Company_Company", password = "azerty123", authorities = "COMPANY")
	public void CtestCreateHrManager() throws Exception {

		String hr = "{\"firstname\":\"Hr\",\"lastname\":\"Hr\",\"username\":\"Hr_Manager\",\"email\":\"hr@gmail.com\",\"password\":\"azerty123\"}";

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
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void DtestCreateTrainer() throws Exception {

		String trainer = "{\"firstname\":\"Trainer\",\"lastname\":\"Trainer\",\"username\":\"Trainer_Trainer\",\"email\":\"trainer@gmail.com\",\"password\":\"azerty123\"}";

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
	 * @Test
	 * 
	 * @WithMockUser(username = "Daryl_See", password = "azerty123", authorities =
	 * "HR_MANAGER") public void EtestEditProfile() throws Exception {
	 * 
	 * String hr =
	 * "{\"firstname\":\"Daryll\",\"lastname\":\"See\",\"username\":\"Daryl_See\",\"email\":\"daryl.see@gmail.com\",\"password\":\"daryl\"}";
	 * MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf",
	 * MediaType.APPLICATION_PDF_VALUE,
	 * "<<pdf data>>".getBytes(StandardCharsets.UTF_8));
	 * 
	 * ObjectMapper objectMapper = new ObjectMapper(); User result =
	 * objectMapper.readValue(String.valueOf(hr), User.class);
	 * 
	 * MockMultipartFile metadata = new MockMultipartFile("u", "hr",
	 * MediaType.APPLICATION_JSON_VALUE,
	 * objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
	 * 
	 * mockMvc.perform(multipart("/api/user/edit-profile").file(file).file(metadata)
	 * .accept(MediaType.APPLICATION_JSON).with(new RequestPostProcessor() {
	 * 
	 * @Override public MockHttpServletRequest
	 * postProcessRequest(MockHttpServletRequest request) {
	 * request.setMethod("PUT"); return request; } })) .andExpect(status().isOk());
	 * 
	 * }
	 */

	
 
	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void FtestGetAllUsers() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/findall")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void FtestFindUserLastName() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/findUserLastname/Company")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void FtestFindUserByUsername() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/user/findUserByUsername/Company_Company").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void FtestBFindUserActivated() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/findActivatedUser")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void FtestBFindCompanyInterns() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/company-interns/Company")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void FtestFindCompanyHrs() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/company-hrs/Company")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	/*****************************************
	 * Internship Vacancy
	 *****************************************/
 
	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void EtestCreateCategory() throws Exception {
		String c = "{\r\n" + "    \"category\":\"test\"\r\n" + "}";
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/vacancy-categories/add")
				.content(c).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void GtestCreateVacancy() throws Exception {
		VacancyCategory category = new VacancyCategory();
		category.setCategory("testCategory");
		VacancyCategory vc = icategory.createCateogry(category);
		assertThat(vc).isNotNull();
		ObjectMapper Obj = new ObjectMapper();

		// get category object as a json string
		String jsonStr = Obj.writeValueAsString(category);

		// Displaying JSON String
		System.out.println(jsonStr);

		String v = "{\r\n" + "\"title\":\"test\",\r\n"
				+ "\"description\":\"Dear Young Talent, Are you seeking for an exciting end-of-studies internship in Tunisia: Sousse, Siliana or Jemmal? What a great timing, we are recruiting! \",\r\n"
				+ "\"qualification\":\"Must have strong oral and written communications skills, and be able to work effectively and build relationships with others.\",\r\n"
				+ "\"degree\":\"BACHELOR\",\r\n" + "\"category\":" + jsonStr + "," + "\"paid\":\"false\",\r\n"
				+ "\"status\":\"ACTIVE\"\r\n" + "}";
		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"<<pdf data>>".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		Vacancy result = objectMapper.readValue(String.valueOf(v), Vacancy.class);

		MockMultipartFile metadata = new MockMultipartFile("v", "v", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(
				multipart("/api/vacancy/addVacancy").file(file).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	@WithMockUser
	public void ItestGetAllVacancies() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/retreiveAllvacancies")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void ItestFindVacancyById() throws Exception {
		Vacancy v = ivacancy.getVacancyByCompany("Company");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/vacancy/findVacancyById/" + v.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void ItestFindVacanciesByStatus() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/vacancy/findVacanciesByStatus/ACTIVE").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void ItestFindVacanciesArchieved() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/vacancy/find-Archieved-Vacancies").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void ItestFindVacanciesActive() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/find-Active-Vacancies")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void ItestFindCompanyVacancies() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/vacancy/find-company-vacancies/Company").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void ItestFindPaidVacancies() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/find-paid-Vacancies")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void ItestcountVacancies() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/count-Vacancies")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void ItestcountActiveVacancies() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/count-active-Vacancies")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void ItestcountclosedVacancies() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/count-closed-Vacancies")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void ItestcountDraftVacancies() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vacancy/count-Draft-Vacancies")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void ItestcountArchievedVacancies() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/vacancy/count-Archieved-Vacancies").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")

	public void ItestgetcompanyVacancies() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/vacancy/find-own-company-vacancies").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	/***************************************
	 * Application
	 ***********************************************/
 
	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void JJtestApply() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"<<pdf data>>".getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/application/apply-by-title/test").file(file)).andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void JtestBGetAllVacancyApplications() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/application/getAllApplicationsByVacancy-title/test").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	/*
	 * @Test
	 * 
	 * @WithMockUser(username = "Daryl_See", password = "daryl", authorities =
	 * "HR_MANAGER") public void testCAcceptInternApplication() throws Exception {
	 * MockHttpServletRequestBuilder requestBuilder =
	 * MockMvcRequestBuilders.put("/api/vacancy/accept-application/1")
	 * .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
	 * 
	 * MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	 * 
	 * assertEquals(200, result.getResponse().getStatus());
	 * 
	 * }
	 */

 
	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty", authorities = "HR_MANAGER")
	public void JtestDGetAllVacanciesAppliedByIntern() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/application/get-vacancies-By-intern-username/Intern_Intern")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void JtestEGetAllInternsAppliesForVacancy() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/application/getAllInternByVacancyTitle/test").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	/*****************************************
	 * Qualifying Test
	 **********************************************/
 
	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void KKKKtestACreateSkillAssessment() throws Exception {
		String sa = "{\"title\":\"test\",\r\n" + "    \"description\":\"test\"}";

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/skill-assessment/create-skill-assessment").content(sa).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void KKKtestCreateQuestions() throws Exception {

		Question q1 = new Question();
		q1.setText("q1");
		q1.setQuestionScore(1);

		Question q2 = new Question();
		q2.setText("q2");
		q2.setQuestionScore(1);

		ObjectMapper Obj = new ObjectMapper();

		String jsonStr1 = Obj.writeValueAsString(q1);
		String jsonStr2 = Obj.writeValueAsString(q2);

		MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders
				.post("/api/skill-assessment/question/new-question-of-sk-title/test").content(jsonStr1)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();

		assertEquals(200, result1.getResponse().getStatus());

		MockHttpServletRequestBuilder requestBuilder2 = MockMvcRequestBuilders
				.post("/api/skill-assessment/question/new-question-of-sk-title/test").content(jsonStr2)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();

		assertEquals(200, result2.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void KKtestCCreateAnswers() throws Exception {

		Question q1 = iquestion.getQuestionByText("q1");
		Question q2 = iquestion.getQuestionByText("q2");

		Answer a1 = new Answer();
		a1.setText("option1-q1");
		Answer a2 = new Answer();
		a2.setText("option2-q1");
		Answer a3 = new Answer();
		a3.setText("option3-q1");

		Answer aa1 = new Answer();
		aa1.setText("option1-q2");
		Answer aa2 = new Answer();
		aa2.setText("option2-q2");
		Answer aa3 = new Answer();
		aa3.setText("option3-q2");
		ObjectMapper Obj = new ObjectMapper();

		String jsonStr1 = Obj.writeValueAsString(a1);
		String jsonStr2 = Obj.writeValueAsString(a2);
		String jsonStr3 = Obj.writeValueAsString(a3);

		String jsonStra1 = Obj.writeValueAsString(aa1);
		String jsonStra2 = Obj.writeValueAsString(aa2);
		String jsonStra3 = Obj.writeValueAsString(aa3);

		MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders
				.post("/api/skill-assessment/question/answers/add-answer-to-question/" + q1.getId()).content(jsonStr1)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();

		assertEquals(200, result1.getResponse().getStatus());

		MockHttpServletRequestBuilder requestBuilder2 = MockMvcRequestBuilders
				.post("/api/skill-assessment/question/answers/add-answer-to-question/" + q1.getId()).content(jsonStr2)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();

		assertEquals(200, result2.getResponse().getStatus());

		MockHttpServletRequestBuilder requestBuilder3 = MockMvcRequestBuilders
				.post("/api/skill-assessment/question/answers/add-answer-to-question/" + q1.getId()).content(jsonStr3)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result3 = mockMvc.perform(requestBuilder3).andReturn();

		assertEquals(200, result3.getResponse().getStatus());

		MockHttpServletRequestBuilder requestBuilder11 = MockMvcRequestBuilders
				.post("/api/skill-assessment/question/answers/add-answer-to-question/" + q2.getId()).content(jsonStra1)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result11 = mockMvc.perform(requestBuilder11).andReturn();

		assertEquals(200, result11.getResponse().getStatus());

		MockHttpServletRequestBuilder requestBuilder22 = MockMvcRequestBuilders
				.post("/api/skill-assessment/question/answers/add-answer-to-question/" + q2.getId()).content(jsonStra2)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result22 = mockMvc.perform(requestBuilder22).andReturn();

		assertEquals(200, result22.getResponse().getStatus());

		MockHttpServletRequestBuilder requestBuilder33 = MockMvcRequestBuilders
				.post("/api/skill-assessment/question/answers/add-answer-to-question/" + q2.getId()).content(jsonStra3)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result33 = mockMvc.perform(requestBuilder33).andReturn();

		assertEquals(200, result33.getResponse().getStatus());

	}

	/*
	 * @Test
	 * 
	 * @WithMockUser(username = "Daryl_See", password = "daryl", authorities =
	 * "HR_MANAGER") public void KtestDDeleteQuestion() throws Exception {
	 * 
	 * 
	 * MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
	 * "/api/skill-assessment/question/delete-question/3")
	 * .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
	 * 
	 * MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	 * 
	 * assertEquals(200, result.getResponse().getStatus());
	 * 
	 * }
	 * 
	 */
 
	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void LtestESetCorrectAnswers() throws Exception {

		Question q1 = iquestion.getQuestionByText("q1");
		Question q2 = iquestion.getQuestionByText("q2");

		Answer a1 = ianswer.getAnswerByText("option1-q1");
		Answer a2 = ianswer.getAnswerByText("option2-q2");

		MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders
				.put("/api/skill-assessment/question/" + q1.getId() + "/set-correct-answer/" + a1.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();

		assertEquals(200, result1.getResponse().getStatus());

		MockHttpServletRequestBuilder requestBuilder2 = MockMvcRequestBuilders
				.put("/api/skill-assessment/question/" + q2.getId() + "/set-correct-answer/" + a2.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();

		assertEquals(200, result2.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void MMMMtestFPublishSA() throws Exception {

		MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders
				.put("/api/skill-assessment/publish-SkillAssessment-title/test").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();

		assertEquals(200, result1.getResponse().getStatus());

	}
 
	
	  @Test
	  @WithMockUser(username = "Hr_Manager", password = "azerty123", authorities ="HR_MANAGER")
	  public void MMtestAssignSAToIntern() throws Exception {
	  User intern=iuser.findUserBylogin("Intern_Intern");
	  SkillAssessment sk=isk.getSkillAssessmentByTitle("test");
	  MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders.put(
	  "/api/skill-assessment/assign-skill-assessment-to-intern/"+sk.getId()+"/"+intern.getId())
	  .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
	  
	  MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
	  
	  assertEquals(200, result1.getResponse().getStatus());

	  }


	@Test

	public void MMMtestActivate() throws Exception {

		User intern = iuser.findUserBylogin("Intern_Intern");

		MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders
				.put("/api/auth/activateAccount/" + intern.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();

		assertEquals(200, result1.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void MtestStartSA() throws Exception {
		Question q1 = iquestion.getQuestionByText("q1");
		Question q2 = iquestion.getQuestionByText("q2");

		Answer a1 = ianswer.getAnswerByText("option1-q1");
		Answer a2 = ianswer.getAnswerByText("option2-q2");
	 
		SkillAssessment sk = isk.getSkillAssessmentByTitle("test");
		String submit = "[{\"question\":" + q1.getId() + ",\"selectedAnswer\":" + a1.getId() + "},{\"question\":"
				+ q2.getId() + ",\"selectedAnswer\":" + a2.getId() + "}]";

		MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders
				.post("/api/skill-assessment/" + sk.getId() + "/submitAnswers").content(submit)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();

		assertEquals(200, result1.getResponse().getStatus());

	}

}
