package com.qeema.authorizationserver.dto;

public class UserStatisticsDTO {
  
  private int totalCreatedUser;
  private int toatalLoginUser;
  
  public UserStatisticsDTO() {
  }
  
  public UserStatisticsDTO(int totalCreatedUser, int toatalLoginUser) {
    this.totalCreatedUser = totalCreatedUser;
    this.toatalLoginUser = toatalLoginUser;
  }
  public int getTotalCreatedUser() {
    return totalCreatedUser;
  }
  public void setTotalCreatedUser(int totalCreatedUser) {
    this.totalCreatedUser = totalCreatedUser;
  }
  public int getToatalLoginUser() {
    return toatalLoginUser;
  }
  public void setToatalLoginUser(int toatalLoginUser) {
    this.toatalLoginUser = toatalLoginUser;
  }
  
  
  

}
