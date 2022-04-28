package tn.uae.nawanera.spring.batch;

import org.springframework.batch.item.ItemProcessor;

import tn.uae.nawanera.spring.entities.Role;

 
public class RoleProcessor implements ItemProcessor<Role, Role> {
	/*11. logique métier de notre job*/
	@Override
	public Role process(Role role) {
		
		role.setId(role.getId());
		role.setRoleType(role.getRoleType());
		 
		return role;
	}
}
