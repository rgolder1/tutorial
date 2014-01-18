package com.aztec.ldap;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The @EnableWebSecurity turns on a variety of beans needed to use Spring Security.
 * 
 * You also need an LDAP server. Spring Security's LDAP module includes an embedded server written in pure 
 * Java, which is being used for this guide. The ldapAuthentication() method configures things where the 
 * username at the login form is plugged into {0} such that it searches 
 * uid={0},ou=people,dc=springframework,dc=org in the LDAP server.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder
			.ldapAuthentication()
				.userDnPatterns("uid={0},ou=people")
				.groupSearchBase("ou=groups")
				.contextSource()
					.ldif("classpath:test-server.ldif");
	}
}
