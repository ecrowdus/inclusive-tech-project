package com.prefchefs.findfood.dao;

import com.prefchefs.findfood.FoodStatus;
import com.prefchefs.findfood.Meal;
import com.prefchefs.findfood.RestrictionType;
import com.prefchefs.findfood.Util;
import com.prefchefs.findfood.exception.RestrictionNotFoundException;
import com.prefchefs.findfood.service.RestrictionsService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MenuData {

    private List<Station> breakfast;
    private List<Station> lunch;
    private List<Station> dinner;

    public List<Station> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(List<Station> breakfast) {
        this.breakfast = breakfast;
    }

    public List<Station> getLunch() {
        return lunch;
    }

    public void setLunch(List<Station> lunch) {
        this.lunch = lunch;
    }

    public List<Station> getDinner() {
        return dinner;
    }

    public void setDinner(List<Station> dinner) {
        this.dinner = dinner;
    }

    public List<Station> getMeal(Meal meal) {
        if(meal.equals(Meal.BREAKFAST)) return this.breakfast;
        else if(meal.equals(Meal.LUNCH)) return this.lunch;
        else if(meal.equals(Meal.DINNER)) return this.dinner;
        else return null;
    }

    public List<Station> getMealWithStatus(Meal meal, List<Restriction> restrictions, RestrictionsService restrictionsService) {
        List<Station> stations = getMeal(meal);
        Set<String> inedibleFoods = new HashSet<>();
        for(Restriction r : restrictions) {
            if(r.getType() == RestrictionType.ALLERGEN) {
                inedibleFoods.add(r.getName().toLowerCase());
            }
            else {
                try {
                    List<String> ingredients = restrictionsService.getIngredients(r.getName());
                    inedibleFoods.addAll(ingredients);
                }
                catch(RestrictionNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        for(Station s : stations) {
            boolean stationContainsInedible = false;
            boolean stationContainsOnlyInedible = true;

            for(Dish d : s.getDishes()) {
                FoodStatus dishStatus = FoodStatus.OK;
                for(Ingredient i : d.getIngredients()) {
                    if(Util.containsSomewhere(inedibleFoods, i.getName().toLowerCase())) {
                        i.setOkToEat(FoodStatus.INEDIBLE);
                        dishStatus = FoodStatus.INEDIBLE;
                        stationContainsInedible = true;
                    }
                }

                if(dishStatus == FoodStatus.OK) {
                    stationContainsOnlyInedible = false;
                }
                d.setFoodStatus(dishStatus);
            }

            if(stationContainsOnlyInedible) {
                s.setFoodStatus(FoodStatus.INEDIBLE);
            }
            else if(stationContainsInedible) {
                s.setFoodStatus(FoodStatus.POTENTIALLY);
            }
            else {
                s.setFoodStatus(FoodStatus.OK);
            }
        }

        return stations;
    }

}
