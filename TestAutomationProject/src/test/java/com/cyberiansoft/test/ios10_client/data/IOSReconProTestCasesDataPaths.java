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
            System.out.println("Can't load ReconPro Test Cases test cases data properties");
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

    public String getProdRegressionSuiteTestCasesDataPath() { return props.getProperty("prod.regression.suite.td"); }

    public String getDentWizardSuiteTestCasesDataPath() { return props.getProperty("dentwizard.td"); }

    public String getArchiveInspectionsTestCasesDataPath() { return props.getProperty("archive.inspections.td"); }

    public String getCreateInspectionsTestCasesDataPath() { return props.getProperty("create.inspections.td"); }

    public String getChangeCustomerForInspectionsTestCasesDataPath() { return props.getProperty("change.customer.inspections.td"); }

    public String getApproveInspectionsTestCasesDataPath() { return props.getProperty("approve.inspections.td"); }

    public String getDraftModeInspectionsTestCasesDataPath() { return props.getProperty("draftmode.inspections.td"); }

    public String getInspectionsNotesTestCasesDataPath() { return props.getProperty("inspections.notes.td"); }

    public String getCreateWOFromInspectionTestCasesDataPath() { return props.getProperty("inspections.createwo.td"); }

    public String getAcceptRejectInspectionTestCasesDataPath() { return props.getProperty("accept.reject.inspections.td"); }

    public String getSinglePageInspectionTestCasesDataPath() { return props.getProperty("singlepage.inspections.td"); }

    public String getInspectionsQFTestCasesDataPath() { return props.getProperty("inspections.qf.td"); }

    public String getInspectionsValidationsTestCasesDataPath() { return props.getProperty("inspections.validations.td"); }

    public String getInspectionsServiceGroupingTestCasesDataPath() { return props.getProperty("inspections.servicegrouping.td"); }

    public String getCreateWorkOrdersTestCasesDataPath() { return props.getProperty("create.workorders.td"); }

    public String getApproveWorkOrdersTestCasesDataPath() { return props.getProperty("approve.workorders.td"); }

    public String getAutoSaveWorkOrdersTestCasesDataPath() { return props.getProperty("autosave.workorders.td"); }

    public String getWorkOrdersCalculationsTestCasesDataPath() { return props.getProperty("workorders.calc.td"); }

    public String getWorkOrdersTechSplitTestCasesDataPath() { return props.getProperty("workorders.techsplit.td"); }

    public String getServiceRequestsCreateWOAndInspectionTestCasesDataPath() { return props.getProperty("sr.createwoandinsp.td"); }

    public String getServiceRequestsCreateAppointmentTestCasesDataPath() { return props.getProperty("sr.createappointment.td"); }

    public String getServiceRequestsCheckInTestCasesDataPath() { return props.getProperty("sr.checkin.td"); }

    public String getServiceRequestsRejectAcceptTestCasesDataPath() { return props.getProperty("sr.rejectaccept.td"); }

    public String getServiceRequestsCloseTestCasesDataPath() { return props.getProperty("sr.close.td"); }

    public String getServiceRequestsCreateTestCasesDataPath() { return props.getProperty("sr.create.td"); }

    public String getServiceRequestsStatusReasonTestCasesDataPath() { return props.getProperty("sr.statusreason.td"); }

    public String getInvoiceApproveTestCasesDataPath() { return props.getProperty("invoice.approve.td"); }

    public String getInvoicePrintTestCasesDataPath() { return props.getProperty("invoice.print.td"); }

    public String getInvoiceEmailTestCasesDataPath() { return props.getProperty("invoice.email.td"); }

    public String getInvoiceCreateTestCasesDataPath() { return props.getProperty("invoice.create.td"); }

    public String getInvoicePaymentTestCasesDataPath() { return props.getProperty("invoice.payment.td"); }

    public String getOrderMonitorTestCasesDataPath() { return props.getProperty("ordermonitor.td"); }

    public String getAssignTechToWOServicesTestCasesDataPath() { return props.getProperty("assigntech.woservices.td"); }

    public String getCustomersCRUDTestCasesDataPath() { return props.getProperty("customers.crud.td"); }

    public String getWorkOrdersChangeCustomerTestCasesDataPath() { return props.getProperty("workorders.changecustomer.td"); }

    public String getCreateInspectionfromWOTestCasesDataPath() { return props.getProperty("inspections.createfromwo.td"); }

    public String getInvoiceChangeCustomerTestCasesDataPath() { return props.getProperty("invoice.changecustomer.td"); }

    public String getWorkOrdersBillingTestCasesDataPath() { return props.getProperty("workorders.billing.td"); }

    public String getWorkOrdersServicesTestCasesDataPath() { return props.getProperty("workorders.services.td"); }

    public String getCarHistoryTestCasesDataPath() { return props.getProperty("carhistory.td"); }

    public String getDeviceRegistrationAndUpdateCasesDataPath() { return props.getProperty("deviceregistration.td"); }
}
