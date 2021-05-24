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
import net.breezeware.dynamo.drools.kjar.entity.Counter;
import net.breezeware.dynamo.drools.kjar.entity.Customer;
import net.breezeware.dynamo.drools.kjar.entity.Customer.CustomerType;
import net.breezeware.dynamo.drools.kjar.entity.Order;
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
    public void lockOnActiveTest() {

        File dtf = new File(
                "/home/guru/Projects/dynamo-framework/dynamo-modules/dynamo-drools/dynamo-drools-template/dynamo-drools-kjar"
                + "/src/main/resources/rules/Order.xlsx");
        InputStream is;
        try {
            is = new FileInputStream(new File(""));
            SpreadsheetCompiler ssComp = new SpreadsheetCompiler();
            String s = ssComp.compile(is, InputType.XLS);
            System.out.println("=== Begin generated DRL ===");
            System.out.println(s);
            System.out.println("=== End generated DRL ===");

            Order order = new Order(1, "Guitar", 6000, 0);
            System.out.println("--- before rule firing");
            System.out.println(order);
            xmlBasedSession.insert(order);
            xmlBasedSession.fireAllRules();
            System.out.println("--- after rule firing");
            System.out.println(order);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test()
    public void bpPatientTest() {
        BpPatient bpPatient = new BpPatient();
        List<Integer> bpValueList = new ArrayList<>();
        bpValueList.add(200);
        bpValueList.add(200);
        bpValueList.add(200);

        bpPatient.setBpValueList(bpValueList);
//        Map<Integer, List<Person>> map = new HashMap<Integer, List<Person>>();  
//        Employee employee = new Employee();  
//        Map<String, List<Integer>> employeeLeavesMap = new HashMap<String, List<Integer>>();  
//        List<Integer> integers = new ArrayList<Integer>();  
//        integers.add(2);  
//        integers.add(3);  
//        integers.add(1);  
//        integers.add(5);  
//        integers.add(3);  
//        integers.add(8);  
//        integers.add(1);  
//        integers.add(0);  
//        integers.add(0);  
//        integers.add(0);  
//        integers.add(1);  
//        integers.add(3);  
//        employeeLeavesMap.put("Kumar", integers);  
//        employee.setEmployeeLeavesMap(employeeLeavesMap);  
         
        Counter cnt1 = new Counter(1, "cnt1");
        Counter cnt2 = new Counter(1, "cnt2");

        System.out.println();
        System.out.println("fire rules after inserting counter1");
        System.out.println();
      //  kSession.insert(cnt1);
        //fire rules with counter1
        //kSession.fireAllRules();

        System.out.println();
        System.out.println("fire rules after inserting counter2");
        System.out.println();
      //  kSession.insert(cnt2);
        //fire rules with already existing counter1 and newly inserted counter2
      //  kSession.fireAllRules();
        xmlBasedSession.insert(bpPatient);
     //   xmlBasedSession.insert(cnt2);
        xmlBasedSession.fireAllRules();
        xmlBasedSession.dispose();
        
    }
    
    
    

}
