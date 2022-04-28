package tn.uae.nawanera.spring.test;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import tn.uae.nawanera.spring.entities.Document;
import tn.uae.nawanera.spring.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class IDocumentTest {
	
	
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
	@WithMockUser(username = "Ahmed_Ahmed", password = "azerty123", authorities = "TRAINER")
 	public void testAUploadDocument() throws Exception {
		String doc = "{\"category\":\"REPORT\", \"project\":{ \"id\":1 }}";

		MockMultipartFile file = new MockMultipartFile("file", "Triggers.pdf", MediaType.APPLICATION_PDF_VALUE,
				"<<pdf data>>".getBytes(StandardCharsets.UTF_8));

		ObjectMapper objectMapper = new ObjectMapper();
		Document result = objectMapper.readValue(String.valueOf(doc), Document.class);

		MockMultipartFile metadata = new MockMultipartFile("doc", "doc", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8));

		mockMvc.perform(multipart("/api/document/add-doc").file(file).file(metadata).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
	
	
	/*
	@Autowired
	IDocumentService documentService;
	
	
	@Autowired
	IProjectService projectService;
	
	Project p;
	Document doc;
	/*
	@Test
	public void testAddDocument() throws Exception {

		p = projectService.displayVacancyProjectDetailsById(1);

		doc = new Document();
		doc.setId(1);

		doc.setCategory(Category.PROJECT_DESIGN);
		doc.setDoc("lfjdgjodfjg00");
		doc.setProject(p);
		documentService.addDocument(doc);

	}
	
	*/
	
	

}
