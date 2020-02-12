package com.cyberiansoft.test.vnextbo.interactions.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartsProvidersDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmationDialog;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsDetailsPanel;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import io.appium.java_client.functions.ExpectedCondition;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VNextBOPartsDetailsPanelInteractions {

    public static List<String> getPartStatusFieldsValues() {
        return Utils.getText(new VNextBOPartsDetailsPanel().getPartStatusFields());
    }

    public static int getPartsNumberWithStatus(String status) {
        return (int) getPartStatusFieldsValues().stream().filter(part -> part.contains(status)).count();
    }

    public static void clickGetQuotesPartButton() {
        Utils.clickElement(new VNextBOPartsDetailsPanel().getGetQuotesButton());
        VNextBOPartsProvidersDialogInteractions.waitForPartsProvidersModalDialogToBeOpened();
    }

    public static void setPoByStatusIfEmpty(String status) {
        new VNextBOPartsDetailsPanel().getPoInputFieldsByStatus(status).forEach(po -> {
            if (Utils.getInputFieldValue(po, 2).isEmpty()) {
                Utils.sendKeysWithEnter(po, "auto-test-" + RandomStringUtils.randomNumeric(4));
                Utils.clickElement(po.findElement(By.xpath(".//../..//div[text()='PO#']")));
            }
        });
    }

    public static void setPONumber(int order, String poNumber) {
        final WebElement poInputField = new VNextBOPartsDetailsPanel().getPoInputFieldsList().get(order);
        Utils.sendKeysWithEnter(poInputField, poNumber);
        Utils.clickElement(poInputField.findElement(By.xpath(".//../..//div[text()='PO#']")));
    }

    public static String setPONumber(int order) {
        final String poNumber = "auto-test-" + RandomStringUtils.randomNumeric(4);
        setPONumber(order, poNumber);
        return poNumber;
    }

    public static String getPONumber(int order) {
        return Utils.getInputFieldValue(new VNextBOPartsDetailsPanel().getPoInputFieldsList().get(order), 10);
    }

    public static void setLaborCredit(int order, String laborCredit) {
        final WebElement laborCreditInputField = new VNextBOPartsDetailsPanel().getLaborCreditInputFieldsList().get(order);
        final String prevLaborCredit = Utils.getInputFieldValue(laborCreditInputField);
        Utils.sendKeysWithEnter(laborCreditInputField, laborCredit);
        Utils.clickElement(laborCreditInputField.findElement(By.xpath(".//../..//div[text()='Labor Credit']")));
        try {
            WaitUtilsWebDriver.getShortWait().until((ExpectedCondition<Boolean>) driver ->
                    !prevLaborCredit.equals(Utils.getInputFieldValue(laborCreditInputField)));
        } catch (Exception ignored) {}
    }

    public static String setLaborCredit(int order) {
        final String laborCredit = RandomStringUtils.randomNumeric(2);
        setLaborCredit(order, laborCredit);
        return laborCredit;
    }

    public static String getLaborCredit(int order) {
        return Utils.getInputFieldValue(new VNextBOPartsDetailsPanel().getLaborCreditInputFieldsList().get(order), 10)
                .replaceAll("[$,]", "")
                .replace(".00", "");
    }

    public static void setProvider(String provider) {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        Utils.clickElement(partsDetailsPanel.getProviderFieldArrow());
        Utils.selectOption(partsDetailsPanel.getPartDropDown(), partsDetailsPanel.getPartListBoxOptions(), provider);
    }

    public static List<String> getPartNamesByStatus(String status) {
        return Utils.getText(new VNextBOPartsDetailsPanel().getPartNamesByStatus(status));
    }

    public static List<String> getPartIdsByStatus(String status) {
        final List<WebElement> partsList = new VNextBOPartsDetailsPanel().getPartsList();
        final List<String> partIds = new ArrayList<>();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(partsList, 5);

        partsList.forEach(part -> {
            final WebElement inputField = part.findElement(By.xpath(".//input[contains(@class, 'service-status-dropdown')]"));
            if (WaitUtilsWebDriver.waitForElementNotToBeStale(inputField).getAttribute("data-text").contains(status)) {
                partIds.add(part.getAttribute("id"));
            }
        });

        return partIds;
    }

    public static void clickStatusesCheckBox() {
        Utils.clickElement(new VNextBOPartsDetailsPanel().getStatusesCheckbox());
    }

    public static void waitForStatusesCheckBoxToBeOpened(boolean opened) {
        WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOPartsDetailsPanel().getHeaderDropDown(), opened, 2);
    }

    public static WebElement getSelectedStatus(String status) {
        waitForStatusesCheckBoxToBeOpened(true);
        return new VNextBOPartsDetailsPanel().getDeleteStatusesList()
                .stream()
                .filter(s -> Utils.getText(s).contains(status))
                .findFirst()
                .orElse(null);
    }

    public static void selectStatusToDelete(WebElement status) {
        Utils.clickElement(status);
    }

    public static void clickDeleteButton() {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        final boolean displayed = VNextBOPartsDetailsPanelValidations.isDeleteSelectedPartsButtonDisplayed(true);
        if (displayed) {
            Utils.clickElement(partsDetailsPanel.getDeleteSelectedPartsButton());
            WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOConfirmationDialog().getConfirmDialog(), true, 3);
        }
    }

    public static void clickShoppingCartButton() {
        Utils.clickElement(new VNextBOPartsDetailsPanel().getShoppingCartButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void selectCheckboxesForServicesByName(String ...names) {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        final List<WebElement> partCheckboxesList = partsDetailsPanel.getPartCheckboxesList();
        if (partCheckboxesList.size() > 1) {
            Arrays.asList(names).forEach(name -> Utils.clickElement(partCheckboxesList.get(getPartNumberByPartName(name))));
        }
    }

    public static int getPartNumberByPartName(String partName) {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        final List<String> partNames = Utils.getText(partsDetailsPanel.getPartNames());
        for (int i = 0; i < partNames.size(); i++) {
            if (partNames.get(i).equals(partName)) {
                return i;
            }
        }
        return 0;
    }

    public static void setPartNumber(int order, String partNumber) {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        final WebElement partInputField = WaitUtilsWebDriver.waitForVisibility(partsDetailsPanel.getPartInputField(order));
        Utils.clearAndType(partInputField, partNumber);
        WaitUtilsWebDriver.waitForAttributeToContain(partInputField, "aria-busy", "false");
    }

    public static void clearPartNumber(int order) {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        final WebElement partInputField = partsDetailsPanel.getPartNumberInputFields().get(order);
        Utils.moveToElement(partInputField);
        final WebElement partInputFieldClearIcon = WaitUtilsWebDriver.waitForVisibility(
                partsDetailsPanel.getPartInputFieldClearIcon(partInputField), 3);
        Utils.clickElement(partInputFieldClearIcon);
        WaitUtilsWebDriver.waitForAttributeToContain(partInputFieldClearIcon, "class", "k-hidden");
    }

    public static void setStatusForPartByPartNumber(int partNumber, String status) {
        Utils.clickElement(new VNextBOPartsDetailsPanel().getPartStatusFields().get(partNumber));
        Utils.selectOptionInDropDownWithJsScroll(status);
    }
}
