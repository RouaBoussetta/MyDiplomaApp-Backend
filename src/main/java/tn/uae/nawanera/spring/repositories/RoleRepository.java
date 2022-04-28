package tn.uae.nawanera.spring.repositories;

 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.uae.nawanera.spring.entities.Role;
import tn.uae.nawanera.spring.entities.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	@Query("SELECT r FROM Role r WHERE r.roleType =:roleType ") 

	public Role findByRoleType(@Param("roleType")RoleType roleType)  ;
	public Role findRoleById(int user) ;

	@Query("SELECT r FROM Role r WHERE r.id =:id ") 
	public Role getRoleeId(@Param("id")int id);
	
	@Query("SELECT r FROM Role r WHERE r.roleType like '%COMPANY%' or r.roleType like '%INTERN%'") 

	public List<Role> findRoles();

}
