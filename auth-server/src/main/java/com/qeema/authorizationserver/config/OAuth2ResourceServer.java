package com.qeema.authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter 
{

	
	 @Override
		public void configure(HttpSecurity http) throws Exception {
			// the following configuration is to allow access h2-console, uncomment it for
			// using embedded database @MoSalah
//			http.headers().frameOptions().sameOrigin().and().authorizeRequests().antMatchers("/h2-console/**").permitAll();
			http.authorizeRequests().antMatchers("/registration/**","/ws/**", "/h2-console/**")
					.permitAll().antMatchers("/**").authenticated().and().logout().clearAuthentication(true)
					.deleteCookies().invalidateHttpSession(true);
			
			http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
			http.cors();
	    }
	    
	    @Bean
	    public SessionRegistry sessionRegistry() {
	        return new SessionRegistryImpl();

	    }
}
