package com.qeema.authorizationserver.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.qeema.authorizationserver.controllers.RegistrationController;
import com.qeema.authorizationserver.model.security.User;
import com.qeema.authorizationserver.repository.UserDetailRepository;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

@Service
public class CustomUserService {

    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserDetailRepository userDetailsRepository;
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired 
    private UserStatisticsService userStatisticsService;
   


    public boolean addUser(User user) {
        // lowercase any username
        user.setUsername(user.getUsername().toLowerCase());

        userDetailsRepository.save(user);
       
      
        userStatisticsService.notifyUserStatistics();
        return true;

    }

    public User getUserByUsername(String username) {

        return userDetailsRepository.findByUsername(username).get();

    }

   
}
