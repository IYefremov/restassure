package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOOperationsInvoicesData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.invoices.VNextBOInvoicesPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.invoices.VNextBOAdvancedSearchInvoiceFormSteps;
import com.cyberiansoft.test.vnextbo.steps.invoices.VNextBOInvoicesPageSteps;
import com.cyberiansoft.test.vnextbo.verifications.invoices.VNextBOInvoicesPageValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOOperationsInvoicesTestCases extends BaseTestCase {
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOOperationsInvoicesData.json";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @BeforeMethod
    public void BackOfficeLogin() {
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();

        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        VNextBOLoginScreenWebPage loginPage = new VNextBOLoginScreenWebPage();
        loginPage.userLogin(userName, userPassword);
    }

    @AfterMethod
    public void BackOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

        if (DriverBuilder.getInstance().getDriver() != null)
            DriverBuilder.getInstance().quitDriver();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanVoidInvoice(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOLeftMenuInteractions.selectInvoicesMenu();
        final String firstInvoiceNumber = VNextBOInvoicesPageInteractions.getFirstInvoiceName();

        VNextBOInvoicesPageSteps.confirmVoidingFirstInvoice();
        Assert.assertFalse(VNextBOInvoicesPageValidations.isInvoiceDisplayed(firstInvoiceNumber),
                "The invoice is displayed after being voided");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotVoidInvoiceAfterClickingNo(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOLeftMenuInteractions.selectInvoicesMenu();
        VNextBOAdvancedSearchInvoiceFormSteps.searchByCustomTimeFrameWithFromDateAndStatus(
                data.getFromDate(), data.getStatus());

        final String firstInvoiceNumber = VNextBOInvoicesPageInteractions.getFirstInvoiceName();
        VNextBOInvoicesPageSteps.cancelVoidingFirstInvoice();

        Assert.assertTrue(VNextBOInvoicesPageValidations.isInvoiceDisplayed(firstInvoiceNumber),
                "The invoice is not displayed after clicking 'No' button");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotVoidInvoiceAfterClickingReject(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOLeftMenuInteractions.selectInvoicesMenu();

        VNextBOAdvancedSearchInvoiceFormSteps.searchByCustomTimeFrameWithFromDateAndStatus(
                data.getFromDate(), data.getStatus());
        final String firstInvoiceNumber = VNextBOInvoicesPageInteractions.getFirstInvoiceName();

        VNextBOInvoicesPageSteps.rejectVoidingFirstInvoice();
        Assert.assertTrue(VNextBOInvoicesPageValidations.isInvoiceDisplayed(firstInvoiceNumber),
                "The invoice is not displayed after clicking the 'No' button");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanVoidInvoicesUsingCheckboxes(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOLeftMenuInteractions.selectInvoicesMenu();
        VNextBOAdvancedSearchInvoiceFormSteps.searchByCustomTimeFrameWithFromDateAndStatus(
                data.getFromDate(), data.getStatus());

        final int selected = 3;
        final String[] firstInvoiceNames = VNextBOInvoicesPageInteractions.getFirstInvoiceNames(selected);

        VNextBOInvoicesPageInteractions.clickCheckbox(firstInvoiceNames);
        final String checkedItemsNote = VNextBOInvoicesPageInteractions.getCheckedItemsNote();
        Assert.assertEquals(checkedItemsNote, selected + " invoices have been selected");
        Assert.assertTrue(VNextBOInvoicesPageValidations.areHeaderIconsDisplayed(), "The header icons haven't been displayed");

        VNextBOInvoicesPageInteractions.clickHeaderIconVoidButton();
        VNextBOConfirmationDialogInteractions.clickInvoiceYesButton();

        Assert.assertFalse(VNextBOInvoicesPageValidations.isInvoiceDisplayed(firstInvoiceNames[0]),
                "The invoice " + firstInvoiceNames[0] + " is displayed after clicking the 'Yes' button");
        Assert.assertFalse(VNextBOInvoicesPageValidations.isInvoiceDisplayed(firstInvoiceNames[1]),
                "The invoice " + firstInvoiceNames[1] + " is displayed after clicking the 'Yes' button");
        Assert.assertFalse(VNextBOInvoicesPageValidations.isInvoiceDisplayed(firstInvoiceNames[2]),
                "The invoice " + firstInvoiceNames[2] + " is displayed after clicking the 'Yes' button");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUnvoidInvoice(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOLeftMenuInteractions.selectInvoicesMenu();
        VNextBOAdvancedSearchInvoiceFormSteps.searchByCustomTimeFrameWithFromDateAndStatus(data.getFromDate(), data.getStatus());
        final String invoiceNumber = VNextBOInvoicesPageInteractions.getFirstInvoiceName();
        VNextBOInvoicesPageSteps.confirmUnvoidingFirstInvoice();

        VNextBOInvoicesPageInteractions.clickClearSearchIconIfDisplayed();
        VNextBOAdvancedSearchInvoiceFormSteps.searchByInvoiceAndStatus(invoiceNumber, data.getStatus2());
        Assert.assertTrue(VNextBOInvoicesPageValidations.isInvoiceDisplayed(invoiceNumber),
                "The invoice is not displayed after being unvoided");
    }

    /**
     * commented several steps because
     * a) these steps are already tested in the other TCs
     * b) the necessary invoices cannot be found in a large scope of created invoices
     */
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUnvoidInvoicesUsingCheckboxes(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOLeftMenuInteractions.selectInvoicesMenu();

//        VNextBOAdvancedSearchInvoiceFormSteps.searchByCustomTimeFrameAndStatus(
//                data.getFromDate(), data.getToDate(), data.getStatus2());
//
//        final String[] invoices = {
//                VNextBOInvoicesPageInteractions.getInvoiceName(0),
//                VNextBOInvoicesPageInteractions.getInvoiceName(1),
//                VNextBOInvoicesPageInteractions.getInvoiceName(2)
//        };
//
//        Arrays.stream(invoices)
//                .forEach((invoice) ->
//                        VNextBOInvoicesPageSteps.confirmVoidingFirstInvoice());
//
//        Arrays.stream(invoices)
//                .forEach((invoice) -> Assert.assertFalse(VNextBOInvoicesPageValidations
//                                .isInvoiceDisplayed(invoice),
//                        "The invoice " + invoice + " is displayed after being voided"));

        VNextBOAdvancedSearchInvoiceFormSteps.searchByCustomTimeFrameAndStatus(
                data.getFromDate(), data.getToDate(), data.getStatus());

        final String[] invoices = {
                VNextBOInvoicesPageInteractions.getInvoiceName(0),
                VNextBOInvoicesPageInteractions.getInvoiceName(1),
                VNextBOInvoicesPageInteractions.getInvoiceName(2)
        };
        System.out.println(Arrays.toString(invoices));
        VNextBOInvoicesPageSteps.unvoidSelectedInvoices(invoices);

        VNextBOInvoicesPageInteractions.clickClearSearchIconIfDisplayed();
        VNextBOAdvancedSearchInvoiceFormSteps.searchByCustomTimeFrameAndStatus(
                data.getFromDate(), data.getToDate(), data.getStatus2());
//        VNextBOInvoicesPageInteractions.scrollInvoices();  todo uncomment here, if the test becomes not stable

        Arrays.stream(invoices)
                .forEach((inv) -> Assert.assertTrue(VNextBOInvoicesPageValidations
                                .isInvoiceDisplayed(inv),
                        "The invoice " + inv + " is not displayed after being unvoided"));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanApproveInvoice(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOLeftMenuInteractions.selectInvoicesMenu();
        VNextBOAdvancedSearchInvoiceFormSteps.searchByCustomTimeFrameWithFromDateAndStatus(data.getFromDate(), data.getStatus());
    }
}