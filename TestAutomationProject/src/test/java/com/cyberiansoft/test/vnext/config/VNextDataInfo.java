package com.cyberiansoft.test.vnext.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class VNextDataInfo {

    private static VNextDataInfo _instance = null;

    private Properties props = null;

    private VNextDataInfo() {
        props = new Properties();
        File file =
                new File("src/test/java/com/cyberiansoft/test/vnext/config/vnextdata.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            props.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.out.println("Can't load VNext environment properties");
            e.printStackTrace();
        }
    }

    public synchronized static VNextDataInfo getInstance() {
        if (_instance == null)
            _instance = new VNextDataInfo();
        return _instance;
    }

    public String getPathToAPIDataFiles() { return props.getProperty("pathto.apidata"); }

    public String getPathToDataFiles() { return props.getProperty("pathto.data"); }

    public String getDefaultRetaiCustomerDataFileName() { return props.getProperty("default.retailcustomer"); }

    public String getDefaultEmployeeDataFileName() { return props.getProperty("default.deviceemployee"); }

    public String getDefaultVehicleInfoDataFileName() { return props.getProperty("default.vehicleinfo"); }

    public String getDefaultDeviceInfoDataFileName() { return props.getProperty("default.deviceinfo"); }

    public String getDefaultCustomerDataFileName() { return props.getProperty("default.customerinfo"); }

    public String getDefaultInvoiceOrdersDataFileName() { return props.getProperty("default.invoiceorders"); }
}
