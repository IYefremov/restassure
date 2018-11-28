package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextConfirmationDialog;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VNextInspectionApproveOnBOTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	String inspnumber = "";
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
	
	@Test(testName = "Test Case 64860:vNext mobile: Create Inspection with populated vehicle info for current day",
			description="Create Inspection with populated vehicle info for current day")
	public void createInspectionWithPopulatedVehicleInfoForCurrentDay() {
 		
		final RetailCustomer testcustomer = new RetailCustomer("Retail", "Automation");
		final String insurencecompany = "Test Insurance Company";
		final String claimNumber = "qwerty";
		final String policyNumber = "oops";
		final String deductibleValue = "122";
		
		//final String percservices = "Aluminum Upcharge"; 
		final String moneyservices = "Dent Repair"; 
		final String matrixservice = "Hail Repair";
		final String matrixsubservice = "State Farm";
		final String moneyserviceprice = "58";
		final String moneyservicequant = "1";
		final String vehiclepartname = "Hood";
		final String vehiclepartsize = "Dime";	
		final String vehiclepartseverity = "Light 6 to 15";	
		final String additionalservice = "Aluminum Upcharge";
		final String insppriceexp = "214.25";
		
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
		inspinfoscreen.changeScreen("Services");
		VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		//selectservicesscreen.selectService(percservices);
		inspservicesscreen.selectService(moneyservices);
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(matrixsubservice);
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
	
	@Test(testName = "Test Case 68994:R360: verify posibility to Approve New Inspection on BO",
			description="Verify posibility to Approve New Inspection on BO")
	public void testVerifyPosibilityToApproveNewInspectionOnBO() {
		
		final String approveNotes = "Approve Inspection via QuickAccess";
		final String inspStausExpected = "Approved";
 		
		createInspectionWithPopulatedVehicleInfoForCurrentDay();
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(VNextConfigInfo.getInstance().getBackOfficeCapiURL());
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.waitABit(1000*20);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
		insppage.selectInspectionInTheList(inspnumber);
		insppage.clickInspectionApproveButton();
		VNextConfirmationDialog confirmdialog = new VNextConfirmationDialog(webdriver);

		confirmdialog.clickNoButton();
		BaseUtils.waitABit(500);
		insppage.approveInspection(approveNotes);
		
		insppage = leftmenu.selectInspectionsMenu();
		insppage.selectInspectionInTheList(inspnumber);
		Assert.assertEquals(insppage.getInspectionStatus(inspnumber), inspStausExpected);
		webdriver.quit();
	}
	
	@Test(testName = "Test Case 68995:R360: verify posibility to Decline New Inspection on BO",
			description="Verify posibility to Decline New Inspection on BO")
	public void testVerifyPosibilityToDeclineNewInspectionOnBO() {
		
		final String approveNotes = "Decline Inspection via QuickAccess";
		final String inspStausExpected = "Declined";
 		
		createInspectionWithPopulatedVehicleInfoForCurrentDay();
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(VNextConfigInfo.getInstance().getBackOfficeCapiURL());
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.waitABit(1000*15);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
		insppage.selectInspectionInTheList(inspnumber);
		insppage.clickInspectionApproveButton();
		VNextConfirmationDialog confirmdialog = new VNextConfirmationDialog(webdriver);
		confirmdialog.clickNoButton();
		
		insppage.declineInspection(approveNotes);
		
		insppage = leftmenu.selectInspectionsMenu();
		insppage.selectInspectionInTheList(inspnumber);
		Assert.assertEquals(insppage.getInspectionStatus(inspnumber), inspStausExpected);
		webdriver.quit();
	}
	
	//@Test(testName = "Test Case 69003:R360: verify Archived Inspection can't be approved",
	//		description="Verify Archived Inspection can't be approved")
	public void testVerifyArchivedInspectionCantBeApproved() {
 		
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
		
		final String inspStatus = "Archived";
		
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
		
		final String archivedinspnumber = inspinfoscreen.getNewInspectionNumber();
		inspinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany(insurencecompany);
		claiminfoscreen.setClaimNumber(claimNumber);
		claiminfoscreen.setPolicyNumber(policyNumber);
		claiminfoscreen.setDeductibleValue(deductibleValue);
		inspinfoscreen.changeScreen("Services");
		VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		inspservicesscreen.selectService(percservices);
		inspservicesscreen.selectService(moneyservices);
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(matrixsubservice);
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
		inspservicesscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(archivedinspnumber), PricesCalculations.getPriceRepresentation(insppriceexp));

		inspectionsscreen.archiveInspection(archivedinspnumber);
		inspectionsscreen.clickBackButton();
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(VNextConfigInfo.getInstance().getBackOfficeCapiURL());
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.waitABit(1000*15);
		loginpage.userLogin(VNextConfigInfo.getInstance().getUserCapiUserName(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
		insppage.advancedSearchInspectionByStatus(inspStatus);
		
		insppage.selectInspectionInTheList(archivedinspnumber);
		Assert.assertFalse(insppage.isInspectionApproveButtonVisible());
		webdriver.quit();
	}

}
