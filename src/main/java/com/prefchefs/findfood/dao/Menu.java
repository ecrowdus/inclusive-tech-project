package com.prefchefs.findfood.dao;

import com.prefchefs.findfood.DiningHall;

import java.util.Date;

public class Menu {

    private DiningHall diningHall;
    private MenuData menuData;
    private Date date;

    public Menu(DiningHall diningHall, MenuData menuData, Date date) {
        this.diningHall = diningHall;
        this.menuData = menuData;
        this.date = date;
    }

    public DiningHall getDiningHall() {
        return diningHall;
    }

    public void setDiningHall(DiningHall diningHall) {
        this.diningHall = diningHall;
    }

    public MenuData getMenuData() {
        return menuData;
    }

    public void setMenuData(MenuData menuData) {
        this.menuData = menuData;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
