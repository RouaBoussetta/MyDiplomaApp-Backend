package tn.uae.nawanera.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uae.nawanera.spring.entities.Role;
import tn.uae.nawanera.spring.entities.RoleType;
import tn.uae.nawanera.spring.repositories.RoleRepository;

@Service
public class RoleService implements IRoleservice {

	@Autowired
	RoleRepository roleRepository;

	@Override
	public void saveAllRoles(List<Role> roles) {
		roleRepository.saveAll(roles);

	}

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Role createRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Role updateRole(Role role) {
		return roleRepository.save(role);

	}

	@Override
	public Role findRoleByroleType(RoleType roleType) {
		return roleRepository.findByRoleType(roleType);
	}

	@Override
	public Role findRoleById(int id) {
		return roleRepository.findRoleById(id);
	}

	@Override
	public void deleteRoleById(int roleId) {
		roleRepository.deleteById(roleId);
	}

	@Override
	public Role getRoleeId(int id) {
		return roleRepository.getRoleeId(id);
	}

	@Override
	public void updateRolee(Role r, int id) {

		Role role = roleRepository.getRoleeId(id);

		role.setRoleType(r.getRoleType());
		roleRepository.save(role);

	}

	@Override
	public List<Role> getRoles() {
		return roleRepository.findRoles();
	}

}
