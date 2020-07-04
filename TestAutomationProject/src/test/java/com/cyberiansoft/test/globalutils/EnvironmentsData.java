package com.cyberiansoft.test.globalutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvironmentsData {
    private static EnvironmentsData _instance = null;

    private Properties props = null;

    private EnvironmentsData() {
        props = new Properties();
        File file =
                new File("src/test/java/com/cyberiansoft/test/vnextbo/config/environments.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            props.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.out.println("Can't load project environment properties");
            e.printStackTrace();
        }
    }

    public synchronized static EnvironmentsData getInstance() {
        if (_instance == null)
            _instance = new EnvironmentsData();
        return _instance;
    }

    public String getVNextIntegrationBackOfficeURL() { return props.getProperty("vnext.qc.bo"); }

    public String getVNextQC1BackOfficeURL() { return props.getProperty("vnext.qc1.bo"); }

    public String getVNextDevBackOfficeURL() { return props.getProperty("vnext.dev.bo"); }
}
