package com.cyberiansoft.test.vnext.utils;

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
        return price.equals(Double.valueOf(0));
    }
}
