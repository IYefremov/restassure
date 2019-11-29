package com.cyberiansoft.test.vnextbo.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class VNextBOTestCasesDataPaths {

    private static VNextBOTestCasesDataPaths instance = null;

    private Properties properties;

    private VNextBOTestCasesDataPaths() {
        properties = new Properties();
        File file = new File("src/test/java/com/cyberiansoft/test/vnextbo/config/vnextBOTestCasesDataPath.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            properties.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.out.println("Can't load VNext BO test data properties");
            e.printStackTrace();
        }
    }

    public synchronized static VNextBOTestCasesDataPaths getInstance() {
        if (instance == null)
            instance = new VNextBOTestCasesDataPaths();
        return instance;
    }

    public String getMonitorAdvancedSearchTD() {
        return properties.getProperty("vnextbo.monitor.advanced.search.td");
    }

    public String getMonitorReportProblemTD() {
        return properties.getProperty("vnextbo.monitor.report.problem.td");
    }

    public String getMonitorSimpleSearchTD() {
        return properties.getProperty("vnextbo.monitor.simple.search.td");
    }

    public String getMonitorTD() {
        return properties.getProperty("vnextbo.monitor.td");
    }

    public String getServicesPartsAndLaborServicesTD() {
        return properties.getProperty("vnextbo.services.parts.and.labor.services.td");
    }

    public String getServicesMoneyAndPercentageTD() {
        return properties.getProperty("vnextbo.services.money.and.percentage.td");
    }

    public String getQuickNotesTD() {
        return properties.getProperty("vnextbo.quick.notes.td");
    }

    public String getPartsManagementOrderDetailsTD() {
        return properties.getProperty("vnextbo.parts.management.order.details.td");
    }

    public String getPartsManagementSearchTD() {
        return properties.getProperty("vnextbo.parts.management.search.td");
    }

    public String getPartsManagementTD() {
        return properties.getProperty("vnextbo.parts.management.dashboard.td");
    }

    public String getSmokeTD() {
        return properties.getProperty("vnextbo.smoke.td");
    }

    public String getUsersAddingNewUserAndSearchTD() {
        return properties.getProperty("vnextbo.users.add.new.user.and.search.td");
    }

    public String getUsersTD() {
        return properties.getProperty("vnextbo.users.td");
    }

    public String getBreadcrumbTD() {
        return properties.getProperty("vnextbo.breadcrumb.td");
    }

    public String getWebServicesTD() {
        return properties.getProperty("vnextbo.webservices.td");
    }

    public String getClientsAddNewClientTD() {
        return properties.getProperty("vnextbo.clients.add.new.client.td");
    }

    public String getClientsArchiveRestoreTD() {
        return properties.getProperty("vnextbo.clients.archive.restore.td");
    }

    public String getClientsServicesTD() {
        return properties.getProperty("vnextbo.clients.services.td");
    }

    public String getClientsEditClientTD() {
        return properties.getProperty("vnextbo.clients.edit.client.td");
    }

    public String getClientsTD() {
        return properties.getProperty("vnextbo.clients.td");
    }

    public String getClientsSearchTD() {
        return properties.getProperty("vnextbo.clients.search.td");
    }

    public String getDeviceManagementTD() {
        return properties.getProperty("vnextbo.device.management.td");
    }

    public String getDeviceManagementSearchTD() {
        return properties.getProperty("vnextbo.device.management.search.td");
    }

    public String getDeviceManagementActiveAndPendingRegistrationTabsTD() {
        return properties.getProperty("vnextbo.device.management.active.and.pending.registration.tabs.td");
    }

    public String getHomePageTD() {
        return properties.getProperty("vnextbo.home.page.td");
    }

    public String getHomePageAccessRightsTD() {
        return properties.getProperty("vnextbo.home.page.access.rights.td");
    }

    public String getInspectionsApproveTD() {
        return properties.getProperty("vnextbo.inspections.approve.td");
    }

    public String getInspectionsArchivingTD() {
        return properties.getProperty("vnextbo.inspections.archiving.td");
    }

    public String getInspectionsDetailsTD() {
        return properties.getProperty("vnextbo.inspections.details.td");
    }

    public String getInspectionsTD() {
        return properties.getProperty("vnextbo.inspections.td");
    }

    public String getInspectionsSearchTD() {
        return properties.getProperty("vnextbo.inspections.search.td");
    }

    public String getInvoicesDetailsTD() {
        return properties.getProperty("vnextbo.invoices.details.td");
    }

    public String getInvoicesTD() {
        return properties.getProperty("vnextbo.invoices.td");
    }

    public String getLoginForgotPasswordTD() {
        return properties.getProperty("vnextbo.login.forgot.password.td");
    }

    public String getLoginTD() {
        return properties.getProperty("vnextbo.login.td");
    }
}