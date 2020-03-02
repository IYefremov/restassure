package com.cyberiansoft.test.ios10_client.utils;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;

public class PricesCalculations {

    public static String getPriceRepresentation(String price) {

        String formedprice = "";
        final String dollarsymbol = "$";
        final String floatingpart = ".00";
        if (price.contains(".")) {
            if (isDouble(price))
                formedprice = dollarsymbol + String.format("%,.2f", BackOfficeUtils.getServicePriceValue(price));
        } else if (isInteger(price)) {
            formedprice = dollarsymbol + String.format("%,.2f", BackOfficeUtils.getServicePriceValue(price + floatingpart));
        } else if (price.contains("$")) {
            formedprice = price;
        }
        return formedprice;
    }

    public static String getPercentageRepresentation(String percentage) {
        String formedPercentage = "";
        final String percentageSymbol = "%";
        final String floatingpart = ".00";
        if (percentage.contains(".")) {
            if (isDouble(percentage))
                formedPercentage = percentageSymbol + percentage;
        } else if (isInteger(percentage)) {
            formedPercentage = percentageSymbol + percentage + floatingpart;
        } else if (percentage.contains("%")) {
            formedPercentage = percentage;
        }
        return formedPercentage;
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }


}
