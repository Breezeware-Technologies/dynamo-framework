package net.breezeware.dynamo.drools.kjar.entity;

import java.io.Serializable;
import java.util.List;

/**
 * used this model for Sarvanan's DMN BP Model
 * @author guru
 *
 */
public class Patient implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private List<BpVital> bpVitalList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<BpVital> getBpVitalList() {
        return bpVitalList;
    }

    public void setBpVitalList(List<BpVital> bpVitalList) {
        this.bpVitalList = bpVitalList;
    }

    @Override
    public String toString() {
        final int maxLen = 10;
        return "Patient [" + (name != null ? "name=" + name + ", " : "") + "age=" + age + ", "
                + (bpVitalList != null ? "bpVitalList=" + bpVitalList.subList(0, Math.min(bpVitalList.size(), maxLen))
                        : "")
                + "]";
    }

}
