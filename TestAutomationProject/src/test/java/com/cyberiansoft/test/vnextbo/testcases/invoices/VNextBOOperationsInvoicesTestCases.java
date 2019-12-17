package com.cyberiansoft.test.vnextbo.testcases.invoices;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOOperationsInvoicesData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.invoices.VNextBOInvoicesPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.invoices.VNextBOAdvancedSearchInvoiceFormSteps;
import com.cyberiansoft.test.vnextbo.steps.invoices.VNextBOInvoicesPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.invoices.VNextBOInvoicesPageValidations;
import org.apache.commons.lang3.RandomUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOOperationsInvoicesTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getInvoicesTD();
    }

    @AfterMethod
    public void refreshPage() {
        Utils.refreshPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanVoidInvoice(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOLeftMenuInteractions.selectInvoicesMenu();
        final String firstInvoiceNumber = VNextBOInvoicesPageInteractions.getFirstInvoiceName();

        VNextBOInvoicesPageSteps.confirmVoidingFirstInvoice();
        Assert.assertFalse(VNextBOInvoicesPageValidations.isInvoiceDisplayed(firstInvoiceNumber),
                "The invoice is displayed after being voided");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanUnvoidInvoice(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOLeftMenuInteractions.selectInvoicesMenu();
        VNextBOAdvancedSearchInvoiceFormSteps.searchByCustomTimeFrameWithFromDateAndStatus(data.getFromDate(), data.getStatus());
        final String invoiceNumber = VNextBOInvoicesPageInteractions.getFirstInvoiceName();
        VNextBOInvoicesPageSteps.confirmUnvoidingFirstInvoice();

        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(invoiceNumber);
        Assert.assertTrue(VNextBOInvoicesPageValidations.isInvoiceDisplayed(invoiceNumber),
                "The invoice is not displayed after being unvoided");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanApproveInvoice(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOLeftMenuInteractions.selectInvoicesMenu();
        VNextBOAdvancedSearchInvoiceFormSteps.searchByCustomTimeFrameWithFromDateAndStatus(data.getFromDate(), data.getStatus());

        final String invoice =
                VNextBOInvoicesPageInteractions.getInvoiceName(RandomUtils.nextInt(0, 8));
        VNextBOInvoicesPageInteractions.searchByText(invoice);
        VNextBOInvoicesPageSteps.cancelApprovingFirstInvoiceWithButton();
        VNextBOInvoicesPageSteps.approveInvoiceWithIcon();

        VNextBOAdvancedSearchInvoiceFormSteps.searchByInvoiceAndStatus(invoice, data.getStatus2());

        Assert.assertTrue(VNextBOInvoicesPageValidations.isInvoiceDisplayed(invoice),
                "The invoice hasn't been found");
        Assert.assertTrue(VNextBOInvoicesPageValidations.isRollbackApprovalButtonDisplayed(),
                "The rollback approval button hasn't been shown");
        Assert.assertTrue(VNextBOInvoicesPageValidations.isRollbackApprovalIconDisplayed(),
                "The rollback approval icon hasn't been shown");
        Assert.assertTrue(VNextBOInvoicesPageInteractions.getInvoiceStatusByName(invoice).contains(data.getStatus2()),
                "The status hasn't been changed to 'Approved'");

        VNextBOInvoicesPageSteps.cancelFirstInvoiceRollbackApprovalWithIcon();
        VNextBOInvoicesPageSteps.approveInvoiceRollbackApprovalWithIcon();
        VNextBOAdvancedSearchInvoiceFormSteps.searchByInvoiceAndStatus(invoice, data.getStatus());

        Assert.assertTrue(VNextBOInvoicesPageValidations.isInvoiceDisplayed(invoice),
                "The invoice hasn't been found");
        Assert.assertTrue(VNextBOInvoicesPageValidations.isApproveButtonDisplayed(),
                "The 'Approve invoice' button hasn't been shown");
        Assert.assertTrue(VNextBOInvoicesPageValidations.isApproveIconDisplayed(),
                "The 'Approve invoice' icon hasn't been shown");
        Assert.assertTrue(VNextBOInvoicesPageInteractions.getInvoiceStatusByName(invoice).contains(data.getStatus()),
                "The status hasn't been changed to 'New'");
    }

    /**
     * the first several steps have been deleted because
     * a) these steps are already tested in the other TCs
     * b) the necessary invoices cannot be found in a large scope of created invoices
     */
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanUnvoidInvoicesUsingCheckboxes(String rowID, String description, JSONObject testData) {
        VNextBOOperationsInvoicesData data = JSonDataParser.getTestDataFromJson(testData, VNextBOOperationsInvoicesData.class);

        VNextBOLeftMenuInteractions.selectInvoicesMenu();
        VNextBOAdvancedSearchInvoiceFormSteps.searchByCustomTimeFrameAndStatus(
                data.getFromDate(), data.getToDate(), data.getStatus());
        final String[] invoices = VNextBOInvoicesPageInteractions.getFirstInvoicesNames(3);
        VNextBOInvoicesPageSteps.unvoidFirstInvoices(invoices, data);

        final String[] voidInvoices = VNextBOInvoicesPageInteractions.getFirstInvoicesNames(3);
        VNextBOInvoicesPageSteps.voidFirstInvoices(voidInvoices, data);
    }
}