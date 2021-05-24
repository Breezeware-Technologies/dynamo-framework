package net.breezeware.dynamo.drools.kjar.entity;

public class Course {

    private long id;

    private String name;

    private double price;

    private String specialCourse;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSpecialCourse() {
        return specialCourse;
    }

    public void setSpecialCourse(String specialCourse) {
        this.specialCourse = specialCourse;
    }

}
