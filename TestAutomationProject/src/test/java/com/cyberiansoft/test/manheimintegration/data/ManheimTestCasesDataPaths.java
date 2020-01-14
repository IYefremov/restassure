package com.cyberiansoft.test.manheimintegration.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ManheimTestCasesDataPaths {

    private static ManheimTestCasesDataPaths _instance = null;

    private Properties props = null;

    private ManheimTestCasesDataPaths() {
        props = new Properties();
        File file =
                new File("src/test/java/com/cyberiansoft/test/manheimintegration/data/manheim.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            props.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.out.println("Can't load VNext test cases data properties");
            e.printStackTrace();
        }
    }

    public synchronized static ManheimTestCasesDataPaths getInstance() {
        if (_instance == null)
            _instance = new ManheimTestCasesDataPaths();
        return _instance;
    }

    public String getFindWorkOrdersTestCasesDataPath() {
        return props.getProperty("find.wos.td");
    }
}
