package com.prefchefs.findfood.dao;

import com.prefchefs.findfood.FoodStatus;

import java.util.List;

public class Dish {

    private String name;
    private List<Ingredient> ingredients;
    private FoodStatus foodStatus = FoodStatus.OK;

    public Dish() {
    }

    public Dish(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
        this.foodStatus = FoodStatus.OK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public FoodStatus getFoodStatus() {
        return foodStatus;
    }

    public void setFoodStatus(FoodStatus foodStatus) {
        this.foodStatus = foodStatus;
    }

}
