package net.breezeware.dynamo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@SpringBootApplication
@ComponentScan(basePackages = { "net.breezeware.dynamo" })
@EnableJpaRepositories(basePackages = { "net.breezeware.dynamo" })
@EntityScan(basePackages = { "net.breezeware.dynamo" })
@PropertySources({ @PropertySource(value = { "classpath:internal.properties" }) })
public class IntegrationTestApplication implements WebMvcConfigurer {

}