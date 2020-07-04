package com.cyberiansoft.test.vnextbo.utils;

import com.cyberiansoft.test.enums.TestEnvironments;
import com.cyberiansoft.test.globalutils.EnvironmentsData;

public class VNextEnvironmentUtils {

    public static String getBackOfficeURL(TestEnvironments testEnvironment) {
        switch (testEnvironment) {
            case DEV:
                return EnvironmentsData.getInstance().getVNextDevBackOfficeURL();
            case QC:
                return EnvironmentsData.getInstance().getVNextIntegrationBackOfficeURL();
            default:
                return EnvironmentsData.getInstance().getVNextIntegrationBackOfficeURL();
        }
    }
}
