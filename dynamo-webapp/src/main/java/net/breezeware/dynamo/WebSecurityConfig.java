package net.breezeware.dynamo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import net.breezeware.dynamo.auth.springsecurity.CustomAccessDeniedHandler;
import net.breezeware.dynamo.auth.springsecurity.DynamoUserDetailsAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = { "net.breezeware" })
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DynamoUserDetailsAuthenticationProvider dynamoUserDetailsAuthenticationProvider;

	Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.info("Entering configure(HttpSecurity http)");

		// @formatter:off
		String defaultSuccessUrl = "/dashboard";
		String loginPagePath = "/login";

		logger.debug("Matching following ant paths: '{}'",
				"/, /home,/swagger/**,/images/**,/api-docs/*,/css/**,/styles/**,/js/**,/scripts/**");
		logger.debug("Default success URL = '{}'. Login page path = '{}'.", defaultSuccessUrl, loginPagePath);

		http.requiresChannel().antMatchers("/**").requiresSecure().and().authorizeRequests()
				// http.authorizeRequests()
				.antMatchers("/", "/oauth/token", "/oauth/authorize", "/oauth/check_token", "/oauth/confirm_access",
						"/oauth/error", "/refresh", "/actuator/**", "/resetPassword", "/forgotPassword",
						"/registerUser/**", "/home", "/swagger/**", "/api-docs/**", "/images/**", "/css/**",
						"/styles/**", "/js/**", "/scripts/**")
				.permitAll().anyRequest().authenticated().and().oauth2Login().failureUrl("http://www.amazon.com")
				.loginPage(loginPagePath).defaultSuccessUrl("/oauth2LoginSuccess").and()
				// .oauth2Login().loginPage(loginPagePath).defaultSuccessUrl("/login/oauth2/code/realfactor").and()
				// .csrf().requireCsrfProtectionMatcher(csrfRequestMatcher)
				.csrf().disable().headers().cacheControl().disable().and().formLogin()
				.defaultSuccessUrl(defaultSuccessUrl, true).loginPage(loginPagePath).permitAll().and().logout()
				// .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
				.permitAll().and().sessionManagement().invalidSessionUrl("/login?logout").and().exceptionHandling()
				.accessDeniedHandler(new CustomAccessDeniedHandler());

		// @formatter:on
		logger.info("Leaving configure(HttpSecurity http)");

	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		logger.info("Entering configure(final AuthenticationManagerBuilder auth).");

		// auth.ldapAuthentication().userDnPatterns("uid={0},ou=people").contextSource(contextSource()).passwordCompare()
		// .passwordEncoder(new
		// LdapShaPasswordEncoder()).passwordAttribute("userPassword");

		auth.authenticationProvider(dynamoUserDetailsAuthenticationProvider);

		logger.debug("Set authenticationProvider = '{}'.", dynamoUserDetailsAuthenticationProvider);
		logger.info("Leaving configure(final AuthenticationManagerBuilder auth).");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		logger.info("Entering & Leaving authenticationManagerBean()");
		return super.authenticationManagerBean();
	}

}