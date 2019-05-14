package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextApproveTeamInspectionsTestCases extends BaseTestCaseTeamEditionRegistration {
	
	@BeforeClass(description="Approve Team Inspections Test Cases")
	public void beforeClass() throws Exception {
	}
	
	@Test(testName= "Test Case 64246:Verify user can create Inspection in status 'New',"
			+ "Test Case 64249:Verify user can approve Inspection after creating,"
			+ "Verify user can create Inspection in status 'New'", 
			description = "Verify user can approve Inspection after creating")
	public void testVerifyUserCanApproveInspectionAfterCreating() {

		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		final String inspnumber = createSimpleInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, vinnumber);
		VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.NEW);
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		Assert.assertFalse(inspmenuscreen.isCreateWorkOrderMenuPresent());
		inspmenuscreen.clickApproveInspectionMenuItem();
		VNextApproveScreen approvescreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.APPROVED);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 64250:Verify 'Clean' icon work correctly", 
			description = "Verify 'Clean' icon work correctly")
	public void testVerifyCleanIconWorkCorrectly() {

		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		final String inspnumber = createSimpleInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, vinnumber);
		VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.NEW);
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenuscreen.clickApproveInspectionMenuItem();
		VNextApproveScreen approvescreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approvescreen.drawSignature();
		approvescreen.clickClearSignatureButton();
		approvescreen.clickSaveButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(informationdlg.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.PLEASE_DOMT_LEAVE_SIGNATURE_FIELD_EMPTY);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.APPROVED);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 64264:Verify user can create WO only for Approved Inspections", 
			description = "Verify user can create WO only for Approved Inspections")
	public void testVerifyUserCanCreateWOOnlyForApprovedInspections() {

		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		final String inspnumber = createSimpleInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, vinnumber);
		VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.NEW);
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenuscreen.clickApproveInspectionMenuItem();
		VNextApproveScreen approvescreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approvescreen.drawSignature();
		Assert.assertTrue(approvescreen.isClearButtonVisible());
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.APPROVED);
		inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		Assert.assertFalse(inspmenuscreen.isApproveMenuPresent());
		Assert.assertTrue(inspmenuscreen.isCreateWorkOrderMenuPresent());
		inspmenuscreen.clickCloseInspectionMenuButton();
		inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		homescreen = inspectionscreen.clickBackButton();
	}
	
	public String createSimpleInspection(AppCustomer inspcustomer, InspectionTypes inspectionTypes, String vinnumber) {
		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToMyInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(inspcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		insptypeslist.selectInspectionType(inspectionTypes);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		vehicleinfoscreen.swipeScreenLeft();
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		/*VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		VNextQuestionsScreen questionsScreen = selectedServicesScreen.
				clickServiceQuestionSection("Test_Service_PP_Panel", "zayats section1");
		questionsScreen.selectAllRequiredQuestions(0);
		questionsScreen.setAllRequiredQuestions("test 1");
		questionsScreen.saveQuestions();
		selectedServicesScreen.switchToAvalableServicesView();
		availableServicesScreen.switchToSelectedServicesView();
		questionsScreen = selectedServicesScreen.
				clickServiceQuestionSection("Vlad_Money", "Vovan Test 5");
		questionsScreen.selectRequiredQuestion();
		questionsScreen.clickDoneButton();
*/
		availableServicesScreen.saveInspectionViaMenu();
		return inspnumber;
	}

}
