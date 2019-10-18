package com.cyberiansoft.test.vnextbo.screens.Users;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.*;
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

    @FindBy(xpath = "//tfoot//button[contains(@data-bind, 'previousPage')]")
    public WebElement previousPageBtn;

    @FindBy(xpath = "(//button[@class='pager__item' and contains(@data-bind, 'click: pager.firstPage')])[1]")
    public WebElement firstPageBtn;

    @FindBy(xpath = "(//button[@class='pager__item' and contains(@data-bind, 'click: pager.lastPage')])[1]")
    public WebElement lastPageBtn;

    @FindBy(xpath = "(//span[@class='k-input'])[1]")
    public WebElement topItemsPerPageField;

    @FindBy(xpath = "(//span[@class='k-input'])[2]")
    public WebElement bottomItemsPerPageField;

    @FindBy(xpath = "(//button[@class='pager__item active'])[1]")
    public WebElement activePageTopPagingElement;

    @FindBy(xpath = "(//button[@class='pager__item active'])[2]")
    public WebElement activePageBottomPagingElement;

    @FindBy(xpath = "//div[@id='users-search']//i[@class='caret dropdown-absolute']")
    public WebElement searchFieldAdvancedSearchCaret;

    @FindBy(xpath = "//section[@class='view']//*[@data-bind='text: filterInfoString']")
    public WebElement filterInfoText;

    @FindBy(xpath = "//section[@class='view']//i[@class='custom-search__icon-cancel icon-cancel-circle']")
    public WebElement clearSearchIcon;

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

    public WebElement specificPageButton(int pageNumber) {
        return driver.findElement(By.xpath("(//button[@class='pager__item' and @data-page-index='" +
                (pageNumber - 1) + "'])[1]"));
    }

    public WebElement itemsPerPageOption(String itemsPerPage) {
        return driver.findElement(By.xpath("(//ul[@class='k-list k-reset']/li[text()='" +
                itemsPerPage + " items per page'])[1]"));
    }

    public WebElement tableRowByText(String searchText) {
        return driver.findElement(By.xpath("//td[contains(text(), '" + searchText + "')]/ancestor::tr"));
    }

    public VNexBOUsersWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(searchField));
    }
}
