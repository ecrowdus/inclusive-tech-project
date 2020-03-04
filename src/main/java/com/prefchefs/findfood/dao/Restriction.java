package com.prefchefs.findfood.dao;

import com.prefchefs.findfood.RestrictionType;

import java.util.List;

public class Restriction {

    private RestrictionType type;
    private String name;

    public Restriction() {

    }

    public Restriction(String name, RestrictionType type) {
        this.name = name;
        this.type = type;
    }


    public RestrictionType getType() {
        return type;
    }

    public void setType(RestrictionType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static boolean listContainsRestriction(List<Restriction> list, String str) {
        for(Restriction r : list) {
            if(r.getType() == RestrictionType.RESTRICTION && r.getName().equalsIgnoreCase(str)) return true;
        }

        return false;
    }

}
