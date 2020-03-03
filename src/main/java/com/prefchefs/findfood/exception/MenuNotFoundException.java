package com.prefchefs.findfood.exception;

import com.prefchefs.findfood.DiningHall;

import java.util.Date;

public class MenuNotFoundException extends Exception {

    public MenuNotFoundException(DiningHall diningHall, Date date) {
        super("Dining Hall: " + diningHall + ", date: " + date.toString());
    }

}
