package net.breezeware.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("net.breezeware")
@ComponentScan("net.breezeware")
@EnableJpaRepositories("net.breezeware")
@PropertySources({ @PropertySource(value = { "classpath:test-application.properties" }) })
public class TestApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        new TestApplication().configure(new SpringApplicationBuilder(TestApplication.class)).run(args);
    }
}