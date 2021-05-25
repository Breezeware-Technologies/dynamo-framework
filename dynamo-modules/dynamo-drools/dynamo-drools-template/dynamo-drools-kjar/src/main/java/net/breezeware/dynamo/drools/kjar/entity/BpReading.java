package net.breezeware.dynamo.drools.kjar.entity;

public class BpReading {
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
        return "BpReading [systolic=" + systolic + ", diastolic=" + diastolic + "]";
    }
}
