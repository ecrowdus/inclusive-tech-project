package com.prefchefs.findfood.exception;

public class RestrictionNotFoundException extends Exception {

    public RestrictionNotFoundException(String name) {
        super("Name: " + name);
    }

}
