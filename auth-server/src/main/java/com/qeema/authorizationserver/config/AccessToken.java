package com.qeema.authorizationserver.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;
import com.qeema.authorizationserver.model.security.User;

@Component
public class AccessToken {
	

	
  @Autowired(required=true)
  @Qualifier("jdbcTokenStore")
  private TokenStore tokenStore;
	
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	
	@Autowired
	private TokenEnhancer tokenEnhancer;
	
	public OAuth2AccessToken getAccessToken(User user) {
	    HashMap<String, String> authorizationParameters = new HashMap<String, String>();
//	    authorizationParameters.put("scope", "read");
//	    authorizationParameters.put("username", "web");
	    authorizationParameters.put("client_id", "web");
//	    authorizationParameters.put("client_id", "d2ViOnRlc3Q=");
	    authorizationParameters.put("grant", "password");
//	    authorizationParameters.put("client_secret", "test");

	    Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	    

	    Set<String> responseType = new HashSet<String>();
	    responseType.add("password");

	    Set<String> scopes = new HashSet<String>();
	    scopes.add("read");
	    scopes.add("write");

	    OAuth2Request authorizationRequest = new OAuth2Request(authorizationParameters, "web", authorities, true,
	            scopes, null, "", responseType, null);

	    org.springframework.security.core.userdetails.User userPrincipal = new org.springframework.security.core.userdetails.User(
	            user.getEmail(), "", authorities);
	    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal,
	            null, authorities);

	    OAuth2Authentication authenticationRequest = new OAuth2Authentication(authorizationRequest,
	            authenticationToken);
	    authenticationRequest.setAuthenticated(true);
	    OAuth2AccessToken accessToken = tokenServices().createAccessToken(authenticationRequest);

	    return accessToken;
	}
	
	public DefaultTokenServices tokenServices() {

		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer, jwtAccessTokenConverter));

		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore);
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setTokenEnhancer(enhancerChain);
		return defaultTokenServices;
	}
	
	

}
