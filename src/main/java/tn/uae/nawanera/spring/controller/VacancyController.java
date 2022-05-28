package tn.uae.nawanera.spring.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.config.FileUploadUtil;
import tn.uae.nawanera.spring.entities.Degree;
import tn.uae.nawanera.spring.entities.Status;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;
import tn.uae.nawanera.spring.entities.VacancyCategory;
import tn.uae.nawanera.spring.payload.response.MessageResponse;
import tn.uae.nawanera.spring.services.IUserservice;
import tn.uae.nawanera.spring.services.IVacancyService;

@Slf4j
@RestController
@RequestMapping("/api/vacancy")
public class VacancyController {

	@Autowired
	IVacancyService ivacancyService;

	@Autowired
	IUserservice iuserService;

	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PostMapping("/addVacancy")
	public ResponseEntity<MessageResponse> createVacancy(@RequestPart String v,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		Vacancy vacancy = new Vacancy();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			vacancy = objectMapper.readValue(v, Vacancy.class);
		} catch (JsonProcessingException e) {
			log.info("e :", e);

		}
		try {
			FileUploadUtil.saveFile(file);
			vacancy.setImage(file.getOriginalFilename());

		} catch (IOException e) {
			log.info("e", e);

		}
		ivacancyService.createVacancy(vacancy);
		return ResponseEntity.ok(new MessageResponse("Vacancy Published successfully!"));
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/retreiveAllvacancies")
	public List<Vacancy> getAllVacancies() {
		return ivacancyService.retreiveAllVacancies();
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/findVacancyById/{id}")
	public Vacancy findVacancyById(@PathVariable("id") int id) {
		return ivacancyService.getVacancyById(id);
	}

	@PreAuthorize("hasAuthority('HR_MANAGER') or hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY')")
	@GetMapping("/findVacanciesByStatus/{status}")
	public List<Vacancy> findVacanciesByStatus(@PathVariable("status") Status status) {
		return ivacancyService.getVacanciesByStatus(status);
	}

	@PermitAll
	@GetMapping("/find-vacancies-trainedby-current")
	public List<Vacancy> findVacanciesTrainedBy() {
		return ivacancyService.retrieveVacanciesTrainedBy();
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/findVacanciesByDegree/{degree}")
	public List<Vacancy> findVacanciesByDegree(@PathVariable("degree") Degree degree) {
		return ivacancyService.getVacanciesByDegree(degree);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/findVacanciesByCategory/{category}")
	public List<Vacancy> findVacanciesByCategory(@PathVariable("category") VacancyCategory category) {
		return ivacancyService.getVacanciesByCategory(category);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/find-company-VacanciesByCategory/{company}/{category}")
	public List<Vacancy> findVacanciesByCategory(@PathVariable("company") String company,
			@PathVariable("category") VacancyCategory category) {
		return ivacancyService.getCompanyVacanciesByCategory(company, category);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/find-Archieved-Vacancies")
	public List<Vacancy> findArchievedVacancies() {
		return ivacancyService.getArchievedVacancies();
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/find-Active-Vacancies")
	public List<Vacancy> findActiveVacancies() {
		return ivacancyService.getActiveVacancies();
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/find-Draft-Vacancies")
	public List<Vacancy> findDraftVacancies() {
		return ivacancyService.getDraftVacancies();
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/find-closed-Vacancies")
	public List<Vacancy> findclosedVacancies() {
		return ivacancyService.getClosedVacancies();
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/find-company-vacancies/{companyname}")
	public List<Vacancy> findcompanyVacancies(@PathVariable("companyname") String companyname) {
		return ivacancyService.retrieveCompanyVacancies(companyname);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/find-paid-Vacancies")
	public List<Vacancy> findpaidVacancies() {
		return ivacancyService.getPaidVacancies();
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/find-Vacancies-postedby/{username}")
	public List<Vacancy> findpaidVacancies(@PathVariable("username") String username) {
		return ivacancyService.getVacanciesPostedBy(username);
	}

	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PutMapping("/UpdateVacancy/{id}")
	public Vacancy updateVacancy(@RequestParam String v, @PathVariable("id") int id,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		Vacancy vacancy = new Vacancy();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			vacancy = objectMapper.readValue(v, Vacancy.class);
		} catch (JsonProcessingException e) {
			log.info("e :", e);

		}
		Vacancy exist = findVacancyById(id);
		try {
			FileUploadUtil.saveFile(file);
			exist.setImage(file.getOriginalFilename());

		} catch (IOException e) {
			log.info("e", e);

		}
		return ivacancyService.updateVacancy(vacancy, id);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/count-Vacancies")
	public int countVacancies() {
		return ivacancyService.countVacancies();
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/count-active-Vacancies")
	public int countActiveVacancies() {
		return ivacancyService.countActiveVacancies();
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/count-closed-Vacancies")
	public int countClosedVacancies() {
		return ivacancyService.countClosedVacancies();
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/count-Draft-Vacancies")
	public int countDraftVacancies() {
		return ivacancyService.countDraftVacancies();
	}

	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@GetMapping("/get-own-vacancies")
	public List<Vacancy> getOwnVacancies() {
		return ivacancyService.getOwnVacancies();
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/count-Archieved-Vacancies")
	public int countArchievedVacancies() {
		return ivacancyService.countArchievedVacancies();
	}

	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@DeleteMapping("/delete-vacancy/{id}")
	public void deleteVacancy(@PathVariable("id") int id) {
		ivacancyService.deleteVacancyById(id);
	}

	@PreAuthorize("hasAuthority('HR_MANAGER') or hasAuthority('COMPANY') or hasAuthority('TRAINER')")
	@GetMapping("/find-own-company-vacancies")
	public List<Vacancy> getcompanyVacancies() {
		return ivacancyService.retrieveCompanyVacancies();
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/find-hr-vacancies-categories/{id}")
	public Set<VacancyCategory> findhrvacanciesCategories(@PathVariable("id") int id) {
		User hr = iuserService.getUserById(id);
		List<Vacancy> vacancies = ivacancyService.getVacanciesPostedBy(hr.getUsername());
		Set<VacancyCategory> categories=new HashSet<>();
		for (Vacancy v : vacancies) {
			categories.add(v.getCategory());
		} 

		return categories;
	}
	
	
	//@PreAuthorize("hasAuthority('HR_MANAGER') or hasAuthority('COMPANY') or hasAuthority('TRAINER')")
	@GetMapping("/sort-vacancy-asc")
	public List<Vacancy> getcompanyVacanciesAsc() {
		return ivacancyService.retrieveCompanyVacancies();
	}
	
	/*
	@PermitAll
	@GetMapping("/find-vacancies-trainedby-asc")
	public List<Vacancy> findVacanciesAsc() {
		return ivacancyService.retrieveVacanciesAsc();
	}
*/
}
