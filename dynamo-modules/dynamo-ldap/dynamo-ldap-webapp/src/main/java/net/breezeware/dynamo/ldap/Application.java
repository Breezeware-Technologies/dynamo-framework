package net.breezeware.dynamo.ldap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//import org.thymeleaf.dialect.springdata.SpringDataDialect;

@SpringBootApplication
@ComponentScan("net.breezeware")
@EnableJpaRepositories("net.breezeware")
@EntityScan("net.breezeware")
// @EnableAutoConfiguration
@PropertySources({ @PropertySource(value = { "classpath:internal.properties" }),
		@PropertySource(value = "file:/usr/local/dynamo/dynamo-framework/dynamo-ldap/conf/application.properties", ignoreResourceNotFound = true) })
public class Application extends SpringBootServletInitializer {

	@Value("${spring.ldap.urls}")
	private String springLdapUrls;

	@Value("${spring.ldap.base}")
	private String springLdapBase;

	public static void main(String[] args) {
		new Application().configure(new SpringApplicationBuilder(Application.class)).run(args);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
	}

	// FIXME: CMK - remove comment and resolve dependency issue

//    @Bean
//    public SpringDataDialect springDataDialect() {
//        return new SpringDataDialect();
//    }

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
		bean.setBasename("classpath:messages");
		bean.setDefaultEncoding("UTF-8");
		return bean;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

	public Validator getValidator() {
		return validator();
	}

	@Bean
	ContextSource contextSource() {
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl(springLdapUrls);
		ldapContextSource.setBase(springLdapBase);
		return ldapContextSource;
	}

	@Bean
	LdapTemplate ldapTemplate(ContextSource contextSource) {
		return new LdapTemplate(contextSource);
	}
}