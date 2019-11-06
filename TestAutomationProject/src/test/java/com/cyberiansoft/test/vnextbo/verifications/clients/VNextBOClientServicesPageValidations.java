package com.cyberiansoft.test.vnextbo.verifications.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.clients.clientdetails.VNextBOClientsClientServicesPage;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientServicesPageSteps;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOBaseWebPageValidations;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class VNextBOClientServicesPageValidations extends VNextBOBaseWebPageValidations {

    public static void verifyServicesTableIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOClientsClientServicesPage().getServicesTable()),
                "Services table hasn't been displayed.");
    }

    public static void verifyCorrectRecordsAmountIsDisplayed(int expectedRecordsAmount) {

        Assert.assertEquals(VNextBOClientServicesPageSteps.getServicesAmount(), expectedRecordsAmount,
                "Services table has contained incorrect clients amount.");
    }

    public static void verifySearchResultIsCorrectForColumnWithText(String columnTitle, String expectedValue) {

        for (String cellValue : VNextBOClientServicesPageSteps.getColumnValuesByTitleFromColumnWithText(columnTitle)) {
            Assert.assertTrue(cellValue.toLowerCase().contains(expectedValue.toLowerCase()), "Search result hasn't been correct" );
        }
    }

    public static void verifyServicesNotFoundMessageIsDisplayed() {

        Assert.assertEquals(VNextBOClientServicesPageSteps.getServicesNotFoundMessage(), "No records found. Please refine search criteria.",
                "\"No records found. Please refine search criteria.\" message hasn't been displayed.");
    }

    public static void verifyAllColumnsAreDisplayed() {

        List<String> expectedColumnsTitles =
                Arrays.asList("Service", "Required", "Package Price", "Service Price", "Client Price",
                        "Default Technician", "Effective Date", "Effective Price");
        Assert.assertEquals(VNextBOClientServicesPageSteps.getColumnsTitles(), expectedColumnsTitles);
    }

    public static void verifyFirstLineRequiredDropDownFieldContainsCorrectValue(String expectedValue) {

        Select selectRequired = new Select(new VNextBOClientsClientServicesPage().getFirstLineRequiredDropDownField());
        Assert.assertEquals(selectRequired.getFirstSelectedOption().getText(),
                expectedValue, "Required field has contained incorrect value");
    }

    public static void verifyFirstLineTechnicianDropDownFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getText(new VNextBOClientsClientServicesPage().getFirstLineTechnicianDropDownField()),
                expectedValue, "Technician field has contained incorrect value");
    }

    public static void verifyFirstLineEffectiveDateFieldContainsCorrectValue() {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOClientsClientServicesPage().getFirstLineEffectiveDateField()),
                new SimpleDateFormat("M/d/yyyy").format(new Date()), "Effective Date field has contained incorrect value");
    }

    public static void verifyFirstLineEffectivePriceFieldContainsCorrectValue(String expectedPrice) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOClientsClientServicesPage().getFirstLineEffectivePriceField()),
                "$" + expectedPrice + ".00", "Effective Price field has contained incorrect value");
    }
}