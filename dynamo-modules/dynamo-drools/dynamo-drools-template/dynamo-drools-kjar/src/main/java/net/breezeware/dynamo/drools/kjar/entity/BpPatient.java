package net.breezeware.dynamo.drools.kjar.entity;

import java.io.Serializable;
import java.util.List;


public class BpPatient implements Serializable{

    private static final long serialVersionUID = 1L;
    private List<Integer> bpValueList;
    private String warningMessage;

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public List<Integer> getBpValueList() {
        return bpValueList;
    }

    public void setBpValueList(List<Integer> bpValueList) {
        this.bpValueList = bpValueList;
    }
}
