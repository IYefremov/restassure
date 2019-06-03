package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class VNextBODashboardPanel extends VNextBOBaseWebPage {

    @FindBy(id = "parts-view-dashboard")
    private WebElement dashboardPanel;

    @FindBy(className = "dashboard__item__title")
    private List<WebElement> dashboardItemsNames;

    @FindBy(xpath = "//div[@class='dashboard__item__title' and text()='Past Due Parts']")
    private WebElement pastDuePartsItemName;

    @FindBy(xpath = "//div[@class='dashboard__item__title' and text()='In Progress']")
    private WebElement inProgressItemName;

    @FindBy(xpath = "//div[@class='dashboard__item__title' and text()='Completed']")
    private WebElement completedItemName;

    @FindBy(xpath = "//div[contains(@class, 'dashboard') and text()='Past Due Parts']/..")
    private WebElement pastDuePartsItem;

    @FindBy(xpath = "//div[contains(@class, 'dashboard') and text()='In Progress']/..")
    private WebElement inProgressItem;

    @FindBy(xpath = "//div[contains(@class, 'dashboard') and text()='Completed']/..")
    private WebElement completedItem;

    @FindBy(xpath = "//input[@title='Quantity']")
    private WebElement quantityField;

    public VNextBODashboardPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public String getQuantityValue() {
        return wait.until(ExpectedConditions.visibilityOf(quantityField)).getAttribute("value");
    }

    public VNextBODashboardPanel clickPastDuePartsLink() {
        clickDashboardOptionLink(pastDuePartsItem);
        return this;
    }

    public VNextBODashboardPanel clickInProgressItemLink() {
        clickDashboardOptionLink(inProgressItem);
        return this;
    }

    public VNextBODashboardPanel clickCompletedItemLink() {
        clickDashboardOptionLink(completedItem);
        return this;
    }

    public boolean isPastDuePartsOptionSelected() {
        return isDashboardOptionHighlighted(pastDuePartsItem);
    }

    public boolean isInProgressOptionSelected() {
        return isDashboardOptionHighlighted(inProgressItem);
    }

    public boolean isCompletedOptionSelected() {
        return isDashboardOptionHighlighted(completedItem);
    }

    private void clickDashboardOptionLink(WebElement option) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(option)).click();
            waitForLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isDashboardOptionHighlighted(WebElement option) {
        try {
            return wait.until(ExpectedConditions.attributeToBeNotEmpty(option, "style"));
        } catch (Exception ignored) {}
        return false;
    }

    public List<String> getDashboardItemsNames() {
        wait.until(ExpectedConditions.visibilityOfAllElements(dashboardItemsNames));
        return dashboardItemsNames
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}