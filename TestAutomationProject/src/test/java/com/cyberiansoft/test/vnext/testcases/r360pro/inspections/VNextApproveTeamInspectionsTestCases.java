package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.InspectionStatus;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.InspectionsValidations;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextApproveTeamInspectionsTestCases extends BaseTestClass {

	@BeforeClass(description="Approve Team Inspections Test Cases")
	public void beforeClass()  {
		JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getApproveInspectionsTestCasesDataPath();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanApproveInspectionAfterCreating(String rowID,
													  String description, JSONObject testData) {
        HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
		String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuValidations.menuItemShouldBeVisible(MenuItems.CREATE_WORK_ORDER, false);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);
		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();
		InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.APPROVED);
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyCleanIconWorkCorrectly(String rowID,
																String description, JSONObject testData) {
        HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
		String inspectionNumber = InspectionSteps.saveInspection();
		InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.NEW);
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);
		VNextApproveScreen approveScreen = new VNextApproveScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		ApproveSteps.drawSignature();
		approveScreen.clickClearSignatureButton();
		approveScreen.clickSaveButton();
		VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.PLEASE_DOMT_LEAVE_SIGNATURE_FIELD_EMPTY);
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();
		InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.APPROVED);
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanCreateWOOnlyForApprovedInspections(String rowID,
												 String description, JSONObject testData) {
        HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
		String inspectionNumber = InspectionSteps.saveInspection();
		InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.NEW);
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);
		VNextApproveScreen approveScreen = new VNextApproveScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		ApproveSteps.drawSignature();
		Assert.assertTrue(approveScreen.isClearButtonVisible());
		ApproveSteps.saveApprove();
		InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.APPROVED);
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuValidations.menuItemShouldBeVisible(MenuItems.APPROVE, false);
		MenuValidations.menuItemShouldBeVisible(MenuItems.CREATE_WORK_ORDER, true);
		MenuSteps.closeMenu();
		ScreenNavigationSteps.pressBackButton();
	}

}
