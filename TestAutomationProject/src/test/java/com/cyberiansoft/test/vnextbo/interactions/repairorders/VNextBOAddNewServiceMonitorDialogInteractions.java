package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOAddNewServiceMonitorDialog;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class VNextBOAddNewServiceMonitorDialogInteractions {

    public static void setPriceType(String priceType) {
        clickPriceTypeBox();
        selectPriceType(priceType);
    }

    private static void clickPriceTypeBox() {
        Utils.clickElement(new VNextBOAddNewServiceMonitorDialog().getPriceTypeListBox());
    }

    private static void selectPriceType(String priceType) {
        final VNextBOAddNewServiceMonitorDialog newServiceMonitorDialog = new VNextBOAddNewServiceMonitorDialog();
        Utils.selectOptionInDropDown(newServiceMonitorDialog.getPriceTypeDropDown(),
                newServiceMonitorDialog.getPriceTypeListBoxOptions(), priceType);
    }

    public static void setCategory(String category) {
        clickCategoryBox();
        selectCategory(category);
    }

    private static void clickCategoryBox() {
        WaitUtilsWebDriver.waitForLoading();
        Utils.clickElement(new VNextBOAddNewServiceMonitorDialog().getCategoryListBox());
    }

    private static void selectCategory(String category) {
        Utils.selectOptionInDropDown(new VNextBOAddNewServiceMonitorDialog().getCategoryDropDown(),
                new VNextBOAddNewServiceMonitorDialog().getCategoryListBoxOptions(), category, true);
    }

    public static void setSubcategory(String subcategory) {
        clickSubcategoryBox();
        selectSubcategory(subcategory);
    }

    public static String setSubcategory() {
        clickSubcategoryBox();
        return selectSubcategory();
    }

    public static String selectRandomAddPartsOption() {
        final List<WebElement> addPartsElements =
                WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBOAddNewServiceMonitorDialog().getPartsOptions());
        final int randomIndex = RandomUtils.nextInt(0, addPartsElements.size());
        final String selectedAddPartsNumber = getSelectedAddPartsNumber();
        Utils.clickElement(addPartsElements.get(randomIndex));
        WaitUtilsWebDriver.getWait().until((ExpectedCondition<Boolean>) (num) ->
                !selectedAddPartsNumber.equals(getSelectedAddPartsNumber()));
        return addPartsElements.get(randomIndex).getText();
    }

    public static String getSelectedAddPartsNumber() {
        return Utils.getText(new VNextBOAddNewServiceMonitorDialog().getSelectedAddPartsCounter());
    }

    private static void clickSubcategoryBox() {
        Utils.clickElement(new VNextBOAddNewServiceMonitorDialog().getSubcategoryListBox());
    }

    private static void selectSubcategory(String subcategory) {
        Utils.selectOptionInDropDown(new VNextBOAddNewServiceMonitorDialog().getSubcategoryDropDown(),
                new VNextBOAddNewServiceMonitorDialog().getSubcategoryListBoxOptions(), subcategory);
    }

    private static String selectSubcategory() {
        return Utils.selectOptionInDropDown(new VNextBOAddNewServiceMonitorDialog().getSubcategoryDropDown(),
                new VNextBOAddNewServiceMonitorDialog().getSubcategoryListBoxOptions());
    }

    public static void setService(String service) {
        clickServiceArrow();
        selectService(service);
    }

    private static void clickServiceArrow() {
        Utils.clickElement(new VNextBOAddNewServiceMonitorDialog().getServiceArrow());
    }

    private static void selectService(String service) {
        Utils.selectOptionInDropDown(new VNextBOAddNewServiceMonitorDialog().getServiceDropDown(),
                new VNextBOAddNewServiceMonitorDialog().getServiceListBoxOptions(), service, true);
    }

    public static void setServiceDescription(String description) {
        Utils.clearAndType(new VNextBOAddNewServiceMonitorDialog().getServiceDescription(), description);
    }

    public static void setServiceDetails(String serviceDetail) {
        WaitUtilsWebDriver.waitABit(300);
        Utils.clearAndType(new VNextBOAddNewServiceMonitorDialog().getServiceDetails(), serviceDetail);
    }

    public static void setServicePrice(String price) {
        selectServiceOption(new VNextBOAddNewServiceMonitorDialog().getServicePrice(),
                new VNextBOAddNewServiceMonitorDialog().getServicePriceInputField(), price);
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void setServiceLaborRate(String laborRate) {
        selectServiceOption(new VNextBOAddNewServiceMonitorDialog().getServiceLaborRate(),
                new VNextBOAddNewServiceMonitorDialog().getServiceLaborRateInputField(), laborRate);
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void setServiceQuantity(String quantity) {
        selectServiceOption(new VNextBOAddNewServiceMonitorDialog().getServiceQuantity(),
                new VNextBOAddNewServiceMonitorDialog().getServiceQuantityInputField(), quantity);
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void setServiceLaborTime(String laborTime) {
        selectServiceOption(new VNextBOAddNewServiceMonitorDialog().getServiceLaborTime(),
                new VNextBOAddNewServiceMonitorDialog().getServiceLaborTimeInputField(), laborTime);
    }

    private static void selectServiceOption(WebElement serviceQuantity, WebElement serviceQuantityInputField, String quantity) {
        Utils.clickElement(serviceQuantity);
        Utils.clear(serviceQuantityInputField);
        WaitUtilsWebDriver.waitForElementToBeClickable(serviceQuantity).sendKeys(quantity);
    }

    public static void clickSubmitButton() {
        Utils.clickElement(new VNextBOAddNewServiceMonitorDialog().getSubmitButton());
        WaitUtilsWebDriver.waitForLoading();
        WaitUtilsWebDriver.waitForInvisibility(new VNextBOAddNewServiceMonitorDialog().getNewServicePopup());
        Utils.refreshPage();
    }

    public static void clickXButton() {
        Utils.clickElement(new VNextBOAddNewServiceMonitorDialog().getXButton());
        WaitUtilsWebDriver.waitForInvisibility(new VNextBOAddNewServiceMonitorDialog().getNewServicePopup());
        Utils.refreshPage();
    }

    public static void clickCancelButton() {
        Utils.clickElement(new VNextBOAddNewServiceMonitorDialog().getCancelButton());
        WaitUtilsWebDriver.waitForInvisibility(new VNextBOAddNewServiceMonitorDialog().getNewServicePopup());
        Utils.refreshPage();
    }
}
