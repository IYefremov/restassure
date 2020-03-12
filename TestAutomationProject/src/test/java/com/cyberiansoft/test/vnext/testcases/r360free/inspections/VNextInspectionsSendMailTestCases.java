package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.ios10_client.utils.PDFReader;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDateTime;

public class VNextInspectionsSendMailTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

    RetailCustomer testcustomer = new RetailCustomer("Customer", "MailInspection");
    final String customerstateShort = "CL";

    @BeforeMethod
    public void initTestCustomer() {
        testcustomer.setCompanyName("CompanyMailInspection");
        testcustomer.setMailAddress("test.cyberiansoft@gmail.com");
        testcustomer.setCustomerAddress1("Test Address Street, 1");
        testcustomer.setCustomerAddress2("Addreess2");
        testcustomer.setCustomerPhone("444-51-09");
        testcustomer.setCustomerCity("Lviv");
        testcustomer.setCustomerCountry("Mexico");
        testcustomer.setCustomerState("Colima");
        testcustomer.setCustomerZip("79031");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCorrectCustomerInfoIsShownOnPrinting(String rowID,
                                                               String description, JSONObject testData) throws Exception {
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);

        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EMAIL_INPSECTION);
        NadaEMailService nadaEMailService = new NadaEMailService();
        EmailSteps.sendEmail(nadaEMailService.getEmailId());
        ScreenNavigationSteps.pressBackButton();

        final String inspectionReportFileName = inspectionNumber + ".pdf";
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(inspectionNumber, inspectionReportFileName);
        Assert.assertTrue(nadaEMailService.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + inspectionNumber +
                " in mail box " + nadaEMailService.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nadaEMailService.deleteMessageWithSubject(inspectionNumber);
        File pdfdoc = new File(inspectionReportFileName);
        String pdftext = PDFReader.getPDFText(pdfdoc);
        Assert.assertTrue(pdftext.contains(testcustomer.getFullName()));
        Assert.assertTrue(pdftext.contains(testcustomer.getCustomerAddress1()));
        Assert.assertTrue(pdftext.contains(testcustomer.getCustomerAddress2()));
        Assert.assertTrue(pdftext.contains(testcustomer.getCustomerCity()));
        Assert.assertTrue(pdftext.contains(testcustomer.getCustomerZip()));
        Assert.assertTrue(pdftext.contains(", " + customerstateShort));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyBackButtonDoesntSaveInfoForVehiclePartPrinting(String rowID,
                                                                         String description, JSONObject testData) throws Exception {
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
        AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
        ScreenNavigationSteps.pressBackButton();
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.openServiceDetails(matrixServiceData.getMatrixServiceName());
        for (VehiclePartData vehiclePartData : inspectionData.getVehiclePartsData()) {
			VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
            vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
            ScreenNavigationSteps.pressBackButton();
        }
        ScreenNavigationSteps.pressBackButton();
        final String inspectionNumber = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EMAIL_INPSECTION);
        NadaEMailService nadaEMailService = new NadaEMailService();
        EmailSteps.sendEmail(nadaEMailService.getEmailId());
        ScreenNavigationSteps.pressBackButton();

        final String inspectionReportFileName = inspectionNumber + ".pdf";
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(inspectionNumber, inspectionReportFileName);
        Assert.assertTrue(nadaEMailService.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + inspectionNumber +
                " in mail box " + nadaEMailService.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nadaEMailService.deleteMessageWithSubject(inspectionNumber);
        File pdfdoc = new File(inspectionReportFileName);
        String pdftext = PDFReader.getPDFText(pdfdoc);
        Assert.assertTrue(pdftext.contains(testcustomer.getFullName()));
        Assert.assertTrue(pdftext.contains(testcustomer.getCustomerAddress1()));
        Assert.assertTrue(pdftext.contains(testcustomer.getCustomerAddress2()));
        Assert.assertTrue(pdftext.contains(testcustomer.getCustomerCity()));
        Assert.assertTrue(pdftext.contains(testcustomer.getCustomerZip()));
        Assert.assertTrue(pdftext.contains(", " + customerstateShort));
        Assert.assertTrue(pdftext.contains(matrixServiceData.getMatrixServiceName()));
        for (VehiclePartData vehiclePartData : inspectionData.getVehiclePartsData())
            Assert.assertFalse(pdftext.contains(vehiclePartData.getVehiclePartName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyHardwareBackButtonDoesntSaveInfoForVehiclePartPrinting(String rowID,
                                                                                 String description, JSONObject testData) throws Exception {
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
        AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
        ScreenNavigationSteps.pressBackButton();
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.openServiceDetails(matrixServiceData.getMatrixServiceName());
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        for (VehiclePartData vehiclePartData : inspectionData.getVehiclePartsData()) {
            vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
            ScreenNavigationSteps.pressBackButton();

        }
        ScreenNavigationSteps.pressBackButton();
        final String inspectionNumber = InspectionSteps.saveInspection();

		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.EMAIL_INPSECTION);
        NadaEMailService nadaEMailService = new NadaEMailService();
		EmailSteps.sendEmail(nadaEMailService.getEmailId());
		ScreenNavigationSteps.pressBackButton();

        final String inspectionReportFileName = inspectionNumber + ".pdf";
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(inspectionNumber, inspectionReportFileName);
        Assert.assertTrue(nadaEMailService.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + inspectionNumber +
                " in mail box " + nadaEMailService.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nadaEMailService.deleteMessageWithSubject(inspectionNumber);
        File pdfdoc = new File(inspectionReportFileName);
        String pdftext = PDFReader.getPDFText(pdfdoc);
        Assert.assertTrue(pdftext.contains(testcustomer.getFullName()));
        Assert.assertTrue(pdftext.contains(testcustomer.getCustomerAddress1()));
        Assert.assertTrue(pdftext.contains(testcustomer.getCustomerAddress2()));
        Assert.assertTrue(pdftext.contains(testcustomer.getCustomerCity()));
        Assert.assertTrue(pdftext.contains(testcustomer.getCustomerZip()));
        Assert.assertTrue(pdftext.contains(", " + customerstateShort));
        Assert.assertTrue(pdftext.contains(matrixServiceData.getMatrixServiceName()));
        for (VehiclePartData vehiclePartData : inspectionData.getVehiclePartsData())
            Assert.assertFalse(pdftext.contains(vehiclePartData.getVehiclePartName()));
    }
}
