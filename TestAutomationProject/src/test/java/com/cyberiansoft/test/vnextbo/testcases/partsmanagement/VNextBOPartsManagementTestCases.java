package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOPartsManagementData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBODashboardPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOPartsDetailsPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOPartsManagementSearchPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOPartsOrdersListPanel;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOFooterPanelValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

public class VNextBOPartsManagementTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPartsManagementTD();
    }

    private VNextBODashboardPanel dashboardPanel;
    private VNextBOPartsManagementSearchPanel partsManagementSearch;
    private VNextBOPartsOrdersListPanel partsOrdersListPanel;
    private VNextBOPartsDetailsPanel partsDetailsPanel;

    @BeforeMethod
    public void BackOfficeLogin() {
        dashboardPanel = PageFactory.initElements(webdriver, VNextBODashboardPanel.class);
        partsManagementSearch = PageFactory.initElements(webdriver, VNextBOPartsManagementSearchPanel.class);
        partsManagementSearch = PageFactory.initElements(webdriver, VNextBOPartsManagementSearchPanel.class);
        partsOrdersListPanel = PageFactory.initElements(webdriver, VNextBOPartsOrdersListPanel.class);
        partsDetailsPanel = PageFactory.initElements(webdriver, VNextBOPartsDetailsPanel.class);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenOperationsPartsManagementWithFullSetOfElements(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());

        Assert.assertTrue(dashboardPanel
                        .getDashboardItemsNames()
                        .containsAll(Arrays.asList(data.getDashboardItemsNames())),
                "The dashboard items names haven't been displayed");
        Assert.assertTrue(partsManagementSearch.isPartsManagementSearchPanelDisplayed(),
                "The Parts Management search panel hasn't been displayed");
        Assert.assertTrue(partsOrdersListPanel.isPartsOrdersListDisplayed(),
                "The Parts Management RO list panel hasn't been displayed");
        Assert.assertTrue(VNextBOFooterPanelValidations.isFooterDisplayed(),
                "The Parts Management footer panel hasn't been displayed");
        Assert.assertTrue(VNextBOFooterPanelValidations.isTermsAndConditionsLinkDisplayed(),
                "The Parts Management Terms and Conditions link hasn't been displayed");
        Assert.assertTrue(VNextBOFooterPanelValidations.isPrivacyPolicyLinkDisplayed(),
                "The Parts Management Privacy Policy link hasn't been displayed");
        Assert.assertTrue(VNextBOFooterPanelValidations.isIntercomDisplayed(),
                "The Intercom link hasn't been displayed on the Parts Management page");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeePartsDetailsOfDifferentROs(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());

        final int partsOrderListSize = partsOrdersListPanel.getPartsOrderListSize();
        final WebElement randomPartsOrder = partsOrdersListPanel.getRandomPartsOrder();
        final String breadCrumbRONumber = VNextBOBreadCrumbInteractions.getLastBreadCrumbText();
        partsOrdersListPanel.clickPartsOrder(randomPartsOrder);

        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "The Parts Details table hasn't been displayed");

        if (partsOrderListSize > 1) {
            Assert.assertNotEquals(breadCrumbRONumber, VNextBOBreadCrumbInteractions.getLastBreadCrumbText());
        }
    }

    //todo often fails, bug!!! Sometimes forbidden status option is displayed for Past Due Parts search
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectOrdersWithPastDuePartsOption(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());

        dashboardPanel.clickPastDuePartsLink();
        Assert.assertTrue(dashboardPanel.isPastDuePartsOptionSelected(),
                "The Past Due Parts Option hasn't been selected");

        final int partsOrderListSize = partsOrdersListPanel.getPartsOrderListSize();
        final WebElement randomPartsOrder = partsOrdersListPanel.getRandomPartsOrder();
        final String breadCrumbRONumber = VNextBOBreadCrumbInteractions.getLastBreadCrumbText();
        partsDetailsPanel = partsOrdersListPanel.clickPartsOrder(randomPartsOrder);

        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "The Parts Details table hasn't been displayed");

        if (partsOrderListSize > 1) {
            Assert.assertNotEquals(breadCrumbRONumber, VNextBOBreadCrumbInteractions.getLastBreadCrumbText());
        }

        final WebElement randomPartsOption = partsDetailsPanel.getRandomPartsDetailsOption();
        final String partsOrderStatusValue = partsDetailsPanel.getPartsOrderStatusValue(randomPartsOption);

        System.out.println("Parts order status value: " + partsOrderStatusValue + "\nAvailable statuses values:");
        Arrays.stream(data.getStatusesList()).forEach(System.out::println);
        final boolean present = Arrays
                .stream(data.getStatusesList())
                .anyMatch(part -> part.equals(partsOrderStatusValue));
        Assert.assertTrue(present,
                "The status " + partsOrderStatusValue + " is not allowed for the Past Due Parts option");

        final String estimatedTimeArrivalValue = partsDetailsPanel.getEstimatedTimeArrivalValue(randomPartsOption);
        Assert.assertNotNull(estimatedTimeArrivalValue,
                "The Estimated Time Arrival Value cannot be null");

        System.out.println("Displayed date: " + estimatedTimeArrivalValue);
        System.out.println("Current date: " + CustomDateProvider.getCurrentDate());

        if (estimatedTimeArrivalValue.equals("")) {
            System.out.println("The Estimated Time Arrival Value is empty");
            Assert.assertTrue(true, "The Estimated Time Arrival Value is not empty");
        } else {
            System.out.println("The Estimated Time Arrival Value is before the current date");
            Assert.assertTrue(partsDetailsPanel.isDateBefore(
                    estimatedTimeArrivalValue, CustomDateProvider.getCurrentDate()),
                    "The Estimated Time Arrival Value isn't before the current date");
        }
    }

    //todo often fails, bug!!! Sometimes forbidden status option is displayed for In Progress search
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectOrdersWithInProgressOption(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());

        dashboardPanel.clickInProgressItemLink();
        Assert.assertTrue(dashboardPanel.isInProgressOptionSelected(),
                "The In Progress Option hasn't been selected");

        final int partsOrderListSize = partsOrdersListPanel.getPartsOrderListSize();
        final WebElement randomPartsOrder = partsOrdersListPanel.getRandomPartsOrder();
        final String breadCrumbRONumber = VNextBOBreadCrumbInteractions.getLastBreadCrumbText();
        partsDetailsPanel = partsOrdersListPanel.clickPartsOrder(randomPartsOrder);

        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "The Parts Details table hasn't been displayed");

        if (partsOrderListSize > 1) {
            Assert.assertNotEquals(breadCrumbRONumber, VNextBOBreadCrumbInteractions.getLastBreadCrumbText());
        }

        final WebElement randomPartsOption = partsDetailsPanel.getRandomPartsDetailsOption();
        final String partsOrderStatusValue = partsDetailsPanel.getPartsOrderStatusValue(randomPartsOption);

        System.out.println("Parts order status value: " + partsOrderStatusValue + "\nAvailable statuses values:");
        Arrays.stream(data.getStatusesList()).forEach(System.out::println);
        final boolean present = Arrays
                .stream(data.getStatusesList())
                .anyMatch(part -> part.equals(partsOrderStatusValue));
        Assert.assertTrue(present,
                "The status " + partsOrderStatusValue + " is not allowed for the In Progress option");

        final String estimatedTimeArrivalValue = partsDetailsPanel.getEstimatedTimeArrivalValue(randomPartsOption);
        Assert.assertNotNull(estimatedTimeArrivalValue,
                "The Estimated Time Arrival Value cannot be null");

        System.out.println("Displayed date: " + estimatedTimeArrivalValue);
        System.out.println("Current date: " + CustomDateProvider.getCurrentDate());

        final boolean dateAfter = partsDetailsPanel.isDateAfter(estimatedTimeArrivalValue, CustomDateProvider.getCurrentDate());
        final boolean dateBefore = partsDetailsPanel.isDateBefore(estimatedTimeArrivalValue, CustomDateProvider.getCurrentDate());
        final boolean dateEqual = partsDetailsPanel.isDateEqual(estimatedTimeArrivalValue, CustomDateProvider.getCurrentDate());

        if (estimatedTimeArrivalValue.equals("")) {
            System.out.println("The Estimated Time Arrival Value is empty");
            Assert.assertTrue(true, "The Estimated Time Arrival Value is not empty");
        } else if (dateAfter) {
            System.out.println("The Estimated Time Arrival Value is after the current date");
            Assert.assertTrue(true, "The Estimated Time Arrival Value isn't after the current date");
        } else if (dateBefore) {
            System.out.println("The Estimated Time Arrival Value is before the current date, but has to be equal or after");
            Assert.fail("The Estimated Time Arrival Value is before the current date, but has to be equal or after");
        } else if (dateEqual) {
            System.out.println("The Estimated Time Arrival Value is equal to the current date");
            Assert.assertTrue(true, "The Estimated Time Arrival Value is equal to the current date");
        }
    }

    //todo often fails, bug!!! Sometimes forbidden status option is displayed for Completed search
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectOrdersWithCompletedOption(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());

        dashboardPanel.clickCompletedItemLink();
        Assert.assertTrue(dashboardPanel.isCompletedOptionSelected(),
                "The Completed Option hasn't been selected");

        final int partsOrderListSize = partsOrdersListPanel.getPartsOrderListSize();
        final WebElement randomPartsOrder = partsOrdersListPanel.getRandomPartsOrder();
        final String breadCrumbRONumber = VNextBOBreadCrumbInteractions.getLastBreadCrumbText();
        partsDetailsPanel = partsOrdersListPanel.clickPartsOrder(randomPartsOrder);

        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "The Parts Details table hasn't been displayed");

        if (partsOrderListSize > 1) {
            Assert.assertNotEquals(breadCrumbRONumber, VNextBOBreadCrumbInteractions.getLastBreadCrumbText());
        }

        final WebElement randomPartsOption = partsDetailsPanel.getRandomPartsDetailsOption();
        final String partsOrderStatusValue = partsDetailsPanel.getPartsOrderStatusValue(randomPartsOption);

        System.out.println("Parts order status value: " + partsOrderStatusValue + "\nAvailable statuses values:");
        Arrays.stream(data.getStatusesList()).forEach(System.out::println);
        final boolean present = Arrays.stream(data
                .getStatusesList())
                .anyMatch(part -> part.equals(partsOrderStatusValue));
        Assert.assertTrue(present, "The status " + partsOrderStatusValue + " is not allowed for the Completed option");

        final String estimatedTimeArrivalValue = partsDetailsPanel.getEstimatedTimeArrivalValue(randomPartsOption);
        Assert.assertNotNull(estimatedTimeArrivalValue, "The Estimated Time Arrival Value cannot be null");
    }
}