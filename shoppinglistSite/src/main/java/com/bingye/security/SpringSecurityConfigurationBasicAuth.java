package com.bingye.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfigurationBasicAuth {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
	    http.headers().frameOptions().disable();
//		http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin","*"));
//		
//		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/**").permitAll().anyRequest()
//			.authenticated().and()
//			.httpBasic();
//		return http.build();
//	}
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest()
				.authenticated().and()
				// .formLogin().and()
				.httpBasic();
		return http.build();
	}

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//	    return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		//TODO later can improved with your own user service and also connect with db
//		//https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details-service.html
//		UserDetails user = User.withUsername("bingye").password(passwordEncoder().encode("bingye")).roles("USER")
//				.build();
//
//		return new InMemoryUserDetailsManager(user);
//	}
}
