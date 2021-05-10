package net.breezeware.dynamo.aws.iam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@PropertySources({ @PropertySource(value = { "classpath:test.properties" }) })
@ComponentScan(basePackages = { "net.breezeware.dynamo.aws" })
@EnableJpaRepositories(basePackages = { "net.breezeware.dynamo.aws" })
@EntityScan(basePackages = { "net.breezeware.dynamo.aws" })
public class TestApplication extends SpringBootServletInitializer {

    Logger logger = LoggerFactory.getLogger(TestApplication.class);

    public static void main(String[] args) {
        new TestApplication().configure(new SpringApplicationBuilder(TestApplication.class)).run(args);
    }
}
