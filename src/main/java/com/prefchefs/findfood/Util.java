package com.prefchefs.findfood;

import java.util.Set;

public class Util {

    public static boolean containsSomewhere(Set<String> set, String str) {
        str = str.toLowerCase();
        for(String setStr : set) {
            if(str.toLowerCase().contains(setStr)) return true;
        }

        return false;
    }

}
