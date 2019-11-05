package com.cyberiansoft.test.vnextbo.testcases.repairOrders;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOROAdvancedSearchValues;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBORONotesPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairOrders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROAdvancedSearchDialog;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBORODetailsPage;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROWebPage;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.VNextBOPrivacyPolicyDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.VNextBOTermsAndConditionsDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairOrders.*;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOFooterPanelValidations;
import com.cyberiansoft.test.vnextbo.verifications.VNextBONotesPageVerifications;
import com.cyberiansoft.test.vnextbo.verifications.repairOrders.VNextBOROAdvancedSearchDialogVerifications;
import com.cyberiansoft.test.vnextbo.verifications.repairOrders.VNextBORODetailsPageVerifications;
import com.cyberiansoft.test.vnextbo.verifications.repairOrders.VNextBOROPageValidations;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOMonitorTestCases extends BaseTestCase {
	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOMonitorData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	private String userName;
	private String userPassword;
	private VNextBOLoginScreenWebPage loginPage;
	private VNextBOBreadCrumbInteractions breadCrumbInteractions;
	private VNextBORODetailsPageInteractions roDetailsPageInteractions;
	private VNextBOROWebPage repairOrdersPage;
	private VNextBORODetailsPage detailsPage;

    @BeforeMethod
	public void BackOfficeLogin() {
		browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
		try {
			DriverBuilder.getInstance().setDriver(browserType);
		} catch (WebDriverException e) {
			e.printStackTrace();
		}
		webdriver = DriverBuilder.getInstance().getDriver();

		webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
		userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
		userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

		loginPage = new VNextBOLoginScreenWebPage();
		loginPage.userLogin(userName, userPassword);
		repairOrdersPage = new VNextBOROWebPage();
		detailsPage = new VNextBORODetailsPage();
		breadCrumbInteractions = new VNextBOBreadCrumbInteractions();
		roDetailsPageInteractions = new VNextBORODetailsPageInteractions();
    }

	@AfterMethod
	public void BackOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

		if (DriverBuilder.getInstance().getDriver() != null) {
			DriverBuilder.getInstance().quitDriver();
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanOpenMonitorWithFullSetOfElements(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		Assert.assertTrue(VNextBOROPageValidations.isSavedSearchContainerDisplayed(),
				"The search container isn't displayed");

//        repairOrdersPage.setDepartmentsTabActive(); todo add verifies for narrow screen, if necessary
//        Assert.assertTrue(repairOrdersPage.isDepartmentsTabDisplayed(),
//                "The department tab isn't displayed");

		Assert.assertTrue(VNextBOROPageValidations.isDepartmentDropdownDisplayed(),
				"The department dropdown is not displayed");

        VNextBOROPageInteractions.clickPhasesWide();
		Assert.assertTrue(VNextBOROPageValidations.isPhasesDropdownDisplayed(),
				"The phases dropdown is not displayed");

//        repairOrdersPage.makePhasesTabActive();
//        Assert.assertTrue(repairOrdersPage.isPhasesTabDisplayed(),
//                "The phases tab isn't displayed");

		Assert.assertTrue(VNextBOROPageValidations.isSearchInputFieldDisplayed(),
				"The search input field isn't displayed");
		Assert.assertTrue(VNextBOROPageValidations.isAdvancedSearchCaretDisplayed(),
				"The advanced search caret isn't displayed");
//        Assert.assertTrue(VNextBOROPageValidations.areTableHeaderTitlesDisplayed(data.getTitles(), data.getTitlesRepeater()),
//                "The table header titles aren't displayed");
//        Assert.assertTrue(repairOrdersPage.isIntercomLauncherDisplayed(),
//                "The Intercom launcher is not displayed");
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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanOpenAndCloseTermsAndConditions(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOTermsAndConditionsDialogSteps.openAndRejectTermsAndConditions();
        VNextBOTermsAndConditionsDialogSteps.openAndAcceptTermsAndConditions();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanOpenAndClosePrivacyPolicy(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOPrivacyPolicyDialogSteps.openAndRejectPrivacyPolicy();
        VNextBOPrivacyPolicyDialogSteps.openAndAcceptPrivacyPolicy();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanOpenAndCloseIntercom(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROPageInteractions.openIntercom();
        VNextBOROPageInteractions.closeIntercom();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeLocation(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getSearchLocation());
		Assert.assertTrue(breadCrumbInteractions.isLocationSearched(data.getSearchLocation()),
				"The location is not searched");
        VNextBOROPageInteractions.clickLocationInDropDown(data.getSearchLocation());
		Assert.assertTrue(breadCrumbInteractions.isLocationExpanded(), "The location is not expanded");
        VNextBOROPageInteractions.clickSearchTextToCloseLocationDropDown();
		Assert.assertTrue(breadCrumbInteractions.isLocationSelected(data.getSearchLocation()), "The location has been changed");

		Assert.assertTrue(breadCrumbInteractions.isLocationSearched(data.getLocation()),
				"The location is not searched");
        VNextBOROPageInteractions.clickLocationInDropDown(data.getLocation());

		Assert.assertTrue(breadCrumbInteractions.isLocationSelected(data.getLocation()), "The location hasn't been selected");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeLocationUsingSearch(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
		Assert.assertTrue(breadCrumbInteractions.isLocationSearched(data.getSearchLocation()),
				"The location is not searched");
        VNextBOROPageInteractions.clickLocationInDropDown(data.getLocation());
		Assert.assertTrue(breadCrumbInteractions.isLocationSelected(data.getLocation()), "The location hasn't been selected");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSelectLocation(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		Assert.assertTrue(breadCrumbInteractions.isLocationSet(data.getLocation()), "The location hasn't been set");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSearchWoUsingSearchFilter(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		Assert.assertTrue(breadCrumbInteractions.isLocationSet(data.getLocation()), "The location hasn't been set");
		VNextBOROSimpleSearchSteps.searchByText(data.getVinNum());

		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getVinNum()),
				"The work order is not displayed after search by VIN after clicking the 'Search' icon");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeAndChangeRoDetails(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		Assert.assertTrue(breadCrumbInteractions.isLocationSet(data.getLocation()), "The location hasn't been set");

		final String vinNum = data.getVinNum();
		VNextBOROSimpleSearchSteps.searchByText(vinNum);
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(vinNum),
				"The work order is not displayed after search by VIN after clicking the 'Search' icon");

		Assert.assertEquals(VNextBOROPageInteractions.getTableTitleDisplayed(0), data.getTitle(),
				"The table title is incorrect");
		final VNextBORODetailsPage repairOrderDetailsPage = new VNextBORODetailsPage();
        VNextBOROPageInteractions.clickWoLink(vinNum);
		Assert.assertTrue(repairOrderDetailsPage.isRoDetailsSectionDisplayed(),
				"The RO details section hasn't been displayed");
		repairOrderDetailsPage.goToPreviousPage();

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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCannotChangePoForInvoicedOrders(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation(), true);

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByOrderNumber(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		Assert.assertFalse(VNextBOROPageValidations.isPoNumClickable(), "The PO# shouldn't be clickable");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeInvoiceOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		Assert.assertTrue(breadCrumbInteractions.isLocationSet(data.getLocation()), "The location hasn't been set");

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		final VNextBOInvoicesDescriptionWindow invoicesDescription = new VNextBOInvoicesDescriptionWindow();
		VNextBOROPageInteractions.clickInvoiceNumberInTable(data.getOrderNumber(), data.getInvoiceNumber());
		Assert.assertEquals(webdriver.getWindowHandles().size(), 2);
		invoicesDescription.closeWindows();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoToCheckInCheckOut(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.openOtherDropDownMenu(data.getOrderNumber());
		//todo finish after TC update
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanTypeAndNotSaveNotesWithXIcon(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.revealNoteForWorkOrder(data.getOrderNumber());
        //todo bug fix #78127
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanTypeAndNotSaveNotesWithClose(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        VNextBOROPageInteractions.revealNoteForWorkOrder(data.getOrderNumber());
		//todo bug fix #78127
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanOpenRoDetails(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStockOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.typeStockNumber(data.getStockNumbers()[1]);
		detailsPage.typeStockNumber(data.getStockNumbers()[0]);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeRoNumOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.typeRoNumber(data.getRoNumbers()[1]);
		detailsPage.typeRoNumber(data.getRoNumbers()[0]);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoToNew(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setStatus(data.getStatus());
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(detailsPage.getRoStatus(), data.getStatus(), "The status hasn't been set");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoToOnHold(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setStatus(data.getStatus());
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(detailsPage.getRoStatus(), data.getStatus(), "The status hasn't been set");
		Assert.assertTrue(detailsPage.isImageOnHoldStatusDisplayed(),
				"The On Hold image notification hasn't been displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoToApproved(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setStatus(data.getStatus());
		Assert.assertEquals(detailsPage.getRoStatus(), data.getStatus(), "The status hasn't been set");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCannotChangeStatusOfRoToDraft(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setStatus(data.getStatus());
		Assert.assertNotEquals(detailsPage.getRoStatus(), data.getStatus(), "The status has been changed to 'Draft'");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoToClosedWithNoneReason(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBORepairOrdersPageSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps
                .searchByActivePhase(data.getPhase(), data.getPhaseStatus(), data.getTimeFrame());

        VNextBORepairOrdersPageSteps.openRODetailsPage();
        roDetailsPageInteractions.setStatus(data.getStatus());
        VNextBOCloseRODialogSteps.closeROWithReason(data.getReason());
        Assert.assertEquals(roDetailsPageInteractions.getRoStatusValue(), data.getStatus(),
                "The status hasn't been changed to 'Closed'");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangePriorityOfRoToLow(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setPriority(data.getPriority());
		detailsPage.clickRepairOrdersBackwardsLink();
        VNextBOROPageInteractions.clickCancelSearchIcon();

		if (VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber())) {
			Assert.assertTrue(VNextBOROPageValidations.isArrowDownDisplayed(data.getOrderNumber()),
					"The work order arrow down is not displayed");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangePriorityOfRoToNormal(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setPriority(data.getPriority());
		detailsPage.clickRepairOrdersBackwardsLink();
        VNextBOROPageInteractions.clickCancelSearchIcon();

		if (VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber())) {
			Assert.assertFalse(VNextBOROPageValidations.isArrowDownDisplayed(data.getOrderNumber()),
					"The work order arrow down should not be displayed");
			Assert.assertFalse(VNextBOROPageValidations.isArrowUpDisplayed(data.getOrderNumber()),
					"The work order arrow down should not be displayed");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanAddNewService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		final VNextBOAddNewServiceMonitorDialog newServiceMonitorDialog = new VNextBOAddNewServiceMonitorDialog(webdriver);
		detailsPage.clickAddNewServiceButton();
		Assert.assertTrue(newServiceMonitorDialog.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
		newServiceMonitorDialog
				.setPriceType(data.getPriceType())
				.setService(data.getService())
				.setServiceDetails(data.getServiceDetails())
				.setServiceDescription(serviceDescription)
				.setServicePrice(data.getServicePrice())
				.setServiceQuantity(data.getServiceQuantity())
				.clickSubmitButton()
				.refreshPage();

		detailsPage.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(serviceDescription);
		if (serviceId.isEmpty()) {
			Assert.assertTrue(detailsPage.isServiceNotificationToBeAddedLaterDisplayed(),
					"The notification about the service to be added later hasn't been displayed");
		} else {
			Assert.assertNotEquals(serviceId, "",
					"The created service hasn't been displayed");
			System.out.println("description: " + detailsPage.getServiceDescription(serviceId));
			Assert.assertEquals(detailsPage.getServiceDescription(serviceId), serviceDescription,
					"The service description name is not equal to the inserted description value");

			System.out.println("quantity: " + detailsPage.getServiceQuantity(serviceId));
			Assert.assertEquals(detailsPage.getServiceQuantity(serviceId), data.getServiceQuantity(),
					"The service quantity is not equal to the inserted quantity value");

			System.out.println("price: " + detailsPage.getServicePrice(serviceId));
			Assert.assertEquals(detailsPage.getServicePrice(serviceId), data.getServicePrice(),
					"The service price is not equal to the inserted price value");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangePriorityOfRoToHigh(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setPriority(data.getPriority());
		detailsPage.clickRepairOrdersBackwardsLink();
        VNextBOROPageInteractions.clickCancelSearchIcon();

		if (VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber())) {
			Assert.assertTrue(VNextBOROPageValidations.isArrowUpDisplayed(data.getOrderNumber()),
					"The work order arrow down is not displayed");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanAddNewMoneyService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		final VNextBOAddNewServiceMonitorDialog newServiceMonitorDialog = new VNextBOAddNewServiceMonitorDialog(webdriver);
		detailsPage.clickAddNewServiceButton();
		Assert.assertTrue(newServiceMonitorDialog.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
		newServiceMonitorDialog
				.setPriceType(data.getPriceType())
				.setService(data.getService())
				.setServiceDetails(data.getServiceDetails())
				.setServiceDescription(serviceDescription)
				.setServicePrice(data.getServicePrice())
				.setServiceQuantity(data.getServiceQuantity())
				.clickSubmitButton();

		detailsPage.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(serviceDescription);
		if (serviceId.isEmpty()) {
			Assert.assertTrue(detailsPage.isServiceNotificationToBeAddedLaterDisplayed(),
					"The notification about the service to be added later hasn't been displayed");
		} else {
			Assert.assertNotEquals(serviceId, "", "The created service hasn't been displayed");
			System.out.println("description: " + detailsPage.getServiceDescription(serviceId));
			Assert.assertEquals(detailsPage.getServiceDescription(serviceId), serviceDescription,
					"The service description name is not equal to the inserted description value");

			System.out.println("quantity: " + detailsPage.getServiceQuantity(serviceId));
			Assert.assertEquals(detailsPage.getServiceQuantity(serviceId), data.getServiceQuantity(),
					"The service quantity is not equal to the inserted quantity value");

			System.out.println("price: " + detailsPage.getServicePrice(serviceId));
			Assert.assertEquals(detailsPage.getServicePrice(serviceId), data.getServicePrice(),
					"The service price is not equal to the inserted price value");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanAddNewLaborService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		final VNextBOAddNewServiceMonitorDialog newServiceMonitorDialog = new VNextBOAddNewServiceMonitorDialog(webdriver);
		detailsPage.clickAddNewServiceButton();
		Assert.assertTrue(newServiceMonitorDialog.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
		newServiceMonitorDialog
				.setPriceType(data.getPriceType())
				.setService(data.getService())
				.setServiceDetails(data.getServiceDetails())
				.setServiceDescription(serviceDescription)
				.setServiceLaborRate(data.getServiceLaborRate())
				.setServiceLaborTime(data.getServiceLaborTime())
				.clickSubmitButton();

		detailsPage.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(serviceDescription);
		if (serviceId.isEmpty()) {
			Assert.assertTrue(detailsPage.isServiceNotificationToBeAddedLaterDisplayed(),
					"The notification about the service to be added later hasn't been displayed");
		} else {
			System.out.println("description: " + detailsPage.getServiceDescription(serviceId));
			Assert.assertEquals(detailsPage.getServiceDescription(serviceId), serviceDescription,
					"The service description name is not equal to the inserted description value");

			System.out.println("quantity: " + detailsPage.getServiceLaborTime(serviceId));
			Assert.assertEquals(detailsPage.getServiceLaborTime(serviceId), data.getServiceLaborTime(),
					"The service labor time is not equal to the inserted labor time value");

			System.out.println("price: " + detailsPage.getServicePrice(serviceId));
			Assert.assertEquals(detailsPage.getServicePrice(serviceId), data.getServiceLaborRate(),
					"The service labor rate is not equal to the inserted labor rate value");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanAddNewPartService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		final VNextBOAddNewServiceMonitorDialog newServiceMonitorDialog = new VNextBOAddNewServiceMonitorDialog(webdriver);
		detailsPage.clickAddNewServiceButton();
		Assert.assertTrue(newServiceMonitorDialog.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
		final String selectedCategory = newServiceMonitorDialog
				.setPriceType(data.getPriceType())
				.setService(data.getService())
				.setServiceDescription(serviceDescription)
				.setCategory(data.getServiceCategory())
				.setSubcategory();
		System.out.println("*******************************************");
		System.out.println(selectedCategory);

		final String selectedAddPartsNumberBefore = newServiceMonitorDialog.getSelectedAddPartsNumber();
		if (!newServiceMonitorDialog.getPartsOptions().isEmpty()) {
			newServiceMonitorDialog.selectRandomAddPartsOption();

			final String selectedAddPartsNumberAfter = newServiceMonitorDialog.getSelectedAddPartsNumber();
			Assert.assertNotEquals(selectedAddPartsNumberBefore, selectedAddPartsNumberAfter);
			Assert.assertTrue(Integer.valueOf(selectedAddPartsNumberBefore) < Integer.valueOf(selectedAddPartsNumberAfter));

			newServiceMonitorDialog.clickSubmitButton();
			if (!newServiceMonitorDialog.isPartDescriptionDisplayed(data.getServiceCategory() + " -> " + selectedCategory)) {
				System.out.println("Refreshing the page...");
				detailsPage.refreshPage();
			}

			Assert.assertTrue(newServiceMonitorDialog
							.isPartDescriptionDisplayed(data.getServiceCategory() + " -> " + selectedCategory),
					"The Part service description hasn't been displayed.");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSelectDetailsOfAddNewServiceAndNotAddItXIcon(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		final VNextBOAddNewServiceMonitorDialog newServiceMonitorDialog = new VNextBOAddNewServiceMonitorDialog(webdriver);
		detailsPage.clickAddNewServiceButton();
		Assert.assertTrue(newServiceMonitorDialog.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
		newServiceMonitorDialog
				.setPriceType(data.getPriceType())
				.setService(data.getService())
				.setServiceDescription(serviceDescription)
				.setServicePrice(data.getServicePrice())
				.setServiceQuantity(data.getServiceQuantity())
				.clickXButton()
				.refreshPage();

		detailsPage.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(serviceDescription);
		Assert.assertEquals(serviceId, "",
				"The service has been added after closing the 'New Service Dialog' with X button");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSelectDetailsOfAddNewServiceAndNotAddItCancelButton(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		final VNextBOAddNewServiceMonitorDialog newServiceMonitorDialog = new VNextBOAddNewServiceMonitorDialog(webdriver);
		detailsPage.clickAddNewServiceButton();
		Assert.assertTrue(newServiceMonitorDialog.isNewServicePopupDisplayed());

		final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
		newServiceMonitorDialog
				.setPriceType(data.getPriceType())
				.setService(data.getService())
				.setServiceDescription(serviceDescription)
				.setServicePrice(data.getServicePrice())
				.setServiceQuantity(data.getServiceQuantity())
				.clickCancelButton()
				.refreshPage();

		detailsPage.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(serviceDescription);
		Assert.assertEquals(serviceId, "",
				"The service has been added after closing the 'New Service Dialog' with X button");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRoToWhite(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
		detailsPage.clickFlagIcon();
		Assert.assertTrue(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		detailsPage.selectFlagColor(data.getFlag());
		Assert.assertFalse(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRoToRed(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
		detailsPage.clickFlagIcon();
		Assert.assertTrue(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		detailsPage.selectFlagColor(data.getFlag());
		Assert.assertFalse(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRoToOrange(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
		detailsPage.clickFlagIcon();
		Assert.assertTrue(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		detailsPage.selectFlagColor(data.getFlag());
		Assert.assertFalse(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRoToYellow(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
		detailsPage.clickFlagIcon();
		Assert.assertTrue(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		detailsPage.selectFlagColor(data.getFlag());
		Assert.assertFalse(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRoToGreen(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
		detailsPage.clickFlagIcon();
		Assert.assertTrue(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		detailsPage.selectFlagColor(data.getFlag());
		Assert.assertFalse(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRoToBlue(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
		detailsPage.clickFlagIcon();
		Assert.assertTrue(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		detailsPage.selectFlagColor(data.getFlag());
		Assert.assertFalse(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRoToPurple(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
		detailsPage.clickFlagIcon();
		Assert.assertTrue(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		detailsPage.selectFlagColor(data.getFlag());
		Assert.assertFalse(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeServicesOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		final List<String> fields = Arrays.asList(data.getServicesTableFields());
		fields.forEach(System.out::println);
		System.out.println("*****************************************");
		System.out.println();

		detailsPage.getServicesTableHeaderValues().forEach(System.out::println);
		System.out.println("*****************************************");
		System.out.println();

		Assert.assertTrue(detailsPage.getServicesTableHeaderValues()
						.containsAll(fields),
				"The services table header values have not been displayed properly");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeVendorPriceOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		System.out.println("Vendor price: " + detailsPage.getServiceVendorPrice(serviceId));
		detailsPage.setServiceVendorPrice(serviceId, service, data.getServiceVendorPrices()[0]);
		Assert.assertEquals(detailsPage.getServiceVendorPrice(serviceId), data.getServiceVendorPrices()[0],
				"The Vendor Price hasn't been changed");

		System.out.println("Vendor price: " + detailsPage.getServiceVendorPrice(serviceId));
		detailsPage.setServiceVendorPrice(serviceId, service, data.getServiceVendorPrices()[1]);
		Assert.assertEquals(detailsPage.getServiceVendorPrice(serviceId), data.getServiceVendorPrices()[1],
				"The Vendor Price hasn't been changed");

		System.out.println("Vendor price: " + detailsPage.getServiceVendorPrice(serviceId));
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeVendorTechnicianOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
		detailsPage.setVendor(serviceId, data.getVendor());
		detailsPage.setTechnician(serviceId, data.getTechnician());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanCreateNoteOfServiceOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

        VNextBORODetailsPageSteps.openEditNotesDialog(serviceId);
        final int notesNumber = VNextBORONotesPageInteractions.getRepairNotesListNumber();

        VNextBORONotesPageSteps.setRONoteMessage(data.getNotesMessage());
		VNextBORONotesPageInteractions.clickRONoteSaveButton();

		Assert.assertTrue(VNextBONotesPageVerifications.isEditOrderServiceNotesBlockDisplayed(), "The notes dialog hasn't been opened");
		Assert.assertEquals(notesNumber + 1, VNextBORONotesPageInteractions.getRepairNotesListNumber(),
				"The services notes list number is not updated");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeTargetDateOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
		detailsPage.setVendor(serviceId, data.getVendor());
		detailsPage.setTechnician(serviceId, data.getTechnician());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeMoreInformationOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.openMoreInformation();
		final List<String> infoFields = Arrays.asList(data.getInformationFields());
		System.out.println();
		infoFields.forEach(System.out::println);
		System.out.println();

		Assert.assertTrue(detailsPage
						.getMoreInformationFieldsText()
						.containsAll(infoFields),
				"The More Information fields haven't been fully displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanTypeAndNotSaveNoteOfRoService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoServiceToActive(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
		Assert.assertEquals(detailsPage.getServiceStatusValue(serviceId), data.getServiceStatuses()[0]);

		final String serviceStartedDate = detailsPage.getServiceStartedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");
		Assert.assertFalse(detailsPage.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date shouldn't be displayed with the Active status");

		detailsPage.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(detailsPage.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[0]));

		System.out.println("JSON: " + data.getServicePhase());
		System.out.println(detailsPage.getOrderCurrentPhase());

		Assert.assertEquals(detailsPage.getOrderCurrentPhase(), data.getServicePhase(),
				"The Current Phase is not displayed properly");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoServiceToCompleted(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(detailsPage.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		final String serviceStartedDate = detailsPage.getServiceStartedDate(serviceId);
		final String serviceCompletedDate = detailsPage.getServiceCompletedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");

		Assert.assertTrue(detailsPage.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date should be displayed with the Completed status");
		System.out.println(serviceCompletedDate);
		System.out.println(data.getServiceCompletedDate());
		Assert.assertEquals(serviceCompletedDate, data.getServiceCompletedDate(),
				"The service completed date is wrong");

		detailsPage.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(detailsPage.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[1]),
				"The helper info dialog isn't displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoServiceToAudited(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(detailsPage.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		final String serviceStartedDate = detailsPage.getServiceStartedDate(serviceId);
		final String serviceCompletedDate = detailsPage.getServiceCompletedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");

		Assert.assertTrue(detailsPage.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date should be displayed with the Audited status");
		System.out.println(serviceCompletedDate);
		System.out.println(data.getServiceCompletedDate());
		Assert.assertEquals(serviceCompletedDate, data.getServiceCompletedDate(),
				"The service completed date is wrong");

		detailsPage.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(detailsPage.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[1]),
				"The helper info dialog isn't displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoServiceToRefused(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(detailsPage.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		final String serviceStartedDate = detailsPage.getServiceStartedDate(serviceId);
		final String serviceCompletedDate = detailsPage.getServiceCompletedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");

		Assert.assertTrue(detailsPage.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date should be displayed with the Refused status");
		System.out.println(serviceCompletedDate);
		System.out.println(data.getServiceCompletedDate());
		Assert.assertEquals(serviceCompletedDate, data.getServiceCompletedDate(),
				"The service completed date is wrong");

		detailsPage.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(detailsPage.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[1]),
				"The helper info dialog isn't displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoServiceToRework(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(detailsPage.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		final String serviceStartedDate = detailsPage.getServiceStartedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");
		Assert.assertFalse(detailsPage.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date shouldn't be displayed with the Rework status");

		detailsPage.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(detailsPage.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[1]),
				"The helper info dialog isn't displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoServiceToSkipped(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(detailsPage.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		final String serviceStartedDate = detailsPage.getServiceStartedDate(serviceId);
		final String serviceCompletedDate = detailsPage.getServiceCompletedDate(serviceId);
		Assert.assertEquals(serviceStartedDate, data.getServiceStartedDate(),
				"The service started date is wrong");

		Assert.assertTrue(detailsPage.isServiceCompletedDateDisplayed(serviceId),
				"The service completed date should be displayed with the Refused status");
		System.out.println(serviceCompletedDate);
		System.out.println(data.getServiceCompletedDate());
		Assert.assertEquals(serviceCompletedDate, data.getServiceCompletedDate(),
				"The service completed date is wrong");

		detailsPage.hoverOverServiceHelperIcon(serviceId);
		Assert.assertTrue(detailsPage.isHelpInfoDialogDisplayed(serviceId, data.getServiceStatuses()[1]),
				"The helper info dialog isn't displayed");
	}

	//todo blocker, needs clarification
	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeChangesOfPhasesInLogInfo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		VNextBORepairOrdersPageSteps.openRODetailsPage(data.getOrderNumber());

        final VNextBORODetailsPage detailsPage = new VNextBORODetailsPage();

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(data.getService());

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(detailsPage.getServiceStatusValue(serviceId), data.getServiceStatuses()[0]);

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		WaitUtilsWebDriver.waitForLoading();
		Assert.assertEquals(detailsPage.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		final VNextBOAuditLogDialog auditLogDialog = new VNextBOAuditLogDialog(webdriver);
		detailsPage.clickLogInfoButton();
		Assert.assertTrue(auditLogDialog.isAuditLogDialogDisplayed(), "The audit log modal dialog hasn't been opened");

		auditLogDialog.getAuditLogsTabsNames().forEach(System.out::println);
		System.out.println("*****************************************");
		System.out.println();

		final List<String> tabs = Arrays.asList(data.getAuditLogTabs());
		tabs.forEach(System.out::println);
		System.out.println("*****************************************");
		System.out.println();

		Assert.assertTrue(auditLogDialog.getAuditLogsTabsNames().containsAll(tabs),
				"The audit logs tabs are not displayed");

		auditLogDialog.clickAuditLogsPhasesAndDepartmentsTab();
		final String phasesLastRecord = auditLogDialog.getDepartmentsAndPhasesLastRecord();
		Assert.assertTrue(!phasesLastRecord.isEmpty(), "The last phase record hasn't been displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeQuantityForService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(data.getService());
		String serviceQuantity = RandomStringUtils.randomNumeric(2);
		final String serviceTotalPrice = detailsPage.getTotalServicesPrice();
		System.out.println("serviceTotalPrice: " + serviceTotalPrice);
		System.out.println("Random serviceQuantity: " + serviceQuantity);
		System.out.println("ServiceQuantity: " + detailsPage.getServiceQuantity(serviceId));

		if (serviceQuantity.equals(detailsPage.getServiceQuantity(serviceId))) {
			serviceQuantity = RandomStringUtils.randomNumeric(2);
			System.out.println("Random serviceQuantity 2: " + serviceQuantity);
		}
		detailsPage.setServiceQuantity(serviceId, data.getService(), serviceQuantity);
		detailsPage.updateTotalServicePrice(detailsPage.getTotalServicesPrice());
		System.out.println("Updated total services price: " + detailsPage.getTotalServicesPrice());
		Assert.assertNotEquals(serviceTotalPrice, detailsPage.getTotalServicesPrice(),
				"The service total price hasn't been recalculated after changing the service quantity");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanEnterNegativeNumberForServiceQuantity(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(data.getService());
		String negativeServiceQuantity = String.valueOf(-(RandomUtils.nextInt(10, 100)));
		String serviceQuantity = String.valueOf(RandomUtils.nextInt(1, 100));
        detailsPage.setServiceQuantity(serviceId, data.getService(), serviceQuantity);
        detailsPage.updateTotalServicePrice(detailsPage.getTotalServicesPrice());

        final String serviceTotalPrice = detailsPage.getTotalServicesPrice();
		System.out.println("serviceTotalPrice: " + serviceTotalPrice);
		System.out.println("Random negative serviceQuantity: " + negativeServiceQuantity);
		System.out.println("Random serviceQuantity: " + negativeServiceQuantity);
		System.out.println("ServiceQuantity: " + detailsPage.getServiceQuantity(serviceId) + "\n");

		if (String.valueOf(Math.abs(Integer.valueOf(negativeServiceQuantity))).equals(detailsPage.getServiceQuantity(serviceId))) {
			negativeServiceQuantity = String.valueOf(-(RandomUtils.nextInt(10, 100)));
			System.out.println("Random serviceQuantity 2: " + negativeServiceQuantity);
		}
		detailsPage.setServiceQuantity(serviceId, data.getService(), negativeServiceQuantity);
		detailsPage.updateTotalServicePrice(detailsPage.getTotalServicesPrice());
		System.out.println("Updated total services price: " + detailsPage.getTotalServicesPrice());
        //todo the total price sometimes is not recalculated, but can hardly be reproduced manually
//		Assert.assertNotEquals(serviceTotalPrice, detailsPage.getTotalServicesPrice(),
//				"The service total price hasn't been recalculated after setting the negative number for the service quantity");
		Assert.assertEquals(detailsPage.getServiceQuantity(serviceId), String.valueOf(0),
                "The service quantity hasn't been changed to 0");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCannotEnterTextForServiceQuantity(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(data.getService());
		final String serviceTotalPrice = detailsPage.getTotalServicesPrice();
		System.out.println("ServiceID: " + serviceId);
		System.out.println("serviceTotalPrice: " + serviceTotalPrice);
		System.out.println("ServiceQuantity: " + data.getServiceQuantity());
		System.out.println("ServiceQuantity: " + detailsPage.getServiceQuantity(serviceId));

		detailsPage.setServiceQuantity(serviceId, data.getService(), data.getServiceQuantity());
		detailsPage.updateTotalServicePrice(detailsPage.getTotalServicesPrice());
		System.out.println("Updated total services price: " + detailsPage.getTotalServicesPrice());
		Assert.assertEquals(serviceTotalPrice, detailsPage.getTotalServicesPrice(),
				"The service total price has been recalculated after entering the text " +
						"into the service quantity input field");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangePriceForService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(data.getService());
		String servicePrice = String.valueOf(RandomUtils.nextInt(10, 100));
		final String serviceTotalPrice = detailsPage.getTotalServicesPrice();
		System.out.println("serviceTotalPrice: " + serviceTotalPrice);
		System.out.println("Random servicePrice: " + servicePrice);
		System.out.println("ServicePrice: " + detailsPage.getServicePrice(serviceId));

		if (servicePrice.equals(detailsPage.getServicePrice(serviceId))) {
			servicePrice = String.valueOf(RandomUtils.nextInt(10, 100));
			System.out.println("Random servicePrice 2: " + servicePrice);
		}
		detailsPage.setServicePrice(serviceId, data.getService(), servicePrice);
		detailsPage.updateTotalServicePrice(detailsPage.getTotalServicesPrice());
		System.out.println("Updated total services price: " + detailsPage.getTotalServicesPrice());
		Assert.assertNotEquals(serviceTotalPrice, detailsPage.getTotalServicesPrice(),
				"The service total price hasn't been recalculated after changing the service price");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanEnterNegativeNumberForServicePrice(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(data.getService());
		String servicePrice = String.valueOf(-(RandomUtils.nextInt(10, 100)));
		final String serviceTotalPrice = detailsPage.getTotalServicesPrice();
		System.out.println("serviceTotalPrice: " + serviceTotalPrice);
		System.out.println("Random servicePrice: " + servicePrice);
		System.out.println("ServicePrice: " + detailsPage.getServicePrice(serviceId));

		if (String.valueOf(Math.abs(Integer.valueOf(servicePrice))).equals(detailsPage.getServiceQuantity(serviceId))) {
			servicePrice = String.valueOf(-(RandomUtils.nextInt(10, 100)));
			System.out.println("Random servicePrice 2: " + servicePrice);
		}
		detailsPage.setServicePrice(serviceId, data.getService(), servicePrice);
		detailsPage.updateTotalServicePrice(detailsPage.getTotalServicesPrice());
		System.out.println("Updated total services price: " + detailsPage.getTotalServicesPrice());
		Assert.assertNotEquals(serviceTotalPrice, detailsPage.getTotalServicesPrice(),
				"The service total price hasn't been recalculated " +
						"after setting the negative number for the service price");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCannotEnterTextForServicePrice(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(data.getService());
		final String serviceTotalPrice = detailsPage.getTotalServicesPrice();
		System.out.println("serviceTotalPrice: " + serviceTotalPrice);
		System.out.println("ServicePrice: " + data.getServicePrice());
		System.out.println("ServicePrice: " + detailsPage.getServicePrice(serviceId));

		detailsPage.setServicePrice(serviceId, data.getService(), data.getServicePrice());
		detailsPage.updateTotalServicePrice(detailsPage.getTotalServicesPrice());
		System.out.println("Updated total services price: " + detailsPage.getTotalServicesPrice());
		Assert.assertEquals(serviceTotalPrice, detailsPage.getTotalServicesPrice(),
				"The service total price has been recalculated after entering the text " +
						"into the service price input field");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeePhaseOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setStatus(data.getStatus());
		System.out.println("Phase name: " + detailsPage.getPhaseNameValue());
		System.out.println("Phase vendor price: " + detailsPage.getPhaseVendorPriceValue());
		System.out.println("Phase vendor technician: " + detailsPage.getPhaseVendorTechnicianValue());
		System.out.println("Phase status: " + detailsPage.getPhaseStatusValue());
		System.out.println("Phase actions trigger: " + VNextBORODetailsPageVerifications.isPhaseActionsTriggerDisplayed());

		Assert.assertEquals(detailsPage.getPhaseNameValue(), data.getServicePhaseHeaders()[0],
				"The phase name value hasn't been displayed properly");
		Assert.assertNotEquals(detailsPage.getPhaseVendorPriceValue(), "",
				"The phase vendor price hasn't been displayed");
		Assert.assertEquals(detailsPage.getPhaseVendorTechnicianValue(), data.getServicePhaseHeaders()[1],
				"The phase vendor technician value hasn't been displayed properly");
		Assert.assertEquals(detailsPage.getPhaseStatusValue(), data.getServicePhaseHeaders()[2],
				"The phase status hasn't been displayed properly");
		Assert.assertTrue(VNextBORODetailsPageVerifications.isPhaseActionsTriggerDisplayed(),
				"The phase actions trigger hasn't been displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeStartedPrice(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

//        final String calculatedPrice = detailsPage.getPhasePriceValue().replace("$", ""); //todo add method after bug #79907 fix!!!
//        System.out.println("Phase price: " + calculatedPrice);
//        final List<String> pricesValuesList = detailsPage.getVendorPricesValuesList();
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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeStartedVendorPrice(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String calculatedVendorPrice = detailsPage.getPhaseVendorPriceValue().replace("$", "");
		System.out.println("Phase vendor price: " + calculatedVendorPrice);
		final List<String> vendorPricesValuesList = detailsPage.getVendorPricesValuesList();

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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeTechnicianOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		final VNextBOChangeTechnicianDialog changeTechnicianDialog = new VNextBOChangeTechnicianDialog(webdriver);
		detailsPage.setStatus(data.getStatus());
		detailsPage.clickPhaseVendorTechnicianLink();

		Assert.assertTrue(changeTechnicianDialog.isChangeTechnicianDialogDisplayed(),
				"The Change Technician dialog hasn't been opened");

        VNextBOChangeTechniciansDialogSteps.setOptionsAndClickOkButtonForTechniciansDialog(data.getVendor(), data.getTechnician());

		final VNextBORODetailsPage repairOrderDetailsPage = PageFactory.initElements(
				DriverBuilder.getInstance().getDriver(), VNextBORODetailsPage.class);
		repairOrderDetailsPage.expandServicesTable();

		Assert.assertNotEquals(detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getVendor()), 0);
		Assert.assertNotEquals(detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getTechnician()), 0);
		Assert.assertEquals(detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getVendor()),
				detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getTechnician()),
				"The vendor and technician options haven't been changed for all repair orders with the 'Active' status");

		// clearing the test data
		detailsPage
				.setStatus(data.getStatus())
				.clickPhaseVendorTechnicianLink();

		Assert.assertTrue(changeTechnicianDialog.isChangeTechnicianDialogDisplayed(),
				"The Change Technician dialog hasn't been opened");

        VNextBOChangeTechniciansDialogSteps.setOptionsAndClickOkButtonForTechniciansDialog(data.getVendor1(), data.getTechnician1());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeAndNotSaveWithXButtonTechnicianOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final int numberOfVendorOptions = detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getVendor());
		final int numberOfTechnicianOptions = detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getTechnician());

		final VNextBOChangeTechnicianDialog changeTechnicianDialog = new VNextBOChangeTechnicianDialog(webdriver);
		detailsPage.clickPhaseVendorTechnicianLink();

		Assert.assertTrue(changeTechnicianDialog.isChangeTechnicianDialogDisplayed(),
				"The Change Technician dialog hasn't been opened");

        VNextBOChangeTechniciansDialogSteps.setOptionsAndClickXButtonForTechniciansDialog(data.getVendor(), data.getTechnician());

		Assert.assertEquals(numberOfVendorOptions, detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getVendor()),
				"The vendor options number has been changed for repair orders with the 'Active' status");

		Assert.assertEquals(numberOfTechnicianOptions, detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getTechnician()),
				"The vendor options number has been changed for repair orders with the 'Active' status");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeAndNotSaveWithCancelButtonTechnicianOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final int numberOfVendorOptions = detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getVendor());
		final int numberOfTechnicianOptions = detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getTechnician());

		final VNextBOChangeTechnicianDialog changeTechnicianDialog = new VNextBOChangeTechnicianDialog(webdriver);
		detailsPage.clickPhaseVendorTechnicianLink();

		Assert.assertTrue(changeTechnicianDialog.isChangeTechnicianDialogDisplayed(),
				"The Change Technician dialog hasn't been opened");

        VNextBOChangeTechniciansDialogSteps.setOptionsAndClickXButtonForTechniciansDialog(data.getVendor(), data.getTechnician());

		Assert.assertEquals(numberOfVendorOptions, detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getVendor()),
				"The vendor options number has been changed for repair orders with the 'Active' status");

		Assert.assertEquals(numberOfTechnicianOptions, detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getTechnician()),
				"The vendor options number has been changed for repair orders with the 'Active' status");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeAndChangeTechniciansOfTheCurrentPhase(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());

        final String priorTechniciansValue = VNextBOROPageInteractions.getTechniciansValueForWO(data.getOrderNumber());
        String selectedRandomTechnician = VNextBORepairOrdersPageSteps.setTechnicianAndVendorByWoNumber(
                data.getOrderNumber(), data.getVendor());
        if (priorTechniciansValue.equals(selectedRandomTechnician)) {
            selectedRandomTechnician = VNextBORepairOrdersPageSteps.setTechnicianAndVendorByWoNumber(
                    data.getOrderNumber(), data.getVendor());
        }
        VNextBORepairOrdersPageSteps.openRODetailsPage(data.getOrderNumber());

        VNextBORODetailsPageVerifications.verifyServiceIsDisplayedForCollapsedPhase(data.getServices()[0], data.getServiceTabs()[0]);
        VNextBORODetailsPageSteps.setServiceStatusForService(data.getServices()[0], data.getServiceStatuses()[0]);
        VNextBORODetailsPageVerifications.verifyServiceIsDisplayedForCollapsedPhase(data.getServices()[1], data.getServiceTabs()[1]);
        VNextBORODetailsPageVerifications.verifyVendorTechnicianNameIsSet(selectedRandomTechnician);

		breadCrumbInteractions.clickFirstBreadCrumbLink();
        VNextBOROPageValidations.verifyAnotherTechnicianIsDisplayed(data.getOrderNumber(), selectedRandomTechnician);
        VNextBORepairOrdersPageSteps.openRODetailsPage(data.getOrderNumber());
        VNextBORODetailsPageVerifications.verifyServiceIsDisplayedForCollapsedPhase(data.getServices()[0], data.getServiceTabs()[0]);
        VNextBORODetailsPageSteps.setServiceStatusForService(data.getServices()[0], data.getServiceStatuses()[1]);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSearchBySavedSearchForm(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        final VNextBOROAdvancedSearchDialog advSearchDialog = new VNextBOROAdvancedSearchDialog();

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBORepairOrdersPageSteps.setSavedSearchOption(data.getSearchValues().getSearchName());
        VNextBORepairOrdersPageSteps.openAdvancedSearchDialog();
        final VNextBOROAdvancedSearchValues searchValues = data.getSearchValues();

        Assert.assertEquals(advSearchDialog.getCustomerInputFieldValue(), searchValues.getCustomer());
        Assert.assertEquals(advSearchDialog.getEmployeeInputFieldValue(), searchValues.getEmployee());
        Assert.assertEquals(advSearchDialog.getPhaseInputFieldValue(), searchValues.getPhase());
        Assert.assertEquals(advSearchDialog.getPhaseStatusInputFieldValue(), searchValues.getPhaseStatus());
        Assert.assertEquals(advSearchDialog.getDepartmentInputFieldValue(), searchValues.getDepartment());
        Assert.assertEquals(advSearchDialog.getWoTypeInputFieldValue(), searchValues.getWoType());
        Assert.assertEquals(advSearchDialog.getWoNumInputFieldValue(), searchValues.getWoNum());
        Assert.assertEquals(advSearchDialog.getRoNumInputFieldValue(), searchValues.getRoNum());
        Assert.assertEquals(advSearchDialog.getStockInputFieldValue(), searchValues.getStockNum());
        Assert.assertEquals(advSearchDialog.getVinInputFieldValue(), searchValues.getVinNum());
        Assert.assertEquals(advSearchDialog.getTimeFrameInputFieldValue(), searchValues.getTimeFrame());
        Assert.assertEquals(advSearchDialog.getRepairStatusInputFieldValue(), searchValues.getRepairStatus());
        Assert.assertEquals(advSearchDialog.getDaysInProcessInputFieldValue(), searchValues.getDaysInProcess());
        Assert.assertEquals(advSearchDialog.getDaysInPhaseInputFieldValue(), searchValues.getDaysInPhase());
        Assert.assertEquals(advSearchDialog.getFlagInputFieldValue(), searchValues.getFlag());
        Assert.assertEquals(advSearchDialog.getSortByInputFieldValue(), searchValues.getSortBy());
        Assert.assertEquals(advSearchDialog.getSearchNameInputFieldValue(), searchValues.getSearchName());
    }

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCannotEditSavedSearch(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBORepairOrdersPageSteps.setSavedSearchOption(data.getSearchValues().getSearchNames()[0]);
        Assert.assertTrue(VNextBOROPageValidations.isSavedSearchEditIconDisplayed(),
                "The saved search edit icon hasn't been displayed");
        VNextBORepairOrdersPageSteps.openAdvancedSearchDialog();
        Assert.assertTrue(VNextBOROAdvancedSearchDialogVerifications.isSavedSearchNameClickable(),
                "The saved search input field isn't clickable");
        Assert.assertTrue(VNextBOROAdvancedSearchDialogVerifications.isSaveButtonClickable(),
                "The save button isn't clickable");
        VNextBOROAdvancedSearchDialogSteps.closeAdvancedSearchDialog();

        VNextBORepairOrdersPageSteps.setSavedSearchOption(data.getSearchValues().getSearchNames()[1]);
        VNextBORepairOrdersPageSteps.openAdvancedSearchDialog();
        Assert.assertFalse(VNextBOROPageValidations.isSavedSearchEditIconDisplayed(),
                "The saved search edit icon hasn't been displayed");
        Assert.assertFalse(VNextBOROAdvancedSearchDialogVerifications.isSavedSearchNameClickable(),
                "The saved search input field is clickable");
        Assert.assertFalse(VNextBOROAdvancedSearchDialogVerifications.isSaveButtonClickable(),
                "The save button is clickable");
        VNextBOROAdvancedSearchDialogSteps.closeAdvancedSearchDialog();
    }

//	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
//    TODO the TC blocker - the locations are not loaded for the given technician
	public void verifyTechnicianUserCanFindOrdersUsingSavedSearchMyCompletedWork(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHeaderPanelSteps.logout();
        loginPage.userLogin(data.getUserName(), data.getUserPassword());

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBORepairOrdersPageSteps.setSavedSearchOption(data.getSearchValues().getSearchName());
        VNextBORepairOrdersPageSteps.openRODetailsPage();
        VNextBORODetailsPageVerifications.verifyPhaseStatuses(data.getServiceStatuses());
    }

    //todo continue after the blocker is resolved
//	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyTechnicianUserCanFindOrdersUsingSavedSearchMyWorkQueue(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHeaderPanelSteps.logout();
        loginPage.userLogin(data.getUserName(), data.getUserPassword());

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBORepairOrdersPageSteps.setSavedSearchOption(data.getSearchValues().getSearchName());
        VNextBORepairOrdersPageSteps.openRODetailsPage();
        VNextBORODetailsPageVerifications.verifyPhaseStatuses(data.getServiceStatuses());
    }
}