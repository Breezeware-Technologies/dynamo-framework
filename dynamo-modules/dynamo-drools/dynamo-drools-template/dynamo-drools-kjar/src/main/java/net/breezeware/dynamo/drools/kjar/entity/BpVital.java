package net.breezeware.dynamo.drools.kjar.entity;

import java.io.Serializable;

public class BpVital implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int systolic;

    private int diastolic;

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    @Override
    public String toString() {
        return "BpVital [systolic=" + systolic + ", diastolic=" + diastolic + "]";
    }

}
