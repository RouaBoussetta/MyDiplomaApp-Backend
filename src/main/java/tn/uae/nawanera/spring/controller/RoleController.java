package tn.uae.nawanera.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.uae.nawanera.spring.entities.Role;
import tn.uae.nawanera.spring.entities.RoleType;
import tn.uae.nawanera.spring.services.IRoleservice;

@RestController
public class RoleController {

	@Autowired
	IRoleservice iroleservice;

	@GetMapping("/Role/findall")
	public List<Role> getAllRoles() {
		return iroleservice.getAllRoles();
	}
	
	@GetMapping("/api/role/signup-roles")
	public List<Role> getSignupRoles() {
		return iroleservice.getRoles();
	}

	//@PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY') or hasAuthority('HR_MANAGER') or hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@GetMapping("/Role/rolebyid/{idRole}")
	public Role getUserById(@PathVariable("idRole") int idRole) {
		return iroleservice.findRoleById(idRole);
	}

	@PostMapping("/Role/createRole")
	@ResponseBody
	public Role createRole(@RequestBody Role role) {
		return iroleservice.createRole(role);
	}

	//@PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY') or hasAuthority('HR_MANAGER') or hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@PutMapping("/Role/updateRole")
	@ResponseBody
	public Role updateUser(@RequestBody Role role)  {
		return iroleservice.updateRole(role);
	}

	//@PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY') or hasAuthority('HR_MANAGER') or hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@DeleteMapping("/Role/deleteRoleById/{roleId}")
	public void deleteRoleById(@PathVariable("roleId") int roleId) {
		iroleservice.deleteRoleById(roleId);
	}
	
	//@PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY') or hasAuthority('HR_MANAGER') or hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@GetMapping("/Role/getRoleeId/{idRole}")
	public Role getRoleeId(@PathVariable("idRole") int idRole) {
		return iroleservice.getRoleeId(idRole);
	}
	
	//@PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY') or hasAuthority('HR_MANAGER') or hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@GetMapping("/Role/getRoleType/{roleType}")
	public Role getRoleByType(@PathVariable("roleType") RoleType roleType)  {
		return iroleservice.findRoleByroleType(roleType);
	}
	
	//@PreAuthorize("hasAuthority('ADMINISTRATOR') or hasAuthority('COMPANY') or hasAuthority('HR_MANAGER') or hasAuthority('TRAINER') or hasAuthority('INTERN')")
	@PutMapping("/Role/updateRolee/{id}")
	@ResponseBody
	public void updateRolee(@RequestBody Role role,@PathVariable("id") int id)  {
		 iroleservice.updateRolee(role, id);
	}
}