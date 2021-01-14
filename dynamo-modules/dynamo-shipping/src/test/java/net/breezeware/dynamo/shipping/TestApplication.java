package net.breezeware.dynamo.shipping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@PropertySources({ @PropertySource(value = { "classpath:test.properties" }) })
@EnableJpaRepositories(basePackages = { "net.breezeware.dynamo.shipping" })
@ComponentScan(basePackages = { "net.breezeware.dynamo.shipping" })
@EntityScan(basePackages = { "net.breezeware.dynamo.shipping" })
public class TestApplication extends SpringBootServletInitializer {
    Logger logger = LoggerFactory.getLogger(TestApplication.class);

    public static void main(String[] args) {
        new TestApplication().configure(new SpringApplicationBuilder(TestApplication.class)).run(args);

    }

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}