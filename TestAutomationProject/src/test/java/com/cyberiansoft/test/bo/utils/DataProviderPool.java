package com.cyberiansoft.test.bo.utils;

import org.testng.annotations.DataProvider;

public class DataProviderPool {
    @DataProvider(name = "setUpData")
    public static Object[][] getSetUpData() {
        return new String[][] {
                { "http://capi.qc.cyberianconcepts.com", "olexandr.kramar@cyberiansoft.com", "test12345" }
        };
    }












//    @DataProvider(name = DELIVERY_OPTIONS)
//    public static Object[][] getDeliveryOptions() {
//        String STATE = RandomStringUtils.randomAlphabetic(7);
//        String CITY = RandomStringUtils.randomAlphanumeric(7);
//        String STREET = RandomStringUtils.randomAlphanumeric(7);
//        String STREET2 = RandomStringUtils.randomAlphanumeric(7);
//        String ZIP = RandomStringUtils.randomAlphanumeric(7);
//        String TELEPHONE = "+38(096)" + RandomStringUtils.randomNumeric(7);
//        String FULL_NAME = RandomStringUtils.randomAlphanumeric(7);
////        String NEW_ADDRESS = RandomStringUtils.randomAlphanumeric(7);
//        return new Object[][] {
//                { STATE, CITY, STREET, STREET2, ZIP, TELEPHONE, FULL_NAME }
//        };
//    }
//
//    @DataProvider(name = SHORT_DELIVERY_OPTIONS)
//    public static Object[][] getShortDeliveryOptions() {
//        String TELEPHONE = "+38(096)" + RandomStringUtils.randomNumeric(7);
//        String FULL_NAME = RandomStringUtils.randomAlphanumeric(7);
//        return new Object[][] {
//                { TELEPHONE, FULL_NAME }
//        };
//    }
}
