package com.prefchefs.findfood;

public enum DiningHall {

    CATHEY("Cathey"),
    BARTLETT("Barlett"),
    BAKER("Baker");

    private String name;

    DiningHall(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

}
