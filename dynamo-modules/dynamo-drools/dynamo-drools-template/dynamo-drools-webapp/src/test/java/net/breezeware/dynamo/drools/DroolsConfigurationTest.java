package net.breezeware.dynamo.drools;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNMessage;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.api.core.event.AfterEvaluateBKMEvent;
import org.kie.dmn.api.core.event.AfterEvaluateDecisionEvent;
import org.kie.dmn.api.core.event.AfterEvaluateDecisionTableEvent;
import org.kie.dmn.api.core.event.BeforeEvaluateBKMEvent;
import org.kie.dmn.api.core.event.BeforeEvaluateDecisionEvent;
import org.kie.dmn.api.core.event.BeforeEvaluateDecisionTableEvent;
import org.kie.dmn.api.core.event.DMNRuntimeEventListener;
import org.kie.dmn.core.api.DMNFactory;
import org.kie.dmn.core.api.event.DefaultDMNRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import net.breezeware.dynamo.drools.kjar.entity.BpPatient;
import net.breezeware.dynamo.drools.kjar.entity.BpReading;
import net.breezeware.dynamo.drools.kjar.entity.BpVital;
import net.breezeware.dynamo.drools.kjar.entity.Customer;
import net.breezeware.dynamo.drools.kjar.entity.Customer.CustomerType;
import net.breezeware.dynamo.drools.kjar.entity.Patient;
import net.breezeware.dynamo.drools.kjar.entity.Person;
import net.breezeware.dynamo.drools.kjar.entity.Year;
import net.breezeware.dynamo.drools.service.DroolsService;

@SpringBootTest

