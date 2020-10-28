//package com.quaack.services;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import com.quacck.authorizationserver.AuthorizationServerApplication;
//import com.quacck.authorizationserver.model.security.User;
//import com.quacck.authorizationserver.service.RegistrationService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = AuthorizationServerApplication.class)
//@WebAppConfiguration
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class RegistrationServiceTest {
//
//	@Autowired
//	private RegistrationService registrationService;
//
//	@Test
//	public void aAddUserTest() {
//
//		User user = new User();
//		user.setUsername("mostafa");
//		user.setPassword("{bcrypt}$2a$04$USYFDMAiURV6Y6Z4Y..vVOTcjP5hX3r012vgOQJqv.YozP5WJvxC.");
//		user.setEmail("invite@live.com");
//		user.setEnabled(true);
//		user.setAccountNonExpired(true);
//		user.setAccountNonLocked(true);
//		user.setCredentialsNonExpired(true);
//
//		boolean userAdded = registrationService.addUser(user);
//		boolean isUserActuallyAdded = registrationService.getUserByUsername(user.getUsername()) != null;
//
//		assertEquals(true, userAdded && isUserActuallyAdded);
//	}
//
//}
