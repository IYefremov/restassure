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

    public String getMonitorAdvancedSearchNewTD() {
        return properties.getProperty("vnextbo.monitornew.advanced.search.td");
    }

    public String getMonitorGridTD() {
        return properties.getProperty("vnextbo.monitor.grid.td");
    }

    public String getMonitorBasicTD() {
        return properties.getProperty("vnextbo.monitor.basic.td");
    }

    public String getMonitorReportProblemTD() {
        return properties.getProperty("vnextbo.monitor.report.problem.td");
    }

    public String getMonitorSimpleSearchTD() {
        return properties.getProperty("vnextbo.monitor.simple.search.td");
    }

    public String getMonitorTimeReportingTD() {
        return properties.getProperty("vnextbo.monitor.time.reporting.td");
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

    public String getSRSearchTD() {
        return properties.getProperty("vnextbo.service.requests.search.td");
    }

    public String getSRLoadMoreTD() {
        return properties.getProperty("vnextbo.service.requests.load.more.td");
    }

    public String getSRAdvancedSearchTD() {
        return properties.getProperty("vnextbo.service.requests.advanced.search.td");
    }

    public String getPMOrderDetailsTD() {
        return properties.getProperty("vnextbo.parts.management.order.details.td");
    }

    public String getPMEnforceAddVendorInvoiceDocumentTD() {
        return properties.getProperty("vnextbo.parts.management.enforce.add.vendor.invoice.document.td");
    }

    public String getPMWithTurnedOnPunchOutAddOnTD() {
        return properties.getProperty("vnextbo.parts.management.with.turned.on.punchout.addon.td");
    }

    public String getPMWithTurnedOffPunchOutAddOnTD() {
        return properties.getProperty("vnextbo.parts.management.with.turned.off.punchout.addon.td");
    }

    public String getPMAddNewPartTD() {
        return properties.getProperty("vnextbo.parts.management.add.new.part.td");
    }

    public String getPMOrderDetailsActionsTD() {
        return properties.getProperty("vnextbo.parts.management.order.details.parts.actions.td");
    }

    public String getPartsManagementSearchTD() {
        return properties.getProperty("vnextbo.parts.management.search.td");
    }

    public String getPartsManagementTD() {
        return properties.getProperty("vnextbo.parts.management.dashboard.td");
    }

    public String getPMOrderDetailsStatusCheckBoxTD() {
        return properties.getProperty("vnextbo.parts.management.order.details.status.checkbox.td");
    }

    public String getPMOrderDetailsPartsDetailsStatusTD() {
        return properties.getProperty("vnextbo.parts.management.order.details.parts.details.status.td");
    }

    public String getPMGenericPartProviderFunctionalityTD() {
        return properties.getProperty("vnextbo.parts.management.generic.part.provider.functionality.td");
    }

    public String getPMOrderDetailsPartsDetailsLaborTD() {
        return properties.getProperty("vnextbo.parts.management.order.details.parts.details.labor.td");
    }

    public String getReportsTD() {
        return properties.getProperty("vnextbo.reports.td");
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