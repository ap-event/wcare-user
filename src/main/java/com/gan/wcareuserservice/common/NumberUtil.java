package com.gan.wcareuserservice.common;

public class NumberUtil {

    public static String add(String number1, int number2) {
        int value = stringToInt(number1) + number2;
        String result = String.valueOf(value);
        return result;
    }

    public static int stringToInt(String number1) {
        int result = 0;
        try{
            result = Integer.parseInt(number1);
        } catch (Exception e) {
        }
        return result;
    }



}
