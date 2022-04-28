package tn.uae.nawanera.spring.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 @Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

	
		private int id;
		private String token;
	 
	private String refreshToken;

	private String username;
	private String company;
	private String email;
	private String roles;

}
