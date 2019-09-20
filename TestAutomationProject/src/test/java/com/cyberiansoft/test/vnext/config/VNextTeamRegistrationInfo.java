package com.cyberiansoft.test.vnext.config;

import com.cyberiansoft.test.vnext.factories.environments.EnvironmentType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class VNextTeamRegistrationInfo {

    private static VNextTeamRegistrationInfo _instance = null;

    private Properties props = null;

    private VNextTeamRegistrationInfo() {
        props = new Properties();
        File file =
                new File("src/test/java/com/cyberiansoft/test/vnext/config/vnextteamreginfo.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            props.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.out.println("Can't load VNext environment properties");
            e.printStackTrace();
        }
    }

    public synchronized static VNextTeamRegistrationInfo getInstance() {
        if (_instance == null)
            _instance = new VNextTeamRegistrationInfo();
        return _instance;
    }

    public String getBackOfficeStagingURL() {
        return props.getProperty("backoffice.development");
    }

    public String getBackOfficeIntegrationURL() {
        return props.getProperty("backoffice.integration");
    }

    public String getBackOfficeQC1URL() {
        return props.getProperty("backoffice.qc1");
    }

    public String getBackOfficeStagingUserName() {
        return props.getProperty("userstage.name");
    }

    public String getBackOfficeStagingUserPassword() {
        return props.getProperty("userstage.password");
    }

    public String getDeviceEmployeeSearchLicenseName() {
        return props.getProperty("employeesearchlicense.name");
    }

    public String getBackOfficeUrlFromEnvType(EnvironmentType environmentType) {
        switch (environmentType) {
            case DEVELOPMENT:
                return VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingURL();
            case INTEGRATION:
            case AUTOTESTS:
                return VNextTeamRegistrationInfo.getInstance().getBackOfficeIntegrationURL();
            case QC1:
                return VNextTeamRegistrationInfo.getInstance().getBackOfficeQC1URL();
            default:
                throw new RuntimeException("Cannot resolve url for env type:" + environmentType.getEnvironmentTypeName());
        }
    }

    public boolean isHelpPopupShown() {
        boolean installNewBuild = false;
        String newBuild= props.getProperty("popuphelp.show");
        if (newBuild.equalsIgnoreCase("true"))
            installNewBuild = true;
        return installNewBuild;
    }
}
