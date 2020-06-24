package net.breezeware.dynamo.drools;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = DispatcherServletAutoConfiguration.class)
@EnableJpaRepositories("net.breezeware")
@EntityScan("net.breezeware")
@ComponentScan("net.breezeware")
@EnableAutoConfiguration
// @PropertySources({
// @PropertySource(value = { "classpath:internal.properties" })
// //,@PropertySource(value =
// "file:/usr/local/tulasi/carejoy/conf/application.properties",
// ignoreResourceNotFound = true)
// })
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        new Application().configure(new SpringApplicationBuilder(Application.class)).run(args);
    }
}