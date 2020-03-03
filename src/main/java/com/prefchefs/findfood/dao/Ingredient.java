package com.prefchefs.findfood.dao;

import com.prefchefs.findfood.FoodStatus;

public class Ingredient {

    private String name;
    private FoodStatus foodStatus = FoodStatus.OK;

    private Ingredient(String name) {
        this.name = name;
//        this.foodStatus = FoodStatus.OK;
    }

    public Ingredient(String name, FoodStatus foodStatus) {
        this.name = name;
        this.foodStatus = foodStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodStatus getFoodStatus() {
        return foodStatus;
    }

    public void setOkToEat(FoodStatus foodStatus) {
        this.foodStatus = foodStatus;
    }

}
