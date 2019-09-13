package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.ios10_client.utils.PDFReader;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.interactions.GeneralWizardInteractions;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.steps.VehicleInfoScreenSteps;
import com.cyberiansoft.test.vnext.steps.WizardScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
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
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		if (!customersScreen.isCustomerExists(testcustomer)) {
			VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
			newCustomerScreen.createNewCustomer(testcustomer);
		} else
			customersScreen.selectCustomer(testcustomer);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		final String inspectionNumber = GeneralWizardInteractions.getObjectNumber();

		GeneralWizardInteractions.saveViaMenu();
		inspectionsScreen = new VNextInspectionsScreen();
		
		VNextEmailScreen emailScreen = inspectionsScreen.clickOnInspectionToEmail(inspectionNumber);
		NadaEMailService nadaEMailService = new NadaEMailService();
		emailScreen.sentToEmailAddress(nadaEMailService.getEmailId());
		emailScreen.sendEmail();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickBackButton();

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
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		if (!customersScreen.isCustomerExists(testcustomer)) {
			VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
			newCustomerScreen.createNewCustomer(testcustomer);
		} else
			customersScreen.selectCustomer(testcustomer);

        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		final String inspectionNumber = GeneralWizardInteractions.getObjectNumber();
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
		ScreenNavigationSteps.pressBackButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		selectedServicesScreen.openSelectedMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
		VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (VehiclePartData vehiclePartData : inspectionData.getVehiclePartsData()) {
			VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
			vehiclepartinfoscreen.clickScreenBackButton();
			vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		}
		ScreenNavigationSteps.pressBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
		
		VNextEmailScreen emailScreen = inspectionsScreen.clickOnInspectionToEmail(inspectionNumber);
		NadaEMailService nadaEMailService = new NadaEMailService();
		emailScreen.sentToEmailAddress(nadaEMailService.getEmailId());
		emailScreen.sendEmail();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickBackButton();

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
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		if (!customersScreen.isCustomerExists(testcustomer)) {
			VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
			newCustomerScreen.createNewCustomer(testcustomer);
		} else
			customersScreen.selectCustomer(testcustomer);

        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		final String inspectionNumber = GeneralWizardInteractions.getObjectNumber();
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
		ScreenNavigationSteps.pressBackButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextSelectedServicesScreen selectavailableServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		selectavailableServicesScreen.openSelectedMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
		VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (VehiclePartData vehiclePartData : inspectionData.getVehiclePartsData()) {
			vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
			AppiumUtils.clickHardwareBackButton();

		}
		AppiumUtils.clickHardwareBackButton();
		selectavailableServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = selectavailableServicesScreen.saveInspectionViaMenu();
		
		VNextEmailScreen emailScreen = inspectionsScreen.clickOnInspectionToEmail(inspectionNumber);
		NadaEMailService nadaEMailService = new NadaEMailService();
		emailScreen.sentToEmailAddress(nadaEMailService.getEmailId());
		emailScreen.sendEmail();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickBackButton();

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

	@Test(testName= "Test Case 67001:PRINT - Validate 'Quantity' column is shown on device printout form for money services", 
			description = "Validate 'Quantity' column is shown on device printout form for money services")
	public void testValidateQuantityColumnIsShownOnDevicePrintoutFormForMoneyServices() throws Exception {

		final String vinnumber = "TEST";
		
		final String[] moneyservices = { "Dent Repair", "Bumper Repair" };
		final String[] moneyservicesprices = { "10", "0.99" };
		final String[] moneyservicesquantities = { "0.01", "0.99" };
		final String[] moneyservicesamounts = { "$0.10", "$0.98" };
		final String total = "$1.08";
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		if (!customersScreen.isCustomerExists(testcustomer)) {
			VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
			newCustomerScreen.createNewCustomer(testcustomer);
		} else
			customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, vinnumber);
		final String inspectionNumber = GeneralWizardInteractions.getObjectNumber();
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectServices(moneyservices);
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (int i = 0; i < moneyservices.length; i++) {
			selectedServicesScreen.setServiceAmountValue(moneyservices[i], moneyservicesprices[i]);
			selectedServicesScreen.setServiceQuantityValue(moneyservices[i], moneyservicesquantities[i]);
		}
		
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
		//inspectionsScreen.switchToTeamInspectionsView();
		VNextEmailScreen emailScreen = inspectionsScreen.clickOnInspectionToEmail(inspectionNumber);
		NadaEMailService nadaEMailService = new NadaEMailService();
		emailScreen.sentToEmailAddress(nadaEMailService.getEmailId());
		emailScreen.sendEmail();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickBackButton();

		final String inspectionReportFileName = inspectionNumber + ".pdf";
		NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
				.withSubjectAndAttachmentFileName(inspectionNumber, inspectionReportFileName);
		Assert.assertTrue(nadaEMailService.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + inspectionNumber +
				" in mail box " + nadaEMailService.getEmailId() + ". At time " +
				LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
		nadaEMailService.deleteMessageWithSubject(inspectionNumber);
		File pdfdoc = new File(inspectionReportFileName);
		String pdftext = PDFReader.getPDFText(pdfdoc);
		for (int i = 0; i < moneyservices.length; i++) {
			Assert.assertTrue(pdftext.contains(moneyservices[i]));
			Assert.assertTrue(pdftext.contains(moneyservicesquantities[i]));
			Assert.assertTrue(pdftext.contains(moneyservicesamounts[i]));
		}
		Assert.assertTrue(pdftext.contains(total));
	}
}
