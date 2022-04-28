package tn.uae.nawanera.spring.services;

import java.util.List;

 import tn.uae.nawanera.spring.entities.Role;
import tn.uae.nawanera.spring.entities.RoleType;


public interface IRoleservice {
	public List<Role> getAllRoles();
	public List<Role> getRoles();
	public Role createRole(Role entity) ;
	public Role updateRole(Role entity) ;
	public Role findRoleByroleType(RoleType user) ;
	public Role findRoleById(int id) ;
	public void deleteRoleById(int roleId) ;
	Role getRoleeId(int id) ;
	void updateRolee(Role r, int id);
	public void saveAllRoles(List<Role> roles);
}
