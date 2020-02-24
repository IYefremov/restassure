package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.steps.ApproveSteps;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.MenuSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
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
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		WaitUtils.elementShouldBeVisible(inspectionsMenuScreen.getCreatewoinspectionbtn(), false);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();
		VNextApproveScreen approveScreen = new VNextApproveScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();
		inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), VNextInspectionStatuses.APPROVED);
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyCleanIconWorkCorrectly(String rowID,
																String description, JSONObject testData) {
        HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
		String inspectionNumber = InspectionSteps.saveInspection();
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), VNextInspectionStatuses.NEW);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();
		VNextApproveScreen approveScreen = new VNextApproveScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		approveScreen.drawSignature();
		approveScreen.clickClearSignatureButton();
		approveScreen.clickSaveButton();
		VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.PLEASE_DOMT_LEAVE_SIGNATURE_FIELD_EMPTY);
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();
		inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), VNextInspectionStatuses.APPROVED);
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanCreateWOOnlyForApprovedInspections(String rowID,
												 String description, JSONObject testData) {
        HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
		String inspectionNumber = InspectionSteps.saveInspection();
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), VNextInspectionStatuses.NEW);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();
		VNextApproveScreen approveScreen = new VNextApproveScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		ApproveSteps.drawSignature();
		Assert.assertTrue(approveScreen.isClearButtonVisible());
		ApproveSteps.saveApprove();
		inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), VNextInspectionStatuses.APPROVED);
		InspectionSteps.openInspectionMenu(inspectionNumber);
		WaitUtils.elementShouldBeVisible(inspectionsMenuScreen.getApproveinspectionbtn(), false);
		Assert.assertTrue(inspectionsMenuScreen.isCreateWorkOrderMenuPresent());
		MenuSteps.closeMenu();
		inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionsScreen.clickBackButton();
	}

}
