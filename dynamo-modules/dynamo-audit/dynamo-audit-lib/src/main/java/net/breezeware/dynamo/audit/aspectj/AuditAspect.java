package net.breezeware.dynamo.audit.aspectj;

import java.lang.reflect.Modifier;
import java.time.Instant;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.breezeware.dynamo.audit.DynamoAuditConfigProperties;
import net.breezeware.dynamo.audit.entity.AuditItem;
import net.breezeware.dynamo.audit.service.api.AuditService;
import net.breezeware.dynamo.util.DynamoAppBootstrapBean;

/**
 * AspectJ class to implement auditing functionality. It contains JointPoints
 * and Advices that can be applied on methods being audited.
 */
@Aspect
@Component
public class AuditAspect {

    @Autowired
    AuditService auditService;

    @Autowired
    DynamoAuditConfigProperties dynamoAuditConfigProperties;

    @Autowired
    DynamoAppBootstrapBean dynamoAppBootstrapBean;

    // @Autowired
    // private HttpSession session;

    Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    /**
     * Creates an AuditItem entity by inspecting the Object value returned from the
     * AspectJ advised method. The Audit Item entity is later stored in the DB.
     * @param jp        JointPoint
     * @param auditable Auditable
     * @param retVal    Object
     */
    @AfterReturning(value = "@annotation(auditable)", returning = "retVal")
    public void logAuditActivity(JoinPoint jp, Auditable auditable, Object retVal) {
        logger.info(
                "Entering logAuditActivity(). JointPoint = {}. Auditable = {}. "
                        + "dynamoAuditConfigProperties.isEnableAuditing() = {}",
                jp, auditable, dynamoAuditConfigProperties.isEnableAuditing());

        if (dynamoAuditConfigProperties.isEnableAuditing()) {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();

            // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            // logger.info("auth inside audit = {}", auth);

            // logger.info("auth principal inside audit = {}", auth.getPrincipal());

            // CurrentUserDto currentUserDto = (CurrentUserDto)
            // session.getAttribute("currentUser");
            // String email = currentUserDto.getEmail();
            // long orgId = Long.valueOf(currentUserDto.getOrganizationId());

            String email = dynamoAppBootstrapBean.getCurrentUserEmail();
            long orgId = dynamoAppBootstrapBean.getCurrentUserOrganizationId();

            // create an audit item
            AuditItem auditItem = new AuditItem();
            String event = auditable.event();
            auditItem.setAuditUserEmail(email);
            auditItem.setOrganizationId(orgId);
            auditItem.setAuditDate(Instant.now());
            auditItem.setAuditEvent(event);
            auditItem.setCreatedDate(Calendar.getInstance());
            auditItem.setModifiedDate(Calendar.getInstance());

            // client details, IP address & protocol
            auditItem.setClientDetails(request.getHeader("User-Agent"));
            auditItem.setProtocol(request.getProtocol());
            String ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
            auditItem.setIpAddress(ipAddress);

            // populate input data
            String[] argNames = auditable.argNames().split(",");
            Object[] argValues = jp.getArgs();
            auditItem = populateItemInputData(argNames, argValues, auditItem);

            // populate output data
            // auditItem = populateItemOutputData(retVal, auditItem);

            // store the audit item entity in DB
            auditService.saveItem(auditItem);
            logger.info("Leaving logAuditActivity().");
        } else {
            logger.info("Leaving logAuditActivity(). Auditing is disabled. Not recording any data");
        }
    }

