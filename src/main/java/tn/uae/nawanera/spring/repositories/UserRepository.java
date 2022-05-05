package tn.uae.nawanera.spring.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.RoleType;
import tn.uae.nawanera.spring.entities.User;
 @Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	 
	 User findByUsernameAndPassword(String username,String password);
	  User findByUsername(String userName);
	    List<User> findBylastname(String lastname);
	    
	    
	    User findById(int idUser);
		@Query("SELECT r.roleType FROM User u INNER JOIN Role r on (u.role.id = r.id) where  u.id =:id")
		public String getUserRoleDescription(@Param("id")int id);
		
		@Query("SELECT CONCAT(u.firstname,CONCAT(' ',u.lastname)) FROM User u where  u.valid =TRUE")
		public List<String> getUsersFromActivated();
		
		@Query("SELECT CONCAT(u.firstname,CONCAT(' ',u.lastname)) FROM User u where  u.valid =FALSE")
		public List<String> getUsersFromDisabled();
		
		@Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.username = ?2")
	    @Modifying
	    public void updateFailedAttempts(int failAttempts, String username);
		User findUserByresettoken(String username);
		
		@Query("SELECT u FROM User u WHERE u.firstname LIKE %?1% OR u.username LIKE %?1% ")
		List<User> findUserSearch(String pattern);
		

		
		@Query("Select u FROM User u where u.role.roleType= :role")
		List<User> findAllByRole(@Param("role") RoleType role);
		
		@Query("Select u FROM User u where u.email= :email")
		User findByEmail(@Param("email")String email);
		
		@Query("Select u FROM User u where u.companyName= :companyName")
		 User findByCompanyName(@Param("companyName")String companyName);
		
		@Query("Select u FROM User u where u.companyName= :companyName and u.role.roleType= :role")

		List<User> getCompanyInterns(@Param("companyName")String companyName,@Param("role") RoleType role);
	 
		
		@Query("Select u FROM User u where u.companyName= :companyName")

		List<User> findAllByCompanyName(@Param("companyName") String companyName);
		
		
		@Query("Select u FROM User u where u.companyName= :companyName and u.role.roleType= :role")
		List<User> getCompanyHRs(@Param("companyName")String companyName,@Param("role") RoleType role);
		
		
		@Query("Select u FROM User u where u.companyName= :companyName and u.role.roleType= :role")
		List<User> getCompanyTrainers(@Param("companyName")String companyName,@Param("role") RoleType role);
		
		@Query("Select u FROM User u where u.trainedBy= :trainedBy")
		List<User> findTrainerInterns(@Param("trainedBy")String trainedBy);
		
		
		
		@Transactional
		@Modifying
		@Query("delete from User u where  u.companyName=:companyName")
		void removeUsersByCompanyName(@Param("companyName")String companyName);

 

	
		
}
