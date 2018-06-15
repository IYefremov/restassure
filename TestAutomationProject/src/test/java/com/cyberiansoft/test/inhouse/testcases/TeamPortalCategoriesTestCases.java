package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.dataclasses.inHouseTeamPortal.InHouseCategoriesData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.inhouse.pageObject.CategoriesPage;
import com.cyberiansoft.test.inhouse.pageObject.ClientQuotesPage;
import com.cyberiansoft.test.inhouse.pageObject.ClientSegmentsPage;
import com.cyberiansoft.test.inhouse.pageObject.LeftMenuPanel;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TeamPortalCategoriesTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/inhouse/data/InHouseCategories.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanAddNewCategories(String rowID, String description, JSONObject testData) {
        InHouseCategoriesData data = JSonDataParser.getTestDataFromJson(testData, InHouseCategoriesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu()
                .verifyCategoriesDoNotExist(data.getCategory())
                .clickAddCategoryButton()
                .setCategory(data.getCategory())
                .clickSubmitCategoryButton();
        Assert.assertTrue(categoriesPage.isCategoryDisplayed(data.getCategory()), "The category is not displayed");
        categoriesPage.deleteCategory(data.getCategory());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanAddAttributeToCategories(String rowID, String description, JSONObject testData) {
        InHouseCategoriesData data = JSonDataParser.getTestDataFromJson(testData, InHouseCategoriesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu()
                .verifyCategoriesDoNotExist(data.getCategory())
                .clickAddCategoryButton()
                .setCategory(data.getCategory())
                .clickSubmitCategoryButton()
                .clickAddAttributeButton(data.getCategory())
                .fillNotAutomatedAttributeFields(data.getAttributeName(), data.getDataType())
                .clickAddAttributeButton();
        Assert.assertTrue(categoriesPage.checkAttributeByName(data.getCategory(), data.getAttributeName()));
        categoriesPage.deleteCategory(data.getCategory());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanSearchByKeyWord(String rowID, String description, JSONObject testData) {
        InHouseCategoriesData data = JSonDataParser.getTestDataFromJson(testData, InHouseCategoriesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        ClientSegmentsPage clientSegmentsPage = leftMenuPanel
                .clickClients()
                .clickClientSegmentsSubmenu();

        clientSegmentsPage.searchClientSegment(data.getClientSegmentsPage());
        Assert.assertTrue(clientSegmentsPage.verifyClientIsDisplayed(data.getClientSegmentsPage()),
                "The client has not been displayed in the table!");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanManualAttribute(String rowID, String description, JSONObject testData) {
        InHouseCategoriesData data = JSonDataParser.getTestDataFromJson(testData, InHouseCategoriesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .clickAddClientButton()
                .fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                        data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                        data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail())
                .clickConfirmNewClientButton();
        Assert.assertTrue(clientQuotesPage.isUserCreated(data.getName()), "The user hasn't been created");

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu()
                .verifyCategoriesDoNotExist(data.getCategory())
                .clickAddCategoryButton()
                .setCategory(data.getCategory())
                .clickSubmitCategoryButton()
                .clickAddAttributeButton(data.getCategory())
                .fillNotAutomatedAttributeFields(data.getAttributeName(), data.getDataType())
                .clickAddAttributeButton();

        ClientSegmentsPage clientSegmentsPage = leftMenuPanel
                .clickClientSegmentsSubmenu()
                .searchClientSegment(data.getName())
                .expandAttributesList(data.getName())
                .setAttributeValue(data.getAttributeName(), data.getAttributeValue());
        Assert.assertTrue(clientSegmentsPage.checkAttributeValue(data.getAttributeName(), data.getAttributeValue()));

        leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .deleteUsers(data.getName());

        leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu();
        categoriesPage.deleteCategory(data.getCategory());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanAddAutomatedAttributeToCategories(String rowID, String description, JSONObject testData) {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        InHouseCategoriesData data = JSonDataParser.getTestDataFromJson(testData, InHouseCategoriesData.class);

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu()
                .verifyCategoriesDoNotExist(data.getCategory())
                .addCategory(data.getCategory())
                .clickAddAttributeButton(data.getCategory())
                .fillAutomatedAttributeFields(data.getAttributeName(), data.getIsAutomatedStatus(),
                        data.getProcedureName())
                .clickAddAttributeButton();
        Assert.assertTrue(categoriesPage.checkAttributeByName(data.getCategory(), data.getAttributeName()),
                "The saved attribute name is not displayed in the category");
        ClientSegmentsPage clientSegmentsPage = leftMenuPanel
                .clickClientSegmentsSubmenu()
                .searchClientSegment(data.getClient());
        Assert.assertTrue(clientSegmentsPage.verifyClientIsDisplayed(data.getClient()),
                "The client has not been displayed in the table!");
        clientSegmentsPage.expandAttributesList(data.getClient());
        Assert.assertTrue(clientSegmentsPage.verifyAttributeNameIsDisplayed(data.getAttributeName(), data.getCategory()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanSearchCategories(String rowID, String description, JSONObject testData) {
        InHouseCategoriesData data = JSonDataParser.getTestDataFromJson(testData, InHouseCategoriesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .clickAddClientButton()
                .fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                        data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                        data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail())
                .clickConfirmNewClientButton();
        Assert.assertTrue(clientQuotesPage.isUserCreated(data.getName()), "The user hasn't been created");

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu()
                .verifyCategoriesDoNotExist(data.getCategory())
                .clickAddCategoryButton()
                .setCategory(data.getCategory())
                .clickSubmitCategoryButton()
                .clickAddAttributeButton(data.getCategory())
                .fillNotAutomatedAttributeFields(data.getAttributeName(), data.getDataType())
                .clickAddAttributeButton();
        Assert.assertTrue(categoriesPage.checkAttributeByName(data.getCategory(), data.getAttributeName()));

        ClientSegmentsPage clientSegmentsPage = leftMenuPanel
                .clickClientSegmentsSubmenu()
                .searchClientSegment(data.getName())
                .expandAttributesList(data.getName())
                .setAttributeValue(data.getAttributeName(), data.getAttributeValue())
                .selectCategory(data.getCategory());
        Assert.assertTrue(clientSegmentsPage.isCategorySelected(data.getCategory()),
                "The category has not been selected");
        clientSegmentsPage.clickSearch();

        Assert.assertTrue(clientSegmentsPage.verifyClientIsDisplayed(data.getName()),
                        "The client is not displayed after clicking the \"Search\" button");

        leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .deleteUsers(data.getName());
    }

    //the test case is similar to 59887, because of its preconditions
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCategoriesAreDisplayedWithAttributes(String rowID, String description, JSONObject testData) {
        InHouseCategoriesData data = JSonDataParser.getTestDataFromJson(testData, InHouseCategoriesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .clickAddClientButton()
                .fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                        data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                        data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail())
                .clickConfirmNewClientButton();
        Assert.assertTrue(clientQuotesPage.isUserCreated(data.getName()), "The user hasn't been created");

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu()
                .verifyCategoriesDoNotExist(data.getCategory())
                .clickAddCategoryButton()
                .setCategory(data.getCategory())
                .clickSubmitCategoryButton()
                .clickAddAttributeButton(data.getCategory())
                .fillNotAutomatedAttributeFields(data.getAttributeName(), data.getDataType())
                .clickAddAttributeButton();
        Assert.assertTrue(categoriesPage.checkAttributeByName(data.getCategory(), data.getAttributeName()));

        ClientSegmentsPage clientSegmentsPage = leftMenuPanel
                .clickClientSegmentsSubmenu()
                .searchClientSegment(data.getName())
                .expandAttributesList(data.getName())
                .setAttributeValue(data.getAttributeName(), data.getAttributeValue())
                .selectCategory(data.getCategory());
        Assert.assertTrue(clientSegmentsPage.isCategorySelected(data.getCategory()),
                "The category has not been selected");
        clientSegmentsPage.clickSearch();

        Assert.assertTrue(clientSegmentsPage.verifyAttributeValueIsDisplayed(data.getAttributeValue()),
                "The attribute is not displayed after clicking the \"Search\" button");

        leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .deleteUsers(data.getName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCategoriesCanBeSelectedAndDeselected(String rowID, String description, JSONObject testData) {
        InHouseCategoriesData data = JSonDataParser.getTestDataFromJson(testData, InHouseCategoriesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .clickAddClientButton()
                .fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                        data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                        data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail())
                .clickConfirmNewClientButton();
        Assert.assertTrue(clientQuotesPage.isUserCreated(data.getName()), "The user hasn't been created");

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu()
                .verifyCategoriesDoNotExist(data.getCategory())
                .clickAddCategoryButton()
                .setCategory(data.getCategory())
                .clickSubmitCategoryButton()
                .clickAddAttributeButton(data.getCategory())
                .fillNotAutomatedAttributeFields(data.getAttributeName(), data.getDataType())
                .clickAddAttributeButton();
        Assert.assertTrue(categoriesPage.checkAttributeByName(data.getCategory(), data.getAttributeName()));

        ClientSegmentsPage clientSegmentsPage = leftMenuPanel
                .clickClientSegmentsSubmenu()
                .searchClientSegment(data.getName())
                .expandAttributesList(data.getName())
                .setAttributeValue(data.getAttributeName(), data.getAttributeValue())
                .selectCategory(data.getCategory());
        Assert.assertTrue(clientSegmentsPage.isCategorySelected(data.getCategory()),
                "The category has not been selected");

        clientSegmentsPage.deselectCategory(data.getCategory());
        Assert.assertFalse(clientSegmentsPage.isCategorySelected(data.getCategory()),
                "The category has not been deselected");

        leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .deleteUsers(data.getName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testValueCanBeAddedToAttribute(String rowID, String description, JSONObject testData) {
        InHouseCategoriesData data = JSonDataParser.getTestDataFromJson(testData, InHouseCategoriesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);

        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .clickAddClientButton()
                .fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                        data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                        data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail())
                .clickConfirmNewClientButton();
        Assert.assertTrue(clientQuotesPage.isUserCreated(data.getName()), "The user hasn't been created");

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu()
                .verifyCategoriesDoNotExist(data.getCategory())
                .clickAddCategoryButton()
                .setCategory(data.getCategory())
                .clickSubmitCategoryButton()
                .clickAddAttributeButton(data.getCategory())
                .fillNotAutomatedAttributeFields(data.getAttributeName(), data.getDataType())
                .clickAddAttributeButton();
        Assert.assertTrue(categoriesPage.checkAttributeByName(data.getCategory(), data.getAttributeName()));

        ClientSegmentsPage clientSegmentsPage = leftMenuPanel
                .clickClientSegmentsSubmenu()
                .searchClientSegment(data.getName())
                .expandAttributesList(data.getName())
                .setAttributeValue(data.getAttributeName(), data.getAttributeValue())
                .selectCategory(data.getCategory());
        Assert.assertTrue(clientSegmentsPage.isCategorySelected(data.getCategory()),
                "The category has not been selected");
        clientSegmentsPage.clickSearch();

        Assert.assertTrue(clientSegmentsPage.verifyAttributeValueIsDisplayed(data.getAttributeValue()),
                "The attribute is not displayed after clicking the \"Search\" button");

        leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu()
                .searchUser(data.getName())
                .deleteUsers(data.getName());
    }
}
