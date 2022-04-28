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

import tn.uae.nawanera.spring.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
 
public class KLikingServiceTest {
	
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
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testALeaveLike() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/training-course/liking/add-like/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testBgetAllLikes() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/liking/get-all-likes") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testCgetLike() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/liking/detail-like/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser
 	public void testDgetlikescount() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/liking/count-all-likes") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	@Test
	@WithMockUser
 	public void testEgetuserlikescount() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/liking/count-user-likes/5") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser
 	public void testFgetLikingsByTc() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/liking/likes-by-training-course/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser
 	public void testGgetTclikescount() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/liking/count-tc-likes/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testHgetOwnLikes() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/liking/get-my-likes") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	@Test
	@WithMockUser
 	public void testEgetLikesByUser() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/liking/likes-by-user/5") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testIdeleteLiking() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/training-course/liking/delete-like/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	/*

	@Autowired
	ICommentService commentService;

	@Autowired
	ILikingService likingService;
	Liking l;
	Comment c;
	@Autowired
	ITrainingCourse trainingCourseService;
	TrainingCourse tc;
	@Autowired
	IUserservice userService;
	User u;

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper mapper;
/*
	@Before
	public void setUp() {

		//this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();

	}
/*
	@WithMockUser(value = "myDiploma")
    @Test
	public void testAddLiking() throws Exception {

		tc = trainingCourseService.getTrainingCourseById(1);
		u = userService.getUserById(5);
		l = new Liking();
		l.setId(1);
		l.setTrainingCourse(tc);
		l.setUser(u);

		String jsonRequest = mapper.writeValueAsString(l);
		MvcResult result = mockMvc.perform(post("/add-like/1").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
assertEquals(200,result.getResponse().getStatus());
	}
	
 

	@Test
	public void testGetAllLikings() {

		List<Liking> likes = likingService.getAllLikings();
		assertThat(likes.size()).isNotZero();
	}

	
	  @Test 
	  public void testGetLikingById() {
	  
	   Liking  like = likingService.getLikingById(1);
	  assertThat(like).isNotNull();
	  }
	  
	  
	  @Test 
	  public void testCountLikings() {
	  
	   int  likes = likingService.countLikings();
	  assertThat(likes).isNotZero();
	  }
	  
	  
	  @Test 
	  public void testGetLikingsByUserId() {
	  
	   List<Liking>  likes = likingService.getLikingsByUserId(5);
	  assertThat(likes.size()).isEqualTo(1);
	  }
	  
	  
	  
	  @Test 
	  public void testCountLikingsByUser() {
	  
	   int  likes = likingService.countLikingsByUser(5);
	  assertThat(likes).isEqualTo(1);
	  }
	  
	  
	  
	  @Test 
	  public void testGetLikingsByTCId() {
	  
	   List<Liking>  likes = likingService.getLikingsByTCId(1);
	  assertThat(likes.size()).isEqualTo(1);
	  }
	  
	  
	  @Test 
	  public void testCountLikingsByTC() {
	  
	  int  likes = likingService.countLikingsByTC(1);
	  assertThat(likes).isEqualTo(1);
	  }
	  
	  
	  
	  
	  @Test 
	  public void testIsLikeExists() {
	  
	  boolean  like = likingService.isLikeExists(5, 1);
	  assertThat(like).isTrue();
	  }

	  
	  */
	  
	  
	  
	  
	  
	 

}
