package com.cyberiansoft.test.vnext.data.r360pro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class VNextProTestCasesDataPaths {

    private static VNextProTestCasesDataPaths _instance = null;

    private Properties props = null;

    private VNextProTestCasesDataPaths() {
        props = new Properties();
        File file =
                new File("src/test/java/com/cyberiansoft/test/vnext/data/r360pro/r360protestcasesdatapath.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            props.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.out.println("Can't load VNext test cases data properties");
            e.printStackTrace();
        }
    }

    public synchronized static VNextProTestCasesDataPaths getInstance() {
        if (_instance == null)
            _instance = new VNextProTestCasesDataPaths();
        return _instance;
    }

    public String getCustomersTestCasesDataPath() {
        return props.getProperty("customers.td");
    }

    public String getPresetCustomerTestCasesDataPath() {
        return props.getProperty("preset.customer.td");
    }

    public String getApproveInspectionsTestCasesDataPath() {
        return props.getProperty("approve.inspections.td");
    }

    public String getArchiveInspectionsTestCasesDataPath() {
        return props.getProperty("archive.inspections.td");
    }

    public String getCalculationsTestCasesDataPath() {
        return props.getProperty("calculations.td");
    }

    public String getClaimInfoScreenTestCasesDataPath() {
        return props.getProperty("claim.info.screen.td");
    }

    public String getDraftInspectionsTestCasesDataPath() {
        return props.getProperty("draft.inspections.td");
    }

    public String getInspectionNotesTestCasesDataPath() {
        return props.getProperty("inspection.notes.td");
    }

    public String getInspectionsChangeCustomersTestCasesDataPath() {
        return props.getProperty("inspections.change.customer.td");
    }

    public String getInspectionsLineApprovalTestCasesDataPath() {
        return props.getProperty("inspections.line.approval.td");
    }

    public String getInspectionsTestCasesDataPath() {
        return props.getProperty("inspections.td");
    }

    public String getInspectionsServicesTestCasesDataPath() {
        return props.getProperty("inspections.services.td");
    }

    public String getLaborServiceTestCasesDataPath() {
        return props.getProperty("labor.service.td");
    }

    public String getSupplementsTestCasesDataPath() {
        return props.getProperty("supplements.td");
    }

    public String getVehicleInfoTestCasesDataPath() {
        return props.getProperty("vehicle.info.td");
    }

    public String getInvoiceEditingTestCasesDataPath() {
        return props.getProperty("invoice.editing.td");
    }

    public String getInvoicePaymentTestCasesDataPath() {
        return props.getProperty("invoice.payment.td");
    }

    public String getInvoicesEditWOInvoiceTestCasesDataPath() {
        return props.getProperty("invoices.edit.wo.invoice.td");
    }

    public String getInvoicesTestCasesDataPath() {
        return props.getProperty("invoices.td");
    }

    public String getCreateMultipleWOFromInspectionTestCasesDataPath() {
        return props.getProperty("create.multiple.wo.from.inspection.td");
    }

    public String getChangeDepartment() {
        return props.getProperty("change.department");
    }

    public String getDraftWorkOrdersTestCasesDataPath() {
        return props.getProperty("draft.wo.td");
    }

    public String getWorkOrdersChangeCustomerTestCasesDataPath() {
        return props.getProperty("workorders.change.customer.td");
    }

    public String getWorkOrdersListTestCasesDataPath() {
        return props.getProperty("workorders.list.td");
    }

    public String getWorkOrdersTestCasesDataPath() {
        return props.getProperty("workorders.td");
    }

    public String getMonitoringBaseCaseDataPath() {
        return props.getProperty("monitoring.basic.flow");
    }

    public String getMonitoringTimeReportDataPath() {
        return props.getProperty("monitoring.timereport");
    }

    public String getTechSplitDataPath() {
        return props.getProperty("techsplit.basic.flow");
    }

    public String getTechSplitServiceDataPath() {
        return props.getProperty("techsplit.service.flow");
    }

    public String getPartServiceBasicCasesDataPath() {
        return props.getProperty("part.service.flow");
    }

    public String getPartServiceMatrixCasesDataPath() {
        return props.getProperty("part.service.matrix");
    }

    public String getPartServiceLaborCasesDataPath() {
        return props.getProperty("part.service.labor");
    }

    public String getPartServiceBundleCaseDataPath() {
        return props.getProperty("part.service.bundle");
    }

    public String getQuestionFormBasicCasesDataPath() {
        return props.getProperty("question.form.basic");
    }

    public String getVehiclePartsCasesDataPath() {
        return props.getProperty("vehicle.parts.basic");
    }

    public String getServiceMultipleOptionDataPath() {
        return props.getProperty("service.multiple.option");
    }

    public String getMonitoringRoActionMenu() {
        return props.getProperty("monitoring.action.menu");
    }

    public String getAutoAssignTech() {
        return props.getProperty("monitoring.autoassign.tech");
    }

    public String getVehicleOwnerSupportTestCasesDataPath() {
        return props.getProperty("wo.owner.support");
    }

    public String getWholesailCustomersTestCasesDataPath() {
        return props.getProperty("wholesale.customers");
    }

    public String getViewInspectionTestCasesDataPath() {
        return props.getProperty("inspections.view");
    }

    public String getEmailInspectionsTestCasesDataPath() {
        return props.getProperty("inspections.email");
    }

    public String getMonitoringSearchFiltersDataPath() {
        return props.getProperty("monitoring.search.filters");
    }

    public String getMonitoringLocationDataPath() {
        return props.getProperty("monitoring.location");
    }
}
