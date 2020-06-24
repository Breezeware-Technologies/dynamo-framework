package net.breezeware.dynamo.drools;


import java.util.ArrayList;
import java.util.List;

import net.breezeware.dynamo.drools.kjar.entity.MedicalAdvice;
import net.breezeware.dynamo.drools.kjar.entity.Person;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;
import org.kie.api.runtime.rule.FactHandle;
import org.testng.annotations.Test;

/**
 * This is a sample class to launch a rule.
 */
public class MedicalAdviceTest {

    // public static final void main(String[] args) {
    @Test
    public void testDrools() {
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            
            // 'dynamo-drools-kjar-ksession' is the name of the session mentioned in the 
            // 'kmodule.xml' file in KJar project.
            KieSession kSession = kContainer.newKieSession("dynamo-drools-kjar-ksession");
            
            Person person = new Person();
            person.setAge(66);
            person.setName("Saravanan");
            person.setSex(Person.MALE);
            
            kSession.insert(person);
            kSession.fireAllRules();
            MedicalAdvice medAdvice = findMedicalAdvice(kSession);
            
            System.out.println("medicalAdvice = " + medAdvice);
            
            kSession.dispose();
        } catch (Throwable t) {
            t.printStackTrace();
        }
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