package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.testcases.BaseTestCase;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOOperationsInvoicesData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInvoicesWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VNextBOOperationsInvoicesTestCases extends BaseTestCase {
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOOperationsInvoicesData.json";

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

        WebDriverUtils.webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOURL());
        userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        loginPage = PageFactory.initElements(webdriver, VNextBOLoginScreenWebPage.class);
        loginPage
                .userLogin(userName, userPassword)
                .executeJsForAddOnSettings(); //todo use the method getJsForAddOnSettings() from VNextBOServicesPartsAndLaborBundleData.java after fix
        leftMenu = PageFactory.initElements(webdriver, VNexBOLeftMenuPanel.class);
    }

    @AfterMethod
    public void BackOfficeLogout() {
        VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
                VNextBOHeaderPanel.class);
        if (headerpanel.logOutLinkExists())
            headerpanel.userLogout();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanVoidInvoice(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOInvoicesWebPage invoicesPage = leftMenu.selectInvoicesMenu();
        final String firstInvoiceNumber = invoicesPage.getFirstInvoiceName();
        invoicesPage
                .clickFirstInvoice()
                .clickVoidButton()
                .clickInvoiceYesButton();

        Assert.assertFalse(invoicesPage.isInvoiceDisplayed(firstInvoiceNumber),
                "The invoice is displayed after being voided");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotVoidInvoiceAfterClickingNo(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOInvoicesWebPage invoicesPage = leftMenu.selectInvoicesMenu();
        final String firstInvoiceNumber = invoicesPage.getFirstInvoiceName();
        invoicesPage
                .clickFirstInvoice()
                .clickVoidButton()
                .clickInvoiceNoButton();

        Assert.assertTrue(invoicesPage.isInvoiceDisplayed(firstInvoiceNumber),
                "The invoice is not displayed after clicking 'No' button");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotVoidInvoiceAfterClickingReject(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOInvoicesWebPage invoicesPage = leftMenu.selectInvoicesMenu();
        final String firstInvoiceNumber = invoicesPage.getFirstInvoiceName();
        invoicesPage
                .clickFirstInvoice()
                .clickVoidButton()
                .clickInvoiceRejectButton();

        Assert.assertTrue(invoicesPage.isInvoiceDisplayed(firstInvoiceNumber),
                "The invoice is not displayed after clicking 'No' button");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanVoidInvoicesUsingCheckboxes(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOInvoicesWebPage invoicesPage = leftMenu.selectInvoicesMenu();
        final int selected = 3;
        final String[] firstInvoiceNames = invoicesPage.getFirstInvoiceNames(selected);

        invoicesPage.clickCheckbox(firstInvoiceNames);
        final String checkedItemsNote = invoicesPage.getCheckedItemsNote();
        Assert.assertEquals(checkedItemsNote, selected + " invoices have been selected");
        Assert.assertTrue(invoicesPage.areHeaderIconsDisplayed(), "The header icons haven't been displayed");

        invoicesPage
                .clickHeaderIconVoidButton()
                .clickInvoiceYesButton();

            Assert.assertFalse(invoicesPage.isInvoiceDisplayed(firstInvoiceNames[0]),
                "The invoice " + firstInvoiceNames[0] + " is displayed after clicking 'Yes' button");
            Assert.assertFalse(invoicesPage.isInvoiceDisplayed(firstInvoiceNames[1]),
                "The invoice " + firstInvoiceNames[1] + " is displayed after clicking 'Yes' button");
            Assert.assertFalse(invoicesPage.isInvoiceDisplayed(firstInvoiceNames[2]),
                "The invoice " + firstInvoiceNames[2] + " is displayed after clicking 'Yes' button");
    }
}