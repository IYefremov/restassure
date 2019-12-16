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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class VNextBOPartsDetailsPanelValidations {

    public static void verifyDetailsPanelIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsDetailsPanel().getPartsDetailsTable()),
                "Parts details panel hasn't been displayed");
    }

    public static void verifyPartStatusIsCorrect(int partNumber, String expectedStatus) {

        Assert.assertEquals(Utils.getText(new VNextBOPartsDetailsPanel().getPartStatusField().get(partNumber)), expectedStatus,
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

    public static void verifyLabourBlockIsDisplayed(int partNumber, boolean shouldBeMaximized) {

        if (shouldBeMaximized) Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsDetailsPanel().getPartLaborsBlock().get(partNumber)),
                "Labour block hasn't been maximized");
        else Assert.assertTrue(Utils.isElementNotDisplayed(new VNextBOPartsDetailsPanel().getPartLaborsBlock().get(partNumber)),
                "Labour block hasn't been minimized");
    }

    public static void verifyAddLaborButtonIsDisplayed(int partNumber) {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsDetailsPanel().getAddLaborButton().get(partNumber)),
                "Parts details panel hasn't been displayed");
    }

    public static void verifyLaborsAmountIsCorrect(int partNumber, int expectedLaborsAmount) {

        Assert.assertEquals(VNextBOPartsDetailsPanelSteps.getLaborsAmountForPartByNumberInList(partNumber), expectedLaborsAmount,
                "Labors amount hasn't been correct");
    }

    public static void verifyPartContainsLabourByPartNumberAndLabourServiceName(int partNumber, String labourService) {

        Assert.assertTrue(new VNextBOPartsDetailsPanel().laborsNamesListForPartByNumberInList(0).
                stream().map(WebElement::getText).collect(Collectors.toList()).contains(labourService), "Part hasn't contained labour service");
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

        if (shouldBeDisplayed) Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsDetailsPanel().getDeleteSelectedPartsButton()),
                "Delete button hasn't been displayed");
        else Assert.assertFalse(Utils.isElementDisplayed(new VNextBOPartsDetailsPanel().getDeleteSelectedPartsButton()),
                "Delete button has been displayed");
    }

    public static void verifyStatusesListIsCorrect(List<String> expectedStatusesList) {

        List<String> actualPartsStatuses = new ArrayList<>();
        for (WebElement partStatusWebElement : new VNextBOPartsDetailsPanel().getPartsStatusesList()) {
            actualPartsStatuses.add(Utils.getText(partStatusWebElement));
        }
        Collections.sort(actualPartsStatuses);
        Collections.sort(expectedStatusesList);

        Assert.assertEquals(actualPartsStatuses, expectedStatusesList, "Statuses list hasn't been correct");
    }
}
