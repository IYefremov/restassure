package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.inhouse.config.InHouseConfigInfo;
import com.cyberiansoft.test.inhouse.pageObject.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TeamPortalCategoriesTestCases extends BaseTestCase {

    @DataProvider
    public Object[][] provideNewClientData() {
        return new Object[][]{
                {"CompanyAutomation", "Nock for company", "Address 1", "Address 2", "123AB", "United States",
                        "California", "LA", "+380963665214", "+380963665214", "Test", "User",
                        "Job title", InHouseConfigInfo.getInstance().getUserEmail()}

        };
    }

    @Test(testName = "Test Case 59877:Verify user can add new categories.")
    public void testUserCanAddNewCategories() {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);
        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu();
        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory("Test Category");
        categoriesPage.clickSubmitCategoryButton();
        categoriesPage.deleteCategory("Test Category");
    }

    @Test(testName = "Test Case 59879:Verify user can add new categories.")
    public void testUserCanAddAttributeToCategories() {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);
        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu();

        categoriesPage.verifyThatCategoriesDoNoExist("Test Category");
        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory("Test Category");
        categoriesPage.clickSubmitCategoryButton();
        categoriesPage.clickAddAttributeButton("Test Category");
        categoriesPage.fillNotAutomatedAttributeFields("Test Attribute Name", "String");
        categoriesPage.clickAddAttributeButton();
        categoriesPage.refreshPage();
        Assert.assertTrue(categoriesPage.checkAttributeByName("Test Category", "Test Attribute Name"));
        categoriesPage.deleteCategory("Test Category");
    }

    // @Test(testName = "Test Case 59880:Verify automated attribute.")
    public void testUserCanAddAutomatedAttributeToCategories() throws InterruptedException {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);
        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu();

        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory("Test Category");
        categoriesPage.clickSubmitCategoryButton();
        categoriesPage.clickAddAttributeButton("Test Category");
        categoriesPage.fillAutomatedAttributeFields("Test Attribute Name", "Number");
        categoriesPage.clickAddAttributeButton();
        categoriesPage.refreshPage();
        Assert.assertTrue(categoriesPage.checkAttributeByName("Test Category", "Test Attribute Name"));
        categoriesPage.deleteCategory("Test Category");
    }

    @Test(testName = "Test Case 59881:Verify manual attribute.", dataProvider = "provideNewClientData")
    public void testUserCanManualAttribute(String name, String nickname, String address, String address2, String zip,
                                           String country, String state, String city, String businessPhone, String cellPhone, String firstName, String lastName,
                                           String title, String email) throws InterruptedException {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);
        ClientQuotesPage clientQuotesPage = leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();

        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile(name, nickname, address, address2, zip,
                country, state, city, businessPhone, cellPhone, firstName, lastName,
                title, email);
        clientQuotesPage.clickConfirmNewClientBTN();
        Assert.assertTrue(clientQuotesPage.verifyUserWasCreated(name));

        CategoriesPage categoriesPage = leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu();

        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory("Test Category");
        categoriesPage.clickSubmitCategoryButton();
        categoriesPage.clickAddAttributeButton("Test Category");
        categoriesPage.fillNotAutomatedAttributeFields("Manual attribute", "String");
        categoriesPage.clickAddAttributeButton();

        ClientSegmentsPage clientSegmentsPage = leftMenuPanel.clickClientSegmentsSubMenu();

        clientSegmentsPage.searchClientSegment("CompanyAutomation");
        clientSegmentsPage.expandAttributesList("CompanyAutomation");
        clientSegmentsPage.setAttributeValue("Manual attribute", "15");
        clientSegmentsPage.refreshPage();
        clientSegmentsPage.searchClientSegment("CompanyAutomation");
        clientSegmentsPage.expandAttributesList("CompanyAutomation");
        Assert.assertTrue(clientSegmentsPage.checkAttributeValue("Manual attribute", "15"));

        leftMenuPanel
                .clickClientManagement()
                .clickClientQuotesSubmenu();

        clientQuotesPage.searchUser("CompanyAutomation");
        clientQuotesPage.deleteUser("CompanyAutomation");

        leftMenuPanel
                .clickClients()
                .clickCategoriesSubmenu();
        categoriesPage.deleteCategory("Test Category");
    }

    @Test(testName = "Test Case 59882:Verify user can search by key-word.")
    public void testUserCanSearchByKeyWord() throws InterruptedException {
        LeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                LeftMenuPanel.class);

        ClientSegmentsPage clientSegmentsPage = leftMenuPanel
                .clickClients()
                .clickClientSegmentsSubMenu();

        clientSegmentsPage.searchClientSegment("Google");
        Assert.assertTrue(clientSegmentsPage.checkClientyName("Google"));
    }

    //@Test(testName = "Test Case 59883:Verify user can search by categories.")
    public void testUserCanSearchCategories() throws InterruptedException {
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
    public void testCategiriesdisplaysWithAttributes() throws InterruptedException {
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
