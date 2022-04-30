package tn.uae.nawanera.spring.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tn.uae.nawanera.spring.config.FileUploadUtil;
import tn.uae.nawanera.spring.entities.Notification;
import tn.uae.nawanera.spring.entities.Role;
import tn.uae.nawanera.spring.entities.RoleType;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.services.EmailService;
import tn.uae.nawanera.spring.services.INotificationService;
import tn.uae.nawanera.spring.services.IRoleservice;
import tn.uae.nawanera.spring.services.IUserservice;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	IUserservice iuserservice;
	@Autowired
	IRoleservice iroleservice;
	@Autowired
	INotificationService inotifservice;
	@Autowired
	private EmailService emailService;
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping("/findall")
	public List<User> getAllUsers() {
		return iuserservice.getAllUsers();
	}
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping("/findall-interns")
	public List<User> getAllInterns() {
		return iuserservice.getAllInterns();
	}
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping("/findall-trainers")
	public List<User> getAllTrainers() {
		return iuserservice.getAllTrainers();
	}
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping("/findall-companies")
	public List<User> getAllCompanies() {
		return iuserservice.getAllCompanies();
	}
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping("/findall-hr-managers")
	public List<User> getAllHrManagers() {
		return iuserservice.getAllhrManagers();
	}

	@PermitAll
	@GetMapping("/findUserLastname/{lastname}")
	public List<User> findUserLastName(@PathVariable("lastname") String username) {
		return iuserservice.findUserLastname(username);
	}
	
	@PermitAll
	@GetMapping("/findUserById/{id}")
	public User findUserById(@PathVariable("id") int id) {
		return iuserservice.getUserById(id);
	}

	@PermitAll
	@GetMapping("/findUserByUsername/{username}")
	public User findUserByUsername(@PathVariable("username") String username) {
		return iuserservice.findUserBylogin(username);
	}

	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping("/findActivatedUser")
	public List<String> findUserActivated() {
		return iuserservice.findUsersActivated();
	}

	@PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY')")
	@PutMapping("/disable-user/{id}")
	public void disableUser(@PathVariable("id") int id) {
		  iuserservice.deactivateUser(id);
	}

	@PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY') or hasAuthority('HR_MANAGER')")
	@GetMapping("/company-interns/{companyName}")
	public List<User> findCompanyInterns(@PathVariable("companyName") String companyName) {
		return iuserservice.findCompanyInterns(companyName);
	}
	


	@PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY')")
	@GetMapping("/company-hrs/{companyName}")
	public List<User> findCompanyHrs(@PathVariable("companyName") String companyName) {
		return iuserservice.findCompanyHRs(companyName);
	}
	
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY')  or hasAuthority('HR_MANAGER')")
	@GetMapping("/company-trainers/{companyName}")
	public List<User> findCompanyTrainers(@PathVariable("companyName") String companyName) {
		return iuserservice.findCompanyTrainers(companyName);
	}
	
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY') ")
	@GetMapping("/nb-company-hrs/{companyName}")
	public int countCompanyHrs(@PathVariable("companyName") String companyName) {
		return iuserservice.findCompanyHRs(companyName).size();
	}
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY') or hasAuthority('HR_MANAGER')")
	@DeleteMapping("/delete-user/{id}")
	public void deleteUser(@PathVariable("id") int id) {
		  iuserservice.deleteUserById(id);
	}

	@PermitAll
	@GetMapping("/project-interns/{id}")
	public List<User> getProjectInterns(@PathVariable("id") int id ) {
		return iuserservice.getProjectInterns(id);
	}
	
	@PermitAll
	@GetMapping("/trainer-interns")
	public List<User> getTrainerInterns( ) {
		return iuserservice.getTrainerInterns();
	}
	
	
	@GetMapping("/current")
	public String getCurrentUserContext() {
		return iuserservice.currentUser().getCompanyName();
	}

	@PreAuthorize("permitAll()")
	@PutMapping("/edit-profile/{id}")

	public User editProfile(@PathVariable("id") int id,@RequestPart String u,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		User user = new User();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			user = objectMapper.readValue(u, User.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			FileUploadUtil.saveFile(file);
			user.setUserImage(file.getOriginalFilename());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return iuserservice.editProfile(id,user);
	}
	
	
	@PreAuthorize("permitAll()")
	@PutMapping("/edit-user-profile/{id}")

	public User updateUser(@RequestBody User user , @PathVariable("id")int id) {
		
		
		return iuserservice.updateUserr(user,id);
	}
	

	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@PostMapping("/addCompany")
	public User createCompanies(@RequestPart String u,
			@RequestParam(value = "file", required = false) MultipartFile file) {

		User user = new User();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			user = objectMapper.readValue(u, User.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		try {
			FileUploadUtil.saveFile(file);
			user.setUserImage(file.getOriginalFilename());

		} catch (IOException e) {
			e.printStackTrace();
		}
		user.setAddedBy(iuserservice. currentUser().getUsername());

		Role role = iroleservice.findRoleByroleType(RoleType.COMPANY);
		user.setRole(role);
 		iuserservice.createUser(user);
		return user;
	}

	@PreAuthorize("hasAuthority('COMPANY')")
	@PostMapping("/addManager")
	public User createHRManager(@RequestPart String u,
			@RequestParam(value = "file", required = false) MultipartFile file) {

		User user = new User();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			user = objectMapper.readValue(u, User.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			FileUploadUtil.saveFile(file);
			user.setUserImage(file.getOriginalFilename());

		} catch (IOException e) {
			e.printStackTrace();
		}
		user.setAddedBy(iuserservice. currentUser().getUsername());

		Role role = iroleservice.findRoleByroleType(RoleType.HR_MANAGER);

		// Create new user's account

		user.setCompanyName(iuserservice.currentUser().getCompanyName());
		user.setRole(role);

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Registration");
		email.setText("Dear " + user.getFirstname() + ":\n" + "Congratulations !"
				+ ":\n You are now member of MyDiploma App ." + "\n Your Director added you as : "
				+ user.getRole().getRoleType().name() + " Of " + user.getCompanyName() + " Company.\n "
				+ "You have a 14 day trial period starting today.\r\n"
				+ "As soon as the trial period ends you will have to pay to be able to continue using our App.");

		emailService.sendEmail(email);

		return iuserservice.createUser(user);
	}

	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PostMapping("/addTrainer")
	public User createTrainer(@RequestPart String u,
			@RequestParam(value = "file", required = false) MultipartFile file) {

		User user = new User();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			user = objectMapper.readValue(u, User.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		try {
			FileUploadUtil.saveFile(file);
			user.setUserImage(file.getOriginalFilename());

		} catch (IOException e) {
			e.printStackTrace();
		}
		user.setAddedBy(iuserservice. currentUser().getUsername());

		Role role = iroleservice.findRoleByroleType(RoleType.TRAINER);

		user.setCompanyName(iuserservice.currentUser().getCompanyName());
		user.setRole(role);

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Registration");
		email.setText("Dear " + user.getFirstname() + ":\n" + "Congratulations !"
				+ ":\n You are now member of MyDiploma App ." + "\n Your Director added you as : "
				+ user.getRole().getRoleType().name() + " Of " + user.getCompanyName() + " Company.\n "
				+ "You have a 14 day trial period starting today.\r\n"
				+ "As soon as the trial period ends you will have to pay to be able to continue using our App.");

		emailService.sendEmail(email);

		return iuserservice.createUser(user);
	}

	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PutMapping(value = "/affectTrainerToIntern/{idTrainer}/{idIntern}")
	public void affectTrainerToIntern(@PathVariable("idTrainer") int idTrainer,
			@PathVariable("idIntern") int idIntern) {
		iuserservice.affectTrainerToIntern(idTrainer, idIntern);
	}

	@PreAuthorize("hasAuthority('ADMINISTRATOR') ")
	@PutMapping(value = "/lock-company-service/{id}")
	public void lockCompanyService(@PathVariable("id") int id) {
		iuserservice.lockCompanyService(id);
	}

	@PreAuthorize("hasAuthority('ADMINISTRATOR') ")
	@PutMapping(value = "/unlock-company-service/{id}")
	public void unlockCompanyService(@PathVariable("id") int id) {
		iuserservice.unlockCompanyService(id);
	}
	
	
	@PermitAll
	@GetMapping("/company-users/{companyName}")
	public List<User> findCompanyusers(@PathVariable("companyName") String companyName) {
		return iuserservice.findUsersByCompanyName(companyName);
	}
	
	
	@PermitAll
	@GetMapping("/notifs")
	public List<Notification> notifs( ) {
		return inotifservice.getAllNotif();
	}
	
	@PermitAll
	@GetMapping("/myNotifs")
	public List<Notification> myNotifs( ) {
		return inotifservice.getMyNotifs();
	}

	
	@PermitAll
	@GetMapping("/myNotifs/{id}")
	public List<Notification> myNotifs(@PathVariable("id") int id) {
		return inotifservice.getNotifByReceiver(id);
	}
	
	
	@PermitAll
	@GetMapping("/count-myNotifs/{id}")
	public int countmyNotifs(@PathVariable("id") int id) {
		return inotifservice.getNotifByReceiver(id).size();
	}
	
	@PermitAll
	@DeleteMapping("/delete-notifs/{idcurrent}")
	public String removeNotifs(@PathVariable("idcurrent") int idcurrent) {
		 

		inotifservice.deleteNotifbyUser(idcurrent);

		return "All user notifs has been removed successfuly";
	}
	
}
