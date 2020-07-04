package com.cyberiansoft.test.vnextbo.screens.users;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class VNexBOUsersWebPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='users-form-popup-view']/button")
    public WebElement addNewUserBtn;

    @FindBy(xpath = "//div[@id='users-list']/table")
    public WebTable usersTable;

    @FindBy(xpath = "//tfoot//button[contains(@data-bind, 'nextPage')]")
    public WebElement nextPageBtn;

    @FindBy(xpath = "//div[@id='users-search']//i[@class='caret dropdown-absolute']")
    public WebElement searchFieldAdvancedSearchCaret;

    @FindBy(xpath = "//section[@class='view']//i[@class='icon-search']")
    public WebElement searchIcon;

    @FindBy(id = "advSearchUsers-freeText")
    public WebElement searchField;

    @FindBy(xpath = "//div[@id='users-list']//div[@class='progress-message']")
    public WebElement noItemsFoundMessage;

    @FindBy(xpath = "//i[@class='icon-warning text-red']")
    public WebElement redTriangleWarningIcon;

    @FindBy(xpath = "//button[@data-bind='click: resendConfirmation']")
    public WebElement reSendButton;

    @FindBy(xpath = "//span[@title='Edit']")
    public WebElement editUserButton;

    public WebElement tableRowByText(String searchText) {
        return driver.findElement(By.xpath("//td[contains(text(), '" + searchText + "')]/ancestor::tr"));
    }

    public VNexBOUsersWebPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(searchField));
    }
}
