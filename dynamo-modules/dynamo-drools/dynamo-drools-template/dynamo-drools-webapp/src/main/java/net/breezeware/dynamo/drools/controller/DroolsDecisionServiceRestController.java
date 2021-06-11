package net.breezeware.dynamo.drools.controller;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.api.runtime.KieSession;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.breezeware.dynamo.drools.kjar.entity.Patient;

@RestController
@RequestMapping("/api/dynamo-drools/decision")
public class DroolsDecisionServiceRestController {

    final String sessionName = "dynamo-drools-kjar-ksession";
    @Autowired
    KieContainer defaultKieContainer;
    Logger logger = LoggerFactory.getLogger(DroolsDecisionServiceRestController.class);
    KieSession xmlBasedSession = null;

    @GetMapping("/container")
    public String getDefaultKieSession() {
        logger.info("Entering getDefaultKieSession()");
        xmlBasedSession = defaultKieContainer.newKieSession(sessionName);
        logger.info("Leaving droolsDefaultConfigurationTest()");
        return defaultKieContainer.toString();
    }

    @PostMapping(value = "/checkBp", consumes = "application/json")
    public ResponseEntity<?> checkBpPatientCriticality(@RequestBody Patient patient) {
        logger.info("Entering checkBpPatientCriticality() patient value{}",patient);
        String avgDMNNameSpace = "https://kiegroup.org/dmn/_D7B05B10-F592-431A-95FA-4F5DEE9A11AD";
        String avgDMNModelName = "BpCriticalityBasedonAvg";
        DMNRuntime dmnRuntime = KieRuntimeFactory.of(defaultKieContainer.getKieBase("dynamo-drools-kjar-kbase"))
                .get(DMNRuntime.class);
        final DMNModel dmnModel = dmnRuntime.getModel(avgDMNNameSpace, avgDMNModelName);
        if (dmnModel != null) {
            if (dmnModel.hasErrors()) {
                System.out.println("Error while creating dmn" + dmnModel.getMessages());
            }
        } else {
            System.out.println("DMN NUll");

        }
        final DMNContext context = DMNFactory.newContext();

        dmnRuntime.addListener(createListener());

        context.set("Patient", patient);
        DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, context);

        String result = "";
        for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {
            System.out.println("Decision: '" + dr.getDecisionName() + "', " + "Result: " + dr.getResult());
            result = (String) dr.getResult();
        }

        logger.info("Leaving checkBpPatientCriticality()");
        return ResponseEntity.ok("Patient "+result);
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
}
