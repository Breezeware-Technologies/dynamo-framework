package net.breezeware.dynamo.drools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = { "net.breezeware" })
@EnableJpaRepositories(basePackages = { "net.breezeware" })
@EntityScan(basePackages = { "net.breezeware" })
@EnableAutoConfiguration

 @PropertySource(value = { "classpath:internal.properties" })
@PropertySource(value = {"classpath:dynamo.properties"})
// //,@PropertySource(value =
// "file:/usr/local/tulasi/carejoy/conf/application.properties",
// ignoreResourceNotFound = true)
// })
public class Application {
    public static void main(String[] args) {
//        new Application().configure(new SpringApplicationBuilder(Application.class)).run(args);
        SpringApplication.run(Application.class, args);
    }
}