@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class DroolsConfigurationTest {

    final String sessionName = "dynamo-drools-kjar-ksession";
    @Autowired
    DroolsService defaultKieContainer;
    Logger logger = LoggerFactory.getLogger(DroolsConfigurationTest.class);
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
    public void bpCriticalityTest() {

        DMNRuntime dmnRuntime = KieRuntimeFactory
                .of(defaultKieContainer.kieContainer().getKieBase("dynamo-drools-kjar-kbase")).get(DMNRuntime.class);

        Patient bpPatient = new Patient();
        bpPatient.setName("Kumar");
        bpPatient.setAge(60);

        Patient bpPatient2 = new Patient();
        bpPatient2.setName("Baskar");
        bpPatient2.setAge(30);

        Patient bpPatient3 = new Patient();
        bpPatient3.setName("John Doe");
        bpPatient3.setAge(40);

        Patient bpPatient4 = new Patient();
        bpPatient4.setName("Jonas");
        bpPatient4.setAge(50);

        // List<Integer> bpValueList = new ArrayList<>();
        // bpValueList.add(200);
        // bpValueList.add(200);
        // bpValueList.add(200);
        List<BpVital> bpVitalList = new ArrayList<>();

        BpVital bpReading = new BpVital();
        bpReading.setSystolic(120);
        bpReading.setDiastolic(88);

        BpVital highbpReading1 = new BpVital();
        highbpReading1.setSystolic(150);
        highbpReading1.setDiastolic(98);

        BpVital highbpReading2 = new BpVital();
        highbpReading2.setSystolic(170);
        highbpReading2.setDiastolic(108);

        BpVital highbpReading3 = new BpVital();
        highbpReading3.setSystolic(160);
        highbpReading3.setDiastolic(108);

        bpVitalList.add(bpReading);
        bpVitalList.add(highbpReading1);
        bpVitalList.add(highbpReading2);
        bpVitalList.add(highbpReading3);

        List<BpVital> normalBpReadingList = new ArrayList<>();

        BpVital bpReading11 = new BpVital();
        bpReading11.setSystolic(120);
        bpReading11.setDiastolic(80);

        BpVital normalBpReading21 = new BpVital();
        normalBpReading21.setSystolic(119);
        normalBpReading21.setDiastolic(79);

        BpVital normalBpReading22 = new BpVital();
        normalBpReading22.setSystolic(120);
        normalBpReading22.setDiastolic(80);

        BpVital normalBpReading23 = new BpVital();
        normalBpReading23.setSystolic(120);
        normalBpReading23.setDiastolic(80);

        normalBpReadingList.add(bpReading11);
        normalBpReadingList.add(normalBpReading21);
        normalBpReadingList.add(normalBpReading22);
        normalBpReadingList.add(normalBpReading23);

        bpPatient.setBpVitalList(bpVitalList);
        bpPatient3.setBpVitalList(bpVitalList);
        bpPatient4.setBpVitalList(bpVitalList);

        // bpPatient.setBpVitalObject(loadBpData());
        // SETTING NORMAL BP VALUES
        bpPatient2.setBpVitalList(normalBpReadingList);

        String avgDMNNameSpace = "https://kiegroup.org/dmn/_D7B05B10-F592-431A-95FA-4F5DEE9A11AD";
        String avgDMNModelName = "BpCriticalityBasedonAvg";

//        final DMNModel dmnModel = dmnRuntime.getModel("https://kiegroup.org/dmn/_7826706D-8FE3-4C62-99FE-F7A97A255631",
//                "BpPatientCriticality");
        final DMNModel dmnModel = dmnRuntime.getModel(avgDMNNameSpace,
                avgDMNModelName);
        if (dmnModel != null) {
            if (dmnModel.hasErrors()) {
                System.out.println("Error while creating dmn" + dmnModel.getMessages());
            }
        } else {
            System.out.println("DMN NUll");

        }
        final DMNContext context = DMNFactory.newContext();

        dmnRuntime.addListener(createListener());

        context.set("Patient", bpPatient);
        DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, context);

        for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {
            System.out.println("Decision: '" + dr.getDecisionName() + "', " + "Result: " + dr.getResult());
        }
        System.out.println("Json " + (bpPatient).toString());
        //
        // System.out.println(result.get("Prioritized Waiting List"));
        // System.out.println(result.get("Rebooked Passengers"));
    }

    public DMNRuntimeEventListener createListener() {
        return new DefaultDMNRuntimeEventListener() {
            private final Logger logger = LoggerFactory.getLogger(DMNRuntimeEventListener.class);

            @Override
            public void beforeEvaluateDecision(BeforeEvaluateDecisionEvent event) {
                logger.info(event.toString());
            }

            @Override
            public void afterEvaluateDecision(AfterEvaluateDecisionEvent event) {
                logger.info(event.toString());
            }

            @Override
            public void beforeEvaluateBKM(BeforeEvaluateBKMEvent event) {
                logger.info(event.toString());
            }

            @Override
            public void afterEvaluateBKM(AfterEvaluateBKMEvent event) {
                logger.info(event.toString());
            }

            @Override
            public void beforeEvaluateDecisionTable(BeforeEvaluateDecisionTableEvent event) {
                logger.info(event.toString());
            }

            @Override
            public void afterEvaluateDecisionTable(AfterEvaluateDecisionTableEvent event) {
                logger.info(event.toString());
            }
        };
    }

    public String formatMessages(final List<DMNMessage> messages) {
        return messages.stream().map(Object::toString).peek(m -> logger.debug(m)).collect(Collectors.joining("\n"));
    }

    // @Test
    public void dmnConfigTest() {
        try {
            //
            // String namespace =
            // "https://kiegroup.org/dmn/_01CC543D-7C05-4E97-B394-01E96D3DD2B4";
            // String modelName = "Bmi_calc";
            //
            // String flightModelSpace = "https://www.drools.org/kie-dmn/Flight-rebooking";
            // String flightModelName = "0019-flight-rebooking";
            //
            // DMNModel dmnModel = dmnRuntime.getModel(namespace, modelName);
            //
            //// DMNModel flightDmnModel = dmnRuntime.getModel(flightModelSpace,
            // flightModelName);
            //
            // DMNContext dmnContext = dmnRuntime.newContext();

            // dmnContext.set("Weight", 94);
            // dmnContext.set("Height", 1.66);
            //
            // DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
            //
            // for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {
            // System.out.println("Decision: '" + dr.getDecisionName() + "', " + "Result: "
            // + dr.getResult());
            // }

            // final List passengerList = loadPassengerList();
            // final List flightList = loadFlightList();
            //
            // context.set("Passenger List", passengerList);
            // context.set("Flight List", flightList);
            //
            // final DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, context);
            //
            // final DMNContext result = dmnResult.getContext();
            //
            // System.out.println(result.get("Prioritized Waiting List"));
            // System.out.println(result.get("Rebooked Passengers"));
            //
            // assertThat(result.get("Rebooked Passengers"), is(loadExpectedResult()));

            //

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private List loadPassengerList() {
        final Object[][] passengerData = new Object[][] { { "Tom", "bronze", 10, "UA123" },
                { "Igor", "gold", 50000, "UA123" }, { "Jenny", "gold", 500000, "UA123" },
                { "Harry", "gold", 100000, "UA123" }, { "Dick", "silver", 100, "UA123" } };

        final List<Map<String, Object>> passengerList = new ArrayList<>();
        for (final Object[] pd : passengerData) {
            final Map<String, Object> p = new HashMap<>();
            p.put("name", pd[0]);
            p.put("age", pd[1]);
            p.put("bpVitalList", number((Number) pd[2]));
            p.put("Flight Number", pd[3]);
            passengerList.add(p);
        }
        return passengerList;
    }

    private List loadFlightList() {
        final Object[][] flightData = new Object[][] {
                { "UA123", "SFO", "SNA", date("2017-01-01T18:00:00"), date("2017-01-01T19:00:00"), 5, "cancelled" },
                { "UA456", "SFO", "SNA", date("2017-01-01T19:00:00"), date("2017-01-01T20:00:00"), 2, "scheduled" },
                { "UA789", "SFO", "SNA", date("2017-01-01T21:00:00"), date("2017-01-01T23:00:00"), 2, "scheduled" },
                { "UA1001", "SFO", "SNA", date("2017-01-01T23:00:00"), date("2017-01-02T05:00:00"), 0, "scheduled" },
                { "UA1111", "SFO", "LAX", date("2017-01-01T23:00:00"), date("2017-01-02T05:00:00"), 2, "scheduled" } };

        final List<Map<String, Object>> flightList = new ArrayList<>();
        for (final Object[] pd : flightData) {
            final Map<String, Object> p = new HashMap<>();
            p.put("Flight Number", pd[0]);
            p.put("From", pd[1]);
            p.put("To", pd[2]);
            p.put("Departure", pd[3]);
            p.put("Arrival", pd[4]);
            p.put("Capacity", pd[5]);
            p.put("Status", pd[6]);
            flightList.add(p);
        }
        return flightList;
    }

    private List loadExpectedResult() {
        final Object[][] passengerData = new Object[][] { { "Jenny", "gold", 500000, "UA456" },
                { "Harry", "gold", 100000, "UA456" }, { "Igor", "gold", 50000, "UA789" },
                { "Dick", "silver", 100, "UA789" }, { "Tom", "bronze", 10, null } };

        final List<Map<String, Object>> passengerList = new ArrayList<>();
        for (final Object[] pd : passengerData) {
            final Map<String, Object> p = new HashMap<>();
            p.put("Name", pd[0]);
            p.put("Status", pd[1]);
            p.put("Miles", number((Number) pd[2]));
            p.put("Flight Number", pd[3]);
            passengerList.add(p);
        }
        return passengerList;
    }

    private LocalDateTime date(final String date) {
        return LocalDateTime.parse(date);
    }

    private BigDecimal number(final Number n) {
        return BigDecimal.valueOf(n.longValue());
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
