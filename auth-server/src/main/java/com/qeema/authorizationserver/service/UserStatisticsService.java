package com.qeema.authorizationserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import com.qeema.authorizationserver.dto.UserStatisticsDTO;
import com.qeema.authorizationserver.repository.UserDetailRepository;

@Service
public class UserStatisticsService {

  public static int totalLoginUser = 0;

  @Autowired
  private UserDetailRepository userDetailRepository;

  @Autowired
  private SimpMessageSendingOperations messagingTemplate;

  public void userLogin() {
    UserStatisticsService.totalLoginUser++;
    notifyUserStatistics();
  }

  public void userLogout() {
    if (UserStatisticsService.totalLoginUser > 0) {
      UserStatisticsService.totalLoginUser--;
    } else {
      UserStatisticsService.totalLoginUser = 0;
    }

    notifyUserStatistics();
  }


  public void notifyUserStatistics() {
   
    messagingTemplate.convertAndSend("/topic/statistics", getUserStatistics());

  }
  
  public UserStatisticsDTO getUserStatistics() {
    int totalCreatedUser = userDetailRepository.getUserCount();

    UserStatisticsDTO userStatisticsDTO = new UserStatisticsDTO(totalCreatedUser, totalLoginUser);
    System.out.println("userStatisticsDTO " + userStatisticsDTO);
    return userStatisticsDTO;
  }

}
