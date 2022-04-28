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
public class LCommentServiceTest {
	
	
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
 	public void testALeaveComment() throws Exception {
		String comment = "{ \"commentContent\":\"azertyuijhgfdsdfghjytrd\"}";

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/training-course/comments/add-comment/1") .content(comment)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testBupdateComment() throws Exception {
		String comment = "{ \"commentContent\":\"updated\"}";

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/training-course/comments/update-comment/1") .content(comment)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testCgetAllComments() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/comments/get-all-comments") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testDgetOwnComments() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/comments/get-own-comments") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testEgetComment() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/comments/get-own-comments") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser(username = "Noureddine_Rezgui", password = "azerty123", authorities = "INTERN")
 	public void testFgetCommentsByUser() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/comments/comments-by-user/5") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser
 	public void testGgetcommentscount() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/comments/count-all-comments") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser
 	public void testHgetusercommentscount() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/comments/count-user-comments/5") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser
 	public void testIgetCommentsByTC() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/comments/comments-by-Tc/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	@Test
	@WithMockUser 
 	public void testJgetpostcommentscount() throws Exception {
 
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/training-course/comments/count-Tc-comments/1") 
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}
	
	
	 
	
	
	
	
	
	
	
	
	
	
	/*
	@Autowired
	ICommentService commentService;
	
	Comment c;
	@Autowired
	ITrainingCourse trainingCourseService;
	TrainingCourse tc;
	@Autowired
	IUserservice userService;
	User u;
	/*
	@Before
	public void setUp() throws Exception {

		tc=trainingCourseService.getTrainingCourseById(1);
		u=userService.getUserById(5);
		c= new Comment();
		c.setIdComment(1);
		c.setCommentContent("usfhsdhfkhfkhfkdhk");
 		c.setUser(u);

		commentService.addComment(c, tc.getId());

	}
	
	
	@Test
	public void testGetAllComments() {

		List<Comment> comments = commentService.getAllComments();
		assertThat(comments.size()).isNotZero();
	}
	
	
	@Test
	public void testCountComments() {

		List<Comment> comments = commentService.getAllComments();
		assertThat(comments.size()).isEqualTo(1);
	}	
	
	
	
	@Test
	public void testGetCommentsByUserId() {

		List<Comment> comments = commentService.getCommentsByUserId(5);
		assertThat(comments.size()).isEqualTo(1);
	}
	
	
	@Test
	public void testCountCommentsByUser() {

		int comments = commentService.countCommentsByUser(5);
		assertThat(comments).isEqualTo(1);
	}
	
	
	@Test
	public void testGetCommentsByTCId() {

		List<Comment> comments = commentService.getCommentsByTCId(1);
		assertThat(comments.size()).isEqualTo(1);
	}
	
	@Test
	public void testCountCommentsByTC() {

		int comments = commentService.countCommentsByTC(1);
		assertThat(comments).isEqualTo(1);
	}

	
	/*
	@Test
	public void testDeleteComment() throws Exception {

		 commentService.deleteComment(1);
		 
		 c=commentService.getCommentById(1);
		assertThat(c).isNull();
	}
	*/
	
	 
	

}
