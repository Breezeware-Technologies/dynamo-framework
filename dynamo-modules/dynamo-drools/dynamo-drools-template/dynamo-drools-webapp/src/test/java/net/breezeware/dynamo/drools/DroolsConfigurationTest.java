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
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import net.breezeware.dynamo.drools.kjar.entity.BpPatient;
import net.breezeware.dynamo.drools.kjar.entity.BpReading;
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
        File dtf = new File(
                "/home/guru/Projects/dynamo-framework/dynamo-modules/dynamo-drools/dynamo-drools-template/dynamo-drools-kjar/src/main/resources/rules/bp_rules_xls.xlsx");
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

    @Test
    public void dmnConfigTest() {
        try {
            DMNRuntime dmnRuntime = KieRuntimeFactory
                    .of(defaultKieContainer.kieContainer().getKieBase("dynamo-drools-kjar-kbase"))
                    .get(DMNRuntime.class);

            String namespace = "https://kiegroup.org/dmn/_01CC543D-7C05-4E97-B394-01E96D3DD2B4";
            String modelName = "Bmi_calc";

            DMNModel dmnModel = dmnRuntime.getModel(namespace, modelName);

            DMNContext dmnContext = dmnRuntime.newContext();

            dmnContext.set("Height", 1.66);
            dmnContext.set("Weight", 72);
            DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);

            for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {
                System.out.println("Decision: '" + dr.getDecisionName() + "', " + "Result: " + dr.getResult());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // @Test()
    public void bpPatientTest() {

        BpPatient bpPatient = new BpPatient();
        bpPatient.setPatientName("Kumar");

        BpPatient bpPatient2 = new BpPatient();
        bpPatient2.setPatientName("Baskar");

        BpPatient bpPatient3 = new BpPatient();
        bpPatient3.setPatientName("John Doe");

        BpPatient bpPatient4 = new BpPatient();
        bpPatient4.setPatientName("Jonas");

        // List<Integer> bpValueList = new ArrayList<>();
        // bpValueList.add(200);
        // bpValueList.add(200);
        // bpValueList.add(200);
        List<BpReading> highBpReadingList = new ArrayList<>();

        BpReading bpReading = new BpReading();
        bpReading.setSystolic(120);
        bpReading.setDiastolic(80);

        BpReading highbpReading1 = new BpReading();
        highbpReading1.setSystolic(150);
        highbpReading1.setDiastolic(90);

        BpReading highbpReading2 = new BpReading();
        highbpReading2.setSystolic(170);
        highbpReading2.setDiastolic(100);

        BpReading highbpReading3 = new BpReading();
        highbpReading3.setSystolic(160);
        highbpReading3.setDiastolic(100);

        highBpReadingList.add(bpReading);
        highBpReadingList.add(highbpReading1);
        highBpReadingList.add(highbpReading2);
        highBpReadingList.add(highbpReading3);

        List<BpReading> normalBpReadingList = new ArrayList<>();

        BpReading bpReading11 = new BpReading();
        bpReading11.setSystolic(120);
        bpReading11.setDiastolic(80);

        BpReading normalBpReading21 = new BpReading();
        normalBpReading21.setSystolic(119);
        normalBpReading21.setDiastolic(79);

        BpReading normalBpReading22 = new BpReading();
        normalBpReading22.setSystolic(120);
        normalBpReading22.setDiastolic(80);

        BpReading normalBpReading23 = new BpReading();
        normalBpReading23.setSystolic(120);
        normalBpReading23.setDiastolic(80);

        normalBpReadingList.add(bpReading11);
        normalBpReadingList.add(normalBpReading21);
        normalBpReadingList.add(normalBpReading22);
        normalBpReadingList.add(normalBpReading23);

        bpPatient.setBpValueList(highBpReadingList);
        bpPatient3.setBpValueList(highBpReadingList);
        bpPatient4.setBpValueList(highBpReadingList);

        // SETTING NORMAL BP VALUES
        bpPatient2.setBpValueList(normalBpReadingList);

        FactHandle firstFact = xmlBasedSession.insert(bpPatient);
        xmlBasedSession.fireAllRules();
        xmlBasedSession.delete(firstFact);
        // System.out.println("**********************");
        // System.out.println("After Firing Rules");
        if (bpPatient.getWarningMessage() != null) {
            System.out.println(bpPatient.getPatientName() + " is suffering from high Bp");
        } else {
            System.out.println(bpPatient.getPatientName() + " having good Bp vitals");
        }

        FactHandle secondFact = xmlBasedSession.insert(bpPatient2);
        xmlBasedSession.fireAllRules();
        // if we didn't retract the old fact from the memory it will be worked as we
        // expect. The readingList will be taken from any existing BpPatient.
        xmlBasedSession.delete(secondFact);

        if (bpPatient2.getWarningMessage() != null) {
            System.out.println(bpPatient2.getPatientName() + " is suffering from high Bp");
        } else {
            System.out.println(bpPatient2.getPatientName() + "  having good Bp vitals");
        }

        FactHandle thirdFact = xmlBasedSession.insert(bpPatient3);

        xmlBasedSession.fireAllRules();
        xmlBasedSession.delete(thirdFact);

        if (bpPatient3.getWarningMessage() != null) {
            System.out.println(bpPatient3.getPatientName() + " is suffering from high Bp");
        } else {
            System.out.println(bpPatient3.getPatientName() + "having good Bp vitals");
        }

        FactHandle fourthFact = xmlBasedSession.insert(bpPatient4);

        xmlBasedSession.fireAllRules();
        xmlBasedSession.delete(fourthFact);

        if (bpPatient4.getWarningMessage() != null) {
            System.out.println(bpPatient4.getPatientName() + " is suffering from high Bp");
        } else {
            System.out.println(bpPatient4.getPatientName() + "having good Bp vitals");
        }

        xmlBasedSession.dispose();

        System.out.println();

        // System.out.println("fire rules after inserting counter2"+
        // bpPatient.getWarningMessage());
        System.out.println();
        // xmlBasedSession.dispose();

    }

}
