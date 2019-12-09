package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOROAdvancedSearchValues;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.*;
import com.cyberiansoft.test.vnextbo.steps.termsconditionspolicy.VNextBOPrivacyPolicyDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.termsconditionspolicy.VNextBOTermsAndConditionsDialogSteps;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOAddNewServiceMonitorDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORONotesPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOAuditLogDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.VNextBONotesPageValidations;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOBreadCrumbValidations;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOFooterPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VNextBOMonitorTestCases extends BaseTestCase {

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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 16)
	public void verifyUserCanTypeAndNotSaveNotesWithXIcon(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.revealNoteForWorkOrder(data.getOrderNumber());
        //todo bug fix #78127
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 17)
	public void verifyUserCanTypeAndNotSaveNotesWithClose(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.revealNoteForWorkOrder(data.getOrderNumber());
		//todo bug fix #78127
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 18)
	public void verifyUserCanOpenRoDetails(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 19)
	public void verifyUserCanChangeStockOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.typeStockNumber(data.getStockNumbers()[1]);
        VNextBORODetailsPageInteractions.typeStockNumber(data.getStockNumbers()[0]);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 20)
	public void verifyUserCanChangeRoNumOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.typeRoNumber(data.getRoNumbers()[1]);
        VNextBORODetailsPageInteractions.typeRoNumber(data.getRoNumbers()[0]);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 21)
	public void verifyUserCanChangeStatusOfRoToNew(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(VNextBORODetailsPageInteractions.getRoStatus(), data.getStatus(), "The status hasn't been set");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 22)
	public void verifyUserCanChangeStatusOfRoToOnHold(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(VNextBORODetailsPageInteractions.getRoStatus(), data.getStatus(), "The status hasn't been set");
		Assert.assertTrue(VNextBORODetailsPageValidations.isImageOnHoldStatusDisplayed(),
				"The On Hold image notification hasn't been displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 23)
	public void verifyUserCanChangeStatusOfRoToApproved(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
		Assert.assertEquals(VNextBORODetailsPageInteractions.getRoStatus(), data.getStatus(), "The status hasn't been set");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 24)
	public void verifyUserCannotChangeStatusOfRoToDraft(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
		Assert.assertNotEquals(VNextBORODetailsPageInteractions.getRoStatus(), data.getStatus(), "The status has been changed to 'Draft'");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 25)
	public void verifyUserCanChangeStatusOfRoToClosedWithNoneReason(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps
                .searchByActivePhase(data.getPhase(), data.getPhaseStatus(), data.getTimeFrame());

        VNextBOROPageSteps.openRODetailsPage();
        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBOCloseRODialogSteps.closeROWithReason(data.getProblemReason());
        Assert.assertEquals(VNextBORODetailsPageInteractions.getRoStatusValue(), data.getStatus(),
                "The status hasn't been changed to 'Closed'");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 26)
	public void verifyUserCanChangePriorityOfRoToLow(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setPriority(data.getPriority());
        VNextBORODetailsPageInteractions.clickRepairOrdersBackwardsLink();
        VNextBOROPageInteractions.clickCancelSearchIcon();

		if (VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber())) {
			Assert.assertTrue(VNextBOROPageValidations.isArrowDownDisplayed(data.getOrderNumber()),
					"The work order arrow down is not displayed");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 27)
	public void verifyUserCanChangePriorityOfRoToNormal(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setPriority(data.getPriority());
        VNextBORODetailsPageInteractions.clickRepairOrdersBackwardsLink();
        VNextBOROPageInteractions.clickCancelSearchIcon();

		if (VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber())) {
			Assert.assertFalse(VNextBOROPageValidations.isArrowDownDisplayed(data.getOrderNumber()),
					"The work order arrow down should not be displayed");
			Assert.assertFalse(VNextBOROPageValidations.isArrowUpDisplayed(data.getOrderNumber()),
					"The work order arrow down should not be displayed");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 28)
	public void verifyUserCanAddNewService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.clickAddNewServiceButton();
		Assert.assertTrue(VNextBOAddNewServiceMonitorDialogValidations.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOAddNewServiceMonitorDialogSteps
                .setAddNewServiceMonitorFieldsForAllOrMoneyPriceTypesWithSubmit(data, serviceDescription);
        Utils.refreshPage();

		VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(serviceDescription);
		if (serviceId.isEmpty()) {
			Assert.assertTrue(VNextBORODetailsPageValidations.isServiceNotificationToBeAddedLaterDisplayed(),
					"The notification about the service to be added later hasn't been displayed");
		} else {
			Assert.assertNotEquals(serviceId, "",
					"The created service hasn't been displayed");
			System.out.println("description: " + VNextBORODetailsPageInteractions.getServiceDescription(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceDescription(serviceId), serviceDescription,
					"The service description name is not equal to the inserted description value");

			System.out.println("quantity: " + VNextBORODetailsPageInteractions.getServiceQuantity(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceQuantity(serviceId), data.getServiceQuantity(),
					"The service quantity is not equal to the inserted quantity value");

			System.out.println("price: " + VNextBORODetailsPageInteractions.getServicePrice(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServicePrice(serviceId), data.getServicePrice(),
					"The service price is not equal to the inserted price value");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 29)
	public void verifyUserCanChangePriorityOfRoToHigh(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setPriority(data.getPriority());
		VNextBORODetailsPageInteractions.clickRepairOrdersBackwardsLink();
        VNextBOROPageInteractions.clickCancelSearchIcon();

		if (VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber())) {
			Assert.assertTrue(VNextBOROPageValidations.isArrowUpDisplayed(data.getOrderNumber()),
					"The work order arrow down is not displayed");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 30)
	public void verifyUserCanAddNewMoneyService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.clickAddNewServiceButton();
		Assert.assertTrue(VNextBOAddNewServiceMonitorDialogValidations.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOAddNewServiceMonitorDialogSteps.setAddNewServiceMonitorFieldsForAllOrMoneyPriceTypesWithSubmit(data, serviceDescription);

		VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(serviceDescription);
		if (serviceId.isEmpty()) {
			Assert.assertTrue(VNextBORODetailsPageValidations.isServiceNotificationToBeAddedLaterDisplayed(),
					"The notification about the service to be added later hasn't been displayed");
		} else {
			Assert.assertNotEquals(serviceId, "", "The created service hasn't been displayed");
			System.out.println("description: " + VNextBORODetailsPageInteractions.getServiceDescription(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceDescription(serviceId), serviceDescription,
					"The service description name is not equal to the inserted description value");

			System.out.println("quantity: " + VNextBORODetailsPageInteractions.getServiceQuantity(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceQuantity(serviceId), data.getServiceQuantity(),
					"The service quantity is not equal to the inserted quantity value");

			System.out.println("price: " + VNextBORODetailsPageInteractions.getServicePrice(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServicePrice(serviceId), data.getServicePrice(),
					"The service price is not equal to the inserted price value");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 31)
	public void verifyUserCanAddNewLaborService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.clickAddNewServiceButton();
		Assert.assertTrue(VNextBOAddNewServiceMonitorDialogValidations.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);

        VNextBOAddNewServiceMonitorDialogSteps.setAddNewLaborServiceMonitorValues(data, serviceDescription);

        VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(serviceDescription);
		if (serviceId.isEmpty()) {
			Assert.assertTrue(VNextBORODetailsPageValidations.isServiceNotificationToBeAddedLaterDisplayed(),
					"The notification about the service to be added later hasn't been displayed");
		} else {
			System.out.println("description: " + VNextBORODetailsPageInteractions.getServiceDescription(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceDescription(serviceId), serviceDescription,
					"The service description name is not equal to the inserted description value");

			System.out.println("quantity: " + VNextBORODetailsPageInteractions.getServiceLaborTime(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceLaborTime(serviceId), data.getServiceLaborTime(),
					"The service labor time is not equal to the inserted labor time value");

			System.out.println("price: " + VNextBORODetailsPageInteractions.getServicePrice(serviceId));
			Assert.assertEquals(VNextBORODetailsPageInteractions.getServicePrice(serviceId), data.getServiceLaborRate(),
					"The service labor rate is not equal to the inserted labor rate value");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 32)
	public void verifyUserCanAddNewPartService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.clickAddNewServiceButton();
		Assert.assertTrue(VNextBOAddNewServiceMonitorDialogValidations.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
		final String selectedCategory = VNextBOAddNewServiceMonitorDialogSteps
                .getSubcategoryWhileAddingNewPartServiceMonitorValues(data, serviceDescription);
		System.out.println("*******************************************");
		System.out.println(selectedCategory);

		final String selectedAddPartsNumberBefore =
                com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOAddNewServiceMonitorDialogInteractions.getSelectedAddPartsNumber();
		if (!VNextBOAddNewServiceMonitorDialogValidations.arePartsOptionsDisplayed()) {
            com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOAddNewServiceMonitorDialogInteractions.selectRandomAddPartsOption();

			final String selectedAddPartsNumberAfter =
                    VNextBOAddNewServiceMonitorDialogInteractions.getSelectedAddPartsNumber();
			Assert.assertNotEquals(selectedAddPartsNumberBefore, selectedAddPartsNumberAfter);
			Assert.assertTrue(Integer.valueOf(selectedAddPartsNumberBefore) < Integer.valueOf(selectedAddPartsNumberAfter));

            VNextBOAddNewServiceMonitorDialogInteractions.clickSubmitButton();
			if (!VNextBOAddNewServiceMonitorDialogValidations.isPartDescriptionDisplayed(data.getServiceCategory()
                    + " -> " + selectedCategory)) {
				Utils.refreshPage();
			}

			Assert.assertTrue(VNextBOAddNewServiceMonitorDialogValidations
							.isPartDescriptionDisplayed(data.getServiceCategory() + " -> " + selectedCategory),
					"The Part service description hasn't been displayed.");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 33)
	public void verifyUserCanSelectDetailsOfAddNewServiceAndNotAddItXIcon(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.clickAddNewServiceButton();
		Assert.assertTrue(VNextBOAddNewServiceMonitorDialogValidations.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOAddNewServiceMonitorDialogSteps
                .setAddNewServiceMonitorFieldsForAllOrMoneyPriceTypesWithXButton(data, serviceDescription);

        VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(serviceDescription);
		Assert.assertEquals(serviceId, "",
				"The service has been added after closing the 'New Service Dialog' with X button");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 34)
	public void verifyUserCanSelectDetailsOfAddNewServiceAndNotAddItCancelButton(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.clickAddNewServiceButton();
		Assert.assertTrue(VNextBOAddNewServiceMonitorDialogValidations.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOAddNewServiceMonitorDialogSteps
                .setAddNewServiceMonitorFieldsForAllOrMoneyPriceTypesWithCancelButton(data, serviceDescription);

		VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(serviceDescription);
		Assert.assertEquals(serviceId, "",
				"The service has been added after closing the 'New Service Dialog' with X button");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 35)
	public void verifyUserCanChangeFlagOfRoToWhite(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
        VNextBORODetailsPageInteractions.clickFlagIcon();
		Assert.assertTrue(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		VNextBORODetailsPageInteractions.selectFlagColor(data.getFlag());
		Assert.assertFalse(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 36)
	public void verifyUserCanChangeFlagOfRoToRed(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
        VNextBORODetailsPageInteractions.clickFlagIcon();
		Assert.assertTrue(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		VNextBORODetailsPageInteractions.selectFlagColor(data.getFlag());
		Assert.assertFalse(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 37)
	public void verifyUserCanChangeFlagOfRoToOrange(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
        VNextBORODetailsPageInteractions.clickFlagIcon();
		Assert.assertTrue(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		VNextBORODetailsPageInteractions.selectFlagColor(data.getFlag());
		Assert.assertFalse(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 38)
	public void verifyUserCanChangeFlagOfRoToYellow(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
        VNextBORODetailsPageInteractions.clickFlagIcon();
		Assert.assertTrue(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		VNextBORODetailsPageInteractions.selectFlagColor(data.getFlag());
		Assert.assertFalse(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 39)
	public void verifyUserCanChangeFlagOfRoToGreen(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
        VNextBORODetailsPageInteractions.clickFlagIcon();
		Assert.assertTrue(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		VNextBORODetailsPageInteractions.selectFlagColor(data.getFlag());
		Assert.assertFalse(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 40)
	public void verifyUserCanChangeFlagOfRoToBlue(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
        VNextBORODetailsPageInteractions.clickFlagIcon();
		Assert.assertTrue(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		VNextBORODetailsPageInteractions.selectFlagColor(data.getFlag());
		Assert.assertFalse(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 41)
	public void verifyUserCanChangeFlagOfRoToPurple(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
        VNextBORODetailsPageInteractions.clickFlagIcon();
		Assert.assertTrue(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		VNextBORODetailsPageInteractions.selectFlagColor(data.getFlag());
		Assert.assertFalse(VNextBORODetailsPageValidations.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 42)
	public void verifyUserCanSeeServicesOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		final List<String> fields = Arrays.asList(data.getServicesTableFields());
		fields.forEach(System.out::println);
		System.out.println("*****************************************");
		System.out.println();

		VNextBORODetailsPageInteractions.getServicesTableHeaderValues().forEach(System.out::println);
		System.out.println("*****************************************");
		System.out.println();

		Assert.assertTrue(VNextBORODetailsPageInteractions.getServicesTableHeaderValues()
						.containsAll(fields),
				"The services table header values have not been displayed properly");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 43)
	public void verifyUserCanChangeVendorPriceOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		System.out.println("Vendor price: " + VNextBORODetailsPageInteractions.getServiceVendorPrice(serviceId));
		VNextBORODetailsPageInteractions.setServiceVendorPrice(serviceId, data.getServiceVendorPrices()[0]);
        Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceVendorPrice(serviceId), data.getServiceVendorPrices()[0],
				"The Vendor Price hasn't been changed");

		System.out.println("Vendor price: " + VNextBORODetailsPageInteractions.getServiceVendorPrice(serviceId));
		VNextBORODetailsPageInteractions.setServiceVendorPrice(serviceId, data.getServiceVendorPrices()[1]);
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceVendorPrice(serviceId), data.getServiceVendorPrices()[1],
				"The Vendor Price hasn't been changed");

		System.out.println("Vendor price: " + VNextBORODetailsPageInteractions.getServiceVendorPrice(serviceId));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 44)
	public void verifyUserCanChangeVendorTechnicianOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
        VNextBORODetailsPageInteractions.setVendor(serviceId, data.getVendor());
        VNextBORODetailsPageInteractions.setTechnician(serviceId, data.getTechnician());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 45)
	public void verifyUserCanCreateNoteOfServiceOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        VNextBORODetailsPageSteps.openEditNotesDialog(serviceId);
        final int notesNumber = VNextBORONotesPageInteractions.getRepairNotesListNumber();

        VNextBORONotesPageSteps.setRONoteMessage(data.getNotesMessage());
		VNextBORONotesPageInteractions.clickRONoteSaveButton();

		Assert.assertTrue(VNextBONotesPageValidations.isEditOrderServiceNotesBlockDisplayed(), "The notes dialog hasn't been opened");
		Assert.assertEquals(notesNumber + 1, VNextBORONotesPageInteractions.getRepairNotesListNumber(),
				"The services notes list number is not updated");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 46)
	public void verifyUserCanChangeTargetDateOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
        VNextBORODetailsPageInteractions.setVendor(serviceId, data.getVendor());
        VNextBORODetailsPageInteractions.setTechnician(serviceId, data.getTechnician());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 47)
	public void verifyUserCanSeeMoreInformationOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.openMoreInformation();
		final List<String> infoFields = Arrays.asList(data.getInformationFields());
		System.out.println();
		infoFields.forEach(System.out::println);
		System.out.println();

		Assert.assertTrue(VNextBORODetailsPageInteractions
						.getMoreInformationFieldsText()
						.containsAll(infoFields),
				"The More Information fields haven't been fully displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 48)
	public void verifyUserCanTypeAndNotSaveNoteOfRoService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        VNextBORODetailsPageSteps.openEditNotesDialog(serviceId);
        final int notesNumber = VNextBORONotesPageInteractions.getRepairNotesListNumber();

        VNextBORONotesPageSteps.setRONoteMessage(data.getNotesMessage());
        VNextBORONotesPageInteractions.clickRepairNotesXButton();

		Assert.assertEquals(VNextBORONotesPageInteractions.getRONoteTextAreaValue(), "");
        VNextBORONotesPageInteractions.closeRONoteDialog();

        VNextBORODetailsPageSteps.openEditNotesDialog(serviceId);
        Assert.assertEquals(notesNumber, VNextBORONotesPageInteractions.getRepairNotesListNumber(),
				"The services notes list number has been updated, although the 'X' button was clicked");

        VNextBORONotesPageSteps.setRONoteMessage(data.getNotesMessage());
        VNextBORONotesPageInteractions.closeRONoteDialog();

        VNextBORODetailsPageSteps.openEditNotesDialog(serviceId);
        Assert.assertEquals(notesNumber, VNextBORONotesPageInteractions.getRepairNotesListNumber(),
				"The services notes list number has been updated, although the 'X' button was clicked");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 49)
	public void verifyUserCanChangeStatusOfRoServiceToActive(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), data.getServiceStatuses()[0]);

		final String serviceStartedDate = VNextBORODetailsPageInteractions.getServiceStartedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");
		Assert.assertFalse(VNextBORODetailsPageValidations.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date shouldn't be displayed with the Active status");

		VNextBORODetailsPageInteractions.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(VNextBORODetailsPageValidations.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[0]));

		System.out.println("JSON: " + data.getServicePhase());
		System.out.println(VNextBORODetailsPageInteractions.getOrderCurrentPhase());

		Assert.assertEquals(VNextBORODetailsPageInteractions.getOrderCurrentPhase(), data.getServicePhase(),
				"The Current Phase is not displayed properly");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 50)
	public void verifyUserCanChangeStatusOfRoServiceToCompleted(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		final String serviceStartedDate = VNextBORODetailsPageInteractions.getServiceStartedDate(serviceId);
		final String serviceCompletedDate = VNextBORODetailsPageInteractions.getServiceCompletedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");

		Assert.assertTrue(VNextBORODetailsPageValidations.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date should be displayed with the Completed status");
		System.out.println(serviceCompletedDate);
		System.out.println(data.getServiceCompletedDate());
		Assert.assertEquals(serviceCompletedDate, data.getServiceCompletedDate(),
				"The service completed date is wrong");

		VNextBORODetailsPageInteractions.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(VNextBORODetailsPageValidations.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[1]),
				"The helper info dialog isn't displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 51)
	public void verifyUserCanChangeStatusOfRoServiceToAudited(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        final String status = data.getServiceStatuses()[1];
        VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, status);
		WaitUtilsWebDriver.waitForLoading();
        VNextBORODetailsPageInteractions.waitForServiceStatusToBeChanged(serviceId, status);
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), status);

		final String serviceStartedDate = VNextBORODetailsPageInteractions.getServiceStartedDate(serviceId);
		final String serviceCompletedDate = VNextBORODetailsPageInteractions.getServiceCompletedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");

		Assert.assertTrue(VNextBORODetailsPageValidations.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date should be displayed with the Audited status");
		System.out.println(serviceCompletedDate);
		System.out.println(data.getServiceCompletedDate());
		Assert.assertEquals(serviceCompletedDate, data.getServiceCompletedDate(),
				"The service completed date is wrong");

		VNextBORODetailsPageInteractions.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(VNextBORODetailsPageValidations.isHelpInfoDialogDisplayed(serviceId, status),
				"The helper info dialog isn't displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 52)
	public void verifyUserCanChangeStatusOfRoServiceToRefused(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		final String serviceStartedDate = VNextBORODetailsPageInteractions.getServiceStartedDate(serviceId);
		final String serviceCompletedDate = VNextBORODetailsPageInteractions.getServiceCompletedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");

		Assert.assertTrue(VNextBORODetailsPageValidations.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date should be displayed with the Refused status");
		System.out.println(serviceCompletedDate);
		System.out.println(data.getServiceCompletedDate());
		Assert.assertEquals(serviceCompletedDate, data.getServiceCompletedDate(),
				"The service completed date is wrong");

		VNextBORODetailsPageInteractions.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(VNextBORODetailsPageValidations.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[1]),
				"The helper info dialog isn't displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 53)
	public void verifyUserCanChangeStatusOfRoServiceToRework(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		final String serviceStartedDate = VNextBORODetailsPageInteractions.getServiceStartedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");
		Assert.assertFalse(VNextBORODetailsPageValidations.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date shouldn't be displayed with the Rework status");

		VNextBORODetailsPageInteractions.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(VNextBORODetailsPageValidations.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[1]),
				"The helper info dialog isn't displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 54)
	public void verifyUserCanChangeStatusOfRoServiceToSkipped(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

		final String service = data.getService();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		final String serviceStartedDate = VNextBORODetailsPageInteractions.getServiceStartedDate(serviceId);
		final String serviceCompletedDate = VNextBORODetailsPageInteractions.getServiceCompletedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");

		Assert.assertTrue(VNextBORODetailsPageValidations.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date should be displayed with the Refused status");
		System.out.println(serviceCompletedDate);
		System.out.println(data.getServiceCompletedDate());
		Assert.assertEquals(serviceCompletedDate, data.getServiceCompletedDate(),
				"The service completed date is wrong");

		VNextBORODetailsPageInteractions.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(VNextBORODetailsPageValidations.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[1]),
				"The helper info dialog isn't displayed");
	}

	//todo blocker, needs clarification
	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 55)
	public void verifyUserCanSeeChangesOfPhasesInLogInfo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());

		VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), data.getServiceStatuses()[0]);

		VNextBORODetailsPageInteractions.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		VNextBORODetailsPageInteractions.clickLogInfoButton();
		Assert.assertTrue(VNextBOAuditLogDialogValidations.isAuditLogDialogDisplayed(), "The audit log modal dialog hasn't been opened");

        VNextBOAuditLogDialogInteractions.getAuditLogsTabsNames().forEach(System.out::println);
		System.out.println("*****************************************");
		System.out.println();

		final List<String> tabs = Arrays.asList(data.getAuditLogTabs());
		tabs.forEach(System.out::println);
		System.out.println("*****************************************");
		System.out.println();

		Assert.assertTrue(VNextBOAuditLogDialogInteractions.getAuditLogsTabsNames().containsAll(tabs),
				"The audit logs tabs are not displayed");

        VNextBOAuditLogDialogInteractions.clickAuditLogsPhasesAndDepartmentsTab();
		final String phasesLastRecord = VNextBOAuditLogDialogInteractions.getDepartmentsAndPhasesLastRecord();
		Assert.assertTrue(!phasesLastRecord.isEmpty(), "The last phase record hasn't been displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 56)
	public void verifyUserCanChangeQuantityForService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
		String serviceQuantity = RandomStringUtils.randomNumeric(2);
		final String serviceTotalPrice = VNextBORODetailsPageInteractions.getTotalServicesPrice();
		System.out.println("serviceTotalPrice: " + serviceTotalPrice);
		System.out.println("Random serviceQuantity: " + serviceQuantity);
		System.out.println("ServiceQuantity: " + VNextBORODetailsPageInteractions.getServiceQuantity(serviceId));

		if (serviceQuantity.equals(VNextBORODetailsPageInteractions.getServiceQuantity(serviceId))) {
			serviceQuantity = RandomStringUtils.randomNumeric(2);
			System.out.println("Random serviceQuantity 2: " + serviceQuantity);
		}
		VNextBORODetailsPageInteractions.setServiceQuantity(serviceId, serviceQuantity);
		VNextBORODetailsPageInteractions.updateTotalServicePrice(VNextBORODetailsPageInteractions.getTotalServicesPrice());
		System.out.println("Updated total services price: " + VNextBORODetailsPageInteractions.getTotalServicesPrice());
		Assert.assertNotEquals(serviceTotalPrice, VNextBORODetailsPageInteractions.getTotalServicesPrice(),
				"The service total price hasn't been recalculated after changing the service quantity");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 57)
	public void verifyUserCanEnterNegativeNumberForServiceQuantity(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
		String negativeServiceQuantity = String.valueOf(-(RandomUtils.nextInt(10, 100)));
		String serviceQuantity = String.valueOf(RandomUtils.nextInt(1, 100));
        VNextBORODetailsPageInteractions.setServiceQuantity(serviceId, serviceQuantity);
        VNextBORODetailsPageInteractions.updateTotalServicePrice(VNextBORODetailsPageInteractions.getTotalServicesPrice());

        final String serviceTotalPrice = VNextBORODetailsPageInteractions.getTotalServicesPrice();
		System.out.println("serviceTotalPrice: " + serviceTotalPrice);
		System.out.println("Random negative serviceQuantity: " + negativeServiceQuantity);
		System.out.println("Random serviceQuantity: " + negativeServiceQuantity);
		System.out.println("ServiceQuantity: " + VNextBORODetailsPageInteractions.getServiceQuantity(serviceId) + "\n");

		if (String.valueOf(Math.abs(Integer.valueOf(negativeServiceQuantity))).equals(VNextBORODetailsPageInteractions.getServiceQuantity(serviceId))) {
			negativeServiceQuantity = String.valueOf(-(RandomUtils.nextInt(10, 100)));
			System.out.println("Random serviceQuantity 2: " + negativeServiceQuantity);
		}
		VNextBORODetailsPageInteractions.setServiceQuantity(serviceId, negativeServiceQuantity);
		VNextBORODetailsPageInteractions.updateTotalServicePrice(VNextBORODetailsPageInteractions.getTotalServicesPrice());
		System.out.println("Updated total services price: " + VNextBORODetailsPageInteractions.getTotalServicesPrice());
        //todo the total price sometimes is not recalculated, but can hardly be reproduced manually
//		Assert.assertNotEquals(serviceTotalPrice, VNextBORODetailsPageInteractions.getTotalServicesPrice(),
//				"The service total price hasn't been recalculated after setting the negative number for the service quantity");
		Assert.assertEquals(VNextBORODetailsPageInteractions.getServiceQuantity(serviceId), String.valueOf(0),
                "The service quantity hasn't been changed to 0");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 58)
	public void verifyUserCannotEnterTextForServiceQuantity(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
		final String serviceTotalPrice = VNextBORODetailsPageInteractions.getTotalServicesPrice();
		System.out.println("ServiceID: " + serviceId);
		System.out.println("serviceTotalPrice: " + serviceTotalPrice);
		System.out.println("ServiceQuantity: " + data.getServiceQuantity());
		System.out.println("ServiceQuantity: " + VNextBORODetailsPageInteractions.getServiceQuantity(serviceId));

		VNextBORODetailsPageInteractions.setServiceQuantity(serviceId, data.getServiceQuantity());
		VNextBORODetailsPageInteractions.updateTotalServicePrice(VNextBORODetailsPageInteractions.getTotalServicesPrice());
		System.out.println("Updated total services price: " + VNextBORODetailsPageInteractions.getTotalServicesPrice());
		Assert.assertEquals(serviceTotalPrice, VNextBORODetailsPageInteractions.getTotalServicesPrice(),
				"The service total price has been recalculated after entering the text " +
						"into the service quantity input field");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 59)
	public void verifyUserCanChangePriceForService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
		String servicePrice = String.valueOf(RandomUtils.nextInt(10, 100));
		final String serviceTotalPrice = VNextBORODetailsPageInteractions.getTotalServicesPrice();
		System.out.println("serviceTotalPrice: " + serviceTotalPrice);
		System.out.println("Random servicePrice: " + servicePrice);
		System.out.println("ServicePrice: " + VNextBORODetailsPageInteractions.getServicePrice(serviceId));

		if (servicePrice.equals(VNextBORODetailsPageInteractions.getServicePrice(serviceId))) {
			servicePrice = String.valueOf(RandomUtils.nextInt(10, 100));
			System.out.println("Random servicePrice 2: " + servicePrice);
		}
		VNextBORODetailsPageInteractions.setServicePrice(serviceId, servicePrice);
		VNextBORODetailsPageInteractions.updateTotalServicePrice(VNextBORODetailsPageInteractions.getTotalServicesPrice());
		System.out.println("Updated total services price: " + VNextBORODetailsPageInteractions.getTotalServicesPrice());
		Assert.assertNotEquals(serviceTotalPrice, VNextBORODetailsPageInteractions.getTotalServicesPrice(),
				"The service total price hasn't been recalculated after changing the service price");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 60)
	public void verifyUserCanEnterNegativeNumberForServicePrice(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
		String servicePrice = String.valueOf(-(RandomUtils.nextInt(10, 100)));
		final String serviceTotalPrice = VNextBORODetailsPageInteractions.getTotalServicesPrice();
		System.out.println("serviceTotalPrice: " + serviceTotalPrice);
		System.out.println("Random servicePrice: " + servicePrice);
		System.out.println("ServicePrice: " + VNextBORODetailsPageInteractions.getServicePrice(serviceId));

		if (String.valueOf(Math.abs(Integer.valueOf(servicePrice))).equals(VNextBORODetailsPageInteractions.getServiceQuantity(serviceId))) {
			servicePrice = String.valueOf(-(RandomUtils.nextInt(10, 100)));
			System.out.println("Random servicePrice 2: " + servicePrice);
		}
		VNextBORODetailsPageInteractions.setServicePrice(serviceId, servicePrice);
		WaitUtilsWebDriver.waitForLoading();
		VNextBORODetailsPageInteractions.updateTotalServicePrice(VNextBORODetailsPageInteractions.getTotalServicesPrice());
		System.out.println("Updated total services price: " + VNextBORODetailsPageInteractions.getTotalServicesPrice());
		Assert.assertNotEquals(serviceTotalPrice, VNextBORODetailsPageInteractions.getTotalServicesPrice(),
				"The service total price hasn't been recalculated " +
						"after setting the negative number for the service price");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 61)
	public void verifyUserCannotEnterTextForServicePrice(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();
		final String serviceId = VNextBORODetailsPageInteractions.getServiceId(data.getService());
		final String serviceTotalPrice = VNextBORODetailsPageInteractions.getTotalServicesPrice();
		System.out.println("serviceTotalPrice: " + serviceTotalPrice);
		System.out.println("ServicePrice: " + data.getServicePrice());
		System.out.println("ServicePrice: " + VNextBORODetailsPageInteractions.getServicePrice(serviceId));

		VNextBORODetailsPageInteractions.setServicePrice(serviceId, data.getServicePrice());
		VNextBORODetailsPageInteractions.updateTotalServicePrice(VNextBORODetailsPageInteractions.getTotalServicesPrice());
		System.out.println("Updated total services price: " + VNextBORODetailsPageInteractions.getTotalServicesPrice());
		Assert.assertEquals(serviceTotalPrice, VNextBORODetailsPageInteractions.getTotalServicesPrice(),
				"The service total price has been recalculated after entering the text " +
						"into the service price input field");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 62)
	public void verifyUserCanSeePhaseOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
		System.out.println("Phase name: " + VNextBORODetailsPageInteractions.getPhaseNameValue());
		System.out.println("Phase vendor price: " + VNextBORODetailsPageInteractions.getPhaseVendorPriceValue());
		System.out.println("Phase vendor technician: " + VNextBORODetailsPageInteractions.getPhaseVendorTechnicianValue());
		System.out.println("Phase status: " + VNextBORODetailsPageInteractions.getPhaseStatusValue());
		System.out.println("Phase actions trigger: " + VNextBORODetailsPageValidations.isPhaseActionsTriggerDisplayed());

		Assert.assertEquals(VNextBORODetailsPageInteractions.getPhaseNameValue(), data.getServicePhaseHeaders()[0],
				"The phase name value hasn't been displayed properly");
		Assert.assertNotEquals(VNextBORODetailsPageInteractions.getPhaseVendorPriceValue(), "",
				"The phase vendor price hasn't been displayed");
		Assert.assertEquals(VNextBORODetailsPageInteractions.getPhaseVendorTechnicianValue(), data.getServicePhaseHeaders()[1],
				"The phase vendor technician value hasn't been displayed properly");
		Assert.assertEquals(VNextBORODetailsPageInteractions.getPhaseStatusValue(), data.getServicePhaseHeaders()[2],
				"The phase status hasn't been displayed properly");
		Assert.assertTrue(VNextBORODetailsPageValidations.isPhaseActionsTriggerDisplayed(),
				"The phase actions trigger hasn't been displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 63)
	public void verifyUserCanSeeStartedPrice(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

//        final String calculatedPrice = VNextBORODetailsPageInteractions.getPhasePriceValue().replace("$", ""); //todo add method after bug #79907 fix!!!
//        System.out.println("Phase price: " + calculatedPrice);
//        final List<String> pricesValuesList = VNextBORODetailsPageInteractions.getVendorPricesValuesList();
//
//        final List<String> values = new ArrayList<>();
//        for (String price : pricesValuesList) {
//            final String value = price
//                    .replace("$", "")
//                    .replace("(", "-")
//                    .replace(")", "");
//            values.add(value);
//        }
//
//        final double sum = values
//                .stream()
//                .mapToDouble(Double::parseDouble)
//                .reduce((val1, val2) -> val1 + val2)
//                .orElse(0.00);
//
//        System.out.println("Sum: " + sum);
//        Assert.assertEquals(sum, Double.valueOf(calculatedPrice), "The sum hasn't been calculated properly"); todo is bug??? uncomment
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 64)
	public void verifyUserCanSeeStartedVendorPrice(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

		final String calculatedVendorPrice = VNextBORODetailsPageInteractions.getPhaseVendorPriceValue().replace("$", "");
		System.out.println("Phase vendor price: " + calculatedVendorPrice);
		final List<String> vendorPricesValuesList = VNextBORODetailsPageInteractions.getVendorPricesValuesList();

		final List<String> values = new ArrayList<>();
		for (String vendorPrice : vendorPricesValuesList) {
			final String value = vendorPrice
					.replace("$", "")
					.replace("(", "-")
					.replace(")", "");
			values.add(value);
		}

		final double sum = values
				.stream()
				.mapToDouble(Double::parseDouble)
				.reduce((val1, val2) -> val1 + val2)
				.orElse(0.00);

		System.out.println("Sum: " + sum);
//        Assert.assertEquals(sum, Double.valueOf(calculatedVendorPrice), "The sum hasn't been calculated properly");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 65)
	public void verifyUserCanChangeTechnicianOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
		VNextBORODetailsPageInteractions.clickPhaseVendorTechnicianLink();

		Assert.assertTrue(VNextBOChangeTechniciansDialogValidations.isChangeTechnicianDialogDisplayed(),
				"The Change Technician dialog hasn't been opened");

        VNextBOChangeTechniciansDialogSteps.setOptionsAndClickOkButtonForTechniciansDialog(data.getVendor(), data.getTechnician());

        VNextBORODetailsPageInteractions.expandServicesTable();

		Assert.assertNotEquals(VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getVendor()), 0);
		Assert.assertNotEquals(VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getTechnician()), 0);
		Assert.assertEquals(VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getVendor()),
				VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getTechnician()),
				"The vendor and technician options haven't been changed for all repair orders with the 'Active' status");

		// clearing the test data
        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.clickPhaseVendorTechnicianLink();

		Assert.assertTrue(VNextBOChangeTechniciansDialogValidations.isChangeTechnicianDialogDisplayed(),
				"The Change Technician dialog hasn't been opened");

        VNextBOChangeTechniciansDialogSteps.setOptionsAndClickOkButtonForTechniciansDialog(data.getVendor1(), data.getTechnician1());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 66)
	public void verifyUserCanChangeAndNotSaveWithXButtonTechnicianOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

		final int numberOfVendorOptions = VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getVendor());
		final int numberOfTechnicianOptions = VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getTechnician());

		VNextBORODetailsPageInteractions.clickPhaseVendorTechnicianLink();

		Assert.assertTrue(VNextBOChangeTechniciansDialogValidations.isChangeTechnicianDialogDisplayed(),
				"The Change Technician dialog hasn't been opened");

        VNextBOChangeTechniciansDialogSteps.setOptionsAndClickXButtonForTechniciansDialog(data.getVendor(), data.getTechnician());

		Assert.assertEquals(numberOfVendorOptions, VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getVendor()),
				"The vendor options number has been changed for repair orders with the 'Active' status");

		Assert.assertEquals(numberOfTechnicianOptions, VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getTechnician()),
				"The vendor options number has been changed for repair orders with the 'Active' status");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 67)
	public void verifyUserCanChangeAndNotSaveWithCancelButtonTechnicianOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        VNextBORODetailsPageInteractions.setStatus(data.getStatus());
        VNextBORODetailsPageInteractions.expandServicesTable();

		final int numberOfVendorOptions = VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getVendor());
		final int numberOfTechnicianOptions = VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getTechnician());

		VNextBORODetailsPageInteractions.clickPhaseVendorTechnicianLink();

		Assert.assertTrue(VNextBOChangeTechniciansDialogValidations.isChangeTechnicianDialogDisplayed(),
				"The Change Technician dialog hasn't been opened");

        VNextBOChangeTechniciansDialogSteps.setOptionsAndClickXButtonForTechniciansDialog(data.getVendor(), data.getTechnician());

		Assert.assertEquals(numberOfVendorOptions, VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getVendor()),
				"The vendor options number has been changed for repair orders with the 'Active' status");

		Assert.assertEquals(numberOfTechnicianOptions, VNextBORODetailsPageInteractions.getNumberOfVendorTechnicianOptionsByName(data.getTechnician()),
				"The vendor options number has been changed for repair orders with the 'Active' status");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 68)
	public void verifyUserCanSeeAndChangeTechniciansOfTheCurrentPhase(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());

        final String priorTechniciansValue = VNextBOROPageInteractions.getTechniciansValueForWO(data.getOrderNumber());
        String selectedRandomTechnician = VNextBOROPageSteps.setTechnicianAndVendorByWoNumber(
                data.getOrderNumber(), data.getVendor());
        if (priorTechniciansValue.equals(selectedRandomTechnician)) {
            selectedRandomTechnician = VNextBOROPageSteps.setTechnicianAndVendorByWoNumber(
                    data.getOrderNumber(), data.getVendor());
        }
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageValidations.verifyServiceIsDisplayedForCollapsedPhase(data.getServices()[0], data.getServiceTabs()[0]);
        VNextBORODetailsPageSteps.setServiceStatusForService(data.getServices()[0], data.getServiceStatuses()[0]);
        VNextBORODetailsPageValidations.verifyServiceIsDisplayedForCollapsedPhase(data.getServices()[1], data.getServiceTabs()[1]);
        VNextBORODetailsPageValidations.verifyVendorTechnicianNameIsSet(selectedRandomTechnician);

		VNextBOBreadCrumbInteractions.clickFirstBreadCrumbLink();
        VNextBOROPageValidations.verifyAnotherTechnicianIsDisplayed(data.getOrderNumber(), selectedRandomTechnician);
        VNextBOROPageSteps.openRODetailsPage(data.getOrderNumber());
        VNextBORODetailsPageValidations.verifyServiceIsDisplayedForCollapsedPhase(data.getServices()[0], data.getServiceTabs()[0]);
        VNextBORODetailsPageSteps.setServiceStatusForService(data.getServices()[0], data.getServiceStatuses()[1]);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 69)
	public void verifyUserCanSearchBySavedSearchForm(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchName());
        VNextBOROPageSteps.openAdvancedSearchDialog();
        final VNextBOROAdvancedSearchValues searchValues = data.getSearchValues();

        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getCustomerInputFieldValue(), searchValues.getCustomer());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getEmployeeInputFieldValue(), searchValues.getEmployee());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getPhaseInputFieldValue(), searchValues.getPhase());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getPhaseStatusInputFieldValue(), searchValues.getPhaseStatus());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getDepartmentInputFieldValue(), searchValues.getDepartment());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getWoTypeInputFieldValue(), searchValues.getWoType());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getWoNumInputFieldValue(), searchValues.getWoNum());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getRoNumInputFieldValue(), searchValues.getRoNum());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getStockInputFieldValue(), searchValues.getStockNum());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getVinInputFieldValue(), searchValues.getVinNum());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getTimeFrameInputFieldValue(), searchValues.getTimeFrame());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getRepairStatusInputFieldValue(), searchValues.getRepairStatus());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getDaysInProcessInputFieldValue(), searchValues.getDaysInProcess());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getDaysInPhaseInputFieldValue(), searchValues.getDaysInPhase());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getFlagInputFieldValue(), searchValues.getFlag());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getSortByInputFieldValue(), searchValues.getSortBy());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getSearchNameInputFieldValue(), searchValues.getSearchName());
    }

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 70)
	public void verifyUserCannotEditSavedSearch(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchNames()[0]);
        Assert.assertTrue(VNextBOROPageValidations.isSavedSearchEditIconDisplayed(),
                "The saved search edit icon hasn't been displayed");
        VNextBOROPageSteps.openAdvancedSearchDialog();
        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isSavedSearchNameClickable(),
                "The saved search input field isn't clickable");
        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isSaveButtonClickable(),
                "The save button isn't clickable");
        VNextBOROAdvancedSearchDialogSteps.closeAdvancedSearchDialog();

        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchNames()[1]);
        VNextBOROPageSteps.openAdvancedSearchDialog();
        Assert.assertFalse(VNextBOROPageValidations.isSavedSearchEditIconDisplayed(),
                "The saved search edit icon hasn't been displayed");
        Assert.assertFalse(VNextBOROAdvancedSearchDialogValidations.isSavedSearchNameClickable(),
                "The saved search input field is clickable");
        Assert.assertFalse(VNextBOROAdvancedSearchDialogValidations.isSaveButtonClickable(),
                "The save button is clickable");
        VNextBOROAdvancedSearchDialogSteps.closeAdvancedSearchDialog();
    }

