package com.qeema.authorizationserver.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.qeema.authorizationserver.dto.UserStatisticsDTO;
import com.qeema.authorizationserver.model.security.User;
import com.qeema.authorizationserver.service.UserStatisticsService;

@RestController
@RequestMapping("/statistics")
public class UserStatisticsController {

  @Autowired
  private UserStatisticsService userStatisticsService;

  @GetMapping
  public HttpEntity<UserStatisticsDTO> getUserStatistics() {
    return new HttpEntity<>(userStatisticsService.getUserStatistics());

  }

}
