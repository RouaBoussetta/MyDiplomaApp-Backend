package tn.uae.nawanera.spring.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.services.IUserservice;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	IUserservice iuserservice;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		User user = new User();
		try {
			user = iuserservice.findUserBylogin(username);
		} catch (Exception e) {
			log.info("e :", e);

		}
		return UserDetailsImpl.build(user);
	}
	
	
	
	
}
