package net.breezeware.dynamo.drools.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.breezeware.dynamo.drools.kjar.entity.Person;
import net.breezeware.dynamo.drools.service.DroolsService;

@Component
@Path("droolsws")
public class DroolsWs {

    Logger logger = LoggerFactory.getLogger(DroolsWs.class);

    @Autowired
    DroolsService droolsService;

    @GET
    @Path("/sampleperson")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getSamplePerson() {
        return new Person("Karthik", 34, "Male");
    }

//    @POST
//    @Consumes({ MediaType.APPLICATION_JSON })
//    @Path("/medicaladvice")
//    @Produces(MediaType.APPLICATION_JSON)
//    @ApiOperation(value = "Gets medical advice for a person.", response = MedicalAdvice.class)
//    public MedicalAdvice getMedicalAdvice(Person person) {
//        MedicalAdvice medAdvice = droolsService.getMedicalAdvice(person);
//
//        logger.info("Medical advice = " + medAdvice);
//
//        return medAdvice;
//    }
}