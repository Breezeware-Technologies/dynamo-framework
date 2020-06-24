package net.breezeware.dynamo.apps;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@EnableAutoConfiguration
@PropertySources({ @PropertySource(value = { "classpath:test-application.properties" }) })
public class TestApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        new TestApplication().configure(new SpringApplicationBuilder(TestApplication.class)).run(args);
    }
}