package net.breezeware.dynamo.communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages = { "net.breezeware" })
@EntityScan(basePackages = { "net.breezeware" })
@EnableScheduling
@PropertySources({ @PropertySource(value = { "classpath:test.properties" }) })
public class TestApplication extends SpringBootServletInitializer {

	Logger logger = LoggerFactory.getLogger(TestApplication.class);

	public static void main(String[] args) {
		new TestApplication().configure(new SpringApplicationBuilder(TestApplication.class)).run(args);
	}
}