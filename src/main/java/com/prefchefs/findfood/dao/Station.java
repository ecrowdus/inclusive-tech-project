package com.prefchefs.findfood.dao;

import com.prefchefs.findfood.FoodStatus;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Station {

    private String name;
    private List<Dish> dishes;
    private FoodStatus foodStatus = FoodStatus.OK;

    public Station() {
    }

    public Station(String name, List<Dish> dishes) {
        this.name = name;
        this.dishes = dishes;
//        this.foodStatus = FoodStatus.OK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = StringUtils.capitalize(name);
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public FoodStatus getFoodStatus() {
        return foodStatus;
    }

    public void setFoodStatus(FoodStatus foodStatus) {
        this.foodStatus = foodStatus;
    }

}
