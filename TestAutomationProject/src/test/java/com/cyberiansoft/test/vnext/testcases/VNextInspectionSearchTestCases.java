package com.cyberiansoft.test.vnext.testcases;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.ios_client.utils.PricesCalculations;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.screens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextPriceMatrixesScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoPage;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOAdvancedSearchInspectionDialog;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;

public class VNextInspectionSearchTestCases extends BaseTestCaseWithDeviceRegistrationAndExistingUserLogin {

	String inspnumber = "";
	String archivedinspnumber = "";
	
	final String VIN = "3N1AB7AP3HY327077";
	final String _make = "Nissan";
	final String _model = "Sentra";
	final String _color = "Red";
	final String year = "2017";
	final String mileage = "123";
	final String stock = "123";
	final String ro = "321";
	final String po = "987";
	final String licPlate = "qwerty";
	
	final String defaultTimeFrameValue = "Timeframe: Last 90 Days";
	
	@Test(testName = "Test Case 64860:vNext mobile: Create Inspection with populated vehicle info for current day",
			description="Create Inspection with populated vehicle info for current day")
	public void testCreateInspectionWithPopulatedVehicleInfoForCurrentDay() {
 		
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
		customersscreen.selectCustomer("Retail Automation");
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
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(percservices);
		selectservicesscreen.selectService(moneyservices);
		VNextPriceMatrixesScreen pricematrixesscreen = selectservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(matrixsubservice);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalservice);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		selectservicesscreen = vehiclepartsscreen.clickVehiclePartsBackButton();

		Assert.assertEquals(selectservicesscreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), matrixsubservice);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspservicesscreen.setServiceAmountValue(moneyservices, moneyserviceprice);
		inspservicesscreen.setServiceQuantityValue(moneyservices, moneyservicequant);

		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(inspnumber), PricesCalculations.getPriceRepresentation(insppriceexp));
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName = "Test Case 64863:vNext mobile: Create Archived Inspection with full populated vehicle info for current day",
			description="Create Archived Inspection with full populated vehicle info for current day")
	public void testCreateArchivedInspectionWithFullPopulatedVehicleInfoForCurrentDay() {
 		
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
		customersscreen.selectCustomer("Retail Automation");
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
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(percservices);
		selectservicesscreen.selectService(moneyservices);
		VNextPriceMatrixesScreen pricematrixesscreen = selectservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(matrixsubservice);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalservice);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		selectservicesscreen = vehiclepartsscreen.clickVehiclePartsBackButton();

		Assert.assertEquals(selectservicesscreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), matrixsubservice);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspservicesscreen.setServiceAmountValue(moneyservices, moneyserviceprice);
		inspservicesscreen.setServiceQuantityValue(moneyservices, moneyservicequant);

		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(archivedinspnumber), PricesCalculations.getPriceRepresentation(insppriceexp));
		inspectionsscreen.archiveInspection(archivedinspnumber);
		
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName = "Test Case 64862:vNext: verify searching inspection by Customer on BO",
			description="Verify searching inspection by Customer on BO",
			dependsOnMethods = { "testCreateInspectionWithPopulatedVehicleInfoForCurrentDay" })
	public void testVerifySearchingInspectionByCustomerOnBO() throws IOException {
		
		final String inspTotalPrice = "$ 267.81";
		
		initiateWebDriver();
		webdriver.get("https://19176361455.cyberianconcepts.com");
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
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
	public void testVerifySearchingInspectionByAllStatusOnBO() throws IOException {
		
		final String inspTotalPrice = "$ 267.81";
		
		initiateWebDriver();
		webdriver.get("https://19176361455.cyberianconcepts.com");
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
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
	public void testVerifySearchingInspectionByArchivedStatusOnBO() throws IOException {
		
		final String inspTotalPrice = "$ 267.81";
		
		initiateWebDriver();
		webdriver.get("https://19176361455.cyberianconcepts.com");
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
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
	public void testVerifySearchingInspectionByApprovedStatusOnBO() throws IOException {
		
		final String inspTotalPrice = "$ 267.81";
		
		initiateWebDriver();
		webdriver.get("https://19176361455.cyberianconcepts.com");
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
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
	public void testVerifySearchingInspectionByStockNumberOnBO() throws IOException {
		
		final String inspTotalPrice = "$ 267.81";
		
		initiateWebDriver();
		webdriver.get("https://19176361455.cyberianconcepts.com");
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
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
	public void testVerifySearchingInspectionByPONumberOnBO() throws IOException {
		
		final String inspTotalPrice = "$ 267.81";
		
		initiateWebDriver();
		webdriver.get("https://19176361455.cyberianconcepts.com");
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
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
	public void testVerifySearchingInspectionByVINOnBO() throws IOException {
		
		final String inspTotalPrice = "$ 267.81";
		
		initiateWebDriver();
		webdriver.get("https://19176361455.cyberianconcepts.com");
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
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
	public void testVerifyPosibilityToSaveInspectionSearchFilterOnBO() throws IOException {
		
		final String inspTotalPrice = "$ 267.81";
		final String filterName = "test12345";
		
		initiateWebDriver();
		webdriver.get("https://19176361455.cyberianconcepts.com");
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
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
	public void testVerifyPosibilityToEditSavedInspectionSearchFilterOnBO() throws IOException {
		
		final String inspTotalPrice = "$ 267.81";
		final String filterName = "test12345";
		final String filterNameEdited = "test12345edited";
		
		initiateWebDriver();
		webdriver.get("https://19176361455.cyberianconcepts.com");
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
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
	public void testVerifyPosibilityToClearSavedInspectionSearchFilterOnBO() throws IOException {
		
		final String filterNameEdited = "test12345edited";
		
		initiateWebDriver();
		webdriver.get("https://19176361455.cyberianconcepts.com");
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
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
	public void testVerifyPosibilityToDeleteSavedInspectionSearchFilterOnBO() throws IOException {
		
		final String filterNameEdited = "test12345edited";
		
		
		initiateWebDriver();
		webdriver.get("https://19176361455.cyberianconcepts.com");
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
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
	public void testVerifyPosibilityToResetInspectionSearchFilterOnBO() throws IOException {
		
		final String filterNameEdited = "test12345edited";
		
		initiateWebDriver();
		webdriver.get("https://19176361455.cyberianconcepts.com");
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
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