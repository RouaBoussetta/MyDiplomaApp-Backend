package tn.uae.nawanera.spring.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.User;

public interface IUserservice {

	public User currentUser();

	public List<User> getAllUsers();

	public User getUserById(int id);

	public User activateUser(int iduser);

	public User deactivateUser(int user);

	public void lockCompanyService(int company);

	public void deleteUserById(Integer userId);

	public User findUserBylogin(String user);

	public List<User> findUserLastname(String user);

	public List<String> findUsersActivated();

	public List<String> getUsersFromDisabled();

	public void increaseFailedAttempts(User user);

	boolean unlockWhenTimeExpired(User user);

	void resetFailedAttempts(String email);

	void lock(User user);

	public User findUserByResetToken(String token);

	User updateUserr(User u, int id);

	void affectTrainerToIntern(int trainerId, int internId);

	void assignProjectToIntern(int projectId, int internId);

	public List<User> findCompanyInterns(String companyName);

	public List<User> findCompanyHRs(String companyName);

	public List<User> findCompanyTrainers(String companyName);
	public List<User> findUsersByCompanyName(String companyName);

	void unlockCompanyService(int company);

	public User signUp(User user, MultipartFile file);

 
	User updateUser(User user);

	public List<User> findTrainerInterns();

	void acceptIntern(Application app);

	List<User> getProjectInterns(int idproject);

	List<User> getTrainerInterns();
	List<User> getAllInterns();
	List<User> getAllTrainers();
	List<User> getAllCompanies();
	List<User> getAllhrManagers();


 
	User createUser(User user);

	User editProfile(int id, User user);

	void removeUsersByCompanyName(String companyName);

}
