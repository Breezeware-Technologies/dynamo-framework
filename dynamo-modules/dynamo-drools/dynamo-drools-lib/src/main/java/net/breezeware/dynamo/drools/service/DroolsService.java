package net.breezeware.dynamo.drools.service;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class DroolsService {

    Logger logger = LoggerFactory.getLogger(DroolsService.class);

    @Bean
    public KieContainer kieContainer() {
        return KieServices.Factory.get().getKieClasspathContainer();
    }

    // @Autowired
    // KieContainer kieContainer;

    // This method gets a session from XML configuration 'k-module' and not creates
    // a new one.
    public KieSession getKieSession(String sessionName) {
        logger.info("Session name = {}", sessionName);
        KieSession kieSession = kieContainer().newKieSession(sessionName);
        return kieSession;
    }
}