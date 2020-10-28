package com.qeema.authorizationserver.model.security;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		// creates default user profile if it's first time to get a token
//		userProfileService.firstTimeCreateUserProfile((AuthUserDetail)authentication.getPrincipal() );
//		Map<String, Object> additionalInfo = new HashMap<>();
//		additionalInfo.put("user_profile", );
//		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}
}