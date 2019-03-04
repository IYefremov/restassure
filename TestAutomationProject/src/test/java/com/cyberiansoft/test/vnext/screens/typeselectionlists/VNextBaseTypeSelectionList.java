package com.cyberiansoft.test.vnext.screens.typeselectionlists;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextBaseTypeSelectionList extends VNextBaseScreen {

    @FindBy(xpath="//div[@data-page='entity-types']")
    private WebElement typeslist;

    @FindBy(xpath="//*[@data-automation-id='search-icon']")
    private WebElement searchbtn;

    @FindBy(xpath="//*[@data-autotests-id='search-input']")
    private WebElement searchfld;

    public VNextBaseTypeSelectionList(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        //PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 90);
        wait.until(ExpectedConditions.visibilityOf(typeslist));
    }

    public void selectType(String typeName) {
        if (!elementExists("//div[@class='item-title']/div[text()='" + typeName + "']")) {
            clickSearchButton();
            setSearchText(typeName);
            WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='item-title']/div[text()='" + typeName + "']")));

        } else {
            WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='item-title']/div[text()='" + typeName + "']")));
            if (!appiumdriver.findElement(By.xpath("//div[@class='item-title']/div[text()='" + typeName + "']")).isDisplayed()) {
                WebElement elem = appiumdriver.findElement(By.xpath("//div[@class='item-title']/div[text()='" + typeName + "']"));
                JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
                je.executeScript("arguments[0].scrollIntoView(true);", elem);
                BaseUtils.waitABit(500);
            }
        }
        typeslist.findElement(By.xpath(".//div[@class='item-title']/div[text()='" + typeName + "']")).click();
    }

    public void clickSearchButton() {
        tap(searchbtn);
    }

    private void setSearchText(String searchtext) {
        tap(searchfld);
        searchfld.clear();
        searchfld.sendKeys(searchtext);
        //appiumdriver.hideKeyboard();
    }
}
