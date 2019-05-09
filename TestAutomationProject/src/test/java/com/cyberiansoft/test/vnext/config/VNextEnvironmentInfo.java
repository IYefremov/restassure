package com.cyberiansoft.test.vnext.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class VNextEnvironmentInfo {

    private static VNextEnvironmentInfo _instance = null;

    private Properties props = null;

    private VNextEnvironmentInfo() {
        props = new Properties();
        File file =
                new File("src/test/java/com/cyberiansoft/test/vnext/config/vnextenvironment.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            props.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.out.println("Can't load VNext environment properties");
            e.printStackTrace();
        }
    }

    public synchronized static VNextEnvironmentInfo getInstance() {
        if (_instance == null)
            _instance = new VNextEnvironmentInfo();
        return _instance;
    }

    public String getEnvironmentType() {
        return props.getProperty("environment.type");
    }

    public String getBuildProductionAttribute() {
        return props.getProperty("build.production");
    }

    public String getReportFolderPath() {
        return props.getProperty("report.folder");
    }

    public String getReportFileName() { return props.getProperty("report.filename"); }

    public boolean installNewBuild() {
        boolean installNewBuild = false;
        String newBuild= props.getProperty("new.build");
        if (newBuild.equalsIgnoreCase("true"))
            installNewBuild = true;
        return installNewBuild;
    }
}
