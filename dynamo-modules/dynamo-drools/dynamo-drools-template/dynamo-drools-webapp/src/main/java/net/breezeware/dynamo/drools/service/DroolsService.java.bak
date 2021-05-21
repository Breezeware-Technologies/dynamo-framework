package net.breezeware.dynamo.drools.service;

import java.util.ArrayList;


import java.util.List;

import net.breezeware.dynamo.drools.kjar.entity.MedicalAdvice;
import net.breezeware.dynamo.drools.kjar.entity.Person;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class DroolsService {

    Logger logger = LoggerFactory.getLogger(DroolsService.class);

    @Bean
    public KieContainer kieContainer() {
        return KieServices.Factory.get().getKieClasspathContainer();
    }

    @Autowired
    KieContainer kieContainer;

    public MedicalAdvice getMedicalAdvice(Person person) {

        logger.info("Person = " + person);

        KieSession kieSession = kieContainer.newKieSession("ksession-rules");
        kieSession.insert(person);
        kieSession.fireAllRules();
        MedicalAdvice medAdvice = findMedicalAdvice(kieSession);
        kieSession.dispose();
        return medAdvice;
    }

    private MedicalAdvice findMedicalAdvice(KieSession kieSession) {

        // Find all MedicalAdvice facts.
        ObjectFilter medAdviceFilter = new ObjectFilter() {
            public boolean accept(Object object) {
                if (MedicalAdvice.class.equals(object.getClass()))
                    return true;
                return false;
            }
        };

        List<MedicalAdvice> facts = new ArrayList<MedicalAdvice>();
        for (FactHandle handle : kieSession.getFactHandles(medAdviceFilter)) {
            facts.add((MedicalAdvice) kieSession.getObject(handle));
        }
        if (facts.size() == 0) {
            return null;
        }
        // Assumes that the rules will always be generating a single medical
        // advice.
        return facts.get(0);
    }
}