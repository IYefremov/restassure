package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsDetailsPanel;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VNextBOPartsDetailsPanelValidations {

    public static void verifyDetailsPanelIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsDetailsPanel().getPartsDetailsTable()),
                "Parts details panel hasn't been displayed");
    }

    public static void verifyPartStatusIsCorrect(int partNumber, String expectedStatus) {

        Assert.assertEquals(Utils.getText(new VNextBOPartsDetailsPanel().getPartStatusField().get(partNumber)), expectedStatus,
                "Status hasn't been correct");
    }

    public static void verifyPartNumberIsCorrect(int partNumberInList, String expectedPartNumber) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOPartsDetailsPanel().getPartNumberField().get(partNumberInList)), expectedPartNumber,
                "Part# hasn't been correct");
    }

    public static void verifyActionsMenuIsDisplayed(int partNumber) {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsDetailsPanel().getActionsDropDownMenu().get(partNumber)),
                "Actions menu hasn't been displayed");
    }

    public static void verifyPartsAmountIsCorrect(int expectedPartsAmount) {

        Assert.assertEquals(VNextBOPartsDetailsPanelSteps.getPartsListSize(), expectedPartsAmount,
                "Parts amount hasn't been correct");
    }

    public static void verifyAddLaborButtonIsDisplayed(int partNumber) {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsDetailsPanel().getAddLaborButton().get(partNumber)),
                "Parts details panel hasn't been displayed");
    }

    public static void verifyLaborsAmountIsCorrect(int partNumber, int expectedLaborsAmount) {

        Assert.assertEquals(VNextBOPartsDetailsPanelSteps.getLaborsAmountForPartByNumberInList(partNumber), expectedLaborsAmount,
                "Labors amount hasn't been correct");
    }

    public static void verifyEtaDateIsCorrectDependsOnPhase(int partNumber, String orderPhase) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date currentDate = new Date();
        String actualEtaDate = Utils.getInputFieldValue(new VNextBOPartsDetailsPanel().getPartEtaField().get(partNumber));
        if (orderPhase.equals("Past Due Parts"))
            Assert.assertTrue(dateFormat.parse(actualEtaDate).before(currentDate), "Part order's ETA date hasn't been correct");
        if (orderPhase.equals("In Progress"))
            Assert.assertTrue( actualEtaDate.equals("") || dateFormat.parse(actualEtaDate).equals(currentDate) ||
                    dateFormat.parse(actualEtaDate).after(currentDate), "Part order's ETA date hasn't been correct");
    }

    public static void verifyEtaDateIsCorrectAfterSearch(String expectedEtaDate, String etaSearchField) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String actualEtaDate = Utils.getInputFieldValue(new VNextBOPartsDetailsPanel().getPartEtaField().get(0));
        if (etaSearchField.equals("ETA From"))
        Assert.assertTrue(dateFormat.parse(actualEtaDate).after(dateFormat.parse(expectedEtaDate)) ||
                dateFormat.parse(actualEtaDate).equals(dateFormat.parse(expectedEtaDate)), "ETA hasn't been correct");
        if (etaSearchField.equals("ETA To"))
            Assert.assertTrue(dateFormat.parse(actualEtaDate).before(dateFormat.parse(expectedEtaDate)), "ETA hasn't been correct");
    }

    public static void verifyAtLeastOnePartHasCorePriceMoreThanZero() {

        VNextBOPartsDetailsPanel detailsPanel = new VNextBOPartsDetailsPanel();
        List<Boolean> corePriceMoreThanZeroFlagsList = new ArrayList<>();
        for (WebElement orderCorePrice : detailsPanel.getPartCorePriceField()) {
            corePriceMoreThanZeroFlagsList.add(Utils.getInputFieldValue(orderCorePrice).equals("0.00"));
        }
        Assert.assertTrue(corePriceMoreThanZeroFlagsList.contains(false), "There hasn't been part with core price more than zero");
    }

    public static void verifyAtLeastOnePartHasLaborCreditMoreThanZero() {

        VNextBOPartsDetailsPanel detailsPanel = new VNextBOPartsDetailsPanel();
        List<Boolean> laborCreditMoreThanZeroFlagsList = new ArrayList<>();
        for (WebElement laborCredit : detailsPanel.getPartLaborCreditField()) {
            laborCreditMoreThanZeroFlagsList.add(Utils.getInputFieldValue(laborCredit).equals("0.00"));
        }
        Assert.assertTrue(laborCreditMoreThanZeroFlagsList.contains(false), "There hasn't been part with labor credit more than zero");
    }

    public static void verifyAtLeastOnePartHasCorrectCoreStatus(String expectedCoreStatus) {

        VNextBOPartsDetailsPanel detailsPanel = new VNextBOPartsDetailsPanel();
        List<Boolean> laborCoreStatusIsCorrectFlagsList = new ArrayList<>();
        for (WebElement coreStatus : detailsPanel.getPartCoreStatusField()) {
            laborCoreStatusIsCorrectFlagsList.add(Utils.getText(coreStatus).contains(expectedCoreStatus));
        }
        Assert.assertTrue(!laborCoreStatusIsCorrectFlagsList.contains(false), "There hasn't been part with correct core status");
    }
}
