package com.cyberiansoft.test.ios10_client.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReconProIOSStageInfo {

    private static ReconProIOSStageInfo _instance = null;

    private Properties props = null;

    private ReconProIOSStageInfo() {
        props = new Properties();
        File file =
                new File("src/test/java/com/cyberiansoft/test/ios10_client/config/reconproiosstsgeenvironment.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            props.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.out.println("Can't load VNext environment properties");
            e.printStackTrace();
        }
    }

    public synchronized static ReconProIOSStageInfo getInstance() {
        if (_instance == null)
            _instance = new ReconProIOSStageInfo();
        return _instance;
    }

    public String getEnvironmentType() {
        return props.getProperty("environment.type");
    }

    public String getBackOfficeStagingURL() {
        return props.getProperty("backoffice.integration");
    }

    public String getBackOfficeDevelopmentURL() {
        return props.getProperty("backoffice.development");
    }

    public String getBackOfficeUATURL() {
        return props.getProperty("backoffice.uat");
    }

    public String getDentWizardBackOfficeStagingURL() {
        return props.getProperty("backofficedw.integration");
    }

    public String getDentWizardBackOfficeDevelopmentURL() {
        return props.getProperty("backofficedw.development");
    }



    public String getUserStageMail() {
        return props.getProperty("userstage.mail");
    }

    public String getUserStageUserName() {
        return props.getProperty("userstage.name");
    }

    public String getUserStageUserPassword() {
        return props.getProperty("userstage.password");
    }

    public String getReportFileName() {
        return props.getProperty("report.filename");
    }

    public String getReportFolder() {
        return props.getProperty("report.folder");
    }

    public String getDefaultBrowser() {
        return props.getProperty("selenium.browser");
    }

    public String getTestMail() {
        return props.getProperty("test.mail");
    }

    public String getTestMailPassword() {
        return props.getProperty("testmail.password");
    }

    public boolean installNewBuild() {
        boolean installNewBuild = false;
        String newBuild= props.getProperty("new.build");
        if (newBuild.equalsIgnoreCase("true"))
            installNewBuild = true;
        return installNewBuild;
    }
}
