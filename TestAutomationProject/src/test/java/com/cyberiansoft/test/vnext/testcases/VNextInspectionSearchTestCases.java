package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextPriceMatrixesScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoPage;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOAdvancedSearchInspectionDialog;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VNextInspectionSearchTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	private String inspnumber = "";
	private String archivedinspnumber = "";
	
	private final String VIN = "3N1AB7AP3HY327077";
	private final String _make = "Nissan";
	private final String _model = "Sentra";
	private final String _color = "Red";
	private final String year = "2017";
	private final String mileage = "123";
	private final String stock = "123";
	private final String ro = "321";
	private final String po = "987";
	private final String licPlate = "qwerty";

	private final String defaultTimeFrameValue = "Timeframe: Last 90 Days";
	
	@Test(testName = "Test Case 64860:vNext mobile: Create Inspection with populated vehicle info for current day",
			description="Create Inspection with populated vehicle info for current day")
	public void testCreateInspectionWithPopulatedVehicleInfoForCurrentDay() {
 		
		final RetailCustomer testcustomer = new RetailCustomer("Retail", "Automation");
		final String insurencecompany = "Test Insurance Company";
		final String claimNumber = "qwerty";
		final String policyNumber = "oops";
		final String deductibleValue = "122";
		
		final String percservices = "Aluminum Upcharge"; 
		final String moneyservices = "Dent Repair"; 
		final String matrixservice = "Hail Dent Repair";
		final String matrixsubservice = "State Farm";
		final String moneyserviceprice = "58";
		final String moneyservicequant = "1";
		final String vehiclepartname = "Hood";
		final String vehiclepartsize = "Dime";	
		final String vehiclepartseverity = "Light 6 to 15";	
		final String additionalservice = "Aluminum Upcharge";
		final String insppriceexp = "267.81";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(VIN);
		Assert.assertEquals(inspinfoscreen.getMakeInfo(), _make);
		Assert.assertEquals(inspinfoscreen.getModelInfo(), _model);		
		Assert.assertEquals(inspinfoscreen.getYear(), year);
		inspinfoscreen.setMilage(mileage);
		inspinfoscreen.setStockNo(stock);
		inspinfoscreen.setRoNo(ro);
		inspinfoscreen.setPoNo(po);
		inspinfoscreen.setLicPlate(licPlate);
			
		inspnumber = inspinfoscreen.getNewInspectionNumber();
		inspinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany(insurencecompany);
		claiminfoscreen.setClaimNumber(claimNumber);
		claiminfoscreen.setPolicyNumber(policyNumber);
		claiminfoscreen.setDeductibleValue(deductibleValue);
		inspinfoscreen.swipeScreensLeft(2);
		VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		inspservicesscreen.selectService(percservices);
		inspservicesscreen.selectService(moneyservices);
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(matrixsubservice);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalservice);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), matrixsubservice);
		selectedServicesScreen.setServiceAmountValue(moneyservices, moneyserviceprice);
		selectedServicesScreen.setServiceQuantityValue(moneyservices, moneyservicequant);

		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(inspnumber), PricesCalculations.getPriceRepresentation(insppriceexp));
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName = "Test Case 64863:vNext mobile: Create Archived Inspection with full populated vehicle info for current day",
			description="Create Archived Inspection with full populated vehicle info for current day")
	public void testCreateArchivedInspectionWithFullPopulatedVehicleInfoForCurrentDay() {
 		
		final RetailCustomer testcustomer = new RetailCustomer("Retail", "Automation");
		final String insurencecompany = "Test Insurance Company";
		final String claimNumber = "qwerty";
		final String policyNumber = "oops";
		final String deductibleValue = "122";
		
		final String percservices = "Aluminum Upcharge"; 
		final String moneyservices = "Dent Repair"; 
		final String matrixservice = "Hail Dent Repair";
		final String matrixsubservice = "State Farm";
		final String moneyserviceprice = "58";
		final String moneyservicequant = "1";
		final String vehiclepartname = "Hood";
		final String vehiclepartsize = "Dime";	
		final String vehiclepartseverity = "Light 6 to 15";	
		final String additionalservice = "Aluminum Upcharge";
		final String insppriceexp = "267.81";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(VIN);
		Assert.assertEquals(inspinfoscreen.getMakeInfo(), _make);
		Assert.assertEquals(inspinfoscreen.getModelInfo(), _model);		
		Assert.assertEquals(inspinfoscreen.getYear(), year);
		inspinfoscreen.setMilage(mileage);
		inspinfoscreen.setStockNo(stock);
		inspinfoscreen.setRoNo(ro);
		inspinfoscreen.setPoNo(po);
		inspinfoscreen.setLicPlate(licPlate);
		
		archivedinspnumber = inspinfoscreen.getNewInspectionNumber();
		inspinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany(insurencecompany);
		claiminfoscreen.setClaimNumber(claimNumber);
		claiminfoscreen.setPolicyNumber(policyNumber);
		claiminfoscreen.setDeductibleValue(deductibleValue);
		inspinfoscreen.swipeScreensLeft(2);
		VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		inspservicesscreen.selectService(percservices);
		inspservicesscreen.selectService(moneyservices);
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(matrixsubservice);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalservice);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), matrixsubservice);
		selectedServicesScreen.setServiceAmountValue(moneyservices, moneyserviceprice);
		selectedServicesScreen.setServiceQuantityValue(moneyservices, moneyservicequant);

		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(archivedinspnumber), PricesCalculations.getPriceRepresentation(insppriceexp));
		inspectionsscreen.archiveInspection(archivedinspnumber);
		
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName = "Test Case 64862:vNext: verify searching inspection by Customer on BO",
			description="Verify searching inspection by Customer on BO",
			dependsOnMethods = { "testCreateInspectionWithPopulatedVehicleInfoForCurrentDay" })
	public void testVerifySearchingInspectionByCustomerOnBO()  {
		
		final String inspTotalPrice = "$ 267.81";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
		insppage.advancedSearchInspectionByCustomer("Retail Automation");
		
		insppage.selectInspectionInTheList(inspnumber);
		Assert.assertEquals(insppage.getSelectedInspectionTotalAmauntValue(), inspTotalPrice);
		webdriver.quit();
	}
	
	@Test(testName = "Test Case 64864:R360: verify searching inspection by All Status on BO",
			description="Verify searching inspection by All Status on BO",
			dependsOnMethods = { "testCreateInspectionWithPopulatedVehicleInfoForCurrentDay", "testCreateArchivedInspectionWithFullPopulatedVehicleInfoForCurrentDay" })
	public void testVerifySearchingInspectionByAllStatusOnBO()  {
		
		final String inspTotalPrice = "$ 267.81";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
		insppage.advancedSearchInspectionByStatusAndInspectionNumber(archivedinspnumber, "All");
		
		insppage.selectInspectionInTheList(archivedinspnumber);
		Assert.assertEquals(insppage.getSelectedInspectionTotalAmauntValue(), inspTotalPrice);
		webdriver.quit();
	}
	
	@Test(testName = "Test Case 64871:R360: verify searching inspection by Archived Status on BO",
			description="Verify searching inspection by Archived Status on BO",
			dependsOnMethods = { "testCreateArchivedInspectionWithFullPopulatedVehicleInfoForCurrentDay" })
	public void testVerifySearchingInspectionByArchivedStatusOnBO()  {
		
		final String inspTotalPrice = "$ 267.81";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
		insppage.advancedSearchInspectionByStatus("Archived");
		
		insppage.selectInspectionInTheList(archivedinspnumber);
		Assert.assertEquals(insppage.getSelectedInspectionTotalAmauntValue(), inspTotalPrice);
		webdriver.quit();
	}
	
	@Test(testName = "Test Case 64872:R360: verify searching inspection by Approved Status on BO",
			description="Verify searching inspection by Approved Status on BO",
			dependsOnMethods = { "testCreateInspectionWithPopulatedVehicleInfoForCurrentDay" })
	public void testVerifySearchingInspectionByApprovedStatusOnBO()  {
		
		final String inspTotalPrice = "$ 267.81";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
		insppage.advancedSearchInspectionByStatus("Approved");
		
		insppage.selectInspectionInTheList(inspnumber);
		Assert.assertEquals(insppage.getSelectedInspectionTotalAmauntValue(), inspTotalPrice);
		webdriver.quit();
	}

	@Test(testName = "Test Case 64944:R360: verify searching inspection by Stock# on BO",
			description="Verify searching inspection by Stock# on BO",
			dependsOnMethods = { "testCreateInspectionWithPopulatedVehicleInfoForCurrentDay" })
	public void testVerifySearchingInspectionByStockNumberOnBO()  {
		
		final String inspTotalPrice = "$ 267.81";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
		insppage.advancedSearchInspectionByStockNumber(stock);
		
		insppage.selectInspectionInTheList(inspnumber);
		Assert.assertEquals(insppage.getSelectedInspectionTotalAmauntValue(), inspTotalPrice);
		webdriver.quit();
	}
	
	@Test(testName = "Test Case 64945:R360: verify searching inspection by PO# on BO",
			description="Verify searching inspection by PO# on BO",
			dependsOnMethods = { "testCreateInspectionWithPopulatedVehicleInfoForCurrentDay" })
	public void testVerifySearchingInspectionByPONumberOnBO()  {
		
		final String inspTotalPrice = "$ 267.81";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
		insppage.advancedSearchInspectionByPONumber(po);
		
		insppage.selectInspectionInTheList(inspnumber);
		Assert.assertEquals(insppage.getSelectedInspectionTotalAmauntValue(), inspTotalPrice);
		webdriver.quit();
	}
	
	@Test(testName = "Test Case 64946:R360: verify searching inspection by VIN on BO",
			description="Verify searching inspection by VIN on BO",
			dependsOnMethods = { "testCreateInspectionWithPopulatedVehicleInfoForCurrentDay" })
	public void testVerifySearchingInspectionByVINOnBO()  {
		
		final String inspTotalPrice = "$ 267.81";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
		insppage.advancedSearchInspectionByVIN(VIN);
		
		insppage.selectInspectionInTheList(inspnumber);
		Assert.assertEquals(insppage.getSelectedInspectionTotalAmauntValue(), inspTotalPrice);
		webdriver.quit();
	}
	
	@Test(testName = "Test Case 64947:R360: verify posibility to Save Inspection Search filter on BO",
			description="Verify posibility to Save Inspection Search filter on BO",
			dependsOnMethods = { "testCreateInspectionWithPopulatedVehicleInfoForCurrentDay" })
	public void testVerifyPosibilityToSaveInspectionSearchFilterOnBO()  {
		
		final String inspTotalPrice = "$ 267.81";
		final String filterName = "test12345";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
	
		insppage.clickExpandAdvancedSearchPanel();
		if (insppage.isSavedAdvancedSearchFilterExists(filterName))
			insppage.deleteSavedAdvancedSearchFilter(filterName);
		
		
		VNextBOAdvancedSearchInspectionDialog advancedserchdialog = insppage.openAdvancedSearchPanel();
		advancedserchdialog.setAdvancedSearchByInspectionNumber(inspnumber);
		advancedserchdialog.setAdvancedSearchFilterNameAndSave(filterName);
		insppage.selectInspectionInTheList(inspnumber);
		Assert.assertEquals(insppage.getSelectedInspectionTotalAmauntValue(), inspTotalPrice);
		webdriver.quit();
	}
	
	@Test(testName = "Test Case 64949:R360: verify posibility to edit saved Inspection Search filter on BO",
			description="Verify posibility to edit saved Inspection Search filter on BO",
			dependsOnMethods = { "testVerifyPosibilityToSaveInspectionSearchFilterOnBO" })
	public void testVerifyPosibilityToEditSavedInspectionSearchFilterOnBO()  {
		
		final String inspTotalPrice = "$ 267.81";
		final String filterName = "test12345";
		final String filterNameEdited = "test12345edited";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
		
		insppage.clickExpandAdvancedSearchPanel();
		if (insppage.isSavedAdvancedSearchFilterExists(filterNameEdited))
			insppage.deleteSavedAdvancedSearchFilter(filterNameEdited);
		
		insppage.clickExpandAdvancedSearchPanel();
		VNextBOAdvancedSearchInspectionDialog advancedserchdialog = insppage.openSavedAdvancedSearchFilter(filterName);
		advancedserchdialog.setAdvancedSearchByInspectionNumber("");
		advancedserchdialog.setAdvencedSearchVINValue(VIN);
		advancedserchdialog.setAdvancedSearchFilterName(filterNameEdited);
		advancedserchdialog.saveAdvancedSearchFilter();
		
		insppage.selectInspectionInTheList(inspnumber);
		Assert.assertEquals(insppage.getSelectedInspectionTotalAmauntValue(), inspTotalPrice);
		webdriver.quit();
	}
	
	@Test(testName = "Test Case 64956:R360: verify posibility to Clear saved Inspection Search filter on BO",
			description="Verify posibility to Clear saved Inspection Search filter on BO",
			dependsOnMethods = { "testVerifyPosibilityToEditSavedInspectionSearchFilterOnBO" })
	public void testVerifyPosibilityToClearSavedInspectionSearchFilterOnBO()  {
		
		final String filterNameEdited = "test12345edited";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
				
		insppage.clickExpandAdvancedSearchPanel();
		VNextBOAdvancedSearchInspectionDialog advancedserchdialog = insppage.openSavedAdvancedSearchFilter(filterNameEdited);
		advancedserchdialog.setAdvancedSearchByInspectionNumber("2");
		advancedserchdialog.setAdvencedSearchVINValue(VIN);
		advancedserchdialog.setAdvancedSearchFilterName(filterNameEdited + "Clear");
		advancedserchdialog.clickClearButton();
		Assert.assertEquals(advancedserchdialog.getAdvancedSearchInspectionNumberValue(), "");
		Assert.assertEquals(advancedserchdialog.getAdvencedSearchVINValue(), "");
		Assert.assertEquals(advancedserchdialog.getAdvancedSearchFilterName(), filterNameEdited);

		webdriver.quit();
	}
	
	@Test(testName = "Test Case 64957:R360: verify posibility to Delete saved Inspection Search filter on BO",
			description="Verify posibility to Delete saved Inspection Search filter on BO",
			dependsOnMethods = { "testVerifyPosibilityToEditSavedInspectionSearchFilterOnBO" })
	public void testVerifyPosibilityToDeleteSavedInspectionSearchFilterOnBO()  {
		
		final String filterNameEdited = "test12345edited";
		
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
				
		insppage.clickExpandAdvancedSearchPanel();
		insppage.deleteSavedAdvancedSearchFilter(filterNameEdited);
		Assert.assertEquals(insppage.getSearchFieldValue(), "");
		Assert.assertEquals(insppage.getCustomSearchInfoTextValue(), defaultTimeFrameValue);
		insppage.clickExpandAdvancedSearchPanel();
		Assert.assertFalse(insppage.isSavedAdvancedSearchFilterExists(filterNameEdited));		
		webdriver.quit();
	}
	
	@Test(testName = "Test Case 64958:R360: verify posibility to reset Inspection Search filter to default on BO",
			description="Verify posibility to reset Inspection Search filter to default on BO",
			dependsOnMethods = { "testVerifyPosibilityToEditSavedInspectionSearchFilterOnBO" })
	public void testVerifyPosibilityToResetInspectionSearchFilterOnBO()  {
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
				
		insppage.clickExpandAdvancedSearchPanel();
		VNextBOAdvancedSearchInspectionDialog advancedserchdialog = insppage.openAdvancedSearchPanel();
		advancedserchdialog.setAdvencedSearchVINValue(VIN);
		advancedserchdialog.clickSearchButton();
		insppage.selectInspectionInTheList(inspnumber);
		insppage.clickClearFilterIcon();
		Assert.assertEquals(insppage.getSearchFieldValue(), "");
		Assert.assertEquals(insppage.getCustomSearchInfoTextValue(), defaultTimeFrameValue);
		insppage.searchInspectionByText(inspnumber);
		insppage.selectInspectionInTheList(inspnumber);
		insppage.clickClearFilterIcon();
		Assert.assertEquals(insppage.getSearchFieldValue(), "");
		Assert.assertEquals(insppage.getCustomSearchInfoTextValue(), defaultTimeFrameValue);
		
		
		webdriver.quit();
	}
}
