package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;
import com.cyberiansoft.test.vnextbo.utils.VNextBOAlertMessages;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
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
    public WebElement itemsPerPageField;

    public WebElement specificPageButton(int pageNumber) {
        return driver.findElement(By.xpath("(//button[@class='pager__item active' and @data-page-index='" +
                (pageNumber - 1) + "'])[1]"));
    }

    public WebElement itemsPerPageOption(int itemsPerPage) {
        return driver.findElement(By.xpath("(//ul[@class='k-list k-reset']/li[text()='" +
                itemsPerPage + " items per page'])[1]"));
    }

    @FindBy(id = "advSearchUsers-freeText")
    private WebElement searchField;

    public VNexBOUsersWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(usersTable.getWrappedElement()));
    }

    public VNexBOAddNewUserDialog clickAddUserButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addNewUserBtn)).click();
        return PageFactory.initElements(
                driver, VNexBOAddNewUserDialog.class);
    }

    public List<WebElement> getUsersTableRows() {
        waitLong
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(usersTable.getWrappedElement()));
        return usersTable.getTableRows();
    }

    public int getUsersTableRowCount() {
        return getUsersTableRows().size();
    }

    private WebElement getTableRowWithUserMail(String usermail) {
        waitABit(300);
        WebElement userrow = null;
        List<WebElement> rows = getUsersTableRows();
        for (WebElement row : rows) {
            if (
                    wait
                            .ignoring(StaleElementReferenceException.class)
                            .until(ExpectedConditions.visibilityOf(row.findElement(By.xpath("./td[" +
                                    usersTable.getTableColumnIndex("Email") + "]"))))
                            .getText()
                            .equals(usermail)) {
                userrow = row;
                break;
            }
        }
        return userrow;
    }

    public boolean isUserPresentOnCurrentPageByUserEmail(String usermail) {
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(getTableRowWithUserMail(usermail))));
        } catch (NullPointerException ignored) {}
        WebElement row = getTableRowWithUserMail(usermail);
        return row != null;
    }

    public boolean findUserInTableByUserEmail(String usermail) {
        boolean found = false;
        boolean nextpage = true;
        while (nextpage) {
            if (nextPageBtn.getAttribute("disabled") != null) {
                found = isUserPresentOnCurrentPageByUserEmail(usermail);
                nextpage = false;
            } else if (!isUserPresentOnCurrentPageByUserEmail(usermail)) {
                wait.until(ExpectedConditions.elementToBeClickable(nextPageBtn));
                clickWithJS(nextPageBtn);
                waitABit(2500);
            } else {
                found = true;
                break;
            }
        }
        return found;
    }

    public boolean findUserBySearch(String usenFirstName, String userMail) {
        wait.until(ExpectedConditions.elementToBeClickable(searchField)).click();
        searchField.sendKeys(usenFirstName);
        searchField.sendKeys(Keys.ENTER);
        waitForLoading();
        return isUserPresentOnCurrentPageByUserEmail(userMail);
    }

    public boolean isRedWarningTrianglePresentForUser(String usermail) {
        boolean trianglexists = false;
        WebElement row = getTableRowWithUserMail(usermail);
        if (row != null)
            trianglexists = row.findElement(By.xpath(".//i[@class='icon-warning text-red']")).isDisplayed();
        else
            Assert.assertFalse(false, "Can't find user with the following email: " + usermail);
        return trianglexists;
    }

    public VNexBOAddNewUserDialog clickEditButtonForUser(String usermail) {
        WebElement row = getTableRowWithUserMail(usermail);
        if (row != null) {
            Actions act = new Actions(driver);
            act.moveToElement(row.findElement(By.xpath(".//td[@class='grid__actions']"))).perform();
            act.click(row.findElement(By.xpath(".//td[@class='grid__actions']/span[@data-bind='click: edit']"))).perform();
        } else
            Assert.assertFalse(false, "Can't find user with the following email: " + usermail);
        return PageFactory.initElements(
                driver, VNexBOAddNewUserDialog.class);
    }

    public VNextBOConfirmationDialog clickResendButtonForUser(String usermail) {
        WebElement row = getTableRowWithUserMail(usermail);
        if (row != null) {
            row.findElement(By.xpath(".//button[@data-bind='click: resendConfirmation']")).click();
        } else
            Assert.assertFalse(false, "Can't find user with the following email: " + usermail);
        return PageFactory.initElements(
                driver, VNextBOConfirmationDialog.class);
    }

    public void clickUserResendButtonAndAgree(String usermail) {
        VNextBOConfirmationDialog confirmsg = clickResendButtonForUser(usermail);
        final String msg = confirmsg.clickYesAndGetConfirmationDialogMessage();
        Assert.assertEquals(msg, VNextBOAlertMessages.USER_DIDNT_CONFIRM_REGISTRATION);
    }

    public void clickUserResendButtonAndDisagree(String usermail) {
        VNextBOConfirmationDialog confirmsg = clickResendButtonForUser(usermail);
        final String msg = confirmsg.clickNoAndGetConfirmationDialogMessage();
        Assert.assertEquals(msg, VNextBOAlertMessages.USER_DIDNT_CONFIRM_REGISTRATION);
    }

    public String getUserName(String usermail) {
        String username = "";
        WebElement row = getTableRowWithUserMail(usermail);
        if (row != null) {
            username = row.findElement(By.xpath("./td[" + usersTable.getTableColumnIndex("Name") + "]")).getText();
        } else
            Assert.assertFalse(false, "Can't find user with the following email: " + usermail);
        return username;
    }

    public String getUserPhone(String usermail) {
        String userphone = "";
        WebElement row = getTableRowWithUserMail(usermail);
        if (row != null) {
            userphone = row.findElement(By.xpath("./td[" + usersTable.getTableColumnIndex("Phone") + "]")).getText();
        } else
            Assert.assertFalse(false, "Can't find user with the following email: " + usermail);
        return userphone;
    }
}
