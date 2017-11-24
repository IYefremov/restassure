package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.inhouse.pageObject.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TeamPortalCategoriesTestCases extends BaseTestCase {
    @BeforeMethod
    @Parameters({"backoffice.url"})
    public void teamPortalLogin(String backofficeurl) throws InterruptedException {
        webdriverGotoWebPage(backofficeurl);
        TeamPortalLoginPage loginPage = PageFactory.initElements(webdriver,
                TeamPortalLoginPage.class);
        loginPage.loginByGmail();
        Thread.sleep(2000);
    }

    @AfterMethod
    public void teamPortalLogout() throws InterruptedException {
        TeamPortalHeader headerPanel = PageFactory.initElements(webdriver,
                TeamPortalHeader.class);
        headerPanel.clickLogOutButton();
        Thread.sleep(1000);
    }

    @Test(testName = "Test Case 59877:Verify user can add new categories.")
    public void testUserCanAddNewCategories() throws InterruptedException {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Clients");
        BasePage page = leftMenuPanel.clickOnMenu("Categories");
        Assert.assertTrue(page instanceof TeamPortalCategoriesPage);
        TeamPortalCategoriesPage categoriesPage =(TeamPortalCategoriesPage)page;
        categoriesPage.clickAddCategoryButton();
        categoriesPage.setCategory("Test Category");
        categoriesPage.clickSubmitCategoryButton();
    }
} 
