package com.cyberiansoft.test.vnextbo.interactions.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOEditNotesDialog;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROWebPage;
import com.cyberiansoft.test.vnextbo.verifications.repairOrders.VNextBOROPageVerifications;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class VNextBOROPageInteractions {

    public VNextBOROWebPage repairOrdersPage;

    public VNextBOROPageInteractions() {
        repairOrdersPage = PageFactory.initElements(
                DriverBuilder.getInstance().getDriver(), VNextBOROWebPage.class);
    }

    private boolean isWorkOrderDisplayed(String text) {
        try {
            return WaitUtilsWebDriver
                    .getWait()
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOf(repairOrdersPage.getTableBody()
                            .findElement(By.xpath(".//strong[contains(text(), \"" + text + "\")]"))))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isWorkOrderDisplayedByName(String name) {
        return isWorkOrderDisplayed(name);
    }

    public void clickTechniciansFieldForWO(String woNumber) {
        Utils.clickElement(repairOrdersPage.getTechniciansFieldForWO(woNumber));
    }

    public void clickWoLink(String woNumber) {
        WaitUtilsWebDriver.waitForLoading();
        Utils.clickElement(By.xpath("//a[@class='order-no']/strong[text()='" + woNumber + "']/.."));
        WaitUtilsWebDriver.waitForLoading();
    }

    public WebElement clickWoLink() {
        WaitUtilsWebDriver.waitForLoading();
        final WebElement woElement = repairOrdersPage.getRandomOrderNumber();
        if (woElement != null) {
            Utils.clickElement(woElement);
            WaitUtilsWebDriver.waitForLoading();
        } else {
            Assert.fail("The work orders list is empty");
        }
        return woElement;
    }

    public void revealNoteForWorkOrder(String woNumber) {
        if (!new VNextBOROPageVerifications().isNoteForWorkOrderDisplayed(woNumber)) {
            clickNoteForWo(woNumber);
        }
    }

    public void openNotesDialog() {
        Utils.clickElement(repairOrdersPage.getRoNotesLink());
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(new VNextBOEditNotesDialog().getRoEditNotesModalDialog(), 5);
    }

    private void clickNoteForWo(String woNumber) {
        Utils.clickElement(By.xpath("//strong[text()='" + woNumber
                + "']/../../../div[@data-bind='visible: orderDescriptionDisplay']"));
    }

    public void hideNoteForWorkOrder(String woNumber) {
        if (new VNextBOROPageVerifications().isNoteForWorkOrderDisplayed(woNumber)) {
            WaitUtilsWebDriver.waitForLoading();
            clickNoteForWo(woNumber);
        }
    }

    public void clickAdvancedSearchCaret() {
        Utils.clickElement(repairOrdersPage.getAdvancedSearchCaret());
    }

    public void setWoDepartment(String woNumber, String department) {
        hideNoteForWorkOrder(woNumber);
        Utils.clickElement(repairOrdersPage.getTableBody().findElement(By.xpath("//a[@class='order-no']" +
                "/strong[text()='" + woNumber + "']/../following-sibling::div/i[@class='menu-trigger']")));
        final WebElement departmentDropDown = repairOrdersPage.getTableBody()
                .findElement(By.xpath("//a[@class='order-no']/strong[text()='"
                + woNumber + "']/../..//div[@class='drop department-drop']"));
        WaitUtilsWebDriver.waitForVisibility(departmentDropDown);
        final List<WebElement> dropDownOptions = departmentDropDown.findElements(By.xpath(".//label"));
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(dropDownOptions);
        dropDownOptions
                .stream()
                .filter(e -> e.getText().equals(department))
                .findFirst()
                .ifPresent(WebElement::click);
        WaitUtilsWebDriver.waitForInvisibility(departmentDropDown);
        WaitUtilsWebDriver.waitABit(1000);
    }

    public void clickXIconToCloseNoteForWorkOrder(String woNumber) {
        if (new VNextBOROPageVerifications().isNoteForWorkOrderDisplayed(woNumber)) {
            Utils.clickElement(By.xpath("//strong[text()='" + woNumber
                    + "']/../../../div[@data-bind='visible: orderDescriptionDisplay']//a[@class='x']"));
        }
    }

    public void clickSavedSearchArrow() {
        Utils.clickElement(repairOrdersPage.getSavedSearchArrow());
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(repairOrdersPage.getSavedSearchDropDown());
    }

    public void selectSavedSearchDropDownOption(String option) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(repairOrdersPage.getSavedSearchDropDownOptions());
        repairOrdersPage.getSavedSearchDropDownOptions()
                .stream()
                .filter(o -> o.getText().equals(option))
                .findFirst()
                .ifPresent(Utils::clickWithActions);
        WaitUtilsWebDriver.waitForLoading();
    }
}