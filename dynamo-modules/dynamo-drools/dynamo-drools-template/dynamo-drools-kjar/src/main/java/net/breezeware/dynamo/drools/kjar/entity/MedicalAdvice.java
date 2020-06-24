package net.breezeware.dynamo.drools.kjar.entity;

import java.io.Serializable;

public class MedicalAdvice implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String message;

    public MedicalAdvice() {
    }

    public MedicalAdvice(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Advice: { message =\"" + message + "\"" + " }";
    }
}