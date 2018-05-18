package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.dataclasses.InHouseCategoriesData;
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
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);
        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu();
        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory(data.getCategory());
        categoriesPage.clickSubmitCategoryButton();
        categoriesPage.deleteCategory(data.getCategory());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanAddAttributeToCategories(String rowID, String description, JSONObject testData) {
        InHouseCategoriesData data = JSonDataParser.getTestDataFromJson(testData, InHouseCategoriesData.class);
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu();

        categoriesPage.verifyThatCategoriesDoNoExist(data.getCategory());
        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory(data.getCategory());
        categoriesPage.clickSubmitCategoryButton();
        categoriesPage.clickAddAttributeButton(data.getCategory());
        categoriesPage.fillNotAutomatedAttributeFields(data.getAttributeName(), data.getDataType());
        categoriesPage.clickAddAttributeButton();
        categoriesPage.refreshPage();
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
                .clickClientQuotesSubmenu();

        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(data.getName(), data.getNickname(), data.getAddress(), data.getAddress2(),
                data.getZip(), data.getCountry(), data.getState(), data.getCity(), data.getBusinessPhone(),
                data.getCellPhone(), data.getFirstName(), data.getLastName(), data.getTitle(), data.getEmail());
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(data.getName()));

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu();

        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory(data.getCategory());
        categoriesPage.clickSubmitCategoryButton();
        categoriesPage.clickAddAttributeButton(data.getCategory());
        categoriesPage.fillNotAutomatedAttributeFields(data.getAttributeName(), data.getDataType());
        categoriesPage.clickAddAttributeButton();

        ClientSegmentsPage clientSegmentsPage = leftMenuPanel.clickClientSegmentsSubmenu();

        clientSegmentsPage.searchClientSegment(data.getName());
        clientSegmentsPage.expandAttributesList(data.getName());
        clientSegmentsPage.setAttributeValue(data.getAttributeName(), data.getAttributeValue());
        clientSegmentsPage.refreshPage();
        clientSegmentsPage.searchClientSegment(data.getName());
        clientSegmentsPage.expandAttributesList(data.getName());
        Assert.assertTrue(clientSegmentsPage.checkAttributeValue(data.getAttributeName(), data.getAttributeValue()));

        leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();
        clientQuotesPage.searchUser(data.getName());
        clientQuotesPage.deleteUser(data.getName());

        leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu();
        categoriesPage.deleteCategory(data.getCategory());
    }

    //todo sometimes fails because of StaleReferenceElementException
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanAddAutomatedAttributeToCategories(String rowID, String description, JSONObject testData) {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver, LeftMenuPanel.class);
        InHouseCategoriesData data = JSonDataParser.getTestDataFromJson(testData, InHouseCategoriesData.class);
        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu();

        categoriesPage.verifyCategoryDoesNotExist(data.getCategory());
        categoriesPage.addCategory(data.getCategory());

        categoriesPage.clickAddAttributeButton(data.getCategory());
        categoriesPage.fillAutomatedAttributeFields(data.getAttributeName(), data.getIsAutomatedStatus(),
                data.getProcedureName());
        categoriesPage.clickAddAttributeButton();
        Assert.assertTrue(categoriesPage.checkAttributeByName(data.getCategory(), data.getAttributeName()),
                "The saved attribute name is not displayed in the category");
        ClientSegmentsPage clientSegmentsPage = leftMenuPanel.clickClientSegmentsSubmenu();
        clientSegmentsPage.searchClientSegment(data.getClient());
        Assert.assertTrue(clientSegmentsPage.verifyClientIsDisplayed(data.getClient()),
                "The client has not been displayed in the table!");
        clientSegmentsPage.expandAttributesList(data.getClient());
        Assert.assertTrue(clientSegmentsPage.verifyAttributeNameIsDisplayed(data.getAttributeName(), data.getCategory()));
    }

    //@Test(testName = "Test Case 59883:Verify user can search by categories.")
    public void testUserCanSearchCategories() {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);
        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu();

        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory("Test Category");
        categoriesPage.clickSubmitCategoryButton();
        leftMenuPanel.clickClientSegmentsSubmenu();

// todo delete, if leftMenuPanel.clickClientSegmentsSubmenu() works
// page = leftMenuPanel.clickOnMenu("Client Segments");
//        Assert.assertTrue(page instanceof ClientSegmentsPage);
//        ClientSegmentsPage clientSegmentsPage = (ClientSegmentsPage) page;
//no functional
        categoriesPage.deleteCategory("Test Category");
    }

    //    @Test(testName = "Test Case 59885:Verify created category displays with attributes on the page.")
    public void testCategiriesdisplaysWithAttributes() {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);
        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu();

        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory("Test Category");
        categoriesPage.clickSubmitCategoryButton();
        categoriesPage.clickAddAttributeButton("Test Category");
        categoriesPage.fillNotAutomatedAttributeFields("Test Attribute Name", "String");
        categoriesPage.clickAddAttributeButton();
        categoriesPage.refreshPage();
        Assert.assertTrue(categoriesPage.checkAttributeByName("Test Category", "Test Attribute Name"));

        leftMenuPanel.clickClientSegmentsSubmenu();
        // todo delete, if leftMenuPanel.clickClientSegmentsSubmenu() works
//        page = leftMenuPanel.clickOnMenu("Client Segments");
//        Assert.assertTrue(page instanceof ClientSegmentsPage);
//        ClientSegmentsPage clientSegmentsPage = (ClientSegmentsPage) page;
//no functional
        categoriesPage.deleteCategory("Test Category");
    }

    //Test Case 59886:Verify user can select/deselect category.
    //Test Case 59887:Verify user can add a value to attribute.
} 
