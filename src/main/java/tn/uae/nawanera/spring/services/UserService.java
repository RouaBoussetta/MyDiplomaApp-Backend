package tn.uae.nawanera.spring.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.config.FileUploadUtil;
import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.Project;
import tn.uae.nawanera.spring.entities.RoleType;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;
import tn.uae.nawanera.spring.repositories.ApplicationRepository;
import tn.uae.nawanera.spring.repositories.ProjectRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;
import tn.uae.nawanera.spring.repositories.VacancyRepository;
@Slf4j
@Service
public class UserService implements IUserservice {
	private static final Logger logger = LogManager.getLogger(UserService.class);

	public static final int MAX_FAILED_ATTEMPTS = 3;

	private static final long LOCK_TIME_DURATION =86400000; // 24 heurs
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	VacancyRepository vacancyRepository;
	@Autowired
	ApplicationRepository applicationRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	IUserservice iuserService;
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Override
	public List<User> getAllUsers() {
		List<User> users = userRepository.findAll();

		if (!users.isEmpty()) {
			return users;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public User createUser(User user) {

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setTrialPeriod(14);
		user.setValid(true);
		user.setAccountLocked(false);
		user.setFailedAttempt(0);
		userRepository.save(user);
/*
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Registration");
		email.setText("Dear " + user.getFirstname() + ":\n" + "Congratulations !"
				+ ":\n Your Company is now registered on MyDiploma App. "
				+ "You have a 14 day trial period starting today.\r\n"
				+ "As soon as the trial period ends you will have to pay to be able to continue using our App.");

		emailService.sendEmail(email);
*/
		return user;
	}

	@Override
	public User updateUser(User user) {

		return userRepository.save(user);

	}

	@Override
	public User editProfile(int id, User user ) {
		User existantUser = userRepository.findById(id);

		if (!user.getFirstname().equals(existantUser.getFirstname()))
			existantUser.setFirstname(user.getFirstname());

		if (!user.getLastname().equals(existantUser.getLastname()))

			existantUser.setLastname(user.getLastname());

		if (!user.getUsername().equals(existantUser.getUsername()))

			existantUser.setUsername(user.getUsername());

		if (!user.getCompanyName().equals(existantUser.getCompanyName()))

			existantUser.setCompanyName(user.getCompanyName());
		
		if (!user.getBio().equals(existantUser.getBio()))

			existantUser.setBio(user.getBio());
	 

		if (!user.getEmail().equals(existantUser.getEmail()))

			existantUser.setEmail(user.getEmail());

		if (!user.getPassword().equals(existantUser.getPassword()))
			existantUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		return userRepository.save(existantUser);

	}

	@Override
	public void deleteUserById(Integer userId) {
		userRepository.deleteById(userId);
	}
	
	@Override

	public void removeUsersByCompanyName(String companyName) {
		
		userRepository.removeUsersByCompanyName( companyName);
		
	}

	@Override
	public User activateUser(int iduser) {
		User user = userRepository.findById(iduser);
	 
			user. setValid(true);
 
		return updateUser(user );

	}

	@Override
	public User deactivateUser(int iduser) {
		User user = userRepository.findById(iduser);

		user.setValid(false);

		return updateUser(user);
	}

	@Override
	public User getUserById(int iduser) {

		return userRepository.findById(iduser);
	}

	@Override
	public User findUserBylogin(String user) {
		return userRepository.findByUsername(user);
	}

	@Override
	public List<User> findUserLastname(String username) {
		return userRepository.findBylastname(username);
	}

	@Override
	public List<String> findUsersActivated() {
		return userRepository.getUsersFromActivated();

	}

	@Override
	public List<String> getUsersFromDisabled() {
		return userRepository.getUsersFromDisabled();
	}

	@Override
	public void increaseFailedAttempts(User user) {
		int newFailAttempts = user.getFailedAttempt() + 1;
		userRepository.updateFailedAttempts(newFailAttempts, user.getEmail());
	}

	@Override
	public void resetFailedAttempts(String email) {
		userRepository.updateFailedAttempts(0, email);
	}

	@Override
	public void lock(User user) {
		user.setAccountLocked(true);
		user.setLockTime(new Date());

		userRepository.save(user);
	}

	@Override
	public boolean unlockWhenTimeExpired(User user) {
		long lockTimeInMillis = user.getLockTime().getTime();
		long currentTimeInMillis = System.currentTimeMillis();

		if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
			user.setAccountLocked(false);
			user.setLockTime(null);
			user.setFailedAttempt(0);
			userRepository.save(user);

			return true;
		}

		return false;
	}

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	@Override
	public User findUserByResetToken(String username) {

		return userRepository.findUserByresettoken(username);
	}
/*
	@Override
	public User updateUserr(User u, int id) {

		User user = userRepository.findById(id);
		
		if (!u.isValid() == user.isValid())

			user.setValid(u.isValid());

		return userRepository.save(user);

	}
*/
	public void affectTrainerToIntern(int trainerId, int internId) {
		User intern = userRepository.findById(internId);
		User trainer = userRepository.findById(trainerId);

		Vacancy vacancy = applicationRepository.getAffectedApplication(intern).getVacancy();

		intern.setTrainedBy(trainer.getUsername());
		vacancy.setTrainedby(trainer);
		trainer.setAffected(true);
		intern.setAffected(true);

		userRepository.save(intern);
		userRepository.save(trainer);
		vacancyRepository.save(vacancy);

	}

	public void assignProjectToIntern(int projectId, int internId) {
		User intern = userRepository.findById(internId);
		Project project = projectRepository.findById(projectId) ;
		List<Project> internProjects = new ArrayList<>();
		internProjects.add(project);
		intern.setInternProjects(internProjects);

		userRepository.save(intern);
	}

	@Override
	public List<User> findCompanyInterns(String companyName) {
		return userRepository.getCompanyInterns(companyName, RoleType.INTERN);
	}

	@Override
	public void acceptIntern(Application app) {

		User intern = userRepository.findById(app.getIntern().getId());

		intern.setCompanyName(currentUser().getCompanyName());

		intern.setAddedBy(currentUser().getUsername());
		
		

		userRepository.save(intern);

	}

	@Override
	public User currentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();

		return userRepository.findByUsername(username);
	}

