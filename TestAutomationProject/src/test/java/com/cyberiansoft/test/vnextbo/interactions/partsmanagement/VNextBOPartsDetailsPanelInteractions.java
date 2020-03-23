package com.cyberiansoft.test.vnextbo.interactions.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartsProvidersDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOConfirmationDialog;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsDetailsPanel;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

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

    public static void waitForGetQuotesButtonToBeDisplayed(boolean expected) {
        WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOPartsDetailsPanel().getGetQuotesButton(), expected, 3);
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

    private static void setCorePrice(int order, String corePrice) {
        setValue(order, corePrice, "Core Price", new VNextBOPartsDetailsPanel().getCorePriceInputFieldsList());
    }

    private static void setVendorPrice(int order, String vendorPrice) {
        setValue(order, vendorPrice, "Vendor Price", new VNextBOPartsDetailsPanel().getVendorPriceInputFieldsList());
    }

    private static void setQuantity(int order, String quantity) {
        setValue(order, quantity, "Quantity", new VNextBOPartsDetailsPanel().getQuantityInputFieldsList());
    }

    private static void setPrice(int order, String quantity) {
        setValue(order, quantity, "Price", new VNextBOPartsDetailsPanel().getPriceInputFieldsList());
    }

    private static void setLaborCredit(int order, String laborCredit) {
        setValue(order, laborCredit, "Labor Credit", new VNextBOPartsDetailsPanel().getLaborCreditInputFieldsList());
    }

    private static void setValue(int order, String value, String fieldTitle, List<WebElement> list) {
        final WebElement inputField = list.get(order);
        final String prevValue = Utils.getInputFieldValue(inputField);
        Utils.sendKeysWithEnter(inputField, value);
        Utils.clickElement(inputField.findElement(By.xpath(".//../..//div[text()='" + fieldTitle + "']")));
        try {
            WaitUtilsWebDriver.getShortWait().until((ExpectedCondition<Boolean>) driver ->
                    !prevValue.equals(Utils.getInputFieldValue(inputField)));
        } catch (Exception ignored) {}
    }

    public static String setLaborCredit(int order) {
        final String laborCredit = String.valueOf(RandomUtils.nextInt(10, 100));
        setLaborCredit(order, laborCredit);
        return laborCredit;
    }

    public static String setCorePrice(int order) {
        final String corePrice = String.valueOf(RandomUtils.nextInt(10, 100));
        setCorePrice(order, corePrice);
        return corePrice;
    }

    public static String setVendorPrice(int order) {
        final String vendorPrice = String.valueOf(RandomUtils.nextInt(10, 100));
        setVendorPrice(order, vendorPrice);
        return vendorPrice;
    }

    public static String setQuantity(int order) {
        final String quantity = String.valueOf(RandomUtils.nextInt(10, 100));
        setQuantity(order, quantity);
        return quantity;
    }

    public static String getQuantity(int order) {
        return Utils
                .getInputFieldValue(new VNextBOPartsDetailsPanel().getQuantityInputFieldsList().get(order), 3)
                .replaceAll("[.][0-9]{3}", "");
    }

    public static void setCurrentDateIntoTheETAField(int order) {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        Utils.clickElement(partsDetailsPanel.getEtaFieldsCalendarButton().get(order));
        WaitUtilsWebDriver.waitForAttributeToBe(
                partsDetailsPanel.getEtaFieldsList().get(order), "aria-expanded", "true", 2);
        Utils.clickElement(partsDetailsPanel.getEtaCalendarTodayCell());
        WaitUtilsWebDriver.waitForAttributeToBe(
                partsDetailsPanel.getEtaFieldsList().get(order), "aria-expanded", "false", 2);
    }

    public static String getETA(int order) {
        return Utils
                .getInputFieldValue(new VNextBOPartsDetailsPanel().getEtaFieldsList().get(order), 3);
    }

    public static String setPrice(int order) {
        final String price = String.valueOf(RandomUtils.nextInt(10, 100));
        setPrice(order, price);
        return price;
    }

    public static String getPrice(int order) {
        return getFormattedInputField(new VNextBOPartsDetailsPanel().getPriceInputFieldsList().get(order));
    }

    public static String getVendorPrice(int order) {
        return getFormattedInputField(new VNextBOPartsDetailsPanel().getVendorPriceInputFieldsList().get(order));
    }

    public static String getCorePrice(int order) {
        return getFormattedInputField(new VNextBOPartsDetailsPanel().getCorePriceInputFieldsList().get(order));
    }

    public static String getLaborCredit(int order) {
        return getFormattedInputField(new VNextBOPartsDetailsPanel().getLaborCreditInputFieldsList().get(order));
    }

    public static void setProvider(String provider) {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        Utils.clickElement(partsDetailsPanel.getProviderFieldArrow());
        Utils.selectOption(partsDetailsPanel.getPartDropDown(), partsDetailsPanel.getPartsListBoxOptions(), provider);
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

    public static void selectCheckboxesForServicesByName(String... names) {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        final List<WebElement> partCheckboxesList = partsDetailsPanel.getPartCheckboxesList();
        if (partCheckboxesList.size() > 1) {
            Arrays.asList(names).forEach(name -> {
                for (int i = 0; i < getPartNumbersByPartName(name).size(); i++) {
                    Utils.clickElement(partCheckboxesList.get(getPartNumbersByPartName(name).get(i)));
                }
            });
        }
    }

    public static void clickServiceCheckbox(int order) {
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitABit(1000);
        final WebElement serviceCheckbox = new VNextBOPartsDetailsPanel().getPartCheckboxesList().get(order);
        WaitUtilsWebDriver.waitForElementNotToBeStale(serviceCheckbox);
        Utils.clickElement(serviceCheckbox);
    }

    public static void waitForStatusesCheckboxToBeEnabled() {
        WaitUtilsWebDriver.waitForAttributeToContain(new VNextBOPartsDetailsPanel().getStatusesCheckbox(),
                "class", "items-toggle--checked", 4);
    }

    public static void waitForStatusesCheckboxToBeDisabled() {
        WaitUtilsWebDriver.waitForAttributeNotToContain(new VNextBOPartsDetailsPanel().getStatusesCheckbox(),
                "class", "items-toggle--checked", 4);
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

    public static List<Integer> getPartNumbersByPartName(String partName) {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        final List<String> partNames = Utils.getText(partsDetailsPanel.getPartNames());
        final ArrayList<Integer> partNumbers = new ArrayList<>();
        for (int i = 0; i < partNames.size(); i++) {
            if (partNames.get(i).contains(partName)) {
                partNumbers.add(i);
            }
        }
        return partNumbers;
    }

    public static String getPartNameByIndex(int index) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBOPartsDetailsPanel().getPartNames(), 5);
        final List<WebElement> partNames = new VNextBOPartsDetailsPanel().getPartNames();
        return partNames.size() != 0 ? Utils.getText(partNames.get(index)) : null;
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

    public static void setConditionForPartByPartNumber(int partNumber, String condition) {
        final VNextBOPartsDetailsPanel detailsPanel = new VNextBOPartsDetailsPanel();
        Utils.clickElement(detailsPanel.getPartConditionFields().get(partNumber));
        if (condition.equals("")) {
            Utils.clickElement(detailsPanel.getPartDropDownEmptyField());
        } else {
            Utils.selectOptionInDropDownWithJs(detailsPanel.getPartsListBoxOptions(), condition);
        }
    }

    public static void setCoreStatusForPartByPartNumber(int partNumber, String status) {
        Utils.clickElement(new VNextBOPartsDetailsPanel().getPartCoreStatusFields().get(partNumber));
        Utils.selectOptionInDropDownWithJsScroll(status);
    }

    public static void openCoreStatusDropDown(int partNumber) {
        final VNextBOPartsDetailsPanel partsDetailsPanel = new VNextBOPartsDetailsPanel();
        final boolean opened = WaitUtilsWebDriver.elementShouldBeVisible(partsDetailsPanel.getPartDropDown(), true, 0);
        if (!opened) {
            Utils.clickElement(partsDetailsPanel.getPartCoreStatusFields().get(partNumber));
            WaitUtilsWebDriver.elementShouldBeVisible(partsDetailsPanel.getPartDropDown(), true, 2);
        }
    }

    public static void clickActionsButtonForPartByNumberInList(int partNumber) {
        Utils.clickElement(new VNextBOPartsDetailsPanel().getActionsButton().get(partNumber));
    }

    public static void clickDuplicateActionButtonForPartByNumberInList(int partNumber) {
        Utils.clickElement(new VNextBOPartsDetailsPanel().getDuplicateActionButton().get(partNumber));
    }

    public static void clickNotesActionButtonForPartByNumberInList(int partNumber) {
        Utils.clickElement(new VNextBOPartsDetailsPanel().getNotesActionButton().get(partNumber));
    }

    public static void clickDeleteActionButtonForPartByNumberInList(int partNumber) {
        Utils.clickElement(new VNextBOPartsDetailsPanel().getDeleteActionButton().get(partNumber));
    }

    public static void clickDocumentsActionButtonForPartByNumberInList(int partNumber) {
        Utils.clickElement(new VNextBOPartsDetailsPanel().getDocumentsActionButton().get(partNumber));
    }

    public static String getFormattedInputField(WebElement inputField) {
        return Utils.getInputFieldValue(inputField, 3)
                .replace("$", "")
                .replaceAll("[.][0-9]{2}", "");
    }

    public static void updatePartsAmount(int expectedPartsAmount) {
        try {
            WaitUtilsWebDriver.getShortWait().until((ExpectedCondition<Boolean>) driver ->
                    VNextBOPartsDetailsPanelSteps.getPartsListSize() == expectedPartsAmount);
        } catch (Exception ignored) {}
    }
}
