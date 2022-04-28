package tn.uae.nawanera.spring.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tn.uae.nawanera.spring.entities.RefreshToken;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.exception.TokenRefreshException;
import tn.uae.nawanera.spring.payload.request.LoginRequest;
import tn.uae.nawanera.spring.payload.request.TokenRefreshRequest;
import tn.uae.nawanera.spring.payload.response.JwtResponse;
import tn.uae.nawanera.spring.payload.response.MessageResponse;
import tn.uae.nawanera.spring.payload.response.TokenRefreshResponse;
import tn.uae.nawanera.spring.security.jwt.JwtUtils;
import tn.uae.nawanera.spring.security.services.RefreshTokenService;
import tn.uae.nawanera.spring.services.EmailService;
import tn.uae.nawanera.spring.services.IRoleservice;
import tn.uae.nawanera.spring.services.IUserservice;

@CrossOrigin(origins = "http://localhost:8081/SpringMVC", maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IUserservice iuserservice;

	@Autowired
	IRoleservice iroleservice;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private EmailService emailService;
	@Autowired
	RefreshTokenService refreshTokenService;

	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@PostMapping("/signin")
	public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest)   {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		User logged = iuserservice.findUserBylogin(userDetails.getUsername());
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(logged.getId());
 		return new JwtResponse(logged.getId(), jwt, refreshToken.getToken(), userDetails.getUsername(),
				logged.getCompanyName(), logged.getEmail(), roles.get(0));

	}

	@PostMapping(value = "/signup")
	@ResponseBody
	public User createUser(@RequestPart String u, @RequestPart(value = "file", required = false) MultipartFile file)
			 {

		User user = new User();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			user = objectMapper.readValue(u, User.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		iuserservice.signUp(user, file);

		return user;

	}

	@PutMapping("/activateAccount/{id}")
	public String activateAccount(@PathVariable("id") int id) {
		User user = iuserservice.activateUser(id);
		if (user != null) {
			iuserservice.updateUser(user);
			return "account activated";

		} else {
			return "operation rejected";
		}
	}

	@PreAuthorize("hasAuthority('ADMINISTRATOR') ")

	@PutMapping("/deactivateAccount/{id}")
	public String deactivateAccount(@PathVariable("id") int id) {
		User user = iuserservice.deactivateUser(id);
		if (user != null) {
			iuserservice.updateUser(user);
			return "account deactivated";

		} else {
			return "operation rejected";
		}
	}

	@PostMapping("/forgot/{email}")
	public String processForgotPasswordForm(@PathVariable("email") String email, HttpServletRequest request) {
		User user = iuserservice.findUserBylogin(email);

		if (user == null) {
			return "user not found";
		} else {
			user.setResettoken(UUID.randomUUID().toString());

			iuserservice.updateUser(user);

			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setTo(user.getEmail());
			passwordResetEmail.setSubject("Password Reset Request");
			passwordResetEmail.setText("To reset your password, click the link below: \n "
					+ "http://localhost:8081/SpringMVC/api/auth/reset/" + "\n "
					+ "Please fill your reset token and your new password \n Your token is:" + user.getResettoken());

			emailService.sendEmail(passwordResetEmail);

		}
		return "mail sent";

	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<TokenRefreshResponse> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser).map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getUsername());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));

	}

	@PermitAll
	@PostMapping("/logout")
	public ResponseEntity<MessageResponse> logoutUser() {

		refreshTokenService.deleteByUserId(iuserservice.currentUser().getId());
		SecurityContextHolder.getContext().setAuthentication(null);
		return ResponseEntity.ok(new MessageResponse("Log out successful!"));
	}

	
	 @PostMapping("/reset/{token}/{newpassword}") public String
	  setNewPassword(@PathVariable("token") String
	 token,@PathVariable("newpassword") String newpassword ){
	 User user = iuserservice.findUserByResetToken(token); if (user != null) {
	  user.setPassword(bCryptPasswordEncoder.encode(newpassword)); user.setResettoken(null);
	 iuserservice.updateUser(user); return "password reseted";
	 
	 } else { return "operation regected"; } }
	

}
