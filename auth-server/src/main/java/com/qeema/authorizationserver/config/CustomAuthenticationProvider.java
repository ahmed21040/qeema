package com.qeema.authorizationserver.config;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;
import com.qeema.authorizationserver.model.security.User;
import com.qeema.authorizationserver.repository.UserDetailRepository;
import com.qeema.authorizationserver.service.UserStatisticsService;



@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailRepository userDetailRepository;

	

//	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired 
	private UserStatisticsService userStatisticsService;
	
	@Autowired
	public CustomAuthenticationProvider(@Lazy PasswordEncoder passwordEncoder) {
		this.passwordEncoder=passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		System.out.println("un > " + username + " pass " + password);

		Optional<User> optionalUser = userDetailRepository.findByUsername(username);
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username or password wrong"));

		User user = optionalUser.get();
		
		
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new UsernameNotFoundException("Username or password wrong");
		}else {
			
			userDetailRepository.save(user);
		}
		
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		userStatisticsService.userLogin();;
		return new UsernamePasswordAuthenticationToken(username, password, authorities);

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