    /**
     * Populate the Audit Item's input data from method arguments and their values.
     * @param argNames  an array of arguments passed to the method being audited
     * @param argValues the value array corresponding to the arguments array
     * @param auditItem the entity to store the audit information for this method
     * @return AuditItem Audit Item populated with method's input/parameter values
     *         data
     */
    private AuditItem populateItemInputData(String[] argNames, Object[] argValues, AuditItem auditItem) {
        logger.info("Entering populateItemInputData()");

        JsonObject jsonObject = new JsonObject();

        if (argNames.length == argValues.length) {

            int index = 0;
            for (String name : argNames) {

                if (argValues[index] == null) {
                    jsonObject.addProperty(name, "");
                } else {
                    JsonParser jp = new JsonParser();
                    try {

                        Gson gson = new GsonBuilder()
                                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                                .serializeNulls().create();

                        JsonElement je = jp.parse(gson.toJson(argValues[index]));
                        if (je.isJsonObject()) {
                            jsonObject.add(name, je.getAsJsonObject());
                        } else if (je.isJsonArray()) {
                            jsonObject.add(name, je.getAsJsonArray());
                        } else {
                            jsonObject.addProperty(name, argValues[index].toString());
                        }
                    } catch (JsonSyntaxException e) {
                        logger.error("Exception occured. e = {}", e);
                        jsonObject.addProperty(name, argValues[index].toString());
                    }
                }
                index++;
            }
            auditItem.setAuditItemInputData(jsonObject.toString());
            logger.info("Leaving populateItemInputData()");
            return auditItem;
        } else {
            logger.info("Leaving populateItemInputData(). Arg names and Arg values "
                    + "counts do not match. Not setting input data.");
            return auditItem;
        }
    }

    // /**
    // * Populates the audit item's ID and output data fields from the Object
    // received
    // * from the Advised method. This is achieved through reflection.
    // * @param obj the object corresponding to the return value of the method
    // * being audited
    // * @param auditItem the entity to store the audit information for this method
    // * @return AuditItem Audit Item populated with method's output/return value
    // data
    // */
    // private AuditItem populateItemOutputData(Object obj, AuditItem auditItem) {
    // logger.info("Entering populateItemOutputData().");
    //
    // JsonObject jsonObject = new JsonObject();
    //
    // if (obj == null) {
    // logger.info("Leaving populateItemDetails(). Obj is null, not populating
    // AuditItem data.");
    // return auditItem;
    // } else {
    // Class<? extends Object> objClass = obj.getClass();
    // auditItem.setAuditItemType(objClass.getName());
    // try {
    // // item data
    //
    // for (Field field : objClass.getDeclaredFields()) {
    //
    // String modifierStr = Modifier.toString(field.getModifiers());
    //
    // // List<String> modNames = Arrays.asList(modifierStr.split("\\s"));
    //
    // JsonParser jp = new JsonParser();
    // if (modifierStr.indexOf("static") <= 0) {
    // // set accessible to true to handle private fields
    // field.setAccessible(true);
    //
    // Object fieldVal;
    // if (field.get(obj) == null) {
    // fieldVal = "";
    // } else {
    // fieldVal = field.get(obj);
    // }
    //
    // logger.debug("Field name = {}. Field value = {}. Field class = {}. Field type
    // = {}.",
    // field.getName(), fieldVal.toString(), field.getClass(), field.getType());
    //
    // if (field.getName().equalsIgnoreCase("id")) {
    // long idFieldValue = field.getLong(obj);
    // auditItem.setAuditItemId("" + idFieldValue);
    // } else if (field.getType().equals(Calendar.class)) {
    //
    // Gson gson = new GsonBuilder()
    // .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT,
    // Modifier.STATIC)
    // .serializeNulls().create();
    //
    // JsonElement je = jp.parse(gson.toJson(fieldVal));
    // jsonObject.add(field.getName(), je.getAsJsonObject());
    // } else {
    // try {
    // JsonElement je = jp.parse(fieldVal.toString());
    // if (je.isJsonObject()) {
    // jsonObject.add(field.getName(), je.getAsJsonObject());
    // } else if (je.isJsonArray()) {
    // jsonObject.add(field.getName(), je.getAsJsonArray());
    // } else {
    // jsonObject.addProperty(field.getName(), fieldVal.toString());
    // }
    //
    // } catch (Exception e) {
    // logger.error("Exception occured. e = {}", e);
    // jsonObject.addProperty(field.getName(), fieldVal.toString());
    // }
    // }
    // }
    // }
    // auditItem.setAuditItemOutputData(jsonObject.toString());
    //
    // } catch (Exception e) {
    // logger.error("Exception occured while creating audit item: " + e);
    // }
    //
    // logger.info("Leaving populateItemOutputData().");
    // return auditItem;
    // }
    // }
}