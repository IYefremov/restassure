package com.cyberiansoft.test.vnext.utils;

import java.util.Locale;

public class PricesUtils {

    public static Double getServicePriceValue(String servicePriceValue) {
        return Double.valueOf(servicePriceValue.
                replace("(", "").replace(")", "").
                replace("$", "").replace("%", "").replace(",", ""));
    }

    public static boolean isServicePriceEqualsZero(String servicePriceValue) {
        if (servicePriceValue.contains("x") || servicePriceValue.contains("Select Price Matrices"))
            return false;
        Double price = getServicePriceValue(servicePriceValue);
        return price.equals((double) 0);
    }

    public static String getFormattedServicePriceValue(float servicePrice) {
        return "$" + String.format(Locale.US, "%.2f", servicePrice);
    }
}
