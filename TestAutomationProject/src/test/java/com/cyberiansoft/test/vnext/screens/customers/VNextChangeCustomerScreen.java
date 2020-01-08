package com.cyberiansoft.test.vnext.screens.customers;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextChangeCustomerScreen extends VNextBaseCustomersScreen {

    @FindBy(xpath="//div[@data-page='orders-change-customer']")
    private WebElement changecustomersscreen;

    @FindBy(xpath="//*[@data-autotests-id='customers-list']")
    private WebElement customerslist;

    public VNextChangeCustomerScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
    }

    public boolean isAddCustomerButtonDisplayed() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
        wait.until(ExpectedConditions.visibilityOf(changecustomersscreen));
        WaitUtils.elementShouldBeVisible(customerslist, true);
        return changecustomersscreen.findElements(By.xpath(".//*[@action='add']")).size() > 0;
    }
}
