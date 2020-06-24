package net.breezeware.dynamo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages = { "net.breezeware", "net.breezeware.dynamo" })
@EnableJpaRepositories(basePackages = { "net.breezeware", "net.breezeware.dynamo" })
@EntityScan(basePackages = { "net.breezeware", "net.breezeware.dynamo" })
@EnableScheduling
@PropertySources({ @PropertySource(value = { "classpath:internal.properties" }),
		@PropertySource(value = "file:/usr/local/dynamo/dynamo-framework/dynamo-admin-webapp/conf/application.properties", ignoreResourceNotFound = true) })
public class Application extends SpringBootServletInitializer {

	Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		new Application().configure(new SpringApplicationBuilder(Application.class)).run(args);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
	}

	@Bean
	public SpringDataDialect springDataDialect() {
		return new SpringDataDialect();
	}

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
}