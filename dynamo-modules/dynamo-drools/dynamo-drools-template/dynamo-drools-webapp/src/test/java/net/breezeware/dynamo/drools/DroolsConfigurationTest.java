package net.breezeware.dynamo.drools;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import net.breezeware.dynamo.drools.kjar.entity.BpPatient;
import net.breezeware.dynamo.drools.kjar.entity.BpReading;
import net.breezeware.dynamo.drools.kjar.entity.Counter;
import net.breezeware.dynamo.drools.kjar.entity.Customer;
import net.breezeware.dynamo.drools.kjar.entity.Customer.CustomerType;
import net.breezeware.dynamo.drools.kjar.entity.Person;
import net.breezeware.dynamo.drools.kjar.entity.Year;
import net.breezeware.dynamo.drools.service.DroolsService;

@SpringBootTest
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class DroolsConfigurationTest {

    final String sessionName = "dynamo-drools-kjar-ksession";

    @Autowired
    DroolsService defaultKieContainer;

    KieSession xmlBasedSession = null;

    @BeforeEach
    public void droolsDefaultConfigurationTest() {
        System.out.println("Entering droolsDefaultConfigurationTest()");
        xmlBasedSession = defaultKieContainer.kieContainer().newKieSession(sessionName);
        // System.out.println("Xml " + xmlBasedSession.toString());
        assertNotNull(xmlBasedSession);
        System.out.println("Leaving droolsDefaultConfigurationTest()");

    }

    // @Test
    public void MaleTest() {
        Person person = new Person();
        person.setSex(Person.MALE);
        xmlBasedSession.insert(person);
        xmlBasedSession.fireAllRules();
        xmlBasedSession.dispose();

    }

    // @Test
    public void spreadSheetTest() {
        List<Customer> customerList = new ArrayList<>();
        List<Year> yearList = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            Customer customer = new Customer(CustomerType.INDIVIDUAL, "aa" + i);
            Year year = new Year(i);
            xmlBasedSession.insert(customer);
            xmlBasedSession.insert(year);
            customerList.add(customer);
            yearList.add(year);
        }

        xmlBasedSession.fireAllRules();
        for (int j = 0; j < customerList.size(); j++) {
            System.out.println("Customer years \t " + customerList.get(j).getYears() + " \t Customer discount \t "
                    + customerList.get(j).getDiscount() + "\n" + "Experience \t " + yearList.get(j).getExperience()
                    + " \t Customer Rating \t " + yearList.get(j).getRating());
        }
    }

  // @Test
    public void spreadsheetToDrlTest() {
        File dtf = new File("/home/guru/Projects/dynamo-framework/dynamo-modules/dynamo-drools/dynamo-drools-template/dynamo-drools-kjar/src/main/resources/rules/bp_rules_xls.xlsx");
        InputStream is;
        try {
            is = new FileInputStream(dtf);

            SpreadsheetCompiler ssComp = new SpreadsheetCompiler();
            String s = ssComp.compile(is, InputType.XLS);
            System.out.println("=== Begin generated DRL ===");
            System.out.println(s);
            System.out.println("=== End generated DRL ===");

//            Order order = new Order(1, "Guitar", 6000, 0);
//            System.out.println("--- before rule firing");
//            System.out.println(order);
//            xmlBasedSession.insert(order);
//            xmlBasedSession.fireAllRules();
//            System.out.println("--- after rule firing");
//            System.out.println(order);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test()
    public void bpPatientTest() {
        BpPatient bpPatient = new BpPatient();
        // List<Integer> bpValueList = new ArrayList<>();
        // bpValueList.add(200);
        // bpValueList.add(200);
        // bpValueList.add(200);
        List<BpReading> bpReadingList = new ArrayList<>();
      
        BpReading bpReading = new BpReading();
        bpReading.setSystolic(120);
        bpReading.setDiastolic(80);
        BpReading bpReading1 = new BpReading();
        bpReading1.setSystolic(150);
        bpReading1.setDiastolic(90);
      
        BpReading bpReading2 = new BpReading();
        bpReading2.setSystolic(170);
        bpReading2.setDiastolic(100);
      
        BpReading bpReading3 = new BpReading();
        bpReading3.setSystolic(160);
        bpReading3.setDiastolic(100);
        
        bpReadingList.add(bpReading);
        bpReadingList.add(bpReading1);
        bpReadingList.add(bpReading2);
        bpReadingList.add(bpReading3);


        bpPatient.setBpValueList(bpReadingList);

        Counter cnt1 = new Counter(1, "cnt1");
        Counter cnt2 = new Counter(1, "cnt2");

        System.out.println();
        System.out.println("fire rules after inserting counter1"+bpPatient.getWarningMessage());
        System.out.println();
        // kSession.insert(cnt1);
        // fire rules with counter1
        // kSession.fireAllRules();

     
        // kSession.insert(cnt2);
        // fire rules with already existing counter1 and newly inserted counter2
        // kSession.fireAllRules();
        xmlBasedSession.insert(bpPatient);
        // xmlBasedSession.insert(cnt2);
        xmlBasedSession.fireAllRules();
        
        System.out.println();
        System.out.println("fire rules after inserting counter2"+ bpPatient.getWarningMessage());
        System.out.println();
        xmlBasedSession.dispose();

    }

}
