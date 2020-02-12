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
import java.util.*;
import java.util.stream.Collectors;

public class VNextBOPartsDetailsPanelValidations {

    public static void verifyDetailsPanelIsDisplayed() {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(partsDetailsPanel.getPartsDetailsTable()));
        Assert.assertTrue(Utils.isElementDisplayed(partsDetailsPanel.getPartsDetailsTable()),
                "Parts details panel hasn't been displayed");
    }

    public static void verifyPartStatusIsCorrect(int partNumber, String expectedStatus) {

        Assert.assertEquals(Utils.getText(new VNextBOPartsDetailsPanel().getPartStatusFields().get(partNumber)), expectedStatus,
                "Status hasn't been correct");
    }

    public static void verifyPartPriceIsCorrect(int partNumber, String expectedPrice) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOPartsDetailsPanel().getPartPriceField().get(partNumber)), expectedPrice,
                "Price hasn't been correct");
    }

    public static void verifyPartQuantityIsCorrect(int partNumber, String expectedQuantity) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOPartsDetailsPanel().getPartQuantityField().get(partNumber)), expectedQuantity,
                "Price hasn't been correct");
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

    public static void verifyLaborBlockIsDisplayed(int partNumber, boolean shouldBeMaximized) {

        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        if (shouldBeMaximized) Assert.assertTrue(Utils.isElementDisplayed(partsDetailsPanel.getPartLaborsBlock().get(partNumber)),
                "Labor block hasn't been maximized");
        else Assert.assertTrue(Utils.isElementNotDisplayed(partsDetailsPanel.getPartLaborsBlock().get(partNumber)),
                "Labor block hasn't been minimized");
    }

    public static void verifyAddLaborButtonIsDisplayed(int partNumber) {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsDetailsPanel().getAddLaborButton().get(partNumber)),
                "Parts details panel hasn't been displayed");
    }

    public static void verifyLaborsAmountIsCorrect(int partNumber, int expectedLaborsAmount) {

        Assert.assertEquals(VNextBOPartsDetailsPanelSteps.getLaborsAmountForPartByNumberInList(partNumber), expectedLaborsAmount,
                "Labors amount hasn't been correct");
    }

    public static void verifyPartContainsLaborByPartNumberAndLaborServiceName(int partNumber, String laborService) {

        Assert.assertTrue(new VNextBOPartsDetailsPanel().laborsNamesListForPartByNumberInList(0).
                stream().map(WebElement::getText).collect(Collectors.toList()).contains(laborService), "Part hasn't contained labor service");
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

    public static void verifyPartsCheckBoxesAreActivatedByPartStatus(String status, boolean shouldBeActivated) {

        List<WebElement> checkBoxes = new ArrayList<>();
        VNextBOPartsDetailsPanel detailsPanel = new VNextBOPartsDetailsPanel();
        if (status.equals("All")) checkBoxes = detailsPanel.getPartCheckboxesList();
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

        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        if (shouldBeDisplayed) Assert.assertTrue(Utils.isElementDisplayed(partsDetailsPanel.getDeleteSelectedPartsButton()),
                "Delete button hasn't been displayed");
        else Assert.assertFalse(Utils.isElementDisplayed(partsDetailsPanel.getDeleteSelectedPartsButton()),
                "Delete button has been displayed");
    }

    public static boolean isDeleteSelectedPartsButtonDisplayed(boolean shouldBeDisplayed) {
        return WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOPartsDetailsPanel().getDeleteSelectedPartsButton(), shouldBeDisplayed, 2);
    }

    public static void verifyStatusesListIsCorrect(List<String> expectedStatusesList) {

        List<String> actualPartsStatuses = new ArrayList<>();
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
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
        final List<String> poValues = Utils.getTextByValue(new VNextBOPartsDetailsPanel().getPoInputFieldsByStatus(status));
        System.out.println(poValues);
        Assert.assertFalse(poValues.contains(""), "The PO number is not entered");
    }

    public static boolean isShoppingCartButtonDisplayed(boolean displayed) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOPartsDetailsPanel().getShoppingCartButton(), displayed, 10);
    }

    public static void verifyProviderIsSet(int index, String provider) {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        try {
            WaitUtilsWebDriver.getShortWait().until((ExpectedCondition<Boolean>) driver ->
                    Utils.getText(partsDetailsPanel.getPartProviderInputField().get(index)).equals(provider));
        } catch (Exception ignored) {}
        Assert.assertEquals(provider, Utils.getText(partsDetailsPanel.getPartProviderInputField().get(index)));
    }

    public static boolean isPartDisplayed(String partName) {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        return Utils.getText(partsDetailsPanel.getPartNames()).stream().anyMatch(name -> name.contains(partName));
    }

    public static void verifyPartIsDisplayed(String partName) {
        Assert.assertTrue(VNextBOPartsDetailsPanelValidations.isPartDisplayed(partName), "The part is not displayed");
    }
}
