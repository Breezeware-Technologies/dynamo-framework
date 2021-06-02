package net.breezeware.dynamo.drools.kjar.entity;

import java.io.Serializable;
import java.util.List;


public class BpPatient implements Serializable{

    private static final long serialVersionUID = 1L;
    private String patientName;
    private List<BpReading> bpValueList;
    private String warningMessage;

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public List<BpReading> getBpValueList() {
        return bpValueList;
    }

    public void setBpValueList(List<BpReading> bpValueList) {
        this.bpValueList = bpValueList;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
