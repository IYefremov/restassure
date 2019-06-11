package com.cyberiansoft.test.ios10_client.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class IOSReconProTestCasesDataPaths {

    private static IOSReconProTestCasesDataPaths _instance = null;

    private Properties props = null;

    private IOSReconProTestCasesDataPaths() {
        props = new Properties();
        File file =
                new File("src/test/java/com/cyberiansoft/test/ios10_client/data/iosreconprotestcasesdatapaths.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            props.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.out.println("Can't load Calculations test cases data properties");
            e.printStackTrace();
        }
    }

    public synchronized static IOSReconProTestCasesDataPaths getInstance() {
        if (_instance == null)
            _instance = new IOSReconProTestCasesDataPaths();
        return _instance;
    }

    public String getCalculationsTestCasesDataPath() { return props.getProperty("calculations.td"); }

    public String getGeneralSuiteTestCasesDataPath() { return props.getProperty("general.suite.td"); }
}
