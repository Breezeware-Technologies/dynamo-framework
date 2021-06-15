package net.breezeware.dynamo.drools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        logger.info("Entering & Leaving authenticationManagerBean()");
        return super.authenticationManagerBean();
    }
        


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Entering configure(HttpSecurity http)");

        String loginPagePath = "/login";
        String defaultSuccessUrl = "/dashboard";

        http.authorizeRequests()
                .antMatchers("/", "/oauth/token", "/oauth/authorize", "/oauth/check_token", "/oauth/confirm_access",
                        "/oauth/error", "/registerUser/**", "/forgotPassword", "/resetPassword", "/login", "/css/**",
                        "/styles/**", "/js/**", "/scripts/**","/dcs/**" ,"/images/**", "/fonts/**", "/request/**",
                        "/ascend-utils/**")
                .permitAll().anyRequest().authenticated().and().csrf().disable().formLogin()
                .defaultSuccessUrl(defaultSuccessUrl, true).loginPage(loginPagePath).usernameParameter("username")
                .passwordParameter("password").failureUrl("/login?authError").and().logout().permitAll().and()
                .sessionManagement().invalidSessionUrl("/login?logout");
        // @formatter:on
        logger.info("Leaving configure(HttpSecurity http)");

        logger.info("Leaving configure(HttpSecurity http)");

    }

  
}
