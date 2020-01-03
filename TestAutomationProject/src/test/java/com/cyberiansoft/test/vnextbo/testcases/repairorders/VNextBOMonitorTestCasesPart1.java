package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORODetailsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROSimpleSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.termsconditionspolicy.VNextBOPrivacyPolicyDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.termsconditionspolicy.VNextBOTermsAndConditionsDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOPageSwitcherValidations;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOBreadCrumbValidations;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOFooterPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextBOMonitorTestCasesPart1 extends BaseTestCase {

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTD();
	}

	@AfterMethod
	public void refreshPage() {
		Utils.refreshPage();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
	public void verifyUserCanOpenMonitorWithFullSetOfElements(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		Assert.assertTrue(VNextBOROPageValidations.isSavedSearchContainerDisplayed(true),
				"The search container isn't displayed");
		Assert.assertTrue(VNextBOROPageValidations.isDepartmentDropdownDisplayed(true),
				"The department dropdown is not displayed");

        VNextBOROPageInteractions.clickPhasesWide();
		Assert.assertTrue(VNextBOROPageValidations.isSearchInputFieldDisplayed(true),
				"The search input field isn't displayed");
		Assert.assertTrue(VNextBOROPageValidations.isAdvancedSearchCaretDisplayed(),
				"The advanced search caret isn't displayed");
		Assert.assertTrue(VNextBOFooterPanelValidations.isFooterDisplayed(),
				"The footer is not displayed");
		Assert.assertTrue(VNextBOFooterPanelValidations.footerContains(data.getCopyright()),
				"The footer doesn't contain text " + data.getCopyright());
		Assert.assertTrue(VNextBOFooterPanelValidations.footerContains(data.getAMT()),
				"The footer doesn't contain text " + data.getAMT());
		Assert.assertTrue(VNextBOFooterPanelValidations.isTermsAndConditionsLinkDisplayed(),
				"The footer doesn't contain Terms and Conditions link");
		Assert.assertTrue(VNextBOFooterPanelValidations.isPrivacyPolicyLinkDisplayed(),
				"The footer doesn't contain Privacy Policy link");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
	public void verifyUserCanOpenAndCloseTermsAndConditions(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOTermsAndConditionsDialogSteps.openAndRejectTermsAndConditions();
        VNextBOTermsAndConditionsDialogSteps.openAndAcceptTermsAndConditions();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
	public void verifyUserCanOpenAndClosePrivacyPolicy(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOPrivacyPolicyDialogSteps.openAndRejectPrivacyPolicy();
        VNextBOPrivacyPolicyDialogSteps.openAndAcceptPrivacyPolicy();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
	public void verifyUserCanOpenAndCloseIntercom(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROPageInteractions.openIntercom();
        VNextBOROPageInteractions.closeIntercom();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
	public void verifyUserCanChangeLocation(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getSearchLocation());
		Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSearched(data.getSearchLocation()),
				"The location is not searched");
        VNextBOROPageInteractions.clickLocationInDropDown(data.getSearchLocation());
		Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationExpanded(), "The location is not expanded");
        VNextBOROPageInteractions.clickSearchTextToCloseLocationDropDown();
		Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSelected(data.getSearchLocation()), "The location has been changed");

		Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSearched(data.getLocation()),
				"The location is not searched");
        VNextBOROPageInteractions.clickLocationInDropDown(data.getLocation());

		Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSelected(data.getLocation()), "The location hasn't been selected");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
	public void verifyUserCanChangeLocationUsingSearch(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
		Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSearched(data.getSearchLocation()),
				"The location is not searched");
        VNextBOROPageInteractions.clickLocationInDropDown(data.getLocation());
		Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSelected(data.getLocation()), "The location hasn't been selected");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
	public void verifyUserCanUsePaging(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogSteps.search();

        Assert.assertFalse(VNextBOPageSwitcherValidations.isHeaderPreviousPageButtonClickable(),
                "The previous page button is not disabled");
        VNextBOPageSwitcherSteps.clickHeaderNextPageButton();
        Assert.assertTrue(VNextBOPageSwitcherValidations.isHeaderPreviousPageButtonClickable(),
                "The previous page button is not enabled");
        VNextBOPageSwitcherSteps.clickHeaderPreviousPageButton();
        Assert.assertFalse(VNextBOPageSwitcherValidations.isHeaderPreviousPageButtonClickable(),
                "The previous page button is not disabled");
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanFilterRObyDepartments(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.setDepartmentsTabActive();
        Assert.assertTrue(VNextBOROPageValidations.isDepartmentsTabDisplayed(true));
        Assert.assertEquals(VNextBOROPageInteractions.getAllDepartmentsSum(), VNextBOROPageInteractions
                        .getDepartmentsValuesSum(), "The departments values are not calculated properly");
        final List<Integer> departmentValues = VNextBOROPageInteractions.getDepartmentsValues().subList(0, 3);

        VNextBOROPageValidations.verifyDepartmentsForWideScreen(departmentValues, data);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanFilterRObyPhases(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.setPhasesTabActive();
        Assert.assertTrue(VNextBOROPageValidations.isPhasesTabDisplayed(true));
        Assert.assertEquals(VNextBOROPageInteractions.getAllPhasesSum(), VNextBOROPageInteractions.getPhasesValuesSum(),
                "The phases values are not calculated properly");
        final List<Integer> phasesValues = VNextBOROPageInteractions.getPhasesValues().subList(0, 4);

        VNextBOROPageValidations.verifyPhasesForWideScreen(phasesValues, data);
    }

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
	public void verifyUserCanSelectLocation(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(data.getLocation()), "The location hasn't been set");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
	public void verifyUserCanSearchWoUsingSearchFilter(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(data.getLocation()), "The location hasn't been set");
		VNextBOROSimpleSearchSteps.search(data.getVinNum());

		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getVinNum(), true),
				"The work order is not displayed after search by VIN after clicking the 'Search' icon");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
	public void verifyUserCanSeeAndChangeRoDetails(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(data.getLocation()), "The location hasn't been set");

		final String vinNum = data.getVinNum();
		VNextBOROSimpleSearchSteps.search(vinNum);
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(vinNum, true),
				"The work order is not displayed after search by VIN after clicking the 'Search' icon");

		Assert.assertEquals(VNextBOROPageInteractions.getTableTitleDisplayed(0), data.getTitle(),
				"The table title is incorrect");
        VNextBOROPageInteractions.clickWoLink(vinNum);
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(),
				"The RO details section hasn't been displayed");
        Utils.goToPreviousPage();

        VNextBOROPageInteractions.hideNoteForWorkOrder(vinNum);
		Assert.assertTrue(VNextBOROPageValidations.isWoTypeDisplayed(data.getWoType()));

        final String department = data.getDepartments().get(0);
        VNextBOROPageInteractions.setWoDepartment(vinNum, department);
        Assert.assertEquals(VNextBOROPageInteractions.getWoDepartment(vinNum), department,
                "The WO department hasn't been set");
    }

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
	public void verifyUserCannotChangePoForInvoicedOrders(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation(), true);

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        Assert.assertTrue(VNextBOROPageValidations.isPoNumClickable(false), "The PO# shouldn't be clickable");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 13)
	public void verifyUserCanChangeStatusOfRoToCheckInCheckOut(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.search(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getOrderNumber(), true));
        VNextBOROPageInteractions.openOtherDropDownMenu(data.getOrderNumber());
		//todo finish after TC update
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 14)
	public void verifyUserCanShowOrCloseNotesToTheLeftOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.search(data.getOrderNumber());
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getOrderNumber(), true),
                "The work order is not displayed after search by clicking the 'Search' icon");

        VNextBOROPageInteractions.revealNoteForWorkOrder(data.getOrderNumber());
        VNextBOROPageInteractions.hideNoteForWorkOrder(data.getOrderNumber());
        VNextBOROPageInteractions.revealNoteForWorkOrder(data.getOrderNumber());
        VNextBOROPageInteractions.clickXIconToCloseNoteForWorkOrder(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isNoteForWorkOrderDisplayed(data.getOrderNumber(), false),
				"The note for work order has not been closed after clicking the 'X' icon");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 15)
	public void verifyPhasesAreUpdatedWithoutRefreshAfterTheirCompletion(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.search(data.getOrderNumber());
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getOrderNumber(), true),
                "The work order is not displayed after search by clicking the 'Search' icon");
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        Utils.openNewTab(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        final String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        final String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();
        VNextBOLoginSteps.userLogin(userName, userPassword);
        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.search(data.getOrderNumber());
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getOrderNumber(), true),
                "The work order is not displayed after search by clicking the 'Search' icon");
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());
        VNextBORODetailsPageValidations.verifyServiceIsDisplayedForCollapsedPhase(data.getService(), data.getPhase());
        VNextBORODetailsPageSteps.setServiceStatusForService(data.getService(),
                OrderMonitorServiceStatuses.ACTIVE.getValue());
        VNextBORODetailsPageSteps.openRoPageByClickingBreadCrumbRo();
        VNextBOROPageInteractions.completeCurrentPhase(data.getOrderNumber());
    }
}