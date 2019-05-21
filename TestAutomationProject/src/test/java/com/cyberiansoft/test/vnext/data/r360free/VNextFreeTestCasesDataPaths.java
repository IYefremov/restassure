package com.cyberiansoft.test.vnext.data.r360free;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class VNextFreeTestCasesDataPaths {

    private static VNextFreeTestCasesDataPaths _instance = null;

    private Properties props = null;

    private VNextFreeTestCasesDataPaths() {
        props = new Properties();
        File file =
                new File("src/test/java/com/cyberiansoft/test/vnext/data/r360free/r360freetestcasesdatapath.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            props.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.out.println("Can't load VNext test cases data properties");
            e.printStackTrace();
        }
    }

    public synchronized static VNextFreeTestCasesDataPaths getInstance() {
        if (_instance == null)
            _instance = new VNextFreeTestCasesDataPaths();
        return _instance;
    }

    public String getCustomersTestCasesDataPath() { return props.getProperty("customers.td"); }

    public String getInspectionApproveOnBOTestCasesDataPath() { return props.getProperty("inspection.approve.on.bo.td"); }

    public String getInspectionServicesTestCasesDataPath() { return props.getProperty("inspection.services.td"); }

    public String getInspectionsNotessTestCasesDataPath() { return props.getProperty("inspections.notes.td"); }

    public String getInspectionsTestCasesDataPath() { return props.getProperty("inspections.td"); }

    public String getSyncInspectionsTestCasesDataPath() { return props.getProperty("sync.inspections.td"); }

    public String getCreateInvoiceFromMultipleWOsTestCasesDataPath() { return props.getProperty("create.invoice.from.multiple.wos.td"); }

    public String getInvoicesTestCasesDataPath() { return props.getProperty("invoices.td"); }

    public String getNavigationTestCasesDataPath() { return props.getProperty("navigation.td"); }

    public String getWorkOrdersTestCasesDataPath() { return props.getProperty("work.orders.td"); }


}
