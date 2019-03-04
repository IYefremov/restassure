package com.cyberiansoft.test.vnextbo.utils;

public class VNextPriceCalculations {

    public static String getPriceRepresentation(String price) {
        String formedprice = "";
        final String dollarsymbol = "$";
        final String floatingpart = ".00";
        if (price.contains(".")) {
            if (isDouble(price))
                formedprice = dollarsymbol + price;
        } else if (isInteger(price)) {
            formedprice = dollarsymbol + price + floatingpart;
        } else if (price.contains("$")) {
            formedprice = price;
        }
        return formedprice;
    }

    public static String getPercentageRepresentation(String percentage) {
        String formedpercentage = "";
        final String percentagesymbol = "%";
        final String floatingpart = ".000";
        if (percentage.contains(".")) {
            if (isDouble(percentage))
                formedpercentage = percentage + percentagesymbol;
        } else if (isInteger(percentage)) {
            formedpercentage = percentage + floatingpart + percentagesymbol;
        } else if (percentage.contains("%")) {
            formedpercentage = percentage;
        }
        return formedpercentage;
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
