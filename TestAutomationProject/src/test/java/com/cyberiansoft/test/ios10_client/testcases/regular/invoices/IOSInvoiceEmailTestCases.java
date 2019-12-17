package com.cyberiansoft.test.ios10_client.testcases.regular.invoices;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularApproveInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularInvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularOrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMyInvoicesScreenValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.PDFReader;
import com.cyberiansoft.test.ios10_client.utils.ScreenNamesConstants;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDateTime;

public class IOSInvoiceEmailTestCases extends IOSRegularBaseTestCase {

    @BeforeClass(description = "Invoice Email Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getInvoiceEmailTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenCustomerApprovalRequiredIsSetToONAutoEmailIsNotSentWhenApprovalDoesNotExist(String rowID,
                                                                                                              String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToCustomersScreen();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularNavigationSteps.navigateToScreen(ScreenNamesConstants.ZAYATS_TEST_PACK);
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();

        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularMyWorkOrdersSteps.waitMyWorkOrdersLoaded();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceForApprove(invoiceNumber);
        myInvoicesScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.clickApproveButton();
        approveInspectionsScreen.drawApprovalSignature();
        RegularNavigationSteps.navigateBackScreen();

        final String invpoicereportfilenname = invoiceNumber + ".pdf";

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(ReconProIOSStageInfo.getInstance().getTestMail());

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(invoiceNumber, invpoicereportfilenname);
        Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + invoiceNumber +
                " in mail box " + nada.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nada.deleteMessageWithSubject(invoiceNumber);

        File pdfdoc = new File(invpoicereportfilenname);
        String pdftext = PDFReader.getPDFText(pdfdoc);
        Assert.assertTrue(pdftext.contains(workOrderData.getVehicleInfoData().getVINNumber()));
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertTrue(pdftext.contains(serviceData.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenCustomerApprovalRequiredIsSetToOffAutoEmailIsSentWhenInvoiceAsAutoApproved(String rowID,
                                                                                                             String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToCustomersScreen();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularNavigationSteps.navigateToScreen(ScreenNamesConstants.ZAYATS_TEST_PACK);
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();

        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALOFF_INVOICETYPE);
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularMyWorkOrdersSteps.waitMyWorkOrdersLoaded();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
        RegularNavigationSteps.navigateBackScreen();


        final String invpoicereportfilenname = invoiceNumber + ".pdf";
        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(ReconProIOSStageInfo.getInstance().getTestMail());
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(invoiceNumber, invpoicereportfilenname);
        Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + invoiceNumber +
                " in mail box " + nada.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nada.deleteMessageWithSubject(invoiceNumber);

        File pdfdoc = new File(invpoicereportfilenname);
        String pdftext = PDFReader.getPDFText(pdfdoc);
        Assert.assertTrue(pdftext.contains(workOrderData.getVehicleInfoData().getVINNumber()));
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertTrue(pdftext.contains(serviceData.getServiceName()));
    }
}