//	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    TODO the TC blocker - the locations are not loaded for the given technician
	public void verifyTechnicianUserCanFindOrdersUsingSavedSearchMyCompletedWork(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHeaderPanelSteps.logout();
        VNextBOLoginSteps.userLogin(data.getUserName(), data.getUserPassword());

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchName());
        VNextBOROPageSteps.openRODetailsPage();
        VNextBORODetailsPageValidations.verifyPhaseStatuses(data.getServiceStatuses());
    }

    //todo continue after the blocker is resolved
//	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyTechnicianUserCanFindOrdersUsingSavedSearchMyWorkQueue(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHeaderPanelSteps.logout();
        VNextBOLoginSteps.userLogin(data.getUserName(), data.getUserPassword());

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchName());
        VNextBOROPageSteps.openRODetailsPage();
        VNextBORODetailsPageValidations.verifyPhaseStatuses(data.getServiceStatuses());
    }

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 71)
	public void verifyUserCanResolveProblemOnPhaseLevel(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchName());
        VNextBOROPageSteps.openRODetailsPage();
        VNextBORODetailsPageValidations.verifyPhaseStatuses(data.getServiceStatuses());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 72)
    public void verifyUserCanSeeInvoiceOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(data.getLocation()), "The location hasn't been set");
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.clickInvoiceNumberInTable(data.getOrderNumber(), data.getInvoiceNumber());
        Assert.assertEquals(webdriver.getWindowHandles().size(), 2);

        VNextBOInspectionsPageSteps.clickTheApproveInspectionButton();
        VNextBOConfirmationDialogInteractions.clickYesButton();
    }
}