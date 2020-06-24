package net.breezeware.dynamo.audit.aspectj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface for an Auditable annotation. This annotation can be used on methods
 * that should be included for auditing.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auditable {

    /**
     * Annotation's attribute for specifying the action or event that the method
     * performs.
     * 
     * @return String value of the event being audited
     */
    String event();

    /**
     * Annotation's attribute for specifying the method's argument names. This is a
     * Comma Separated Value (CSV) string. It can be left "" if there are no method
     * arguments.
     * 
     * @return Method argument's names.
     */
    String argNames();
}