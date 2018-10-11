package com.cyberiansoft.test.vnext.screens.typesscreens;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class VNextBaseTypeScreen extends VNextBaseScreen {

    @FindBy(xpath="//*[@data-automation-id='search-icon']")
    private WebElement searchbtn;

    @FindBy(xpath="//*[@data-autotests-id='search-input']")
    private WebElement searchfld;

    @FindBy(xpath="//*[@data-autotests-id='search-cancel']")
    private WebElement cancelsearchbtn;

    @FindBy(xpath="//*[@data-automation-id='search-clear']")
    private WebElement clearsearchicon;

    @FindBy(xpath="//*[@action='my']")
    private WebElement myviewtab;

    @FindBy(xpath="//*[@action='team']")
    private WebElement teamviewtab;

    public VNextBaseTypeScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
    }

    protected WebElement getListCell(WebElement typesList, String cellValue) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@action='check-item']")));
        List<WebElement> listCells = typesList.findElements(By.xpath(".//*[contains(@class, 'entity-item accordion-item')]"));

        WebElement tableCell = listCells.stream().
                filter(elemnt -> elemnt.findElement(By.xpath(".//div[@action='select']/div[@class='checkbox-item-title']"))
                        .getText().trim().equals(cellValue))
                .findFirst().orElse(null);
        if (tableCell == null)
            Assert.fail( "Can't find cell in the list: " + cellValue);
        return tableCell;
    }

    protected void clickAddButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@action='add']")));
        WaitUtils.click(By.xpath("//*[@action='add']"));
    }

    protected void switchToTeamView() {
        tap(WaitUtils.waitUntilElementIsClickable(By.xpath("//*[@action='team']")));
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='button active' and @action='team']")));
    }

    protected boolean isTeamViewActive() {
        return teamviewtab.getAttribute("class").contains("active");
    }

    protected void switchToMyView() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(myviewtab));
        tap(myviewtab);
        wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='button active' and @action='my']")));
    }

    protected boolean isMyViewActive() {
        return myviewtab.getAttribute("class").contains("active");
    }

    public void searchByFreeText(String searchtext) {
        clickSearchButton();
        setSearchText(searchtext);
    }

    public void clickSearchButton() {
        tap(searchbtn);
    }

    private void setSearchText(String searchtext) {
        tap(searchfld);
        searchfld.clear();
        appiumdriver.getKeyboard().sendKeys(searchtext);
        appiumdriver.hideKeyboard();
        AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
        ((AndroidDriver<MobileElement>) appiumdriver).pressKeyCode(66);
        AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
        BaseUtils.waitABit(1000);
        clickCancelSearchButton();
    }

    public void clickCancelSearchButton() {
        BaseUtils.waitABit(1000);
        tap(cancelsearchbtn);
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading inspections']"));
    }

    public void clearSearchField() {
        if (cancelsearchbtn.isDisplayed()) {
            tap(clearsearchicon);
            clickCancelSearchButton();
        }
        if (searchbtn.findElement(By.xpath(".//span[contains(@class, 'icon-has-query')]")).isDisplayed()) {
            tap(searchbtn);
            if (searchfld.getAttribute("value").length() > 1) {
                //searchfld.clear();
                tap(clearsearchicon);
                WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading inspectiontypes']"));
            }
            clickCancelSearchButton();
        }
    }
}
