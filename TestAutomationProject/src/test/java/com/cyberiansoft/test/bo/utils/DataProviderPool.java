package com.cyberiansoft.test.bo.utils;

import com.cyberiansoft.test.bo.config.BOConfigInfo;
import org.testng.annotations.DataProvider;

public class DataProviderPool {
    @DataProvider
    public static Object[][] getUserNameTestData() {
        return new String[][] {
                { BOConfigInfo.getInstance().getUserName() + "30" }
        };
    }

    @DataProvider
    public static Object[][] getUserData() {
        return new String[][] {
                { BOConfigInfo.getInstance().getUserName(), BOConfigInfo.getInstance().getUserPassword() }
        };
    }
}
