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
                .clickClientSegmentsSubMenu();

        clientSegmentsPage.searchClientSegment(data.getClientSegmentsPage());
        Assert.assertTrue(clientSegmentsPage.checkClientsName(data.getClientSegmentsPage()));
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

        ClientSegmentsPage clientSegmentsPage = leftMenuPanel.clickClientSegmentsSubMenu();

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

    //todo get DB data from Nastya
    @Test(testName = "Test Case 59880:Verify automated attribute.")
    public void testUserCanAddAutomatedAttributeToCategories() {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);
        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu();

        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory("Test Category");
        categoriesPage.clickSubmitCategoryButton();
        categoriesPage.clickAddAttributeButton("Test Category");
//        categoriesPage.fillAutomatedAttributeFields("Test Attribute Name", "Number");
        categoriesPage.fillAutomatedAttributeFields("New Attrinute", "Number");
        categoriesPage.clickAddAttributeButton();
        categoriesPage.refreshPage();
//        Assert.assertTrue(categoriesPage.checkAttributeByName("Test Category", "Test Attribute Name"));
        Assert.assertTrue(categoriesPage.checkAttributeByName("Test Category", "New Attrinute"));
        categoriesPage.deleteCategory("Test Category");
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
        leftMenuPanel.clickClientSegmentsSubMenu();

// todo delete, if leftMenuPanel.clickClientSegmentsSubMenu() works
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

        leftMenuPanel.clickClientSegmentsSubMenu();
        // todo delete, if leftMenuPanel.clickClientSegmentsSubMenu() works
//        page = leftMenuPanel.clickOnMenu("Client Segments");
//        Assert.assertTrue(page instanceof ClientSegmentsPage);
//        ClientSegmentsPage clientSegmentsPage = (ClientSegmentsPage) page;
//no functional
        categoriesPage.deleteCategory("Test Category");
    }

    //Test Case 59886:Verify user can select/deselect category.
    //Test Case 59887:Verify user can add a value to attribute.
} 
