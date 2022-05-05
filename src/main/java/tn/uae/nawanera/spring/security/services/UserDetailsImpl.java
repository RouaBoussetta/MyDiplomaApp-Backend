package tn.uae.nawanera.spring.security.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.entities.User;
@Slf4j 
@Component
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	private transient User user;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(User user,
			Collection<? extends GrantedAuthority> authorities) {
		this.user = user;
		this.authorities = authorities;
	}
	
	public UserDetailsImpl() {
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleType().name()));

		return new UserDetailsImpl(user,authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	public User getUser ()  {
		return user;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		try {
			return user.isValid();
		} catch (Exception e) {
			log.info("e :", e);

		}
		return false;
	}

 

	@Override
	public String getPassword() {
 		return user.getPassword();
	}
	
	

	@Override
	public String getUsername() {
 		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
}
