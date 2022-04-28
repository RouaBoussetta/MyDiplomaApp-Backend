package tn.uae.nawanera.spring.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.entities.Role;
import tn.uae.nawanera.spring.services.IRoleservice;

@Slf4j
public class RoleWriter implements ItemWriter<Role> {

	@Autowired
	private IRoleservice roleService;

 	public void write(List<? extends Role> roles) {
		log.info("Enregistrement des lignes roles dans la base de données", roles);
 
		roleService.saveAllRoles((List<Role>) roles);

	}
}
