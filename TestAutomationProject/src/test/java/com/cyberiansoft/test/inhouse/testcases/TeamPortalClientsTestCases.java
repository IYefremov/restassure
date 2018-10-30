package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.dataclasses.inHouseTeamPortal.TeamPortalClientsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.inhouse.pageObject.webpages.CategoriesPage;
import com.cyberiansoft.test.inhouse.pageObject.webpages.LeftMenuPanel;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TeamPortalClientsTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/inhouse/data/TeamPortalClientsData.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddNewCategory(String rowID, String description, JSONObject testData) {

        TeamPortalClientsData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalClientsData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu()
                .verifyCategoryDoesNotExist(data.getCategory())
                .clickAddCategoryButton()
                .setCategory(data.getCategory())
                .clickSubmitCategoryButton();

        Assert.assertTrue(categoriesPage.isCategoryDisplayed(data.getCategory()), "The category is not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteCategory(String rowID, String description, JSONObject testData) {

        TeamPortalClientsData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalClientsData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu()
                .verifyCategoryDoesNotExist(data.getCategory())
                .clickAddCategoryButton()
                .setCategory(data.getCategory())
                .clickSubmitCategoryButton();

        Assert.assertTrue(categoriesPage.isCategoryDisplayed(data.getCategory()), "The category is not displayed");
        categoriesPage.deleteCategory(data.getCategory());
        Assert.assertFalse(categoriesPage.isCategoryDisplayed(data.getCategory()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddAttributeToCreatedCategory(String rowID, String description, JSONObject testData) {

        TeamPortalClientsData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalClientsData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu()
                .verifyCategoryDoesNotExist(data.getCategory())
                .clickAddCategoryButton()
                .setCategory(data.getCategory())
                .clickSubmitCategoryButton();

        Assert.assertTrue(categoriesPage.isCategoryDisplayed(data.getCategory()), "The category is not displayed");

        categoriesPage
                .clickAddAttributeButtonForCategory(data.getCategory())
                .fillNotAutomatedAttributeFields(data.getAttributeName(), data.getDataType())
                .clickAddAttributeButton();
        Assert.assertTrue(categoriesPage.checkAttributeByName(data.getCategory(), data.getAttributeName()));
        categoriesPage.deleteCategory(data.getCategory());
        Assert.assertFalse(categoriesPage.isCategoryDisplayed(data.getCategory()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyAutomatedAttribute(String rowID, String description, JSONObject testData) {

        TeamPortalClientsData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalClientsData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu()
                .verifyCategoryDoesNotExist(data.getCategory())
                .clickAddCategoryButton()
                .setCategory(data.getCategory())
                .clickSubmitCategoryButton();

        Assert.assertTrue(categoriesPage.isCategoryDisplayed(data.getCategory()), "The category is not displayed");

        categoriesPage
                .clickAddAttributeButtonForCategory(data.getCategory())
                .fillAutomatedAttributeFields(data.getAttributeName(), data.getIsAutomatedStatus(),
                        data.getProcedureName())
                .clickAddAttributeButton();
        Assert.assertTrue(categoriesPage.checkAttributeByName(data.getCategory(), data.getAttributeName()),
                "The saved attribute name is not displayed in the category");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyManualAttribute(String rowID, String description, JSONObject testData) {

        TeamPortalClientsData data = JSonDataParser.getTestDataFromJson(testData, TeamPortalClientsData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu()
                .verifyCategoryDoesNotExist(data.getCategory())
                .clickAddCategoryButton()
                .setCategory(data.getCategory())
                .clickSubmitCategoryButton();

        Assert.assertTrue(categoriesPage.isCategoryDisplayed(data.getCategory()), "The category is not displayed");

        categoriesPage
                .clickAddAttributeButtonForCategory(data.getCategory())
                .fillNotAutomatedAttributeFields(data.getAttributeName(), data.getDataType())
                .clickAddAttributeButton();
        Assert.assertTrue(categoriesPage.checkAttributeByName(data.getCategory(), data.getAttributeName()),
                "The saved attribute name is not displayed in the category");
    }
}