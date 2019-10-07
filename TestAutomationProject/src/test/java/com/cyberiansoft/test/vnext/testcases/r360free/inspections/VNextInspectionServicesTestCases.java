package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServicePackagesWebPage;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.VehicleInfoScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextInspectionServicesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	@BeforeClass(description = "R360 Inspection Services Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getInspectionServicesTestCasesDataPath();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testShowSelectedServicesAfterInspectionIsSaved(String rowID,
															   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());

		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectServices(inspectionData.getServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
		final String inspNumber = availableServicesScreen.getNewInspectionNumber();
		inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();
		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspNumber);
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
		inspectionsScreen = availableServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testShowSelectedServicesForInspectionWhenNavigatingFromServicesScreen(String rowID,
																					  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectServices(inspectionData.getServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
		availableServicesScreen.swipeScreenRight();
		BaseUtils.waitABit(2000);
		availableServicesScreen.swipeScreenLeft();
		availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
		availableServicesScreen.swipeScreenRight();
		BaseUtils.waitABit(2000);
		availableServicesScreen.swipeScreenLeft();
		availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
		inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testAddOneServiceToAlreadySelectedServicesWhenInspectionIsEdited(String rowID,
																				 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

		availableServicesScreen.selectService(inspectionData.getServiceNameByIndex(0));
		availableServicesScreen.selectService(inspectionData.getServiceNameByIndex(1));
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getServiceNameByIndex(0)));
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getServiceNameByIndex(1)));
		final String inspNumber = selectedServicesScreen.getNewInspectionNumber();
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();

		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspNumber);
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getServiceNameByIndex(0)));
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getServiceNameByIndex(1)));

		availableServicesScreen = availableServicesScreen.switchToAvalableServicesView();
		availableServicesScreen.selectService(inspectionData.getServiceNameByIndex(2));
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

		Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getServiceNameByIndex(2)));
		inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();
		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspNumber);
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		;
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testAddSeveralServicesToAlreadySelectedServicesWhenInspectionIsEdited(String rowID,
																					  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int firstPartNumber = 2;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < firstPartNumber; i++)
			availableServicesScreen.selectService(inspectionData.getServiceNameByIndex(i));
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (int i = 0; i < firstPartNumber; i++)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getServiceNameByIndex(i)));
		final String inspNumber = selectedServicesScreen.getNewInspectionNumber();
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspNumber);
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (int i = 0; i < firstPartNumber; i++)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getServiceNameByIndex(i)));
		availableServicesScreen = availableServicesScreen.switchToAvalableServicesView();
		for (int i = firstPartNumber; i < inspectionData.getServicesList().size(); i++)
			availableServicesScreen.selectService(inspectionData.getServiceNameByIndex(i));
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspNumber);
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifySelectedServicesAreSavedToBO(String rowID,
													   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectServices(inspectionData.getServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
		final String inspNumber = availableServicesScreen.getNewInspectionNumber();
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
		inspectionsScreen.clickBackButton();
		BaseUtils.waitABit(30000);
		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginPage.userLogin(deviceuser, devicepsw);
		VNexBOLeftMenuPanel leftMenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionsWebPage = leftMenu.selectInspectionsMenu();
		inspectionsWebPage.selectInspectionInTheList(inspNumber);
		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertTrue(inspectionsWebPage.isServicePresentForSelectedInspection(service.getServiceName()));
		webdriver.quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyLettersIOQAreTrimmedWhileManualEntry(String rowID,
															   String description, JSONObject testData) {

		final String vinNumber = "AI0YQ56ONJ";
		final String redRGBColor = "rgba(239, 83, 78, 1)";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, vinNumber);
		VehicleInfoScreenValidations.vinValidationMessageShouldExist(true);


		VehicleInfoScreenValidations.vinValidationColorShouldBeEqual(redRGBColor);
		inspectionsScreen = vehicleInfoScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyNotAllowedCharactersAreTrimmedWhileManualEntry(String rowID,
																		 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String redRGBColor = "rgba(239, 83, 78, 1)";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		VehicleInfoScreenValidations.vinValidationMessageShouldExist(true);
		VehicleInfoScreenValidations.vinValidationColorShouldBeEqual(redRGBColor);
		inspectionsScreen = vehicleInfoScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyServicesAreSavedWhenSaveInspectionOptionWasUsedFromHumburgerMenu(String rowID,
																						   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectServices(inspectionData.getServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
		selectedServicesScreen.saveInspectionViaMenu();

		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspNumber);
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyVehicleClaimAreSavedWhenSaveInspectionOptionWasUsedFromHumburgerMenu(String rowID,
																							   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());

		final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
		final VehicleInfoData vehicleInfoData = inspectionData.getVehicleInfo();
		VehicleInfoScreenSteps.setVehicleInfo(vehicleInfoData);
		vehicleInfoScreen.swipeScreenLeft();
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspNumber);

		VehicleInfoScreenValidations.validateVehicleInfo(vehicleInfoData);

		vehicleInfoScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testExitCancelInspectionStateCalledFromHumburgerMenu_FirstStep(String rowID,
																			   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		vehicleInfoScreen.clickCancelMenuItem();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		String msg = informationDialog.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.CANCEL_CREATING_INSPECTION_ALERT);
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.swipeScreenLeft();
		vehicleInfoScreen.clickCancelMenuItem();
		new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.clickHardwareBackButton();
		AppiumUtils.clickHardwareBackButton();
		AppiumUtils.clickHardwareBackButton();
		informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		msg = informationDialog.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.CANCEL_CREATING_INSPECTION_ALERT);
		vehicleInfoScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testShowAllAssignedToServicePackageServicesAsAvailableOnes(String rowID,
																		   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginPage.userLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = new CompanyWebPage(webdriver);
		backofficeHeader.clickCompanyLink();
		ServicePackagesWebPage servicepckgspage = new ServicePackagesWebPage(webdriver);
		companypage.clickServicePackagesLink();
		String mainWindowHandle = webdriver.getWindowHandle();
		servicepckgspage.clickServicesLinkForServicePackage("All Services");
		List<WebElement> allServices = servicepckgspage.getAllServicePackageItems();
		List<String> allServicesTxt = new ArrayList<>();
		for (WebElement lst : allServices)
			allServicesTxt.add(lst.getText());
		servicepckgspage.closeNewTab(mainWindowHandle);
		webdriver.quit();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		List<WebElement> services = availableServicesScreen.getServicesListItems();
		List<String> servicesTxt = new ArrayList<>();
		for (WebElement lst : services)
			servicesTxt.add(availableServicesScreen.getServiceListItemName(lst));

		for (String srv : allServicesTxt) {
			Assert.assertTrue(servicesTxt.contains(srv));
		}
		AppiumUtils.clickHardwareBackButton();
		vehicleInfoScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyDefaultPriceForServiceIsShownCorrectly(String rowID,
																 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

		availableServicesScreen.selectServices(inspectionData.getMoneyServicesList());
		availableServicesScreen.selectServices(inspectionData.getPercentageServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

		for (ServiceData moneyService : inspectionData.getMoneyServicesList())
			Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(moneyService.getServiceName()), moneyService.getServicePrice());
		for (ServiceData percService : inspectionData.getPercentageServicesList())
			Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(percService.getServiceName()), percService.getServicePrice());

		selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testAddTheSameServiceMultipleTimes(String rowID,
												   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int servicesNumberSelected = 2;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectServices(inspectionData.getServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
		availableServicesScreen = selectedServicesScreen.switchToAvalableServicesView();
		availableServicesScreen.selectServices(inspectionData.getServicesList());
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertEquals(selectedServicesScreen.getQuantityOfSelectedService(service.getServiceName()), servicesNumberSelected);

		final String inspNumber = selectedServicesScreen.getNewInspectionNumber();
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();

		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspNumber);
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertEquals(selectedServicesScreen.getQuantityOfSelectedService(service.getServiceName()), servicesNumberSelected);

		inspectionsScreen = availableServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testEditInspectionServices(String rowID,
										   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String inspPrice = "$242.00";
		final String firstMoneyServicePrice = "5";
		final String secondMoneyServicePrice = "84.55";
		final String secondMoneyServiceQty = "9.15";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		final String inspNumberber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectServices(inspectionData.getServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			selectedServicesScreen.setServiceAmountValue(serviceData.getServiceName(), serviceData.getServicePrice());
		}
		Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspPrice);
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();

		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspNumberber);
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		selectedServicesScreen.uselectService(inspectionData.getServicesList().get(0).getServiceName());

		availableServicesScreen = selectedServicesScreen.switchToAvalableServicesView();
		availableServicesScreen.selectServices(inspectionData.getMoneyServicesList());
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

		for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
			selectedServicesScreen.setServiceAmountValue(serviceData.getServiceName(), serviceData.getServicePrice());
		}

		selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServicesList().get(0).getServiceName(),
				firstMoneyServicePrice);
		selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServicesList().get(1).getServiceName(),
				secondMoneyServicePrice);
		selectedServicesScreen.setServiceQuantityValue(inspectionData.getMoneyServicesList().get(1).getServiceName(),
				secondMoneyServiceQty);

		Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(),
				BackOfficeUtils.getFormattedServicePriceValue(BackOfficeUtils.getServicePriceValue("$1000.63")));
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyPriceMatrixAddedToServicePackageIsAvailableToChooseWhenAddEditInspection(String rowID,
																								   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		final MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
		VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(matrixServiceData.getVehiclePartData().getVehiclePartName());
		vehiclePartInfoScreen.selectVehiclePartSize(matrixServiceData.getVehiclePartData().getVehiclePartSize());
		vehiclePartInfoScreen.selectVehiclePartSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
		vehiclePartInfoScreen.clickSaveVehiclePartInfo();
		vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixServiceData.getMatrixServiceName()));
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyListOfAvailablePriceMatricesIsLoadedWhenChoosingMatrixService(String rowID,
																						String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
		VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(matrixServiceData.getVehiclePartData().getVehiclePartName());
		vehiclePartInfoScreen.selectVehiclePartSize(matrixServiceData.getVehiclePartData().getVehiclePartSize());
		vehiclePartInfoScreen.selectVehiclePartSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
		vehiclePartInfoScreen.clickSaveVehiclePartInfo();
		vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixServiceData.getMatrixServiceName()));
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyPriceMatrixNameIsShownOnSelectServicesScreenAfterSelection(String rowID,
																					 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
		VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(matrixServiceData.getVehiclePartData().getVehiclePartName());
		vehiclePartInfoScreen.selectVehiclePartSize(matrixServiceData.getVehiclePartData().getVehiclePartSize());
		vehiclePartInfoScreen.selectVehiclePartSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
		vehiclePartInfoScreen.clickSaveVehiclePartInfo();
		availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixServiceData.getMatrixServiceName()));
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyCorrectPriceIsShownInServicesListAfterEditingServicePricePercentage(String rowID,
																							  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		ServiceData percentageService = inspectionData.getPercentageServiceData();
		availableServicesScreen.selectService(percentageService.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(percentageService.getServiceName()));
		Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(percentageService.getServiceName()), percentageService.getServicePrice());
		selectedServicesScreen.setServiceAmountValue(percentageService.getServiceName(), percentageService.getServicePrice2());
		Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(percentageService.getServiceName()), percentageService.getServicePrice2() + "%");
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testEditServicePricePercentage(String rowID,
											   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		;
		availableServicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
		final ServiceData percentageService = inspectionData.getPercentageServiceData();
		availableServicesScreen.selectService(percentageService.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		selectedServicesScreen.setServiceAmountValue(percentageService.getServiceName(), percentageService.getServicePrice());
		Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(percentageService.getServiceName()), percentageService.getServicePrice2());
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testEditServicePriceMoney(String rowID,
										  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		final ServiceData moneyService = inspectionData.getMoneyServiceData();
		availableServicesScreen.selectService(moneyService.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		selectedServicesScreen.setServiceAmountValue(moneyService.getServiceName(), moneyService.getServicePrice());
		Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(moneyService.getServiceName()), moneyService.getServicePrice2());
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testDeleteServiceFromServicesScreen(String rowID,
													String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		String inspNumberber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectServices(inspectionData.getMoneyServicesList());
		availableServicesScreen.selectServices(inspectionData.getPercentageServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

		selectedServicesScreen.uselectService(inspectionData.getMoneyServicesList().get(0).getServiceName());
		selectedServicesScreen.uselectService(inspectionData.getPercentageServicesList().get(0).getServiceName());
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();

		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspNumberber);
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getMoneyServicesList().get(1).getServiceName()));
		Assert.assertFalse(selectedServicesScreen.isServiceSelected(inspectionData.getMoneyServicesList().get(0).getServiceName()));
		Assert.assertFalse(selectedServicesScreen.isServiceSelected(inspectionData.getPercentageServicesList().get(0).getServiceName()));
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionTotalPriceShouldChangeWhenUselectSomeOfTheSelectedServiceOnServicesScreen(String rowID,
																										String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String editedPrice = "$10.00";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectServices(inspectionData.getMoneyServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData serviceData : inspectionData.getMoneyServicesList())
			selectedServicesScreen.setServiceAmountValue(serviceData.getServiceName(), serviceData.getServicePrice());
		Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());

		selectedServicesScreen.uselectService(inspectionData.getMoneyServicesList().get(1).getServiceName());
		BaseUtils.waitABit(300);
		Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), editedPrice);
		selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testServicesArentBecameSelectedIfUserUnselectThemBeforeClickingBackButtonOnServicesScreen(String rowID,
																										  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectServices(inspectionData.getMoneyServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData serviceData : inspectionData.getMoneyServicesList())
			selectedServicesScreen.uselectService(serviceData.getServiceName());

		for (ServiceData serviceData : inspectionData.getMoneyServicesList())
			Assert.assertFalse(selectedServicesScreen.isServiceSelected(serviceData.getServiceName()));
		selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testTotalIsNotSetTo0IfUserAddsMatrixAdditionalServiceWithNegativePercentageService(String rowID,
																								   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		final MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
		VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		final VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
		vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
		vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
		//vehiclePartInfoScreen.selectVehiclePartAdditionalService(additionalservicename);
		VNextServiceDetailsScreen serviceDetailsScreen = vehiclePartInfoScreen.openServiceDetailsScreen(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		serviceDetailsScreen.setServiceAmountValue(vehiclePartData.getVehiclePartAdditionalService().getServicePrice());
		serviceDetailsScreen.clickServiceDetailsDoneButton();
		vehiclePartInfoScreen = new VNextVehiclePartInfoPage(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(vehiclePartInfoScreen.getMatrixServiceTotalPriceValue(), inspectionData.getInspectionPrice());
		vehiclePartInfoScreen.clickScreenBackButton();
		vehiclePartInfoScreen.clickSaveVehiclePartInfo();
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixServiceData.getMatrixServiceName()));
		;

		Assert.assertEquals(selectedServicesScreen.getInspectionTotalPriceValue(), inspectionData.getInspectionPrice());
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}
}
