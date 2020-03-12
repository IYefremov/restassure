package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServicePackagesWebPage;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.ListServicesValidations;
import com.cyberiansoft.test.vnext.validations.SelectedServicesScreenValidations;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
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

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		AvailableServicesScreenSteps.selectServices(inspectionData.getServicesList());
		SelectedServicesScreenSteps.switchToSelectedService();
		for (ServiceData service : inspectionData.getServicesList())
			ListServicesValidations.verifyServiceSelected(service.getServiceName(), true);
		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.EDIT);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		SelectedServicesScreenSteps.switchToSelectedService();
		for (ServiceData service : inspectionData.getServicesList())
			ListServicesValidations.verifyServiceSelected(service.getServiceName(), true);
		InspectionSteps.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testShowSelectedServicesForInspectionWhenNavigatingFromServicesScreen(String rowID,
																					  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		AvailableServicesScreenSteps.selectServices(inspectionData.getServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			ListServicesValidations.verifyServiceSelected(service.getServiceName(), true);
		availableServicesScreen.swipeScreenRight();
		BaseUtils.waitABit(2000);
		availableServicesScreen.swipeScreenLeft();
		availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			ListServicesValidations.verifyServiceSelected(service.getServiceName(), true);
		availableServicesScreen.swipeScreenRight();
		BaseUtils.waitABit(2000);
		availableServicesScreen.swipeScreenLeft();
		availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			ListServicesValidations.verifyServiceSelected(service.getServiceName(), true);
		availableServicesScreen.saveInspectionViaMenu();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testAddOneServiceToAlreadySelectedServicesWhenInspectionIsEdited(String rowID,
																				 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();

		AvailableServicesScreenSteps.selectService(inspectionData.getServiceNameByIndex(0));
		AvailableServicesScreenSteps.selectService(inspectionData.getServiceNameByIndex(1));
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		ListServicesValidations.verifyServiceSelected(inspectionData.getServiceNameByIndex(0), true);
		ListServicesValidations.verifyServiceSelected(inspectionData.getServiceNameByIndex(1), true);
		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.EDIT);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		availableServicesScreen.switchToSelectedServicesView();
		ListServicesValidations.verifyServiceSelected(inspectionData.getServiceNameByIndex(0), true);
		ListServicesValidations.verifyServiceSelected(inspectionData.getServiceNameByIndex(1), true);

		availableServicesScreen.switchToAvalableServicesView();
		AvailableServicesScreenSteps.selectService(inspectionData.getServiceNameByIndex(2));
		availableServicesScreen.switchToSelectedServicesView();

		ListServicesValidations.verifyServiceSelected(inspectionData.getServiceNameByIndex(2), true);
		InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.EDIT);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

		availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			ListServicesValidations.verifyServiceSelected(service.getServiceName(), true);
		selectedServicesScreen.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testAddSeveralServicesToAlreadySelectedServicesWhenInspectionIsEdited(String rowID,
																					  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int firstPartNumber = 2;

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		for (int i = 0; i < firstPartNumber; i++)
			AvailableServicesScreenSteps.selectService(inspectionData.getServiceNameByIndex(i));
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (int i = 0; i < firstPartNumber; i++)
			ListServicesValidations.verifyServiceSelected(inspectionData.getServiceNameByIndex(i), true);
		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.EDIT);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		availableServicesScreen.switchToSelectedServicesView();
		for (int i = 0; i < firstPartNumber; i++)
			ListServicesValidations.verifyServiceSelected(inspectionData.getServiceNameByIndex(i), true);
		availableServicesScreen.switchToAvalableServicesView();
		for (int i = firstPartNumber; i < inspectionData.getServicesList().size(); i++)
			AvailableServicesScreenSteps.selectService(inspectionData.getServiceNameByIndex(i));
		availableServicesScreen.switchToSelectedServicesView();

		for (ServiceData service : inspectionData.getServicesList())
			ListServicesValidations.verifyServiceSelected(service.getServiceName(), true);
		InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.EDIT);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			ListServicesValidations.verifyServiceSelected(service.getServiceName(), true);
		selectedServicesScreen.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifySelectedServicesAreSavedToBO(String rowID,
													   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		AvailableServicesScreenSteps.selectServices(inspectionData.getServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

		for (ServiceData service : inspectionData.getServicesList())
			ListServicesValidations.verifyServiceSelected(service.getServiceName(), true);
		final String inspectionNumber = InspectionSteps.saveInspection();
		ScreenNavigationSteps.pressBackButton();
		BaseUtils.waitABit(30000);
		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		VNextBOLoginSteps.userLogin(deviceuser, devicepsw);
		VNexBOLeftMenuPanel leftMenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionsWebPage = leftMenu.selectInspectionsMenu();
		inspectionsWebPage.selectInspectionInTheList(inspectionNumber);
		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertTrue(inspectionsWebPage.isServicePresentForSelectedInspection(service.getServiceName()));
		webdriver.quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyLettersIOQAreTrimmedWhileManualEntry(String rowID,
															   String description, JSONObject testData) {

		final String vinNumber = "AI0YQ56ONJ";
		final String redRGBColor = "rgba(239, 83, 78, 1)";

		HomeScreenSteps.openCreateMyInspection();
		CustomersScreenSteps.selectCustomer(testcustomer);
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, vinNumber);
		VehicleInfoScreenValidations.vinValidationMessageShouldExist(true);

		VehicleInfoScreenValidations.vinValidationColorShouldBeEqual(redRGBColor);
		InspectionSteps.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyNotAllowedCharactersAreTrimmedWhileManualEntry(String rowID,
																		 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String redRGBColor = "rgba(239, 83, 78, 1)";

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		VehicleInfoScreenValidations.vinValidationMessageShouldExist(true);
		VehicleInfoScreenValidations.vinValidationColorShouldBeEqual(redRGBColor);
		InspectionSteps.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyServicesAreSavedWhenSaveInspectionOptionWasUsedFromHumburgerMenu(String rowID,
																						   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		AvailableServicesScreenSteps.selectServices(inspectionData.getServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

		for (ServiceData service : inspectionData.getServicesList())
			ListServicesValidations.verifyServiceSelected(service.getServiceName(), true);
		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.EDIT);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			ListServicesValidations.verifyServiceSelected(service.getServiceName(), true);
		InspectionSteps.cancelInspection();
		ScreenNavigationSteps.pressBackButton();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyVehicleClaimAreSavedWhenSaveInspectionOptionWasUsedFromHumburgerMenu(String rowID,
																							   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		VNextBaseScreen baseScreen = new VNextBaseScreen();
		baseScreen.swipeScreenLeft();
		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.EDIT);

		VehicleInfoScreenValidations.validateVehicleInfo(inspectionData.getVehicleInfo());

		InspectionSteps.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testExitCancelInspectionStateCalledFromHumburgerMenu_FirstStep(String rowID,
																			   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		CustomersScreenSteps.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		vehicleInfoScreen.clickCancelMenuItem();
		VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String msg = informationDialog.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.CANCEL_CREATING_INSPECTION_ALERT);
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.swipeScreenLeft();
		vehicleInfoScreen.clickCancelMenuItem();
		new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		ScreenNavigationSteps.pressBackButton();
		ScreenNavigationSteps.pressBackButton();
		ScreenNavigationSteps.pressBackButton();
		informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		msg = informationDialog.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.CANCEL_CREATING_INSPECTION_ALERT);
		vehicleInfoScreen.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
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

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		List<WebElement> services = availableServicesScreen.getServicesListItems();
		List<String> servicesTxt = new ArrayList<>();
		//for (WebElement lst : services)
		//	servicesTxt.add(availableServicesScreen.getServiceListItemName(lst));

		for (String srv : allServicesTxt) {
			Assert.assertTrue(servicesTxt.contains(srv));
		}
		ScreenNavigationSteps.pressBackButton();
		InspectionSteps.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyDefaultPriceForServiceIsShownCorrectly(String rowID,
																 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();

		AvailableServicesScreenSteps.selectServices(inspectionData.getMoneyServicesList());
		AvailableServicesScreenSteps.selectServices(inspectionData.getPercentageServicesList());
		SelectedServicesScreenSteps.switchToSelectedService();

		inspectionData.getMoneyServicesList().forEach(moneyService -> SelectedServicesScreenValidations.validateSelectedServicePrice(moneyService.getServiceName(), moneyService.getServicePrice()));
		inspectionData.getPercentageServicesList().forEach(percService -> SelectedServicesScreenValidations.validateSelectedServicePrice(percService.getServiceName(), percService.getServicePrice()));
		InspectionSteps.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testAddTheSameServiceMultipleTimes(String rowID,
												   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int servicesNumberSelected = 2;

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		AvailableServicesScreenSteps.selectServices(inspectionData.getServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

		for (ServiceData service : inspectionData.getServicesList())
			ListServicesValidations.verifyServiceSelected(service.getServiceName(), true);
		selectedServicesScreen.switchToAvalableServicesView();
		AvailableServicesScreenSteps.selectServices(inspectionData.getServicesList());
		availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertEquals(selectedServicesScreen.getQuantityOfSelectedService(service.getServiceName()), servicesNumberSelected);

		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.EDIT);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData service : inspectionData.getServicesList())
			Assert.assertEquals(selectedServicesScreen.getQuantityOfSelectedService(service.getServiceName()), servicesNumberSelected);

		InspectionSteps.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testEditInspectionServices(String rowID,
										   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String inspPrice = "$242.00";
		final String firstMoneyServicePrice = "5";
		final String secondMoneyServicePrice = "84.55";
		final String secondMoneyServiceQty = "9.15";

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		AvailableServicesScreenSteps.selectServices(inspectionData.getServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData serviceData : inspectionData.getServicesList()) {
			SelectedServicesScreenSteps.changeSelectedServicePrice(serviceData.getServiceName(), serviceData.getServicePrice());
		}
		Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspPrice);
		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.EDIT);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		availableServicesScreen.switchToSelectedServicesView();
		SelectedServicesScreenSteps.unSelectService(inspectionData.getServicesList().get(0).getServiceName());

		selectedServicesScreen.switchToAvalableServicesView();
		AvailableServicesScreenSteps.selectServices(inspectionData.getMoneyServicesList());
		availableServicesScreen.switchToSelectedServicesView();

		for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
			SelectedServicesScreenSteps.changeSelectedServicePrice(serviceData.getServiceName(), serviceData.getServicePrice());
		}

		SelectedServicesScreenSteps.changeSelectedServicePrice(inspectionData.getMoneyServicesList().get(0).getServiceName(),
				firstMoneyServicePrice);
		SelectedServicesScreenSteps.changeSelectedServicePrice(inspectionData.getMoneyServicesList().get(1).getServiceName(),
				secondMoneyServicePrice);
		SelectedServicesScreenSteps.changeSelectedServiceQuantity(inspectionData.getMoneyServicesList().get(1).getServiceName(),
				secondMoneyServiceQty);

		Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(),
				BackOfficeUtils.getFormattedServicePriceValue(BackOfficeUtils.getServicePriceValue("$1000.63")));
		InspectionSteps.saveInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyPriceMatrixAddedToServicePackageIsAvailableToChooseWhenAddEditInspection(String rowID,
																								   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		final MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
		VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(matrixServiceData.getVehiclePartData().getVehiclePartName());
		vehiclePartInfoScreen.selectVehiclePartSize(matrixServiceData.getVehiclePartData().getVehiclePartSize());
		vehiclePartInfoScreen.selectVehiclePartSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
		vehiclePartInfoScreen.clickSaveVehiclePartInfo();
		vehiclePartsScreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		ListServicesValidations.verifyServiceSelected(matrixServiceData.getMatrixServiceName(), true);
		selectedServicesScreen.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyListOfAvailablePriceMatricesIsLoadedWhenChoosingMatrixService(String rowID,
																						String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
		VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(matrixServiceData.getVehiclePartData().getVehiclePartName());
		vehiclePartInfoScreen.selectVehiclePartSize(matrixServiceData.getVehiclePartData().getVehiclePartSize());
		vehiclePartInfoScreen.selectVehiclePartSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
		vehiclePartInfoScreen.clickSaveVehiclePartInfo();
		vehiclePartsScreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		ListServicesValidations.verifyServiceSelected(matrixServiceData.getMatrixServiceName(), true);
		selectedServicesScreen.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyPriceMatrixNameIsShownOnSelectServicesScreenAfterSelection(String rowID,
																					 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
		VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(matrixServiceData.getVehiclePartData().getVehiclePartName());
		vehiclePartInfoScreen.selectVehiclePartSize(matrixServiceData.getVehiclePartData().getVehiclePartSize());
		vehiclePartInfoScreen.selectVehiclePartSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
		vehiclePartInfoScreen.clickSaveVehiclePartInfo();
		vehiclePartsScreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		ListServicesValidations.verifyServiceSelected(matrixServiceData.getMatrixServiceName(), true);
		selectedServicesScreen.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyCorrectPriceIsShownInServicesListAfterEditingServicePricePercentage(String rowID,
																							  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		ServiceData percentageService = inspectionData.getPercentageServiceData();
		AvailableServicesScreenSteps.selectService(percentageService);
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		ListServicesValidations.verifyServiceSelected(percentageService.getServiceName(), true);
		SelectedServicesScreenValidations.validateSelectedServicePrice(percentageService.getServiceName(), percentageService.getServicePrice());
		SelectedServicesScreenSteps.changeSelectedServicePrice(percentageService.getServiceName(), percentageService.getServicePrice2());
		SelectedServicesScreenValidations.validateSelectedServicePrice(percentageService.getServiceName(), percentageService.getServicePrice2() + "%");
		InspectionSteps.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testEditServicePricePercentage(String rowID,
											   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		AvailableServicesScreenSteps.selectService(inspectionData.getMoneyServiceData());
		final ServiceData percentageService = inspectionData.getPercentageServiceData();
		AvailableServicesScreenSteps.selectService(percentageService);
		SelectedServicesScreenSteps.switchToSelectedService();
		SelectedServicesScreenSteps.changeSelectedServicePrice(percentageService.getServiceName(), percentageService.getServicePrice());
		SelectedServicesScreenValidations.validateSelectedServicePrice(percentageService.getServiceName(), percentageService.getServicePrice2());
		InspectionSteps.saveInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testEditServicePriceMoney(String rowID,
										  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		final ServiceData moneyService = inspectionData.getMoneyServiceData();
		AvailableServicesScreenSteps.selectService(moneyService);
		SelectedServicesScreenSteps.switchToSelectedService();
		SelectedServicesScreenSteps.changeSelectedServicePrice(moneyService.getServiceName(), moneyService.getServicePrice());
		SelectedServicesScreenValidations.validateSelectedServicePrice(moneyService.getServiceName(), moneyService.getServicePrice2());
		InspectionSteps.saveInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testDeleteServiceFromServicesScreen(String rowID,
													String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		AvailableServicesScreenSteps.selectServices(inspectionData.getMoneyServicesList());
		AvailableServicesScreenSteps.selectServices(inspectionData.getPercentageServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

		SelectedServicesScreenSteps.unSelectService(inspectionData.getMoneyServicesList().get(0).getServiceName());
		SelectedServicesScreenSteps.unSelectService(inspectionData.getPercentageServicesList().get(0).getServiceName());
		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.EDIT);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		availableServicesScreen.switchToSelectedServicesView();
		ListServicesValidations.verifyServiceSelected(inspectionData.getMoneyServicesList().get(1).getServiceName(), true);
		ListServicesValidations.verifyServiceSelected(inspectionData.getMoneyServicesList().get(0).getServiceName(), false);
		ListServicesValidations.verifyServiceSelected(inspectionData.getPercentageServicesList().get(0).getServiceName(),false);
		InspectionSteps.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testInspectionTotalPriceShouldChangeWhenUselectSomeOfTheSelectedServiceOnServicesScreen(String rowID,
																										String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String editedPrice = "$10.00";

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		AvailableServicesScreenSteps.selectServices(inspectionData.getMoneyServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData serviceData : inspectionData.getMoneyServicesList())
			SelectedServicesScreenSteps.changeSelectedServicePrice(serviceData.getServiceName(), serviceData.getServicePrice());
		Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());

		SelectedServicesScreenSteps.unSelectService(inspectionData.getMoneyServicesList().get(1).getServiceName());
		BaseUtils.waitABit(300);
		Assert.assertEquals(selectedServicesScreen.getTotalPriceValue(), editedPrice);
		selectedServicesScreen.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testServicesArentBecameSelectedIfUserUnselectThemBeforeClickingBackButtonOnServicesScreen(String rowID,
																										  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		AvailableServicesScreenSteps.selectServices(inspectionData.getMoneyServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		SelectedServicesScreenSteps.unSelectServices(inspectionData.getMoneyServicesList());

		for (ServiceData serviceData : inspectionData.getMoneyServicesList())
			ListServicesValidations.verifyServiceSelected(serviceData.getServiceName(), false);
		selectedServicesScreen.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testTotalIsNotSetTo0IfUserAddsMatrixAdditionalServiceWithNegativePercentageService(String rowID,
																								   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
		final MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
		VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		final VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
		vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
		vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
		//vehiclePartInfoScreen.selectVehiclePartAdditionalService(additionalservicename);
		VNextServiceDetailsScreen serviceDetailsScreen = vehiclePartInfoScreen.openServiceDetailsScreen(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		serviceDetailsScreen.setServiceAmountValue(vehiclePartData.getVehiclePartAdditionalService().getServicePrice());
		serviceDetailsScreen.clickServiceDetailsDoneButton();
		vehiclePartInfoScreen = new VNextVehiclePartInfoPage(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertEquals(vehiclePartInfoScreen.getMatrixServiceTotalPriceValue(), inspectionData.getInspectionPrice());
		vehiclePartInfoScreen.clickScreenBackButton();
		vehiclePartInfoScreen.clickSaveVehiclePartInfo();
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		ListServicesValidations.verifyServiceSelected(matrixServiceData.getMatrixServiceName(), true);

		Assert.assertEquals(selectedServicesScreen.getInspectionTotalPriceValue(), inspectionData.getInspectionPrice());
		selectedServicesScreen.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}
}
