package com.hyh.ioc_compile.processor;

/**
 * Created by zhuhean on 02/11/2017.
 */

final class Helper {

    private Helper() {
    }

    static String toCamelCase(String s) {
        if (s == null || s.length() < 1) return s;
        char firstChar = s.charAt(0);
        char newFirst = Character.toLowerCase(firstChar);
        return s.replace(firstChar, newFirst);
    }

}
