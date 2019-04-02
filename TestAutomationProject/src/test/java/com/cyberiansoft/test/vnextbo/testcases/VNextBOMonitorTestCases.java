package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.*;
import org.apache.commons.lang3.RandomStringUtils;
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
    private VNexBOLeftMenuPanel leftMenu;

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

        loginPage = PageFactory.initElements(webdriver, VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(userName, userPassword);
        leftMenu = PageFactory.initElements(webdriver, VNexBOLeftMenuPanel.class);
    }

    @AfterMethod
    public void BackOfficeLogout() {
        try {
            VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver, VNextBOHeaderPanel.class);
            if (headerpanel.logOutLinkExists()) {
                headerpanel.userLogout();
            }
        } catch (RuntimeException ignored) {}

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenMonitorWithFullSetOfElements(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());

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
        Assert.assertTrue(repairOrdersPage.areTableHeaderTitlesDisplayed(data.getTitles(), data.getTitlesRepeater()),
                "The table header titles aren't displayed");
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .clickTermsAndConditionsLink()
                .scrollTermsAndConditionsDown()
                .rejectTermsAndConditions()
                .clickTermsAndConditionsLink()
                .acceptTermsAndConditions();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAndClosePrivacyPolicy(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .clickPrivacyPolicyLink()
                .scrollPrivacyPolicyDown()
                .rejectPrivacyPolicy()
                .clickPrivacyPolicyLink()
                .acceptPrivacyPolicy();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAndCloseIntercom(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .openIntercom()
                .closeIntercom();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeLocation(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getSearchLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSearched(data.getSearchLocation()),
                "The location is not searched");
        repairOrdersPage.clickLocationInDropDown(data.getSearchLocation());
        Assert.assertTrue(repairOrdersPage.isLocationExpanded(), "The location is not expanded");
        repairOrdersPage.clickSearchTextToCloseLocationDropDown();
        Assert.assertTrue(repairOrdersPage.isLocationSelected(data.getSearchLocation()), "The location has been changed");

        Assert.assertTrue(repairOrdersPage.isLocationSearched(data.getLocation()),
                "The location is not searched");
        repairOrdersPage.clickLocationInDropDown(data.getLocation());

        Assert.assertTrue(repairOrdersPage.isLocationSelected(data.getLocation()), "The location hasn't been selected");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeLocationUsingSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        Assert.assertTrue(repairOrdersPage.isLocationSearched(data.getSearchLocation()),
                "The location is not searched");
        repairOrdersPage.clickLocationInDropDown(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSelected(data.getLocation()), "The location hasn't been selected");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUsePaging(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isPrevButtonDisabled(), "The previous page button is not disabled");
        repairOrdersPage.clickNextButton();
        Assert.assertFalse(repairOrdersPage.isPrevButtonDisabled(), "The previous page button is not disabled");
        repairOrdersPage.clickPrevButton();
        Assert.assertTrue(repairOrdersPage.isPrevButtonDisabled(), "The previous page button is not disabled");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByVIN(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setRepairOrdersSearchText(data.getVinNum())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getVinNum()),
                "The work order is not displayed after search by VIN after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();

        repairOrdersPage
                .setRepairOrdersSearchText(data.getVinNum())
                .clickEnterToSearch();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getVinNum()),
                "The work order is not displayed after search by VIN after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByOrderNum(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByOrderNumber(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickEnterToSearch();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByOrderNumber(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRoNum(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setRepairOrdersSearchText(data.getRoNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByRoNumber(data.getRoNumber()),
                "The work order is not displayed after search by RO number after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();

        repairOrdersPage
                .setRepairOrdersSearchText(data.getRoNumber())
                .clickEnterToSearch();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByRoNumber(data.getRoNumber()),
                "The work order is not displayed after search by RO number after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByFirstName(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setRepairOrdersSearchText(data.getFirstName())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByFirstName(data.getFirstName()),
                "The work order is not displayed after search by first name after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();

        repairOrdersPage
                .setRepairOrdersSearchText(data.getFirstName())
                .clickEnterToSearch();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByFirstName(data.getFirstName()),
                "The work order is not displayed after search by first name after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByLastName(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setRepairOrdersSearchText(data.getLastName())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByLastName(data.getLastName()),
                "The work order is not displayed after search by last name after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();

        repairOrdersPage
                .setRepairOrdersSearchText(data.getLastName())
                .clickEnterToSearch();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByLastName(data.getLastName()),
                "The work order is not displayed after search by last name after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByEmail(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setRepairOrdersSearchText(data.getEmail())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage
                        .isWorkOrderDisplayedAfterSearchByEmail(data.getFirstName() + " " + data.getLastName()),
                "The work order is not displayed after search by email after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();

        repairOrdersPage
                .setRepairOrdersSearchText(data.getEmail())
                .clickEnterToSearch();
        Assert.assertTrue(repairOrdersPage
                        .isWorkOrderDisplayedAfterSearchByEmail(data.getFirstName() + " " + data.getLastName()),
                "The work order is not displayed after search by email after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByCompanyName(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setRepairOrdersSearchText(data.getCompany())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage
                        .isWorkOrderDisplayedAfterSearchByCompanyName(data.getFirstName() + " " + data.getLastName()),
                "The work order is not displayed after search by company name after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();

        repairOrdersPage
                .setRepairOrdersSearchText(data.getCompany())
                .clickEnterToSearch();
        Assert.assertTrue(repairOrdersPage
                        .isWorkOrderDisplayedAfterSearchByCompanyName(data.getFirstName() + " " + data.getLastName()),
                "The work order is not displayed after search by company name after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanFilterRObyDepartments(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setDepartmentsTabActive();

        Assert.assertTrue(repairOrdersPage.isDepartmentsTabDisplayed());
        Assert.assertEquals(repairOrdersPage.getAllDepartmentsSum(), repairOrdersPage.getDepartmentsValues(),
                "The departments values are not calculated properly");
        final List<Integer> departmentValues = new ArrayList<>();
        departmentValues.add(Integer.valueOf(repairOrdersPage.getDepartmentsValue(1)));
        departmentValues.add(Integer.valueOf(repairOrdersPage.getDepartmentsValue(2)));
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage
                .setLocation(data.getLocation())
                .setPhasesTabActive();

        Assert.assertTrue(repairOrdersPage.isPhasesTabDisplayed());
        Assert.assertEquals(repairOrdersPage.getAllPhasesSum(), repairOrdersPage.getPhasesValues(),
                "The phases values are not calculated properly");
        final List<Integer> phasesValues = new ArrayList<>();
        phasesValues.add(Integer.valueOf(repairOrdersPage.getPhasesValue(1)));
        phasesValues.add(Integer.valueOf(repairOrdersPage.getPhasesValue(2)));
        phasesValues.add(Integer.valueOf(repairOrdersPage.getPhasesValue(3)));

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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchWoUsingSearchFilter(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getVinNum())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getVinNum()),
                "The work order is not displayed after search by VIN after clicking the 'Search' icon");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeAndChangeRoDetails(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        final String vinNum = data.getVinNum();
        repairOrdersPage
                .setRepairOrdersSearchText(vinNum)
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation(), true);
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickEnterToSearch();

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByOrderNumber(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        Assert.assertFalse(repairOrdersPage.isPoNumClickable(), "The PO# shouldn't be clickable");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeInvoiceOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage.searchRepairOrderByNumber(data.getOrderNumber());
        final VNextBOInvoicesDescriptionWindow invoicesDescription = repairOrdersPage
                .clickInvoiceNumberInTable(data.getOrderNumber(), data.getInvoiceNumber());
        Assert.assertEquals(webdriver.getWindowHandles().size(), 2);
        invoicesDescription.closeWindows();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeStatusOfRoToCheckInCheckOut(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage.searchRepairOrderByNumber(data.getOrderNumber());
        repairOrdersPage.openOtherDropDownMenu(data.getOrderNumber());
        //todo finish after TC update
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanShowOrCloseNotesToTheLeftOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon()
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon()
                .openNoteForWorkOrder(data.getOrderNumber());
        //todo bug fix #78127
        // https://cyb.tpondemand.com/RestUI/Board.aspx#page=board/4776777690820028078&appConfig=eyJhY2lkIjoiMDI1QjBFRDZFODYzMzk3M0ZCMDRFNUE5MEQ0QzBGQzMifQ==&boardPopup=Bug/78127/silent
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanTypeAndNotSaveNotesWithClose(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon()
                .openNoteForWorkOrder(data.getOrderNumber());
        //todo bug fix #78127
        // https://cyb.tpondemand.com/RestUI/Board.aspx#page=board/4776777690820028078&appConfig=eyJhY2lkIjoiMDI1QjBFRDZFODYzMzk3M0ZCMDRFNUE5MEQ0QzBGQzMifQ==&boardPopup=Bug/78127/silent
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenRoDetails(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeStockOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        detailsPage.setStatus(data.getStatus());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeStatusOfRoToOnHold(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        detailsPage.setStatus(data.getStatus());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeStatusOfRoToApproved(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        detailsPage.setStatus(data.getStatus());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeStatusOfRoToDraft(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        detailsPage.setStatus(data.getStatus());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeStatusOfRoToClosed(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        detailsPage.setStatus(data.getStatus());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangePriorityOfRoToLow(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        detailsPage
                .setPriority(data.getPriority())
                .clickBackwardsLink()
                .clickCancelSearchIcon();

        if (repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber())) {
            Assert.assertTrue(repairOrdersPage.isArrowDownDisplayed(data.getOrderNumber()),
                    "The work order arrow down is not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangePriorityOfRoToNormal(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        detailsPage
                .setPriority(data.getPriority())
                .clickBackwardsLink()
                .clickCancelSearchIcon();

        if (repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber())) {
            Assert.assertFalse(repairOrdersPage.isArrowDownDisplayed(data.getOrderNumber()),
                    "The work order arrow down should not be displayed");
            Assert.assertFalse(repairOrdersPage.isArrowUpDisplayed(data.getOrderNumber()),
                    "The work order arrow down should not be displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangePriorityOfRoToHigh(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        detailsPage
                .setPriority(data.getPriority())
                .clickBackwardsLink()
                .clickCancelSearchIcon();

        if (repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber())) {
            Assert.assertTrue(repairOrdersPage.isArrowUpDisplayed(data.getOrderNumber()),
                    "The work order arrow down is not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddNewService(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        final VNextBOAddNewServiceMonitorDialog newServiceMonitorDialog = detailsPage.clickAddNewServiceButton();
        Assert.assertTrue(newServiceMonitorDialog.isNewServicePopupDisplayed());

        final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
        newServiceMonitorDialog
                .setPriceType(data.getPriceType())
                .setService(data.getService())
                .setServiceDescription(serviceDescription)
                .setServicePrice(data.getServicePrice())
                .setServiceQuantity(data.getServiceQuantity())
                .clickSubmitButton()
                .refreshPage();

        detailsPage.expandServicesTable();
        final String serviceId = detailsPage.getServiceId(serviceDescription);
        Assert.assertNotEquals(serviceId, "",
                "The created service hasn't been displayed");
        System.out.println("description: "+ detailsPage.getServiceDescription(serviceId));
        Assert.assertEquals(detailsPage.getServiceDescription(serviceId), serviceDescription,
                "The service description name is not equal to the inserted description value");

        System.out.println("quantity: " + detailsPage.getServiceQuantity(serviceId));
        Assert.assertEquals(detailsPage.getServiceQuantity(serviceId), data.getServiceQuantity(),
                "The service quantity is not equal to the inserted quantity value");

        System.out.println("price: " + detailsPage.getServicePrice(serviceId));
        Assert.assertEquals(detailsPage.getServicePrice(serviceId), data.getServicePrice(),
                "The service price is not equal to the inserted price value");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddNewMoneyService(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        final VNextBOAddNewServiceMonitorDialog newServiceMonitorDialog = detailsPage.clickAddNewServiceButton();
        Assert.assertTrue(newServiceMonitorDialog.isNewServicePopupDisplayed());

        final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
        newServiceMonitorDialog
                .setPriceType(data.getPriceType())
                .setService(data.getService())
                .setServiceDescription(serviceDescription)
                .setServicePrice(data.getServicePrice())
                .setServiceQuantity(data.getServiceQuantity())
                .clickSubmitButton()
                .refreshPage();

        detailsPage.expandServicesTable();
        final String serviceId = detailsPage.getServiceId(serviceDescription);
        Assert.assertNotEquals(serviceId, "",
                "The created service hasn't been displayed");
        System.out.println("description: "+ detailsPage.getServiceDescription(serviceId));
        Assert.assertEquals(detailsPage.getServiceDescription(serviceId), serviceDescription,
                "The service description name is not equal to the inserted description value");

        System.out.println("quantity: " + detailsPage.getServiceQuantity(serviceId));
        Assert.assertEquals(detailsPage.getServiceQuantity(serviceId), data.getServiceQuantity(),
                "The service quantity is not equal to the inserted quantity value");

        System.out.println("price: " + detailsPage.getServicePrice(serviceId));
        Assert.assertEquals(detailsPage.getServicePrice(serviceId), data.getServicePrice(),
                "The service price is not equal to the inserted price value");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddNewLaborService(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        final VNextBOAddNewServiceMonitorDialog newServiceMonitorDialog = detailsPage.clickAddNewServiceButton();
        Assert.assertTrue(newServiceMonitorDialog.isNewServicePopupDisplayed());

        final String serviceDescription = data.getServiceDescription() + RandomStringUtils.randomAlphanumeric(3);
        newServiceMonitorDialog
                .setPriceType(data.getPriceType())
                .setService(data.getService())
                .setServiceDescription(serviceDescription)
                .setServiceLaborRate(data.getServiceLaborRate())
                .setServiceLaborTime(data.getServiceLaborTime())
                .clickSubmitButton()
                .refreshPage();

        detailsPage.expandServicesTable();
        final String serviceId = detailsPage.getServiceId(serviceDescription);
        Assert.assertNotEquals(serviceId, "",
                "The created service hasn't been displayed");
        System.out.println("description: "+ detailsPage.getServiceDescription(serviceId));
        Assert.assertEquals(detailsPage.getServiceDescription(serviceId), serviceDescription,
                "The service description name is not equal to the inserted description value");

        System.out.println("quantity: " + detailsPage.getServiceLaborTime(serviceId));
        Assert.assertEquals(detailsPage.getServiceLaborTime(serviceId), data.getServiceLaborTime(),
                "The service labor time is not equal to the inserted labor time value");

        System.out.println("price: " + detailsPage.getServicePrice(serviceId));
        Assert.assertEquals(detailsPage.getServicePrice(serviceId), data.getServiceLaborRate(),
                "The service labor rate is not equal to the inserted labor rate value");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddNewPartService(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        final VNextBOAddNewServiceMonitorDialog newServiceMonitorDialog = detailsPage.clickAddNewServiceButton();
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
        newServiceMonitorDialog.selectRandomAddPartsOption();

        final String selectedAddPartsNumberAfter = newServiceMonitorDialog.getSelectedAddPartsNumber();
        Assert.assertNotEquals(selectedAddPartsNumberBefore, selectedAddPartsNumberAfter);
        Assert.assertTrue(Integer.valueOf(selectedAddPartsNumberBefore) < Integer.valueOf(selectedAddPartsNumberAfter));

        newServiceMonitorDialog.clickSubmitButton();
        Assert.assertTrue(newServiceMonitorDialog
                .isPartDescriptionDisplayed(data.getServiceCategory() + " -> " + selectedCategory),
                "The Part service description hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectDetailsOfAddNewServiceAndNotAddItXIcon(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        final VNextBOAddNewServiceMonitorDialog newServiceMonitorDialog = detailsPage.clickAddNewServiceButton();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        final VNextBOAddNewServiceMonitorDialog newServiceMonitorDialog = detailsPage.clickAddNewServiceButton();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        final VNextBOAuditLogDialog auditLogDialog = detailsPage.clickLogInfoButton();
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

        auditLogDialog.clickAuditLogsTab(data.getAuditLogTabs()[2]);
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        final VNextBOAuditLogDialog auditLogDialog = detailsPage.clickLogInfoButton();
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

        auditLogDialog.clickAuditLogsTab(data.getAuditLogTabs()[1]);
        final String departmentsLastRecord = auditLogDialog.getDepartmentsLastRecord();
        final String actualLocalDateTime = auditLogDialog.getActualLocalDateTime();
        final String actualLocalDateTimePlusMinute = auditLogDialog.getActualLocalDateTimePlusMinute();
        if (departmentsLastRecord.equals(actualLocalDateTime)) {
            Assert.assertEquals(departmentsLastRecord, actualLocalDateTime);
        } else {
            Assert.assertEquals(departmentsLastRecord, actualLocalDateTimePlusMinute);
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeServicesOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        detailsPage.getServicesTableHeaderValues().forEach(System.out::println);
        System.out.println("*****************************************");
        System.out.println();

        final List<String> fields = Arrays.asList(data.getServicesTableFields());
        fields.forEach(System.out::println);
        System.out.println("*****************************************");
        System.out.println();

        Assert.assertTrue(detailsPage.getServicesTableHeaderValues()
                .containsAll(fields),
                "The services table header values have not been displayed properly");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeVendorPriceOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        detailsPage
                .setServiceStatusForService(serviceId, data.getServiceStatuses()[0])
                .setVendor(serviceId, data.getVendor())
                .setTechnician(serviceId, data.getTechnician());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCreateNoteOfServiceOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        final VNextBOOrderServiceNotesDialog notesDialog = detailsPage.openNotesDialog(serviceId);
        Assert.assertTrue(notesDialog.isNotesDialogDisplayed(), "The notes dialog hasn't been opened");
        final int notesNumber = notesDialog.getNotesListNumber();

        notesDialog.typeNotesMessage(data.getServiceNotesMessage())
                .clickNotesAddButton()
                .openNotesDialog(serviceId);
        Assert.assertTrue(notesDialog.isNotesDialogDisplayed(), "The notes dialog hasn't been opened");
        Assert.assertEquals(notesNumber + 1, notesDialog.getNotesListNumber(),
                "The services notes list number is not updated");
    }

    //todo change because of UI changes
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeTargetDateOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        detailsPage
                .setServiceStatusForService(serviceId, data.getServiceStatuses()[0])
                .setVendor(serviceId, data.getVendor())
                .setTechnician(serviceId, data.getTechnician());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeMoreInformationOfRo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        final VNextBOOrderServiceNotesDialog notesDialog = detailsPage.openNotesDialog(serviceId);
        Assert.assertTrue(notesDialog.isNotesDialogDisplayed(), "The notes dialog hasn't been opened");
        final int notesNumber = notesDialog.getNotesListNumber();

        notesDialog
                .typeNotesMessage(data.getServiceNotesMessage())
                .clickNotesXbutton();
        Assert.assertFalse(notesDialog.isNotesDialogDisplayed(), "The notes dialog hasn't been closed");

        detailsPage.openNotesDialog(serviceId);
        Assert.assertTrue(notesDialog.isNotesDialogDisplayed(), "The notes dialog hasn't been opened");
        Assert.assertEquals(notesNumber, notesDialog.getNotesListNumber(),
                "The services notes list number has been updated, although the 'X' button was clicked");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeStatusOfRoServiceToActive(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeChangesOfPhasesInLogInfo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());
        Assert.assertTrue(repairOrdersPage.isLocationSet(data.getLocation()), "The location hasn't been set");

        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();
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

        final VNextBOAuditLogDialog auditLogDialog = detailsPage.clickLogInfoButton();
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

        auditLogDialog.clickAuditLogsTab(data.getAuditLogTabs()[0]);
        final String departmentsLastRecord = auditLogDialog.getDepartmentsLastRecord();
        final String actualLocalDateTime = auditLogDialog.getActualLocalDateTime();
        final String actualLocalDateTimePlusMinute = auditLogDialog.getActualLocalDateTimePlusMinute();
        if (departmentsLastRecord.equals(actualLocalDateTime)) {
            Assert.assertEquals(departmentsLastRecord, actualLocalDateTime);
        } else {
            Assert.assertEquals(departmentsLastRecord, actualLocalDateTimePlusMinute);
        }
    }
}