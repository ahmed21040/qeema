package com.qeema.authorizationserver.config;

import java.security.KeyPair;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import com.qeema.authorizationserver.model.security.CustomTokenEnhancer;

@Configuration
@EnableAuthorizationServer

public class AuthorizationServerConfiguration implements AuthorizationServerConfigurer {

//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Value("${myapp.keystore.name}")
	private String keystore;

	@Value("${myapp.keystore.pass}")
	private String keystorepass;

	@Value("${myapp.key.name}")
	private String key;

	@Value("${myapp.key.pass}")
	private String keypass;
	
	@Autowired
	private UserDetailsService userDetailsService;

//	@Bean
//	TokenStore jdbcTokenStore() {
//		return new JdbcTokenStore(dataSource);
//	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()");

	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("web").accessTokenValiditySeconds(3600).refreshTokenValiditySeconds(600)
//        .secret("{bcrypt}$2a$04$USYFDMAiURV6Y6Z4Y..vVOTcjP5hX3r012vgOQJqv.YozP5WJvxC.") //test
				.secret("test") // test
				.scopes("READ", "WRITE")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials");

	}

////	@Bean
//	TokenEnhancerChain enhancerChain() {
//	    TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
//	    enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
//	    return enhancerChain;
//	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// create the token enhancer!
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));

//        endpoints.tokenStore(jdbcTokenStore()); // saves tokens in DB
		endpoints.tokenStore(tokenStore()); // doesn't save tokens
		endpoints.accessTokenConverter(jwtAccessTokenConverter());
		endpoints.authenticationManager(authenticationManager).userDetailsService(userDetailsService);;

		// attach the token enhancer!
		endpoints.tokenEnhancer(tokenEnhancerChain);
	}

	// TOKEN ENHANCER THAT ENABLES USER TO CARRY SOME DATA
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}

	// -------------------------------------------------------------------- //
	// jwt related //
	// -------------------------------------------------------------------- //
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource(keystore), keystorepass.toCharArray())
				.getKeyPair(key, keypass.toCharArray());
		converter.setKeyPair(keyPair);
		return converter;
	}

//	@Bean
//	public JwtTokenStore jwtTokenStore() {
//		return new JwtTokenStore(jwtAccessTokenConverter());
//	}
	
	@Bean(name="jdbcTokenStore")
    public TokenStore tokenStore() {
        return new CustomInMemoryTokenStore();
    }


//	@Bean
	public DefaultTokenServices tokenServices() {

		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));

		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setTokenEnhancer(enhancerChain);
		return defaultTokenServices;
	}

}
