package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.VNextBOPartsDetailsPanelInteractions;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsDetailsPanel;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class VNextBOPartsDetailsPanelValidations {

    private static VNextBOPartsDetailsPanel partsDetailsPanel;

    static {
        partsDetailsPanel = new VNextBOPartsDetailsPanel();
    }

    public static void verifyDetailsPanelIsDisplayed() {
        WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(partsDetailsPanel.getPartsDetailsTable()));
        Assert.assertTrue(Utils.isElementDisplayed(partsDetailsPanel.getPartsDetailsTable()),
                "Parts details panel hasn't been displayed");
    }

    public static void verifyPartStatusIsCorrect(int partNumber, String expectedStatus) {

        Assert.assertEquals(Utils.getText(partsDetailsPanel.getPartStatusFields().get(partNumber)), expectedStatus,
                "Status hasn't been correct");
    }

    public static void verifyPartPriceIsCorrect(int partNumber, String expectedPrice) {

        Assert.assertEquals(Utils.getInputFieldValue(partsDetailsPanel.getPartPriceField().get(partNumber)), expectedPrice,
                "Price hasn't been correct");
    }

    public static void verifyPartQuantityIsCorrect(int partNumber, String expectedQuantity) {

        Assert.assertEquals(Utils.getInputFieldValue(partsDetailsPanel.getPartQuantityField().get(partNumber)), expectedQuantity,
                "Price hasn't been correct");
    }

    public static void verifyPartNumberIsCorrect(int partNumberInList, String expectedPartNumber) {

        Assert.assertEquals(Utils.getInputFieldValue(partsDetailsPanel.getPartNumberField().get(partNumberInList)), expectedPartNumber,
                "Part# hasn't been correct");
    }

    public static void verifyActionsMenuIsDisplayed(int partNumber) {

        Assert.assertTrue(Utils.isElementDisplayed(partsDetailsPanel.getActionsDropDownMenu().get(partNumber)),
                "Actions menu hasn't been displayed");
    }

    public static void verifyPartsAmountIsCorrect(int expectedPartsAmount) {

        Assert.assertEquals(VNextBOPartsDetailsPanelSteps.getPartsListSize(), expectedPartsAmount,
                "Parts amount hasn't been correct");
    }

    public static void verifyLaborBlockIsDisplayed(int partNumber, boolean shouldBeMaximized) {

        if (shouldBeMaximized) Assert.assertTrue(Utils.isElementDisplayed(partsDetailsPanel.getPartLaborsBlock().get(partNumber)),
                "Labor block hasn't been maximized");
        else Assert.assertTrue(Utils.isElementNotDisplayed(partsDetailsPanel.getPartLaborsBlock().get(partNumber)),
                "Labor block hasn't been minimized");
    }

    public static void verifyAddLaborButtonIsDisplayed(int partNumber) {

        Assert.assertTrue(Utils.isElementDisplayed(partsDetailsPanel.getAddLaborButton().get(partNumber)),
                "Parts details panel hasn't been displayed");
    }

    public static void verifyLaborsAmountIsCorrect(int partNumber, int expectedLaborsAmount) {

        Assert.assertEquals(VNextBOPartsDetailsPanelSteps.getLaborsAmountForPartByNumberInList(partNumber), expectedLaborsAmount,
                "Labors amount hasn't been correct");
    }

    public static void verifyPartContainsLaborByPartNumberAndLaborServiceName(int partNumber, String laborService) {

        Assert.assertTrue(partsDetailsPanel.laborsNamesListForPartByNumberInList(0).
                stream().map(WebElement::getText).collect(Collectors.toList()).contains(laborService), "Part hasn't contained labor service");
    }

    public static void verifyEtaDateIsCorrectDependsOnPhase(int partNumber, String orderPhase) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date currentDate = new Date();
        String actualEtaDate = Utils.getInputFieldValue(partsDetailsPanel.getPartEtaField().get(partNumber));
        if (orderPhase.equals("Past Due Parts"))
            Assert.assertTrue(dateFormat.parse(actualEtaDate).before(currentDate), "Part order's ETA date hasn't been correct");
        if (orderPhase.equals("In Progress"))
            Assert.assertTrue( actualEtaDate.equals("") || dateFormat.parse(actualEtaDate).equals(currentDate) ||
                    dateFormat.parse(actualEtaDate).after(currentDate), "Part order's ETA date hasn't been correct");
    }

    public static void verifyEtaDateIsCorrectAfterSearch(String expectedEtaDate, String etaSearchField) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String actualEtaDate = Utils.getInputFieldValue(partsDetailsPanel.getPartEtaField().get(0));
        if (etaSearchField.equals("ETA From"))
        Assert.assertTrue(dateFormat.parse(actualEtaDate).after(dateFormat.parse(expectedEtaDate)) ||
                dateFormat.parse(actualEtaDate).equals(dateFormat.parse(expectedEtaDate)), "ETA hasn't been correct");
        if (etaSearchField.equals("ETA To"))
            Assert.assertTrue(dateFormat.parse(actualEtaDate).before(dateFormat.parse(expectedEtaDate)), "ETA hasn't been correct");
    }

    public static void verifyAtLeastOnePartHasCorePriceMoreThanZero() {

        VNextBOPartsDetailsPanel detailsPanel = partsDetailsPanel;
        List<Boolean> corePriceMoreThanZeroFlagsList = new ArrayList<>();
        for (WebElement orderCorePrice : detailsPanel.getPartCorePriceField()) {
            corePriceMoreThanZeroFlagsList.add(Utils.getInputFieldValue(orderCorePrice).equals("0.00"));
        }
        Assert.assertTrue(corePriceMoreThanZeroFlagsList.contains(false), "There hasn't been part with core price more than zero");
    }

    public static void verifyAtLeastOnePartHasLaborCreditMoreThanZero() {

        VNextBOPartsDetailsPanel detailsPanel = partsDetailsPanel;
        List<Boolean> laborCreditMoreThanZeroFlagsList = new ArrayList<>();
        for (WebElement laborCredit : detailsPanel.getPartLaborCreditField()) {
            laborCreditMoreThanZeroFlagsList.add(Utils.getInputFieldValue(laborCredit).equals("0.00"));
        }
        Assert.assertTrue(laborCreditMoreThanZeroFlagsList.contains(false), "There hasn't been part with labor credit more than zero");
    }

    public static void verifyAtLeastOnePartHasCorrectCoreStatus(String expectedCoreStatus) {

        VNextBOPartsDetailsPanel detailsPanel = partsDetailsPanel;
        List<Boolean> laborCoreStatusIsCorrectFlagsList = new ArrayList<>();
        for (WebElement coreStatus : detailsPanel.getPartCoreStatusField()) {
            laborCoreStatusIsCorrectFlagsList.add(Utils.getText(coreStatus).contains(expectedCoreStatus));
        }
        Assert.assertTrue(!laborCoreStatusIsCorrectFlagsList.contains(false), "There hasn't been part with correct core status");
    }

    public static void verifyPartsCheckBoxesAreActivatedByPartStatus(String status, boolean shouldBeActivated) {

        List<WebElement> checkBoxes = new ArrayList<>();
        VNextBOPartsDetailsPanel detailsPanel = partsDetailsPanel;
        if (status.equals("All")) checkBoxes = detailsPanel.getPartCheckbox();
        else checkBoxes = detailsPanel.partCheckBoxesByPartStatus(status);
        if (shouldBeActivated) {
            for (WebElement partCheckBox : checkBoxes) {
                Assert.assertEquals(partCheckBox.getAttribute("checked"), "true", "Not all checkboxes were activated for parts with status " + status);
            }
        }
        else {
            for (WebElement partCheckBox : checkBoxes) {
                Assert.assertEquals(partCheckBox.getAttribute("checked"), null, "Not all checkboxes were deactivated for parts with status ");
            }
        }
    }

    public static void verifyDeleteSelectedPartsButtonIsDisplayed(boolean shouldBeDisplayed) {

        if (shouldBeDisplayed) Assert.assertTrue(Utils.isElementDisplayed(partsDetailsPanel.getDeleteSelectedPartsButton()),
                "Delete button hasn't been displayed");
        else Assert.assertFalse(Utils.isElementDisplayed(partsDetailsPanel.getDeleteSelectedPartsButton()),
                "Delete button has been displayed");
    }

    public static void verifyStatusesListIsCorrect(List<String> expectedStatusesList) {

        List<String> actualPartsStatuses = new ArrayList<>();
        for (WebElement partStatusWebElement : partsDetailsPanel.getHeaderPartsStatusesList()) {
            actualPartsStatuses.add(Utils.getText(partStatusWebElement));
        }
        Collections.sort(actualPartsStatuses);
        Collections.sort(expectedStatusesList);

        Assert.assertEquals(actualPartsStatuses, expectedStatusesList, "Statuses list hasn't been correct");
    }

    public static void verifyPartStatusesDoNotContainRestrictedStatus(String restrictedStatus) {
        Assert.assertTrue(!VNextBOPartsDetailsPanelInteractions.getPartStatusFieldsValues().contains(restrictedStatus),
                "The status " + restrictedStatus + " is displayed for the opened part");
    }

    public static boolean isPartStatusPresent(String status) {
        try {
            return WaitUtilsWebDriver.getShortWait().until((ExpectedCondition<Boolean>) driver ->
                    VNextBOPartsDetailsPanelInteractions.getPartStatusFieldsValues().contains(status));
        } catch (Exception e) {
            WaitUtilsWebDriver.waitABit(1000);
            return VNextBOPartsDetailsPanelInteractions.getPartStatusFieldsValues().contains(status);
        }
    }

    public static void verifyPoNumbersAreFilledForStatus(String status) {
        WaitUtilsWebDriver.waitABit(500);
        final List<String> poValues = Utils.getTextByValue(partsDetailsPanel.getPoInputFieldsByStatus(status));
        System.out.println(poValues);
        Assert.assertFalse(poValues.contains(""), "The PO number is not entered");
    }

    public static boolean isShoppingCartButtonDisplayed(boolean displayed) {
        return WaitUtilsWebDriver.elementShouldBeVisible(partsDetailsPanel.getShoppingCartButton(), displayed, 10);
    }
}
