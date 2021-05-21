package net.breezeware.dynamo.drools.kjar.entity;

public class Year {

    private int experience;
    private int rating;

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Year(int experience) {
        this.experience = experience;
    }
}
