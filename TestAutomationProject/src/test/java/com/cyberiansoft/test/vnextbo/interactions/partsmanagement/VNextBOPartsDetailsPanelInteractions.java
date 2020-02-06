package com.cyberiansoft.test.vnextbo.interactions.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartsProvidersDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmationDialog;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsDetailsPanel;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
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
        final boolean displayed = WaitUtilsWebDriver.elementShouldBeVisible(
                partsDetailsPanel.getDeleteSelectedPartsButton(), true, 2);
        if (displayed) {
            Utils.clickElement(partsDetailsPanel.getDeleteSelectedPartsButton());
            WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOConfirmationDialog().getConfirmDialog(), true, 3);
        }
    }

    public static void clickShoppingCartButton() {
        Utils.clickElement(new VNextBOPartsDetailsPanel().getShoppingCartButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}
