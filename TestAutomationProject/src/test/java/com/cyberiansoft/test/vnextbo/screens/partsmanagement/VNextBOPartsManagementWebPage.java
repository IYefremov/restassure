package com.cyberiansoft.test.vnextbo.screens.partsmanagement;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOPartsManagementWebPage extends VNextBOBaseWebPage {

    @FindBy(id = "parts-view")
    private WebElement partsManagementBlock;

    @FindBy(xpath = "//i[@data-bind='click: location.toggle']")
    private WebElement locationArrowSearchExpander;

    @FindBy(xpath = "//ul[@class='scroll-pane-locations']")
    private WebElement locationsOptionsList;

    @FindBy(xpath = "//ul[@data-automation-id='partsOrdersList']")
    private WebElement partsOrdersList;

    @FindBy(xpath = "//div[text()='Past Due Parts']/ancestor::div[@class='dashboard__item']")
    private WebElement pastDuePartsButton;

    @FindBy(xpath = "//div[text()='In Progress']/ancestor::div[@class='dashboard__item']")
    private WebElement inProgressButton;

    @FindBy(xpath = "//div[text()='Completed']/ancestor::div[@class='dashboard__item']")
    private WebElement completedButton;

    @FindBy(xpath = "//div[@id='advSearchPartsOrders-savedSearchList']")
    private WebElement savedSearchList;

    @FindBy(xpath = "//div[@id='advSearchPartsOrders-savedSearchList']//i[@class='icon-gear']")
    private WebElement savedSearchListGearIcon;

    @FindBy(xpath = "//div[@id='advSearchPartsOrders-savedSearchList']//i[@class='icon-gear']")
    private WebElement searchListGearIcon;

    public WebElement savedSearchOptionByName(String searchName) {

        return driver.findElement(By.xpath("//span[@data-bind=\"click: applySavedSearch\" and text()='" + searchName + "']"));
    }

    public WebElement editSavedSearchButtonByName(String searchName) {

        return driver.findElement(By.xpath("//span[text()='" + searchName + "']/following-sibling::i[@data-bind='click: editSavedSearch']"));
    }

    public VNextBOPartsManagementWebPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
