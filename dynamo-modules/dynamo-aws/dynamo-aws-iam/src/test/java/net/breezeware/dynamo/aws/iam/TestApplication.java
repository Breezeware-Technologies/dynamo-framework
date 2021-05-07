package net.breezeware.dynamo.aws.iam;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "net.breezeware" })
@EnableJpaRepositories(basePackages = { "net.breezeware" })
@EntityScan(basePackages = { "net.breezeware" })
public class TestApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new TestApplication().configure(new SpringApplicationBuilder(TestApplication.class)).run(args);
    }
}
