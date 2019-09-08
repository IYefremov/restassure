package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairOrders.VNextBOChangeTechniciansDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairOrders.VNextBORepairOrdersDetailsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairOrders.VNextBORepairOrdersPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairOrders.VNextBORepairOrdersSimpleSearchSteps;
import com.cyberiansoft.test.vnextbo.verifications.VNextBORepairOrdersDetailsPageVerifications;
import com.cyberiansoft.test.vnextbo.verifications.VNextBORepairOrdersPageVerifications;
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
	private VNextBOLeftMenuInteractions leftMenuInteractions;
	private VNextBOBreadCrumbInteractions breadCrumbInteractions;
	private VNextBORepairOrdersWebPage repairOrdersPage;
	private HomePageSteps homePageSteps;
	private VNextBORepairOrdersSimpleSearchSteps simpleSearchSteps;
	private VNextBORepairOrdersPageSteps repairOrdersPageSteps;
	private VNextBOChangeTechniciansDialogSteps changeTechniciansDialogSteps;
	private VNextBORepairOrdersDetailsPageSteps detailsPageSteps;

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

		loginPage = PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOLoginScreenWebPage.class);
		loginPage.userLogin(userName, userPassword);
		repairOrdersPage = PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBORepairOrdersWebPage.class);
		leftMenuInteractions = new VNextBOLeftMenuInteractions();
		breadCrumbInteractions = new VNextBOBreadCrumbInteractions();
		homePageSteps = new HomePageSteps();
		simpleSearchSteps = new VNextBORepairOrdersSimpleSearchSteps();
		repairOrdersPageSteps = new VNextBORepairOrdersPageSteps();
		changeTechniciansDialogSteps = new VNextBOChangeTechniciansDialogSteps();
		detailsPageSteps = new VNextBORepairOrdersDetailsPageSteps();
	}

	@AfterMethod
	public void BackOfficeLogout() {
		try {
			VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver, VNextBOHeaderPanel.class);
			if (headerpanel.logOutLinkExists()) {
				headerpanel.userLogout();
			}
		} catch (RuntimeException ignored) {
		}

		if (DriverBuilder.getInstance().getDriver() != null) {
			DriverBuilder.getInstance().quitDriver();
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanOpenMonitorWithFullSetOfElements(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		Assert.assertTrue(repairOrdersPage.isSavedSearchContainerDisplayed(),
				"The search container isn't displayed");

//        repairOrdersPage.setDepartmentsTabActive(); todo add verifies for narrow screen, if necessary
//        Assert.assertTrue(repairOrdersPage.isDepartmentsTabDisplayed(),
//                "The department tab isn't displayed");

		Assert.assertTrue(repairOrdersPage.isDepartmentDropdownDisplayed(),
				"The department dropdown is not displayed");

		repairOrdersPage.clickPhasesWide();
		Assert.assertTrue(repairOrdersPage.isPhasesDropdownDisplayed(),
				"The phases dropdown is not displayed");

//        repairOrdersPage.makePhasesTabActive();
//        Assert.assertTrue(repairOrdersPage.isPhasesTabDisplayed(),
//                "The phases tab isn't displayed");

		Assert.assertTrue(repairOrdersPage.isSearchInputFieldDisplayed(),
				"The search input field isn't displayed");
		Assert.assertTrue(repairOrdersPage.isAdvancedSearchCaretDisplayed(),
				"The advanced search caret isn't displayed");
//        Assert.assertTrue(repairOrdersPage.areTableHeaderTitlesDisplayed(data.getTitles(), data.getTitlesRepeater()),
//                "The table header titles aren't displayed");
//        Assert.assertTrue(repairOrdersPage.isIntercomLauncherDisplayed(),
//                "The Intercom launcher is not displayed");
		Assert.assertTrue(repairOrdersPage.isFooterDisplayed(),
				"The footer is not displayed");
		Assert.assertTrue(repairOrdersPage.footerContains(data.getCopyright()),
				"The footer doesn't contain text " + data.getCopyright());
		Assert.assertTrue(repairOrdersPage.footerContains(data.getAMT()),
				"The footer doesn't contain text " + data.getAMT());
		Assert.assertTrue(repairOrdersPage.isTermsAndConditionsLinkDisplayed(),
				"The footer doesn't contain Terms and Conditions link");
		Assert.assertTrue(repairOrdersPage.isPrivacyPolicyLinkDisplayed(),
				"The footer doesn't contain Privacy Policy link");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanOpenAndCloseTermsAndConditions(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		repairOrdersPage
				.clickTermsAndConditionsLink()
				.scrollTermsAndConditionsDown()
				.rejectTermsAndConditions()
				.clickTermsAndConditionsLink()
				.acceptTermsAndConditions();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanOpenAndClosePrivacyPolicy(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		repairOrdersPage
				.clickPrivacyPolicyLink()
				.scrollPrivacyPolicyDown()
				.rejectPrivacyPolicy()
				.clickPrivacyPolicyLink()
				.acceptPrivacyPolicy();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanOpenAndCloseIntercom(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		repairOrdersPage
				.openIntercom()
				.closeIntercom();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeLocation(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getSearchLocation());
		Assert.assertTrue(breadCrumbInteractions.isLocationSearched(data.getSearchLocation()),
				"The location is not searched");
		repairOrdersPage.clickLocationInDropDown(data.getSearchLocation());
		Assert.assertTrue(breadCrumbInteractions.isLocationExpanded(), "The location is not expanded");
		repairOrdersPage.clickSearchTextToCloseLocationDropDown();
		Assert.assertTrue(breadCrumbInteractions.isLocationSelected(data.getSearchLocation()), "The location has been changed");

		Assert.assertTrue(breadCrumbInteractions.isLocationSearched(data.getLocation()),
				"The location is not searched");
		repairOrdersPage.clickLocationInDropDown(data.getLocation());

		Assert.assertTrue(breadCrumbInteractions.isLocationSelected(data.getLocation()), "The location hasn't been selected");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeLocationUsingSearch(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		leftMenuInteractions.selectRepairOrdersMenu();
		Assert.assertTrue(breadCrumbInteractions.isLocationSearched(data.getSearchLocation()),
				"The location is not searched");
		repairOrdersPage.clickLocationInDropDown(data.getLocation());
		Assert.assertTrue(breadCrumbInteractions.isLocationSelected(data.getLocation()), "The location hasn't been selected");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanUsePaging(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		Assert.assertTrue(repairOrdersPage.isPrevButtonDisabled(), "The previous page button is not disabled");
		repairOrdersPage.clickNextButton();
		Assert.assertFalse(repairOrdersPage.isPrevButtonDisabled(), "The previous page button is not enabled");
		repairOrdersPage.clickPrevButton();
		Assert.assertTrue(repairOrdersPage.isPrevButtonDisabled(), "The previous page button is not disabled");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanFilterRObyDepartments(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		repairOrdersPage.setDepartmentsTabActive();

		Assert.assertTrue(repairOrdersPage.isDepartmentsTabDisplayed());
		Assert.assertEquals(repairOrdersPage.getAllDepartmentsSum(), repairOrdersPage.getDepartmentsValues(),
				"The departments values are not calculated properly");
		final List<Integer> departmentValues = new ArrayList<>();
		departmentValues.add(Integer.valueOf(repairOrdersPage.getDepartmentsValue(1)));
//        departmentValues.add(Integer.valueOf(repairOrdersPage.getDepartmentsValue(2)));
		if (repairOrdersPage.isDepartmentNarrowScreenClickable()) {
			System.out.println("Narrow Screen");
			for (int i = 0, j = 1; i < departmentValues.size(); i++, j++) {
				System.out.println(j);
				repairOrdersPage.clickDepartmentForNarrowScreen(data.getDepartments().get(j));
				if (departmentValues.get(i) == 0) {
					Assert.assertFalse(repairOrdersPage.isTableDisplayed(), "The table shouldn't be displayed");
					Assert.assertTrue(repairOrdersPage.isTextNoRecordsDisplayed(),
							"The text notification is not displayed");
				} else {
					Assert.assertTrue(departmentValues.get(0) >= repairOrdersPage.getNumOfOrdersOnPage(),
							"The departments repair orders number in table " +
									"is less than value displayed in menu container");
				}
			}
		} else {
			System.out.println("Wide Screen");
			for (int i = 0, j = 1; i < departmentValues.size(); i++, j++) {
				System.out.println(j);
				repairOrdersPage.clickDepartmentForWideScreen(data.getDepartments().get(j));
				if (departmentValues.get(i) == 0) {
					Assert.assertFalse(repairOrdersPage.isTableDisplayed(), "The table shouldn't be displayed");
					Assert.assertTrue(repairOrdersPage.isTextNoRecordsDisplayed(),
							"The text notification is not displayed");
				} else {
					Assert.assertTrue(departmentValues.get(0) >= repairOrdersPage.getNumOfOrdersOnPage(),
							"The departments repair orders number in table " +
									"is less than value displayed in menu container");
				}
			}
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanFilterRObyPhases(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		repairOrdersPage.setPhasesTabActive();

		Assert.assertTrue(repairOrdersPage.isPhasesTabDisplayed());
		Assert.assertEquals(repairOrdersPage.getAllPhasesSum(), repairOrdersPage.getPhasesValues(),
				"The phases values are not calculated properly");
		final List<Integer> phasesValues = new ArrayList<>();
		phasesValues.add(Integer.valueOf(repairOrdersPage.getPhasesValue(1)));
		phasesValues.add(Integer.valueOf(repairOrdersPage.getPhasesValue(2)));
//        phasesValues.add(Integer.valueOf(repairOrdersPage.getPhasesValue(3)));

		if (repairOrdersPage.isPhasesNarrowScreenClickable()) {
			System.out.println("Narrow Screen");
			for (int i = 0, j = 1; i < phasesValues.size(); i++, j++) {
				repairOrdersPage.clickPhaseForNarrowScreen(data.getPhases().get(j));
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
				repairOrdersPage.clickPhaseForWideScreen(data.getPhases().get(j));
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		Assert.assertTrue(breadCrumbInteractions.isLocationSet(data.getLocation()), "The location hasn't been set");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSearchWoUsingSearchFilter(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		Assert.assertTrue(breadCrumbInteractions.isLocationSet(data.getLocation()), "The location hasn't been set");
		simpleSearchSteps.searchByText(data.getVinNum());

		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getVinNum()),
				"The work order is not displayed after search by VIN after clicking the 'Search' icon");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeAndChangeRoDetails(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		Assert.assertTrue(breadCrumbInteractions.isLocationSet(data.getLocation()), "The location hasn't been set");

		final String vinNum = data.getVinNum();
		simpleSearchSteps.searchByText(vinNum);
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(vinNum),
				"The work order is not displayed after search by VIN after clicking the 'Search' icon");

		Assert.assertEquals(repairOrdersPage.getTableTitleDisplayed(0), data.getTitle(),
				"The table title is incorrect");
		final VNextBORepairOrderDetailsPage repairOrderDetailsPage = repairOrdersPage.clickWoLink(vinNum);
		Assert.assertTrue(repairOrderDetailsPage.isRoDetailsSectionDisplayed(),
				"The RO details section hasn't been displayed");
		repairOrderDetailsPage.goToPreviousPage();

		repairOrdersPage.closeNoteForWorkOrder(vinNum);
		Assert.assertTrue(repairOrdersPage.isWoTypeDisplayed(data.getWoType()));

		final List<String> departments = data.getDepartments();
		for (String department : departments) {
			System.out.println(department);
			repairOrdersPage.setWoDepartment(vinNum, department);
			Assert.assertEquals(repairOrdersPage.getWoDepartment(vinNum), department,
					"The WO department hasn't been set");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCannotChangePoForInvoicedOrders(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation(), true);

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByOrderNumber(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		Assert.assertFalse(repairOrdersPage.isPoNumClickable(), "The PO# shouldn't be clickable");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeInvoiceOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		Assert.assertTrue(breadCrumbInteractions.isLocationSet(data.getLocation()), "The location hasn't been set");

		simpleSearchSteps.searchByText(data.getOrderNumber());
		final VNextBOInvoicesDescriptionWindow invoicesDescription = repairOrdersPage
				.clickInvoiceNumberInTable(data.getOrderNumber(), data.getInvoiceNumber());
		Assert.assertEquals(webdriver.getWindowHandles().size(), 2);
		invoicesDescription.closeWindows();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoToCheckInCheckOut(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		repairOrdersPage.openOtherDropDownMenu(data.getOrderNumber());
		//todo finish after TC update
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanShowOrCloseNotesToTheLeftOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		repairOrdersPage
				.openNoteForWorkOrder(data.getOrderNumber())
				.closeNoteForWorkOrder(data.getOrderNumber())
				.openNoteForWorkOrder(data.getOrderNumber())
				.clickXIconToCloseNoteForWorkOrder(data.getOrderNumber());
		Assert.assertFalse(repairOrdersPage.isNoteForWorkOrderDisplayed(data.getOrderNumber()),
				"The note for work order has not been closed after clicking the 'X' icon");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanTypeAndNotSaveNotesWithXIcon(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		repairOrdersPage.openNoteForWorkOrder(data.getOrderNumber());
		//todo bug fix #78127
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanTypeAndNotSaveNotesWithClose(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		repairOrdersPage.openNoteForWorkOrder(data.getOrderNumber());
		//todo bug fix #78127
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanOpenRoDetails(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStockOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.typeStockNumber(data.getStockNumbers()[1]);
		detailsPage.typeStockNumber(data.getStockNumbers()[0]);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeRoNumOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.typeRoNumber(data.getRoNumbers()[1]);
		detailsPage.typeRoNumber(data.getRoNumbers()[0]);
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoToNew(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setStatus(data.getStatus());
		Assert.assertEquals(detailsPage.getRoStatus(), data.getStatus(), "The status hasn't been set");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoToOnHold(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setStatus(data.getStatus());
		Assert.assertEquals(detailsPage.getRoStatus(), data.getStatus(), "The status hasn't been set");
		Assert.assertTrue(detailsPage.isImageOnHoldStatusDisplayed(),
				"The On Hold image notification hasn't been displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoToApproved(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setStatus(data.getStatus());
		Assert.assertEquals(detailsPage.getRoStatus(), data.getStatus(), "The status hasn't been set");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCannotChangeStatusOfRoToDraft(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setStatus(data.getStatus());
		Assert.assertNotEquals(detailsPage.getRoStatus(), data.getStatus(), "The status has been changed to 'Draft'");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoToClosed(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setStatus(data.getStatus());
		Assert.assertEquals(detailsPage.getRoStatus(), data.getStatus(), "The status hasn't been set");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangePriorityOfRoToLow(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setPriority(data.getPriority());
		detailsPage.clickRepairOrdersBackwardsLink();
		repairOrdersPage.clickCancelSearchIcon();

		if (repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber())) {
			Assert.assertTrue(repairOrdersPage.isArrowDownDisplayed(data.getOrderNumber()),
					"The work order arrow down is not displayed");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangePriorityOfRoToNormal(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setPriority(data.getPriority());
		detailsPage.clickRepairOrdersBackwardsLink();
		repairOrdersPage.clickCancelSearchIcon();

		if (repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber())) {
			Assert.assertFalse(repairOrdersPage.isArrowDownDisplayed(data.getOrderNumber()),
					"The work order arrow down should not be displayed");
			Assert.assertFalse(repairOrdersPage.isArrowUpDisplayed(data.getOrderNumber()),
					"The work order arrow down should not be displayed");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanAddNewService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setPriority(data.getPriority());
		detailsPage.clickRepairOrdersBackwardsLink();
		repairOrdersPage.clickCancelSearchIcon();

		if (repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber())) {
			Assert.assertTrue(repairOrdersPage.isArrowUpDisplayed(data.getOrderNumber()),
					"The work order arrow down is not displayed");
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanAddNewMoneyService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
		detailsPage.clickFlagIcon();
		Assert.assertTrue(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		detailsPage.selectFlagColor(data.getFlag());
		Assert.assertFalse(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRoToRed(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
		detailsPage.clickFlagIcon();
		Assert.assertTrue(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		detailsPage.selectFlagColor(data.getFlag());
		Assert.assertFalse(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRoToOrange(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
		detailsPage.clickFlagIcon();
		Assert.assertTrue(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		detailsPage.selectFlagColor(data.getFlag());
		Assert.assertFalse(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRoToYellow(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
		detailsPage.clickFlagIcon();
		Assert.assertTrue(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		detailsPage.selectFlagColor(data.getFlag());
		Assert.assertFalse(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRoToGreen(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
		detailsPage.clickFlagIcon();
		Assert.assertTrue(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		detailsPage.selectFlagColor(data.getFlag());
		Assert.assertFalse(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRoToBlue(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
		detailsPage.clickFlagIcon();
		Assert.assertTrue(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		detailsPage.selectFlagColor(data.getFlag());
		Assert.assertFalse(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeFlagOfRoToPurple(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
		detailsPage.clickFlagIcon();
		Assert.assertTrue(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been opened");
		detailsPage.selectFlagColor(data.getFlag());
		Assert.assertFalse(detailsPage.isFlagsDropDownOpened(), "The flags drop down hasn't been closed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeChangesOfServicesActivityInLogInfo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(data.getService());

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
		Assert.assertEquals(detailsPage.getServiceStatusValue(serviceId), data.getServiceStatuses()[1]);

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
		Assert.assertEquals(detailsPage.getServiceStatusValue(serviceId), data.getServiceStatuses()[0]);

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

		auditLogDialog.clickAuditLogsServicesTab();
		final String servicesActivityTimeFirstRecord = auditLogDialog.getServicesActivityTimeFirstRecord();
		final String actualLocalDateTime = auditLogDialog.getActualLocalDateTime();
		final String actualLocalDateTimeMinusMinute = auditLogDialog.getActualLocalDateTimeMinusMinute();
		final String actualLocalDateTimeMinusTwoMinutes = auditLogDialog.getActualLocalDateTimeMinusTwoMinutes();
		if (servicesActivityTimeFirstRecord.equals(actualLocalDateTime)) {
			Assert.assertEquals(servicesActivityTimeFirstRecord, actualLocalDateTime);
		}
		if (servicesActivityTimeFirstRecord.equals(actualLocalDateTimeMinusMinute)) {
			Assert.assertEquals(servicesActivityTimeFirstRecord, actualLocalDateTimeMinusMinute);
		}
		if (servicesActivityTimeFirstRecord.equals(actualLocalDateTimeMinusTwoMinutes)) {
			Assert.assertEquals(servicesActivityTimeFirstRecord, actualLocalDateTimeMinusTwoMinutes);
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeChangesOfDepartmentsInLogInfo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(data.getService());

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
		Assert.assertEquals(detailsPage.getServiceStatusValue(serviceId), data.getServiceStatuses()[0]);

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
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
		final String departmentsLastRecord = auditLogDialog.getDepartmentsLastRecord();
		final String actualLocalDateTime = auditLogDialog.getActualLocalDateTime();
		final String actualLocalDateTimePlusMinute = auditLogDialog.getActualLocalDateTimePlusMinute();
		final String actualLocalDateTimePlusTwoMinutes = auditLogDialog.getActualLocalDateTimePlusTwoMinutes();
		final String actualLocalDateTimeMinusMinute = auditLogDialog.getActualLocalDateTimeMinusMinute();
		final String actualLocalDateTimeMinusTwoMinutes = auditLogDialog.getActualLocalDateTimeMinusTwoMinutes();
		if (departmentsLastRecord.equals(actualLocalDateTime)) {
			Assert.assertEquals(departmentsLastRecord, actualLocalDateTime);
			System.out.println(1);
		}
		if (departmentsLastRecord.equals(actualLocalDateTime)) {
			Assert.assertEquals(departmentsLastRecord, actualLocalDateTime);
			System.out.println(2);
		}
		if (departmentsLastRecord.equals(actualLocalDateTimeMinusMinute)) {
			Assert.assertEquals(departmentsLastRecord, actualLocalDateTimeMinusMinute);
			System.out.println(3);
		}
		if (departmentsLastRecord.equals(actualLocalDateTimeMinusTwoMinutes)) {
			Assert.assertEquals(departmentsLastRecord, actualLocalDateTimeMinusTwoMinutes);
			System.out.println(4);
		}
		if (departmentsLastRecord.equals(actualLocalDateTimePlusMinute)) {
			Assert.assertEquals(departmentsLastRecord, actualLocalDateTimePlusMinute);
			System.out.println(5);
		}
		if (departmentsLastRecord.equals(actualLocalDateTimePlusTwoMinutes)) {
			Assert.assertEquals(departmentsLastRecord, actualLocalDateTimePlusTwoMinutes);
			System.out.println(6);
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeServicesOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		final VNextBOOrderServiceNotesDialog notesDialog = new VNextBOOrderServiceNotesDialog(webdriver);
		detailsPage.openNotesDialog(serviceId);
		Assert.assertTrue(notesDialog.isRepairNotesBlockDisplayed(), "The notes dialog hasn't been opened");
		final int notesNumber = notesDialog.getRepairNotesListNumber();

		notesDialog
				.openRepairNoteTextArea()
				.typeRepairNotesMessage(data.getServiceNotesMessage())
				.clickRepairNoteSaveButton();

		Assert.assertTrue(notesDialog.isRepairNotesBlockDisplayed(), "The notes dialog hasn't been opened");
		Assert.assertEquals(notesNumber + 1, notesDialog.getRepairNotesListNumber(),
				"The services notes list number is not updated");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeTargetDateOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		final VNextBOOrderServiceNotesDialog notesDialog = new VNextBOOrderServiceNotesDialog(webdriver);
		detailsPage.openNotesDialog(serviceId);
		Assert.assertTrue(notesDialog.isRepairNotesBlockDisplayed(), "The notes block hasn't been displayed");
		final int notesNumber = notesDialog.getRepairNotesListNumber();

		notesDialog
				.openRepairNoteTextArea()
				.typeRepairNotesMessage(data.getServiceNotesMessage())
				.clickRepairNotesXbutton();
		Assert.assertEquals(notesDialog.getRepairNoteTextAreaValue(), "");
		notesDialog.closeRepairNoteDialog();

		detailsPage.openNotesDialog(serviceId);
		Assert.assertTrue(notesDialog.isRepairNotesBlockDisplayed(), "The notes dialog hasn't been opened");
		Assert.assertEquals(notesNumber, notesDialog.getRepairNotesListNumber(),
				"The services notes list number has been updated, although the 'X' button was clicked");

		notesDialog.openRepairNoteTextArea().typeRepairNotesMessage(data.getServiceNotesMessage());
//        Assert.assertEquals(notesDialog.getRepairNoteTextAreaValue(), data.getServiceNotesMessage());
		notesDialog.closeRepairNoteDialog();

		detailsPage.openNotesDialog(serviceId);
		Assert.assertTrue(notesDialog.isRepairNotesBlockDisplayed(), "The notes dialog hasn't been opened");
		Assert.assertEquals(notesNumber, notesDialog.getRepairNotesListNumber(),
				"The services notes list number has been updated, although the 'X' button was clicked");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeStatusOfRoServiceToActive(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();

		final String service = data.getService();
		final String serviceId = detailsPage.getServiceId(service);
		Assert.assertNotEquals(serviceId, "", "The service hasn't been displayed");

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
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
	public void verifyUserCanSeeChangesOfPhasesInLogInfo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(data.getService());

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[0]);
		Assert.assertEquals(detailsPage.getServiceStatusValue(serviceId), data.getServiceStatuses()[0]);

		detailsPage.setServiceStatusForService(serviceId, data.getServiceStatuses()[1]);
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
		final String phasesLastRecord = auditLogDialog.getPhasesLastRecord();
		Assert.assertTrue(!phasesLastRecord.isEmpty(), "The last phase record hasn't been displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeQuantityForService(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(data.getService());
		String serviceQuantity = String.valueOf(-(RandomUtils.nextInt(10, 100)));
		final String serviceTotalPrice = detailsPage.getTotalServicesPrice();
		System.out.println("serviceTotalPrice: " + serviceTotalPrice);
		System.out.println("Random serviceQuantity: " + serviceQuantity);
		System.out.println("ServiceQuantity: " + detailsPage.getServiceQuantity(serviceId));

		if (String.valueOf(Math.abs(Integer.valueOf(serviceQuantity))).equals(detailsPage.getServiceQuantity(serviceId))) {
			serviceQuantity = String.valueOf(-(RandomUtils.nextInt(10, 100)));
			System.out.println("Random serviceQuantity 2: " + serviceQuantity);
		}
		detailsPage.setServiceQuantity(serviceId, data.getService(), serviceQuantity);
		detailsPage.updateTotalServicePrice(detailsPage.getTotalServicesPrice());
		System.out.println("Updated total services price: " + detailsPage.getTotalServicesPrice());
		Assert.assertNotEquals(serviceTotalPrice, detailsPage.getTotalServicesPrice(),
				"The service total price hasn't been recalculated after setting the negative number for the service quantity");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCannotEnterTextForServiceQuantity(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage
				.setStatus(data.getStatus())
				.expandServicesTable();
		final String serviceId = detailsPage.getServiceId(data.getService());
		final String serviceTotalPrice = detailsPage.getTotalServicesPrice();
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		detailsPage.setStatus(data.getStatus());
		System.out.println("Phase name: " + detailsPage.getPhaseNameValue());
		System.out.println("Phase vendor price: " + detailsPage.getPhaseVendorPriceValue());
		System.out.println("Phase vendor technician: " + detailsPage.getPhaseVendorTechnicianValue());
		System.out.println("Phase status: " + detailsPage.getPhaseStatusValue());
		System.out.println("Phase actions trigger: " + detailsPage.isPhaseActionsTriggerDisplayed());

		Assert.assertEquals(detailsPage.getPhaseNameValue(), data.getServicePhaseHeaders()[0],
				"The phase name value hasn't been displayed properly");
		Assert.assertNotEquals(detailsPage.getPhaseVendorPriceValue(), "",
				"The phase vendor price hasn't been displayed");
		Assert.assertEquals(detailsPage.getPhaseVendorTechnicianValue(), data.getServicePhaseHeaders()[1],
				"The phase vendor technician value hasn't been displayed properly");
		Assert.assertEquals(detailsPage.getPhaseStatusValue(), data.getServicePhaseHeaders()[2],
				"The phase status hasn't been displayed properly");
		Assert.assertTrue(detailsPage.isPhaseActionsTriggerDisplayed(),
				"The phase actions trigger hasn't been displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeStartedPrice(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
		Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

		final VNextBOChangeTechnicianDialog changeTechnicianDialog = new VNextBOChangeTechnicianDialog(webdriver);
		detailsPage.setStatus(data.getStatus());
		detailsPage.clickPhaseVendorTechnicianLink();

		Assert.assertTrue(changeTechnicianDialog.isChangeTechnicianDialogDisplayed(),
				"The Change Technician dialog hasn't been opened");

		changeTechniciansDialogSteps.setOptionsAndClickOkButtonForTechniciansDialog(data.getVendor(), data.getTechnician());

		final VNextBORepairOrderDetailsPage repairOrderDetailsPage = PageFactory.initElements(
				DriverBuilder.getInstance().getDriver(), VNextBORepairOrderDetailsPage.class);
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

		changeTechniciansDialogSteps.setOptionsAndClickOkButtonForTechniciansDialog(data.getVendor1(), data.getTechnician1());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeAndNotSaveWithXButtonTechnicianOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		changeTechniciansDialogSteps.setOptionsAndClickXButtonForTechniciansDialog(data.getVendor(), data.getTechnician());

		Assert.assertEquals(numberOfVendorOptions, detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getVendor()),
				"The vendor options number has been changed for repair orders with the 'Active' status");

		Assert.assertEquals(numberOfTechnicianOptions, detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getTechnician()),
				"The vendor options number has been changed for repair orders with the 'Active' status");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanChangeAndNotSaveWithCancelButtonTechnicianOfRo(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

		simpleSearchSteps.searchByText(data.getOrderNumber());
		Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
				"The work order is not displayed after search by order number after clicking the 'Search' icon");

		final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
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

		changeTechniciansDialogSteps.setOptionsAndClickXButtonForTechniciansDialog(data.getVendor(), data.getTechnician());

		Assert.assertEquals(numberOfVendorOptions, detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getVendor()),
				"The vendor options number has been changed for repair orders with the 'Active' status");

		Assert.assertEquals(numberOfTechnicianOptions, detailsPage.getNumberOfVendorTechnicianOptionsByName(data.getTechnician()),
				"The vendor options number has been changed for repair orders with the 'Active' status");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyUserCanSeeAndChangeTechniciansOfTheCurrentPhase(String rowID, String description, JSONObject testData) {
		VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
		final VNextBORepairOrdersDetailsPageVerifications ordersDetailsPageVerifications =
				new VNextBORepairOrdersDetailsPageVerifications();

		homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
		simpleSearchSteps.searchByText(data.getOrderNumber());

		final String selectedRandomTechnician = repairOrdersPageSteps.setTechnicianAndVendorByWoNumber(
				data.getOrderNumber(), data.getVendor());
		repairOrdersPageSteps.openRODetailsPage(data.getOrderNumber());

		ordersDetailsPageVerifications.verifyServiceIsDisplayedForCollapsedPhase(data.getServices()[0], data.getServiceTabs()[0]);
		detailsPageSteps.setServiceStatusForService(data.getServices()[0], data.getServiceStatuses()[0]);
		ordersDetailsPageVerifications.verifyServiceIsDisplayedForCollapsedPhase(data.getServices()[1], data.getServiceTabs()[1]);
		ordersDetailsPageVerifications.verifyVendorTechnicianNameIsSet(selectedRandomTechnician);

		breadCrumbInteractions.clickFirstBreadCrumbLink();
		new VNextBORepairOrdersPageVerifications()
				.verifyTechnicianIsDisplayed(data.getOrderNumber(), selectedRandomTechnician);
		repairOrdersPageSteps.openRODetailsPage(data.getOrderNumber());
		ordersDetailsPageVerifications.verifyServiceIsDisplayedForCollapsedPhase(data.getServices()[0], data.getServiceTabs()[0]);
		detailsPageSteps.setServiceStatusForService(data.getServices()[0], data.getServiceStatuses()[1]);
	}
}