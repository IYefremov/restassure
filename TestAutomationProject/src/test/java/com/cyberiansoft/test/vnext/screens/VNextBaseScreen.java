package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class VNextBaseScreen {

    protected WebDriver appiumdriver;

    @FindBy(xpath = "//*[@action='show_topbar_popover']")
    private WebElement showTopBarPopover;

    @FindBy(xpath = "//*[@data-autotests-id='change-screen-popover']")
    private WebElement changeScrenPopover;

    @FindBy(xpath = "//*[@action='logout']")
    private WebElement logoutButton;

    public VNextBaseScreen(WebDriver driver) {
        this.appiumdriver = driver;
        PageFactory.initElements(appiumdriver, this);
    }

    public VNextBaseScreen() {
        appiumdriver = ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        PageFactory.initElements(appiumdriver, this);
    }

    public void tap(WebElement element) {
        WaitUtils.click(element);
    }


    public void setValue(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }

    public void tapListElement(WebElement scrollablelist, String value) {
        WebElement elem = scrollablelist.findElement(By.xpath(".//div[contains(text(), '" + value + "')]"));
        JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
        je.executeScript("arguments[0].scrollIntoView(true);", elem);
        BaseUtils.waitABit(1000);
        scrollablelist.findElement(By.xpath(".//div[contains(text(), '" + value + "')]")).click();

    }

    public void swipeScreenLeft() {
        if (appiumdriver instanceof JavascriptExecutor)
            ((JavascriptExecutor) appiumdriver).executeScript("$('.page-content').trigger('swipeleft')");
        BaseUtils.waitABit(1000);
    }

    public void swipeScreenRight() {

        if (appiumdriver instanceof JavascriptExecutor)
            ((JavascriptExecutor) appiumdriver).executeScript("$('.page-content').trigger('swiperight');");
        BaseUtils.waitABit(2000);
    }

    public void clickScreenBackButton() {
        String backButtonLocator = "//*[@action=\"back\"]";
        WaitUtils.getGeneralFluentWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(backButtonLocator)));
        WaitUtils.waitUntilElementIsClickable(By.xpath(backButtonLocator));
        WaitUtils.click(By.xpath(backButtonLocator));
    }

    public void clickScreenForwardButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='forward']")));
        wait = new WebDriverWait(appiumdriver, 5);
        WebElement forwardBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@action='forward']")));
        tap(forwardBtn);
    }

    public void changeScreen(ScreenType screen) {
        clickScreenTitleCaption();
        WaitUtils.click(changeScrenPopover.findElement(By.xpath(".//span[text()='" + screen.getScreenIdentificator() + "']")));
    }

    public void selectScreen(ScreenType screen) {
        WaitUtils.click(changeScrenPopover.findElement(By.xpath(".//span[text()='" + screen.getScreenIdentificator() + "']")));
    }

    public void changeScreen(ScreenType screen, Integer index) {
        clickScreenTitleCaption();
        WaitUtils.click(changeScrenPopover.findElements(By.xpath(".//span[text()='" + screen.getScreenIdentificator() + "']")).get(index));
    }

    public boolean isScreenPresentInChangeScreenPopoverList(String screenName) {
        return changeScrenPopover.findElements(By.xpath(".//span[text()='" + screenName + "']")).size() > 0;
    }

    public void clickScreenTitleCaption() {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            WaitUtils.click(showTopBarPopover);
            return WaitUtils.isElementPresent(changeScrenPopover);
        });
    }
}
