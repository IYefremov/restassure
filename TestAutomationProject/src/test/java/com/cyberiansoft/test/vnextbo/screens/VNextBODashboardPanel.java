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
    private WebElement dashboardPastDuePartsItemName;

    @FindBy(xpath = "//div[@class='dashboard__item__title' and text()='In Progress']")
    private WebElement dashboardInProgressItemName;

    @FindBy(xpath = "//div[@class='dashboard__item__title' and text()='Completed']")
    private WebElement dashboardCompletedItemName;

    public List<String> getDashboardItemsNames() {
        wait.until(ExpectedConditions.visibilityOfAllElements(dashboardItemsNames));
        return dashboardItemsNames
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public VNextBODashboardPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}
