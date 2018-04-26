package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BasePage {

    public WebDriver driver;
    public static WebDriverWait wait;
    public WebDriverWait updateWait;

    private static final long SLEEP_TIMEOUT_IN_SEC = 15;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(SLEEP_TIMEOUT_IN_SEC, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(SLEEP_TIMEOUT_IN_SEC * 4, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(SLEEP_TIMEOUT_IN_SEC * 2, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 60, 250);
    }

    public BasePage goToAgreementApprovementPageFromEmail(String link) {
        driver.get(link);
        try {
            return PageFactory.initElements(driver,
                    TeamPortalAgreementApprovePage.class);
        } catch (TimeoutException e) {
            return PageFactory.initElements(driver,
                    BasePage.class);
        }
    }

    public void goToPreviousPage() {
        driver.navigate().back();
    }

    public void refreshPage() throws InterruptedException {
        driver.navigate().refresh();
        Thread.sleep(2000);
    }

    public void waitForLoading() {
        try {
            wait.until(ExpectedConditions
                    .visibilityOf(driver.findElement(By
                            .xpath("//div[not(contains(@style, 'none'))]/i[@class='fa fa-refresh fa-spin']"))));
            wait.until(ExpectedConditions
                    .invisibilityOf(driver.findElement(By
                            .xpath("//div[not(contains(@style, 'none'))]/i[@class='fa fa-refresh fa-spin']"))));
        } catch (Exception ignored) {
        }
    }

    public static void waitABit(int milliseconds) {
        if (milliseconds > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(milliseconds);
            } catch (InterruptedException ignored) {}
        }
    }

    public BasePage clickElementWithJS(WebElement element) {
        final String javaScript = "if(document.createEvent){" +
                "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initEvent('click', true, false);" + "" +
                "arguments[0].dispatchEvent(evObj);" +
                "} else if(document.createEventObject){" +
                "arguments[0].fireEvent('onclick');" +
                "}";
        ((JavascriptExecutor) driver).executeScript(javaScript, element);
        return this;
    }
}