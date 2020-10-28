package com.qeema.authorizationserver.controllers;

import static org.mockito.Matchers.intThat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.qeema.authorizationserver.model.security.User;
import com.qeema.authorizationserver.service.UserStatisticsService;

@RestController
public class SecurityLogoutController {
  @Autowired
  private ConsumerTokenServices consumerTokenServices;
  
  @Autowired
  @Qualifier("jdbcTokenStore")
  private TokenStore tokenStore;
  
  @Autowired
  @Qualifier("sessionRegistry")
  private SessionRegistry sessionRegistry;
  
  @Autowired 
  private UserStatisticsService userStatisticsService;



  @RequestMapping(path = "/relogout", method = RequestMethod.POST)
  public void logout(HttpServletRequest request, OAuth2Authentication authentication)
      throws ServletException {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
    String tokenValue = details.getTokenValue();
    OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
    OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();

    tokenStore.removeAccessToken(tokenStore.readAccessToken(tokenValue));
    if (refreshToken != null) {
      tokenStore.removeRefreshToken(refreshToken);
    }
    // authService.deleteOtpToken(tokenValue);
    // authService.deleteMfaToken(tokenValue);
    SecurityContextHolder.clearContext();
    request.logout();
    
    userStatisticsService.userLogout();
    
  }
  
  @RequestMapping(path = "/testlogout", method = RequestMethod.POST)
  public void testLogout(HttpServletRequest request, OAuth2Authentication authentication) {
    
    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
    System.out.println("details : " + details);
    
    List<Object> principals = sessionRegistry.getAllPrincipals();


    
    System.out.println("totalLogin : " + principals.size());
    
  }
}
