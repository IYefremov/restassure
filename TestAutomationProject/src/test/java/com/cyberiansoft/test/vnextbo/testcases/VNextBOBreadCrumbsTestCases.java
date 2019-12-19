package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBORODetailsPage;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROSimpleSearchSteps;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOBreadCrumbValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VNextBOBreadCrumbsTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getBreadcrumbTD();
    }

    private VNextBORODetailsPage detailsPage;

    @BeforeMethod
    public void BackOfficeLogin() {
        detailsPage = new VNextBORODetailsPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeBreadCrumb(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        Assert.assertTrue(VNextBOBreadCrumbValidations.isBreadCrumbClickable(), "The breadCrumb is not clickable");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeWoInBreadCrumb(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        Assert.assertTrue(VNextBOBreadCrumbValidations.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber(), true),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        Assert.assertTrue(VNextBOBreadCrumbValidations.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(data.getLocation()),
                "The location hasn't been displayed on the RO details page");
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLastBreadCrumbDisplayed(),
                "The RO# hasn't been displayed on the RO details page");
        Assert.assertEquals(VNextBOBreadCrumbInteractions.getLastBreadCrumbText(), data.getOrderNumber(),
                "The RO details page breadCrumb with RO# hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeLocationOnDetailsPage(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        Assert.assertTrue(VNextBOBreadCrumbValidations.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber(), true),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        Assert.assertTrue(VNextBOBreadCrumbValidations.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(data.getLocation()),
                "The location hasn't been displayed on the RO details page");
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLastBreadCrumbDisplayed(),
                "The RO# hasn't been displayed on the RO details page");
        Assert.assertEquals(VNextBOBreadCrumbInteractions.getLastBreadCrumbText(), data.getOrderNumber(),
                "The RO details page breadCrumb with RO# hasn't been displayed");

        VNextBOBreadCrumbInteractions.setLocation(data.getLocationChanged());
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(data.getLocationChanged()),
                "The location hasn't been changed on the Order Details page");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReturnToMainPageOfWo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        Assert.assertTrue(VNextBOBreadCrumbValidations.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());

        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByVin(data.getOrderNumber(), true),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        VNextBOROPageInteractions.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(VNextBORODetailsPageValidations.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        Assert.assertTrue(VNextBOBreadCrumbValidations.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(data.getLocation()),
                "The location hasn't been displayed on the RO details page");
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLastBreadCrumbDisplayed(),
                "The RO# hasn't been displayed on the RO details page");
        Assert.assertEquals(VNextBOBreadCrumbInteractions.getLastBreadCrumbText(), data.getOrderNumber(),
                "The RO details page breadCrumb with RO# hasn't been displayed");

        WaitUtilsWebDriver.waitForLoading();
        VNextBORODetailsPageInteractions.clickRepairOrdersBackwardsLink();
        Assert.assertTrue(VNextBOBreadCrumbValidations.isLocationSet(data.getLocation()), "The location hasn't been set");
        Assert.assertTrue(VNextBOBreadCrumbValidations.isBreadCrumbClickable(), "The breadCrumb is not clickable");
    }
}