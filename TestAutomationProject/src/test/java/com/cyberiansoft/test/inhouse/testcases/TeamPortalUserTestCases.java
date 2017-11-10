package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.inhouse.pageObject.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

public class TeamPortalUserTestCases extends BaseTestCase {

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

    @Test(testName = "Test Case 66618:Verify user can add new Client", dataProvider = "provideNewClientData")
    public void testUserCanAddNewClient(String name, String nickname, String address, String address2, String zip,
     String country, String state, String city, String businessPhone, String cellPhone, String firstName, String lastName,
                                        String title, String email) {
        TeamPortalLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                TeamPortalLeftMenuPanel.class);
        leftMenuPanel.clickOnMenu("Client Management");
        BasePage page = leftMenuPanel.clickOnMenu("Client Quotes");
        Assert.assertTrue(page instanceof TeamPortalClientQuotesPage);
        TeamPortalClientQuotesPage clientQuotesPage = (TeamPortalClientQuotesPage) page;
        clientQuotesPage.clickAddClientBTN();
        clientQuotesPage.fillNewClientProfile( name,  nickname,  address,  address2,  zip,
                 country,  state,  city,  businessPhone,  cellPhone,  firstName,  lastName,
                 title,  email);
    }

    @DataProvider
    public Object[][] provideNewClientData() {
        return new Object[][]{
                {"@Client_name", "@Client_nickname", "@Client_address", "@Client_address2", "@Client_zip", "@Client_country",
                        "@Client_state", "@Client_city", "@Business_phone", "@Cell_Phone", "@First_Name", "@Last_Name",
                        "@Title", "@Email"}

        };
    }
}