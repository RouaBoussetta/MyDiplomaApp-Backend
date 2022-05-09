package tn.uae.nawanera.spring.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.List;

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

import tn.uae.nawanera.spring.entities.Answer;
import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.Assessment;
import tn.uae.nawanera.spring.entities.Comment;
import tn.uae.nawanera.spring.entities.Demo;
import tn.uae.nawanera.spring.entities.Document;
import tn.uae.nawanera.spring.entities.Project;
import tn.uae.nawanera.spring.entities.Question;
import tn.uae.nawanera.spring.entities.SkillAssessment;
import tn.uae.nawanera.spring.entities.Task;
import tn.uae.nawanera.spring.entities.TrainingCourse;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;
import tn.uae.nawanera.spring.entities.VacancyCategory;
import tn.uae.nawanera.spring.repositories.ApplicationRepository;
import tn.uae.nawanera.spring.repositories.AssessmentRepository;
import tn.uae.nawanera.spring.repositories.DemoRepository;
import tn.uae.nawanera.spring.repositories.ICommentRepository;
import tn.uae.nawanera.spring.repositories.ILikingRepository;
import tn.uae.nawanera.spring.repositories.ITrainingCourseRepository;
import tn.uae.nawanera.spring.repositories.ProjectRepository;
import tn.uae.nawanera.spring.repositories.TaskRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;
import tn.uae.nawanera.spring.repositories.VacancyRepository;
import tn.uae.nawanera.spring.services.CommentService;
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
	ICommentRepository icomment;
	@Autowired
	ILikingRepository iliking;

	@Autowired
	CommentService commentService;
	@Autowired
	IVacancyCategoryService icategory;
	@Autowired
	ApplicationRepository iapp;
	@Autowired
	DemoRepository idemo;
	@Autowired
	AssessmentRepository iassessment;
	@Autowired
	VacancyRepository ivacancyRepo;

	@Autowired
	ProjectRepository iprojectRepo;
	@Autowired
	TaskRepository itaskRepo;

	@Autowired
	ITrainingCourseRepository itcRepo;

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
/*
	@Test
	public void AAtestsignup() throws Exception {

		String admin = "{\"firstname\":\"Admin\",\"lastname\":\"admin\",\"username\":\"Admin_Admin\",\"companyName\":\"myDiploma\",\"email\":\"admin@gmail.com\",\"password\":\"azerty123\",\"role\":{\"id\":1,\"roleType\":\"ADMINISTRATOR\"}}";

		MockMultipartFile image = new MockMultipartFile("file", "admin.jpg", "", "".getBytes(StandardCharsets.UTF_8));
		ObjectMapper objectMapper = new ObjectMapper();
		User result = objectMapper.readValue(String.valueOf(admin), User.class);

		MockMultipartFile metadata = new MockMultipartFile("u", "admin", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/auth/signup").file(image).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
*/
	@Test
	public void AtestSignupIntern() throws Exception {

		String intern = "{\"firstname\":\"intern\",\"lastname\":\"intern\",\"username\":\"Intern_Intern\",\"email\":\"boussettaroua@gmail.com\",\"password\":\"azerty123\",\"role\":{\"id\":5,\"roleType\":\"INTERN\"}}";

		MockMultipartFile image = new MockMultipartFile("file", "intern_intern.jpg", "",
				"".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		User result = objectMapper.readValue(String.valueOf(intern), User.class);

		MockMultipartFile metadata = new MockMultipartFile("u", "intern", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/auth/signup").file(image).file(metadata).accept(MediaType.APPLICATION_JSON))
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

		MockMultipartFile image = new MockMultipartFile("file", "nawanera.jpg", "",
				"".getBytes(StandardCharsets.UTF_8));
		ObjectMapper objectMapper = new ObjectMapper();
		User result = objectMapper.readValue(String.valueOf(company), User.class);

		MockMultipartFile metadata = new MockMultipartFile("u", "company", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
		mockMvc.perform(multipart("/api/user/addCompany").file(image).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "Company_Company", password = "azerty123", authorities = "COMPANY")
	public void CtestCreateHrManager() throws Exception {

		String hr = "{\"firstname\":\"Hr\",\"lastname\":\"Hr\",\"username\":\"Hr_Manager\",\"email\":\"hr@gmail.com\",\"password\":\"azerty123\"}";

		MockMultipartFile image = new MockMultipartFile("file", "roua.jpg", "", "".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		User result = objectMapper.readValue(String.valueOf(hr), User.class);

		MockMultipartFile metadata = new MockMultipartFile("u", "hr", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/user/addManager").file(image).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void DtestCreateTrainer() throws Exception {

		String trainer = "{\"firstname\":\"Trainer\",\"lastname\":\"Trainer\",\"username\":\"Trainer_Trainer\",\"email\":\"trainer@gmail.com\",\"password\":\"azerty123\"}";

		MockMultipartFile image = new MockMultipartFile("file", "noureddine.jpg", "",
				"".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		User result = objectMapper.readValue(String.valueOf(trainer), User.class);

		MockMultipartFile metadata = new MockMultipartFile("u", "trainer", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/user/addTrainer").file(image).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test

	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void EtestEditProfile() throws Exception {
		User u = iuser.findUserBylogin("Hr_Manager");
		String hr = "{\"firstname\":\"Daryl\",\"lastname\":\"See\",\"username\":\"Hr_Manager\",\"email\":\"daryl.see@gmail.com\",\"password\":\"azerty123\",\"bio\":\"kjhfkjflkjflkjfljljojpoj\" ,\"valid\":\"true\"}";
		MockMultipartFile image = new MockMultipartFile("file", "14.PNG", "", "".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		User result = objectMapper.readValue(String.valueOf(hr), User.class);

		MockMultipartFile metadata = new MockMultipartFile("u", "hr", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/user/edit-profile/" + u.getId()).file(image).file(metadata)
				.accept(MediaType.APPLICATION_JSON).with(new RequestPostProcessor() {

					@Override
					public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
						request.setMethod("PUT");
						return request;
					}
				})).andExpect(status().isOk());
	}

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

		MockMultipartFile image = new MockMultipartFile("file", "1.PNG", "", "".getBytes(StandardCharsets.UTF_8));
		ObjectMapper objectMapper = new ObjectMapper();
		Vacancy result = objectMapper.readValue(String.valueOf(v), Vacancy.class);

		MockMultipartFile metadata = new MockMultipartFile("v", "v", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(
				multipart("/api/vacancy/addVacancy").file(image).file(metadata).accept(MediaType.APPLICATION_JSON))
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
	 * @WithMockUser(username = "Hr_Manager", password = "azerty123", authorities =
	 * "HR_MANAGER") public void testCAcceptInternApplication() throws Exception {
	 * 
	 * User intern=iuser.findUserBylogin("Intern_Intern"); Vacancy
	 * vacancy=ivacancyRepo.findByTitle("test"); Application
	 * app=iapp.getApplicationByInternAndVacancy(intern, vacancy);
	 * MockHttpServletRequestBuilder requestBuilder =
	 * MockMvcRequestBuilders.put("/api/vacancy/accept-application/"+app.getId())
	 * .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
	 * 
	 * MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	 * 
	 * assertEquals(200, result.getResponse().getStatus()); }
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
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void MMtestAssignSAToIntern() throws Exception {
		User intern = iuser.findUserBylogin("Intern_Intern");
		SkillAssessment sk = isk.getSkillAssessmentByTitle("test");
		MockHttpServletRequestBuilder requestBuilder1 = MockMvcRequestBuilders
				.put("/api/skill-assessment/assign-skill-assessment-to-intern/" + sk.getId() + "/" + intern.getId())
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

	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void testCAcceptIntern() throws Exception {
		User u = iuser.findUserBylogin("Intern_Intern");

		List<Vacancy> vacancies = ivacancy.getOwnVacancies();
		List<Application> apps = iapp.findByIntern(u);

		for (Vacancy v : vacancies) {
			for (Application app : apps) {
				if (v.getId() == app.getVacancy().getId()) {
					MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
							.put("/api/application/accept-application/" + app.getId())
							.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

					MvcResult result = mockMvc.perform(requestBuilder).andReturn();

					assertEquals(200, result.getResponse().getStatus());
				}
			}
		}

	}

	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void testDAffectTrainerToIntern() throws Exception {
		User trainer = iuser.findUserBylogin("Trainer_Trainer");
		User intern = iuser.findUserBylogin("Intern_Intern");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/user/affectTrainerToIntern/" + trainer.getId() + "/" + intern.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void testBretreiveAllInterviews() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/interview/retreiveAllInterviews").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	/********************* Project ****************************/

	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testACreateProjectDetails() throws Exception {
		Vacancy v = ivacancyRepo.findByTitle("test");

		String project = "{\r\n" + "    \"title\":\"project1\",\r\n"
				+ "    \"description\":\"In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content.\",\r\n"
				+ "    \"vacancy\":{\r\n" + "\"id\":" + v.getId() + "}\r\n" + "}";

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/project/add-project")
				.content(project).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testBGetVacancyProject() throws Exception {
		Vacancy v = ivacancyRepo.findByTitle("test");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/retreive-project-by-vacancy/" + v.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testCRetreiveProjectDetails() throws Exception {
		Vacancy v = ivacancyRepo.findByTitle("test");

		Project p = iprojectRepo.findByVacancy(v);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/retreive-project-details/" + p.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void testDRetreiveAllProjects() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/retreiveAllprojects")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testEgetOwnProjects() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/project/retreive-own-projects")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	/********************* Project Task ****************************/

	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testACreateProjectTask() throws Exception {
		String task = "{ \"taskName\":\"task1\",\"deadline\":\"2022-06-01\" }";
		Vacancy v = ivacancyRepo.findByTitle("test");

		Project p = iprojectRepo.findByVacancy(v);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/project/tasks/add-project-task/" + p.getId()).content(task)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test

	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testBupdateProjectTask() throws Exception {

		String task = "{ \"taskName\":\"task1\", \"status\": \"TODO\",\"taskIssue\": \"\",\"deadline\":\"2022-06-17\"}";
		Vacancy v = ivacancyRepo.findByTitle("test");

		Project p = iprojectRepo.findByVacancy(v);
		Task t = itaskRepo.findByTaskName("task1");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/project/tasks/Update-project-task/" + p.getId()).content(task)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testDgetAllProjectTasks() throws Exception {
		Vacancy v = ivacancyRepo.findByTitle("test");

		Project p = iprojectRepo.findByVacancy(v);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/tasks/retreiveAllProjectTasks/" + p.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testEgetProjectTaskDetails() throws Exception {

		Task t = itaskRepo.findByTaskName("task1");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/tasks/retreive-Project-Task-details/" + t.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void testFcountProjectTasks() throws Exception {
		Vacancy v = ivacancyRepo.findByTitle("test");
		Project p = iprojectRepo.findByVacancy(v);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/tasks/count-Project-Tasks/" + p.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testGretreiveAllProjectTasksByStatus() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/tasks/retreiveAllProjectTasks-byStatus/DOING").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testHassignTask() throws Exception {
		Task t = itaskRepo.findByTaskName("task1");
		User intern = iuser.findUserBylogin("Intern_Intern");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/project/tasks/assign-task/" + t.getId() + "/to-intern/" + intern.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testIdisassociateTask() throws Exception {
		Task t = itaskRepo.findByTaskName("task1");
		User intern = iuser.findUserBylogin("Intern_Intern");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/project/tasks/disassociate-task/" + t.getId() + "/from-intern/" + intern.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testJcountInternTasks() throws Exception {
		User intern = iuser.findUserBylogin("Intern_Intern");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/tasks/count-intern-Tasks/" + intern.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testKretreiveInternTasks() throws Exception {
		User intern = iuser.findUserBylogin("Intern_Intern");
		Project project = iprojectRepo.findByTitle("project1");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/tasks/retreive-intern-Tasks/" + project.getId() + "/" + intern.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());
	}

	/********************* Demo ****************************/

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testAUploadTaskDemo() throws Exception {
		String demo = "{\"category\":\"DOCUMENT\",\"description\":\"jnkjkjk\"}";
		Task t = itaskRepo.findByTaskName("task1");
		User intern = iuser.findUserBylogin("Intern_Intern");
		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		Demo result = objectMapper.readValue(String.valueOf(demo), Demo.class);

		MockMultipartFile metadata = new MockMultipartFile("d", "demo", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/project/task/demo/add-task-demo/" + t.getId()).file(file).file(metadata)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testBretreiveOwnDemos() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/task/demo/retreiveOwnDemos").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testCretreiveAllDemos() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/task/demo/retreiveAllDemos").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testDretreiveAllDemosbyTask() throws Exception {
		Task t = itaskRepo.findByTaskName("task1");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/task/demo/retreiveAllDemos-by-task/" + t.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testEdisplayDemoDetails() throws Exception {
		Task t = itaskRepo.findByTaskName("task1");

		Demo d = idemo.findDemoByTask(t.getId());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/task/demo/display-demo-details/" + d.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testFdisplayDemoDetailsByTask() throws Exception {
		Task t = itaskRepo.findByTaskName("task1");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/task/demo/display-demo-details-by-task/" + t.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	/********************* Document ****************************/
/*
	@Test

	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testKUploadDocument() throws Exception {
		Vacancy v = ivacancyRepo.findByTitle("test");
		Project p = iprojectRepo.findByVacancy(v);
		String doc = "{\"category\":\"REPORT\", \"project\":{ \"id\":" + p.getId() + " }}";

		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"<<pdf data>>".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		Document result = objectMapper.readValue(String.valueOf(doc), Document.class);

		MockMultipartFile metadata = new MockMultipartFile("doc", "doc", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/document/add-doc").file(file).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
*/
	/********************* Training Course ****************************/

	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testACreateTrainingCourse() throws Exception {

		Vacancy v = ivacancyRepo.findByTitle("test");
		Project p = iprojectRepo.findByVacancy(v);
		String tc = "{ \"title\":\"course 1\",\"description\":\"azertyuiop\",\"type\":\"PDF\",\"project\":{\"id\":"
				+ p.getId() + "}}";

		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"<<pdf data>>".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		TrainingCourse result = objectMapper.readValue(String.valueOf(tc), TrainingCourse.class);

		MockMultipartFile metadata = new MockMultipartFile("tc", "tc", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/course-training/add-training-course").file(file).file(metadata)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void testBgetAllTrainingCourses() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/course-training/retrieve-all-training-courses").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void testCcountTrainingCourses() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/course-training/count-training-courses").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testDgetTrainingCoursesByUserId() throws Exception {
		User trainer = iuser.findUserBylogin("Trainer_Trainer");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/course-training/retrieve-all-training-courses-by-trainer/" + trainer.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void testEcountTrainingCoursesByUserId() throws Exception {
		User trainer = iuser.findUserBylogin("Trainer_Trainer");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/course-training/count-training-courses-by-trainer/" + trainer.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testFgetMyTrainingCourses() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/course-training/retrieve-all-own-training-courses").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testGcountMyTrainingCourses() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/course-training/count-all-own-training-courses").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	/********************* Liking ****************************/

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testALeaveLike() throws Exception {

		TrainingCourse tc = itcRepo.findByTitle("course 1");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/training-course/liking/add-like/" + tc.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testBgetAllLikes() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/training-course/liking/get-all-likes").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void testDgetlikescount() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/training-course/liking/count-all-likes").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void testEgetuserlikescount() throws Exception {
		User user = iuser.findUserBylogin("Intern_Intern");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/training-course/liking/count-user-likes/" + user.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void testFgetLikingsByTc() throws Exception {
		TrainingCourse tc = itcRepo.findByTitle("course 1");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/training-course/liking/likes-by-training-course/" + tc.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void testGgetTclikescount() throws Exception {
		TrainingCourse tc = itcRepo.findByTitle("course 1");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/training-course/liking/count-tc-likes/" + tc.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testHgetOwnLikes() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/training-course/liking/get-my-likes").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	/********************* Comment ****************************/

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testALeaveComment() throws Exception {
		String comment = "{ \"commentContent\":\"aaaa\"}";
		TrainingCourse tc = itcRepo.findByTitle("course 1");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/training-course/comments/add-comment/" + tc.getId()).content(comment)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testCgetAllComments() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/training-course/comments/get-all-comments").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testDgetOwnComments() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/training-course/comments/get-own-comments").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testFgetCommentsByUser() throws Exception {
		User user = iuser.findUserBylogin("Trainer_Trainer");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/training-course/comments/comments-by-user/" + user.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void testGgetcommentscount() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/training-course/comments/count-all-comments").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void testHgetusercommentscount() throws Exception {
		User user = iuser.findUserBylogin("Intern_Intern");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/training-course/comments/count-user-comments/" + user.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void testIgetCommentsByTC() throws Exception {
		TrainingCourse tc = itcRepo.findByTitle("course 1");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/training-course/comments/comments-by-Tc/" + tc.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser
	public void testJgetpostcommentscount() throws Exception {
		TrainingCourse tc = itcRepo.findByTitle("course 1");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/training-course/comments/count-Tc-comments/" + tc.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	/********************* Assessment ****************************/
	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testAaddAssessment() throws Exception {
		String assessment = "{\"finalNotice\":\"CONCLUSIVE\",\"remark\":\"remark\"}";

		MockMultipartFile stamp = new MockMultipartFile("stamp", "image.jpg", MediaType.IMAGE_JPEG_VALUE,
				"".getBytes(StandardCharsets.UTF_8));

		MockMultipartFile signature = new MockMultipartFile("signature", "image.jpg", MediaType.IMAGE_JPEG_VALUE,
				"".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		Assessment result = objectMapper.readValue(String.valueOf(assessment), Assessment.class);

		MockMultipartFile metadata = new MockMultipartFile("a", "assessment", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
		User intern = iuser.findUserBylogin("Intern_Intern");
		mockMvc.perform(multipart("/api/project/assessment/add-assessment/intern=/" + intern.getId()).file(stamp)
				.file(signature).file(metadata).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")
	public void testBdisplayassessmentDetailsById() throws Exception {
		Assessment a = iassessment.findByRemark("remark");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/assessment/display-assessment-details/" + a.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testCdisplayassessmentsByIntern() throws Exception {
		User intern = iuser.findUserBylogin("Intern_Intern");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/assessment/display-assessments-by-intern/" + intern.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void testDdisplayAllAssessments() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/assessment/display-all-assessments").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void testFdisplayConclusiveAssessment() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/assessment/display-Conclusive-assessment").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void testGdisplayInconclusiveAssessment() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/assessment/display-Inconclusive-assessment").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void testHcountConclusiveAssessment() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/assessment/count-Conclusive-assessment").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void testIcountInconclusiveAssessment() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/assessment/count-Inconclusive-assessment").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")
	public void testJdisplayAssessmentByNotice() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/project/assessment/display-assessments-by-notice/CONCLUSIVE")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
/*
	@Test

	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void testIDeleteInterview() throws Exception {

		Vacancy v = ivacancyRepo.findByTitle("test");
		Application app = iapp.findByVacancy(v);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/interview/delete-interview/" + app.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
*/
	@Test

	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void testJDeleteApplication() throws Exception {

		Vacancy v = ivacancyRepo.findByTitle("test");
		Application app = iapp.findByVacancy(v);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/application/decline-application/" + app.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test

	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testKdeleteAssessment() throws Exception {
		Assessment a = iassessment.findByRemark("remark");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/project/assessment/delete-assessment/" + a.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test

	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")

	public void testKDeleteComment() throws Exception {

		Comment c = icomment.findByCommentContent("aaaa");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/training-course/comments/delete-comment/" + c.getIdComment())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test

	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")

	public void testKDeleteTrainingCourse() throws Exception {

		TrainingCourse tc = itcRepo.findByTitle("course 1");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/course-training/delete-training-course/" + tc.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test

	@WithMockUser(username = "Intern_Intern", password = "azerty123", authorities = "INTERN")

	public void testKDeleteDemo() throws Exception {

		Task t = itaskRepo.findByTaskName("task1");

		Demo d = idemo.findDemoByTask(t.getId());

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/project/task/demo/delete-task-demo/" + d.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test

	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")

	public void testKDeleteTask() throws Exception {

		Task t = itaskRepo.findByTaskName("task1");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/project/tasks/remove-project-task/" + t.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test

	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void testKDeleteSkillAssessment() throws Exception {

		SkillAssessment sk = isk.getSkillAssessmentByTitle("test");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/skill-assessment/delete-SkillAssessment/" + sk.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test

	@WithMockUser(username = "Trainer_Trainer", password = "azerty123", authorities = "TRAINER")
	public void testLDeleteProject() throws Exception {

		Vacancy v = ivacancyRepo.findByTitle("test");

		Project p = iprojectRepo.findByVacancy(v);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/project/delete-project/" + p.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	@WithMockUser(username = "Hr_Manager", password = "azerty123", authorities = "HR_MANAGER")
	public void testMDeleteVacancy() throws Exception {

		Vacancy v = ivacancyRepo.findByTitle("test");

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/vacancy/delete-vacancy/" + v.getId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test

	@WithMockUser(username = "Admin_Admin", password = "azerty123", authorities = "ADMINISTRATOR")

	public void testNDeleteUser() throws Exception {

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/user/delete-user-company/Company").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

}
