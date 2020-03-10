package com.cyberiansoft.test.vnextbo.testcases.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOPageSwitcherValidations;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOBreadCrumbValidations;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBORODetailsValidationsNew;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROWebPageValidationsNew;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VNextBOMonitorTestCasesPart1New extends BaseTestCase {

	private String parentTabHandle;

	@BeforeClass
	public void settingUp() {

		JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTD();
		VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
		Utils.refreshPage();
		VNextBOBreadCrumbInteractions.setLocation("Best Location Automation");
	}

	@BeforeMethod
	public void switchToParentTab() {

		if (DriverBuilder.getInstance().getDriver().getWindowHandles().size() > 1)
			Utils.closeAllNewWindowsExceptParentTab(parentTabHandle);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
	public void verifyUserCanOpenMonitorWithFullSetOfElements(String rowID, String description, JSONObject testData) {

		VNextBOROWebPageValidationsNew.verifyTermsAndConditionsLinkIsDisplayed();
		VNextBOROWebPageValidationsNew.verifyPrivacyPolicyLinkIsDisplayed();
		VNextBOROWebPageValidationsNew.verifyIntercomButtonIsDisplayed();
		VNextBOROWebPageValidationsNew.verifyLogoIsDisplayed();
		VNextBOROWebPageValidationsNew.verifyTimeBoxIsDisplayed();
		VNextBOSearchPanelValidations.verifySearchFieldIsDisplayed();
		VNextBOPageSwitcherValidations.verifyPageNavigationElementsAreDisplayed();
		VNextBOROWebPageValidationsNew.verifyLogoutButtonIsDisplayed();
		VNextBOROWebPageValidationsNew.verifyHelpButtonIsDisplayed();
		VNextBOROWebPageValidationsNew.verifyCopyRightTextIsDisplayed();
		VNextBOROWebPageValidationsNew.verifySavedSearchDropDownFieldIdDisplayed();
		VNextBOROWebPageValidationsNew.verifyDepartmentsTabIsDisplayed();
		VNextBOROWebPageValidationsNew.verifyDepartmentDropdownIsDisplayed();
		VNextBOROPageStepsNew.switchToFilterTab("Phases");
		VNextBOROWebPageValidationsNew.verifyPhasesTabIsDisplayed();
		VNextBOROWebPageValidationsNew.verifyPhasesDropdownIsDisplayed();
		VNextBOROPageStepsNew.switchToFilterTab("Departments");
		VNextBOROWebPageValidationsNew.verifyOrdersTableContainsCorrectColumns();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanOpenAndCloseTermsAndConditions(String rowID, String description, JSONObject testData) {

		VNextBOROPageStepsNew.clickTermsAndConditionsLink();
		VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
		VNextBOModalDialogValidations.verifyDialogIsDisplayed();
		VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
		Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
				"AMT Service Agreement Terms and Conditions",
				"Dialog header hasn't been correct");
		VNextBOModalDialogSteps.clickOkButton();
		VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanOpenAndClosePrivacyPolicy(String rowID, String description, JSONObject testData) {

		VNextBOROPageStepsNew.clickPrivacyPolicyLink();
		VNextBOModalDialog vNextBOTermsAndConditionsDialog = new VNextBOModalDialog();
		VNextBOModalDialogValidations.verifyDialogIsDisplayed();
		VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
		Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
				"Privacy Policy",
				"Dialog header hasn't been correct");
		VNextBOModalDialogSteps.clickOkButton();
		VNextBOModalDialogValidations.verifyDialogIsClosed(vNextBOTermsAndConditionsDialog);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
	public void verifyUserCanOpenAndCloseIntercom(String rowID, String description, JSONObject testData) {

		VNextBOROPageStepsNew.openIntercomMessenger();
		VNextBOROWebPageValidationsNew.verifyIntercomMessengerIsOpened();
		VNextBOROPageStepsNew.closeIntercom();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanUsePaging(String rowID, String description, JSONObject testData) {

		VNextBOROPageStepsNew.searchOrdersByRepairStatus("All");
		VNextBOPageSwitcherSteps.clickHeaderNextPageButton();
		VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("2");
		VNextBOPageSwitcherSteps.clickFooterPreviousPageButton();
		VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
		VNextBOPageSwitcherSteps.clickHeaderLastPageButton();
		Assert.assertFalse(VNextBOPageSwitcherValidations.isFooterLastPageButtonClickable(),
				"Bottom Last page button has been clickable.");
		Assert.assertFalse(VNextBOPageSwitcherValidations.isHeaderLastPageButtonClickable(),
				"Top Last page button has been clickable.");
		VNextBOPageSwitcherValidations.verifyTopAndBottomPagingElementsHaveSamePageNumber();
		VNextBOPageSwitcherSteps.clickFooterFirstPageButton();
		Assert.assertFalse(VNextBOPageSwitcherValidations.isHeaderFirstPageButtonClickable(), "Top First page button has been clickable.");
		Assert.assertFalse(VNextBOPageSwitcherValidations.isFooterFirstPageButtonClickable(), "Bottom First page button has been clickable.");
		VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
		VNextBOPageSwitcherSteps.openPageByNumber(3);
		VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("3");
		VNextBOPageSwitcherSteps.clickHeaderFirstPageButton();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanUsePaginationFilter(String rowID, String description, JSONObject testData) {

		VNextBOROPageStepsNew.searchOrdersByRepairStatus("All");
		WaitUtilsWebDriver.waitABit(3000);
		VNextBOPageSwitcherSteps.changeItemsPerPage("20");
		VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("20");
		VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
		VNextBOROWebPageValidationsNew.verifyCorrectRecordsAmountIsDisplayed(20);
		VNextBOPageSwitcherSteps.changeItemsPerPage("50");
		VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("50");
		VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
		VNextBOROWebPageValidationsNew.verifyCorrectRecordsAmountIsDisplayed(50);
		VNextBOPageSwitcherSteps.changeItemsPerPage("100");
		VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("100");
		VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
		VNextBOROWebPageValidationsNew.verifyCorrectRecordsAmountIsDisplayed(100);
		VNextBOPageSwitcherSteps.changeItemsPerPage("10");
		VNextBOPageSwitcherValidations.verifyItemsPerPageNumberIsCorrect("10");
		VNextBOPageSwitcherValidations.verifyOpenedPageNumberIsCorrect("1");
		VNextBOROWebPageValidationsNew.verifyCorrectRecordsAmountIsDisplayed(10);
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeLocation(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOBreadCrumbInteractions.setLocation(data.getSearchLocation());
		Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(data.getSearchLocation()), "Location hasn't been changed.");
		VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeLocationUsingSearch(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOBreadCrumbInteractions.setLocation(data.getSearchLocation(), true);
		Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(data.getSearchLocation()), "Location hasn't been changed.");
		VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanFilterRObyDepartments(String rowID, String description, JSONObject testData) {

		VNextBOROPageStepsNew.filterOrdersByDepartment("Default");
		VNextBOROWebPageValidationsNew.verifyDepartmentsAreCorrectInTheTable("Default");
		VNextBOROPageStepsNew.filterOrdersByDepartment("_dep");
		VNextBOROWebPageValidationsNew.verifyDepartmentsAreCorrectInTheTable("_dep");
		VNextBOROPageStepsNew.filterOrdersByDepartment("ALL");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanFilterRObyPhases(String rowID, String description, JSONObject testData) {

		VNextBOROPageStepsNew.switchToFilterTab("Phases");
		VNextBOROPageStepsNew.filterOrdersByPhase("QC");
		VNextBOROWebPageValidationsNew.verifyPhasesAreCorrectInTheTable("QC");
		VNextBOROPageStepsNew.filterOrdersByPhase("Completed");
		VNextBOROWebPageValidationsNew.verifyPhasesAreCorrectInTheTable("Completed");
		VNextBOROPageStepsNew.filterOrdersByPhase("ALL");
		VNextBOROPageStepsNew.switchToFilterTab("Departments");
    }

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeAndChangeRoDetails(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBORODetailsValidationsNew.verifyOrderDetailsSectionIsDisplayed();
		Utils.goToPreviousPage();
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBOROWebPageValidationsNew.verifyWoTypesAreCorrectInTheTable(data.getWoType());
		VNextBOROPageStepsNew.changeDepartmentForFirstOrder("Default");
		int defaultOrdersAmount = VNextBOROPageStepsNew.getOrdersAmountForDepartment("Default");
		int deptOrdersAmount = VNextBOROPageStepsNew.getOrdersAmountForDepartment("_dep");
		VNextBOROPageStepsNew.changeDepartmentForFirstOrder("_dep");
		VNextBOROWebPageValidationsNew.verifyDepartmentsAreCorrectInTheTable("_dep");
		VNextBOROWebPageValidationsNew.verifyOrdersAmountForDepartmentIsCorrect("Default", defaultOrdersAmount - 1);
		VNextBOROWebPageValidationsNew.verifyOrdersAmountForDepartmentIsCorrect("_dep", deptOrdersAmount + 1);
		VNextBOROPageStepsNew.changeDepartmentForFirstOrder("Default");
		VNextBOROWebPageValidationsNew.verifyDepartmentsAreCorrectInTheTable("Default");
		VNextBOROWebPageValidationsNew.verifyOrdersAmountForDepartmentIsCorrect("Default", defaultOrdersAmount);
		VNextBOROWebPageValidationsNew.verifyOrdersAmountForDepartmentIsCorrect("_dep", deptOrdersAmount);
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCannotChangePoForInvoicedOrders(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		WaitUtilsWebDriver.waitABit(3000);
		VNextBOROWebPageValidationsNew.verifyPoNumberFieldIsNotClickableForFirstOrder();
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoToCheckInCheckOut(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.checkInCheckOutOrder(data.getOrderNumber());
		VNextBOROPageStepsNew.checkInCheckOutOrder(data.getOrderNumber());
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanShowOrCloseNotesToTheLeftOfRo(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.hideDisplayOrderNote(false);
		VNextBOROPageStepsNew.hideDisplayOrderNote(true);
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
	public void verifyPhasesAreUpdatedWithoutRefreshAfterTheirCompletion(String rowID, String description, JSONObject testData) {

		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		String ordersPageUrl = DriverBuilder.getInstance().getDriver().getCurrentUrl();
		parentTabHandle = Utils.getParentTab();
		//second tab
		Utils.openNewTab(ordersPageUrl);
		String secondTabHandle = Utils.getNewTab(parentTabHandle);
		VNextBOROPageStepsNew.switchToFilterTab("Phases");
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		WaitUtilsWebDriver.waitForPageToBeLoaded();
		VNextBORODetailsStepsNew.setPhaseStatusIfNeeded("Detail Station", "Active");
		VNextBORODetailsStepsNew.expandPhaseByName("PDR Station");
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
		VNextBORODetailsStepsNew.collapsePhaseByName("PDR Station");
		Utils.goToPreviousPage();
		int pdrStationOrdersAmount = VNextBOROPageStepsNew.getOrdersAmountForPhaseFromTable("PDR Station");
		int detailStation = VNextBOROPageStepsNew.getOrdersAmountForPhaseFromTable("Detail Station");
		//parent tab
		Utils.switchToWindow(parentTabHandle);
		VNextBOROPageStepsNew.searchOrdersByOrderNumber(data.getOrderNumber());
		VNextBOROPageStepsNew.completeCurrentPhaseForFirstOrder();
		VNextBOROWebPageValidationsNew.verifyPhasesAreCorrectInTheTable("Detail Station");
		WaitUtilsWebDriver.waitABit(2000);
		//second tab
		Utils.switchToWindow(secondTabHandle);
		VNextBOROWebPageValidationsNew.verifyPhasesAreCorrectInTheTable("Detail Station");
		VNextBOROWebPageValidationsNew.verifyOrdersAmountForPhaseInTableIsCorrect("PDR Station", pdrStationOrdersAmount - 1);
		VNextBOROWebPageValidationsNew.verifyOrdersAmountForPhaseInTableIsCorrect("Detail Station", detailStation + 1);
		//parent tab
		Utils.switchToWindow(parentTabHandle);
		VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
		VNextBORODetailsStepsNew.expandPhaseByName("PDR Station");
		VNextBORODetailsStepsNew.setServiceStatusIfNeeded(data.getService(), "Active");
		Utils.goToPreviousPage();
		VNextBOROWebPageValidationsNew.verifyPhasesAreCorrectInTheTable("PDR Station");
		WaitUtilsWebDriver.waitABit(2000);
		//second tab
		Utils.switchToWindow(secondTabHandle);
		VNextBOROWebPageValidationsNew.verifyPhasesAreCorrectInTheTable("PDR Station");
		VNextBOROWebPageValidationsNew.verifyOrdersAmountForPhaseInTableIsCorrect("PDR Station", pdrStationOrdersAmount);
		VNextBOROWebPageValidationsNew.verifyOrdersAmountForPhaseInTableIsCorrect("Detail Station", detailStation);
		Utils.closeNewTab(parentTabHandle);
		VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }
}