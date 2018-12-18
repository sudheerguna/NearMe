package com.product.nearme.utils;

public class constant {
    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim().length() <= 0);
    }

}
