package net.breezeware.dynamo.drools;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
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

    @Test
    public void spreadsheetToDrlTest() {
        File dtf = new File("src/main/resources/rules/sample-drools-dt.xls");
        InputStream is;
        try {
            is = new FileInputStream(dtf);
            SpreadsheetCompiler ssComp = new SpreadsheetCompiler();
            String s = ssComp.compile(is, InputType.XLS);
            System.out.println("=== Begin generated DRL ===");
            System.out.println(s);
            System.out.println("=== End generated DRL ===");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