	@Override
	public void lockCompanyService(int company) {

		User c = userRepository.findById(company);
		List<User> users = userRepository.findAllByCompanyName(c.getCompanyName());
		 
		c.setValid(false);
		userRepository.save(c);
		for (User u : users) {
			u.setValid(false);
			userRepository.save(u);
		}

	}

	
	@Override
	public List<User> findCompanyHRs(String companyName) {
		return userRepository.getCompanyHRs(companyName, RoleType.HR_MANAGER);
	}

 
	@Override
	public User signUp(User user, MultipartFile file) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setTrialPeriod(14);
		user.setAccountLocked(false);
		user.setFailedAttempt(0);

		if (user.getRole().getRoleType() == RoleType.ADMINISTRATOR)
			user.setValid(true);
		else
			user.setValid(false);
		try {
			FileUploadUtil.saveFile(file);
			user.setUserImage(file.getOriginalFilename());

		} catch (IOException e) {
			log.info("e:"+e);
		}
		userRepository.save(user);
 /*
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Registration");
		email.setText("Dear " + user.getFirstname() + ":\n" + "Congratulations !"
				+ ":\n You are now member of MyDiploma App with " + user.getRole().getRoleType().name() + " role.\n "
				+ "You have a 14 day trial period starting today.\r\n"
				+ "As soon as the trial period ends you will have to pay to be able to continue using our App." + "");

		emailService.sendEmail(email);
*/
		return user;
	}

	@Override
	public List<User> findCompanyTrainers(String companyName) {
		return userRepository.getCompanyTrainers(companyName, RoleType.TRAINER);
	}

	@Override
	public List<User> findTrainerInterns() {

		return userRepository.findTrainerInterns(currentUser().getUsername());
	}

	@Override
	public List<User> getProjectInterns(int idproject) {
		Project p = projectRepository.findById(idproject);
		return p.getInterns();
	}

	@Override
	public List<User> getTrainerInterns() {

		return userRepository.findTrainerInterns(iuserService.currentUser().getUsername());
	}

	@Override
	public void unlockCompanyService(int company) {
		User c = userRepository.findById(company);
		List<User> users = userRepository.findAllByCompanyName(c.getCompanyName());
	 
		c.setValid(true);
		userRepository.save(c);
		for (User u : users) {
			u.setValid(true);
			userRepository.save(u);
		}
		
	}

	@Override
	public List<User> findUsersByCompanyName(String companyName) {
		 
		return userRepository.findAllByCompanyName(companyName);
	}

	@Override
	public List<User> getAllInterns() {
 		return userRepository.findAllByRole(RoleType.INTERN);
	}

	@Override
	public List<User> getAllTrainers() {
 		return userRepository.findAllByRole(RoleType.TRAINER);
	}

	@Override
	public List<User> getAllCompanies() {
  		return userRepository.findAllByRole(RoleType.COMPANY);
	}

	@Override
	public List<User> getAllhrManagers() {
 		return userRepository.findAllByRole(RoleType.HR_MANAGER);
	}
	
	
	@Override
	public int countAllUsers() {
 		return userRepository.findAll().size();
	}
	
	@Override
	public int countAllCompanies() {
 		return getAllCompanies().size();
	}
	
	
	@Override
	public int countAllInterns() {
 		return getAllInterns().size();
	}
	
	
	
	
	/************************************************************************/
	
	

	  @Scheduled(fixedRate = 60000)
	  public void fixedRateMethod() {
		  logger.info("Method with fixed Rate");
	  }

	@Override
	public User updateUserr(User u, int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 

	 // @Scheduled(cron = "*/60 * * * * *" )
	/*  public void cronMethod() {
		  logger.info("Method with cron expression");
	 }
*/
	

	

}
