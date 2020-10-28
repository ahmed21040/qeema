package com.qeema.authorizationserver.controllers;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.qeema.authorizationserver.controllers.custom_exception.UserRegistrationException;
import com.qeema.authorizationserver.model.security.User;
import com.qeema.authorizationserver.service.CustomUserService;

@RestController
@RequestMapping("/registration")
public class RegistrationController extends GenericController {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	public RegistrationController() {
		super();
		logger = LoggerFactory.getLogger(RegistrationController.class);

	}

	@Autowired
	private CustomUserService registrationService;

	@RequestMapping(value = "user", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<?> addUser(@RequestBody @Valid User user) {
		logger.info("attempt to add users: {}", user);
		
		user.setPassword(passwordEncoder.encode(user.getPassword())); // encode password
		boolean response = registrationService.addUser(user);

		return new HttpEntity<>(response);

	}

	@ExceptionHandler(UserRegistrationException.class)
	public ResponseEntity<?> handleRegistrationException(UserRegistrationException e) {
		logger.error(e.getMessage());
		return new ResponseEntity("Registration failed!", HttpStatus.BAD_REQUEST);
	}

}
