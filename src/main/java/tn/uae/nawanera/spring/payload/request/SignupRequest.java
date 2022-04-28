package tn.uae.nawanera.spring.payload.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import tn.uae.nawanera.spring.entities.Role;
 @Getter
@Setter
public class SignupRequest {
	@NotBlank
	private String email;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	
	private Set<Role> roles ;


	
	 
	
}
