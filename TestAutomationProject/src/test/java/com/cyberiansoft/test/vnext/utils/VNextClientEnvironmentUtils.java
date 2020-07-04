package com.cyberiansoft.test.vnext.utils;

import com.cyberiansoft.test.vnext.config.VNextEnvironmentInfo;
import com.cyberiansoft.test.vnext.factories.environments.EnvironmentType;

public class VNextClientEnvironmentUtils {

    public static String getBackOfficeURL(EnvironmentType environmentType) {
        switch (environmentType) {
            case DEVELOPMENT:
                return VNextEnvironmentInfo.getInstance().getDevelopmentBackOfficeURL();
            case QC:
                return VNextEnvironmentInfo.getInstance().getIntegrationBackOfficeURL();
            case QC1:
                return VNextEnvironmentInfo.getInstance().getQC1BackOfficeURL();
            case QC4:
                return VNextEnvironmentInfo.getInstance().getQC4BackOfficeURL();
            case MANHEIM_UAT:
                return VNextEnvironmentInfo.getInstance().getManheimUATBackOfficeURL();
            default:
                return VNextEnvironmentInfo.getInstance().getIntegrationBackOfficeURL();
        }
    }

    public static String getEnvironmentString(EnvironmentType environmentType) {
        switch (environmentType) {
            case DEVELOPMENT:
                return VNextEnvironmentInfo.getInstance().getDevelopmentEnvString();
            case QC:
                return VNextEnvironmentInfo.getInstance().getIntegrationEnvString();
            case QC1:
                return VNextEnvironmentInfo.getInstance().getQC1EnvString();
            case QC4:
                return VNextEnvironmentInfo.getInstance().getQC4EnvString();
            case MANHEIM_UAT:
                return VNextEnvironmentInfo.getInstance().getManheimUATEnvString();
            default:
                return VNextEnvironmentInfo.getInstance().getIntegrationEnvString();
        }
    }
}
