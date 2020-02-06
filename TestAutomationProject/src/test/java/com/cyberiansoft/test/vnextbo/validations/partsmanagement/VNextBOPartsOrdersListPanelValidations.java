package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsOrdersListPanel;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class VNextBOPartsOrdersListPanelValidations {

    public static void verifyOrdersListPanelIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsOrdersListPanel().getListPanel()), "WO orders list panel hasn't been displayed");
    }

    public static void verifyOrdersAreDisplayed() {

        Assert.assertTrue(new VNextBOPartsOrdersListPanel().getWONumberListOptions().size() > 0, "WO orders hasn't been displayed");
    }

    public static void verifyWoNumbersAreCorrect(String expectedWoNumber) {

        final VNextBOPartsOrdersListPanel ordersListPanel = new VNextBOPartsOrdersListPanel();
        for (WebElement woNumber : ordersListPanel.getWONumberListOptions()) {
            Assert.assertEquals(Utils.getText(woNumber), expectedWoNumber, "WO order number hasn't been correct");
        }
    }

    public static void verifyCustomerNamesAreCorrect(String expectedText) {

        final VNextBOPartsOrdersListPanel ordersListPanel = new VNextBOPartsOrdersListPanel();
        for (WebElement customerName : ordersListPanel.getCustomerNamesListOptions()) {
            Assert.assertTrue(Utils.getText(customerName).contains(expectedText), "Customer name hasn't been correct");
        }
    }

    public static void verifyPhasesAreCorrect(String expectedText) {

        final VNextBOPartsOrdersListPanel ordersListPanel = new VNextBOPartsOrdersListPanel();
        for (WebElement phase : ordersListPanel.getPhasesListOptions()) {
            Assert.assertTrue(Utils.getText(phase).contains(expectedText), "Phase hasn't been correct");
        }
    }

    public static void verifyOrdersStockNumbersAreCorrect(String expectedText) {

        final VNextBOPartsOrdersListPanel ordersListPanel = new VNextBOPartsOrdersListPanel();
        for (WebElement stockNumber : ordersListPanel.getStockNumbersListOptions()) {
            Assert.assertTrue(Utils.getText(stockNumber).contains(expectedText), "Stock# hasn't been correct");
        }
    }

    public static void verifyOrdersVinNumbersAreCorrect(String expectedText) {

        final VNextBOPartsOrdersListPanel ordersListPanel = new VNextBOPartsOrdersListPanel();
        for (WebElement vinNumber : ordersListPanel.getVinNumbersListOptions()) {
            Assert.assertTrue(Utils.getText(vinNumber).contains(expectedText), "VIN hasn't been correct");
        }
    }

    public static void verifyOrdersVendorsAreCorrect(String expectedText) {

        final VNextBOPartsOrdersListPanel ordersListPanel = new VNextBOPartsOrdersListPanel();
        for (WebElement vendor : ordersListPanel.getVendorNumOptions()) {
            Assert.assertTrue(Utils.getText(vendor).contains(expectedText), "Vendor hasn't been correct");
        }
    }

    public static void verifyOrdersDatesAreCorrectAfterSearch(String expectedDate, String searchField) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        final VNextBOPartsOrdersListPanel ordersListPanel = new VNextBOPartsOrdersListPanel();
        for (WebElement actualDate : ordersListPanel.getDatesListOptions()) {
            String etaDateValue = StringUtils.substringBetween(actualDate.getAttribute("innerText"), ", ", ")");
            if (searchField.equals("From"))
            Assert.assertTrue(dateFormat.parse(etaDateValue).after(dateFormat.parse(expectedDate)) ||
                    dateFormat.parse(etaDateValue).equals(dateFormat.parse(expectedDate)), "Date hasn't been correct");
            if (searchField.equals("To"))
                Assert.assertTrue(dateFormat.parse(etaDateValue).before(dateFormat.parse(expectedDate)) ||
                        dateFormat.parse(etaDateValue).equals(dateFormat.parse(expectedDate)), "Date hasn't been correct");
        }
    }

    public static void verifyOrdersListEmptyStateIsCorrect() {

        final VNextBOPartsOrdersListPanel ordersListPanel = new VNextBOPartsOrdersListPanel();
        Assert.assertTrue(Utils.isElementDisplayed(ordersListPanel.getOrdersListEmptyState()),
               "\"Click here to learn how to create your first work order\" message hasn't been displayed");
        Assert.assertEquals(Utils.getText(ordersListPanel.getOrdersListEmptyState()), "Click here to learn how to create your first work order",
                 "Empty state message hasn't been correct");
    }
}
