package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROSimpleSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.termsconditionspolicy.VNextBOPrivacyPolicyDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.termsconditionspolicy.VNextBOTermsAndConditionsDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOBreadCrumbValidations;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOFooterPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
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

		Assert.assertTrue(VNextBOROPageValidations.isSavedSearchContainerDisplayed(),
				"The search container isn't displayed");
		Assert.assertTrue(VNextBOROPageValidations.isDepartmentDropdownDisplayed(),
				"The department dropdown is not displayed");

        VNextBOROPageInteractions.clickPhasesWide();
		Assert.assertTrue(VNextBOROPageValidations.isSearchInputFieldDisplayed(),
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
	//Test fails due to orders are displayed only for last 30 days, it should be updated with advanced search by custom timeframe
	public void verifyUserCanUsePaging(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		Assert.assertTrue(VNextBOROPageValidations.isPrevButtonDisabled(), "The previous page button is not disabled");
        VNextBOROPageInteractions.clickNextButton();
		Assert.assertFalse(VNextBOROPageValidations.isPrevButtonDisabled(), "The previous page button is not enabled");
        VNextBOROPageInteractions.clickPrevButton();
		Assert.assertTrue(VNextBOROPageValidations.isPrevButtonDisabled(), "The previous page button is not disabled");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
	public void verifyUserCanFilterRObyDepartments(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.setDepartmentsTabActive();

		Assert.assertTrue(VNextBOROPageValidations.isDepartmentsTabDisplayed());
		Assert.assertEquals(VNextBOROPageInteractions.getAllDepartmentsSum(), VNextBOROPageInteractions.getDepartmentsValues(),
				"The departments values are not calculated properly");
		final List<Integer> departmentValues = new ArrayList<>();
		departmentValues.add(Integer.valueOf(VNextBOROPageInteractions.getDepartmentsValue(1)));
//        departmentValues.add(Integer.valueOf(repairOrdersPage.getDepartmentsValue(2)));
		if (VNextBOROPageValidations.isDepartmentNarrowScreenClickable()) {
			System.out.println("Narrow Screen");
			for (int i = 0, j = 1; i < departmentValues.size(); i++, j++) {
				System.out.println(j);
                VNextBOROPageInteractions.clickDepartmentForNarrowScreen(data.getDepartments().get(j));
				if (departmentValues.get(i) == 0) {
					Assert.assertFalse(VNextBOROPageValidations.isTableDisplayed(), "The table shouldn't be displayed");
					Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(),
							"The text notification is not displayed");
				} else {
					Assert.assertTrue(departmentValues.get(0) >= VNextBOROPageInteractions.getNumOfOrdersOnPage(),
							"The departments repair orders number in table " +
									"is less than value displayed in menu container");
				}
			}
		} else {
			System.out.println("Wide Screen");
			for (int i = 0, j = 1; i < departmentValues.size(); i++, j++) {
				System.out.println(j);
                VNextBOROPageInteractions.clickDepartmentForWideScreen(data.getDepartments().get(j));
				if (departmentValues.get(i) == 0) {
					Assert.assertFalse(VNextBOROPageValidations.isTableDisplayed(), "The table shouldn't be displayed");
					Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(),
							"The text notification is not displayed");
				} else {
					Assert.assertTrue(departmentValues.get(0) >= VNextBOROPageInteractions.getNumOfOrdersOnPage(),
							"The departments repair orders number in table " +
									"is less than value displayed in menu container");
				}
			}
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
	public void verifyUserCanFilterRObyPhases(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.setPhasesTabActive();

		Assert.assertTrue(VNextBOROPageValidations.isPhasesTabDisplayed());
		Assert.assertEquals(VNextBOROPageInteractions.getAllPhasesSum(), VNextBOROPageInteractions.getPhasesValues(),
				"The phases values are not calculated properly");
		final List<Integer> phasesValues = new ArrayList<>();
		phasesValues.add(Integer.valueOf(VNextBOROPageInteractions.getPhasesValue(1)));
		phasesValues.add(Integer.valueOf(VNextBOROPageInteractions.getPhasesValue(2)));
//        phasesValues.add(Integer.valueOf(repairOrdersPage.getPhasesValue(3)));

		if (VNextBOROPageValidations.isPhasesNarrowScreenClickable()) {
			System.out.println("Narrow Screen");
			for (int i = 0, j = 1; i < phasesValues.size(); i++, j++) {
                VNextBOROPageInteractions.clickPhaseForNarrowScreen(data.getPhases().get(j));
//                if (phasesValues.get(i) == 0) { todo uncomment after bug #70126 fix!!!
//                    Assert.assertFalse(repairOrdersPage.isTableDisplayed(), "The table shouldn't be displayed");
//                    Assert.assertTrue(repairOrdersPage.isTextNoRecordsDisplayed(),
//                            "The text notification is not displayed");
//                } else {
//                    Assert.assertTrue(phasesValues.get(0) >= repairOrdersPage.getNumOfOrdersOnPage(),
//                            "The phases repair orders number in table " +
//                                    "is less than value displayed in menu container");
//                }
			}
		} else {
			System.out.println("Wide Screen");
			for (int i = 0, j = 1; i < phasesValues.size(); i++, j++) {
				System.out.println(j);
                VNextBOROPageInteractions.clickPhaseForWideScreen(data.getPhases().get(j));
//                if (phasesValues.get(i) == 0) { todo uncomment after bug #70126 fix!!!
//                    Assert.assertFalse(repairOrdersPage.isTableDisplayed(), "The table shouldn't be displayed");
//                    Assert.assertTrue(repairOrdersPage.isTextNoRecordsDisplayed(),
//                            "The text notification is not displayed");
//                } else {
//                    Assert.assertTrue(phasesValues.get(0) >= repairOrdersPage.getNumOfOrdersOnPage(),
//                            "The phases repair orders number in table " +
//                                    "is less than value displayed in menu container");
//                }
			}
		}
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
		VNextBOROSimpleSearchSteps.searchByText(data.getVinNum());

		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getVinNum()),
				"The work order is not displayed after search by VIN after clicking the 'Search' icon");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
	public void verifyUserCanSeeAndChangeRoDetails(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(data.getLocation()), "The location hasn't been set");

		final String vinNum = data.getVinNum();
		VNextBOROSimpleSearchSteps.searchByText(vinNum);
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(vinNum),
				"The work order is not displayed after search by VIN after clicking the 'Search' icon");

		Assert.assertEquals(VNextBOROPageInteractions.getTableTitleDisplayed(0), data.getTitle(),
				"The table title is incorrect");
        VNextBOROPageInteractions.clickWoLink(vinNum);
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(),
				"The RO details section hasn't been displayed");
        Utils.goToPreviousPage();

        VNextBOROPageInteractions.hideNoteForWorkOrder(vinNum);
		Assert.assertTrue(VNextBOROPageValidations.isWoTypeDisplayed(data.getWoType()));

		final List<String> departments = data.getDepartments();
		for (String department : departments) {
			System.out.println(department);
            VNextBOROPageInteractions.setWoDepartment(vinNum, department);
			Assert.assertEquals(VNextBOROPageInteractions.getWoDepartment(vinNum), department,
					"The WO department hasn't been set");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
	public void verifyUserCannotChangePoForInvoicedOrders(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation(), true);

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByOrderNumber(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		Assert.assertFalse(VNextBOROPageValidations.isPoNumClickable(), "The PO# shouldn't be clickable");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 14)
	public void verifyUserCanChangeStatusOfRoToCheckInCheckOut(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.openOtherDropDownMenu(data.getOrderNumber());
		//todo finish after TC update
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 15)
	public void verifyUserCanShowOrCloseNotesToTheLeftOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.revealNoteForWorkOrder(data.getOrderNumber());
        VNextBOROPageInteractions.hideNoteForWorkOrder(data.getOrderNumber());
        VNextBOROPageInteractions.revealNoteForWorkOrder(data.getOrderNumber());
        VNextBOROPageInteractions.clickXIconToCloseNoteForWorkOrder(data.getOrderNumber());
		Assert.assertFalse(VNextBOROPageValidations.isNoteForWorkOrderDisplayed(data.getOrderNumber()),
				"The note for work order has not been closed after clicking the 'X' icon");
	}
}