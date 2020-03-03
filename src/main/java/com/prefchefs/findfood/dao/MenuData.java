package com.prefchefs.findfood.dao;

import com.prefchefs.findfood.Meal;

import java.util.List;

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

}
