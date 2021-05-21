package net.breezeware.dynamo.drools.kjar.entity;

public class Customer {

    private CustomerType type;

    private String years;

    
    private int discount;

    public Customer(CustomerType type, String numOfYears) {
        super();
        this.type = type;
        this.years = numOfYears;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public enum CustomerType {
        INDIVIDUAL, BUSINESS;
    }
}
