package com.cyberiansoft.test.inhouse.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BasePage {

    @FindBy(xpath = "//div[@class='shirma-dialog' and contains(@style, 'display: block')]")
    private WebElement shirmaDialog;

    @FindBy(xpath="//button[@data-dismiss='alert']")
    private WebElement successNotificationCloseButton;

    public WebDriver driver;
    public static WebDriverWait wait;

    private static final long SLEEP_TIMEOUT_IN_SEC = 15;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(SLEEP_TIMEOUT_IN_SEC, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(SLEEP_TIMEOUT_IN_SEC * 4, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(SLEEP_TIMEOUT_IN_SEC * 2, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 60, 250);
    }

    @Step
    public void waitForShirmaDialog() {
        try {
            if (shirmaDialog.isDisplayed())
            wait.until(ExpectedConditions.invisibilityOf(shirmaDialog));
        } catch (Exception ignored) {}
    }

    @Step
    public void goToPreviousPage() {
        driver.navigate().back();
    }

    @Step
    public void refreshPage() {
        driver.navigate().refresh();
        waitABit(4000);
    }

    @Step
    public void waitForOverflowToDisappear() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body[contains(@style, 'auto')]")));
        } catch (Exception ignored) {}
    }

    @Step
    public void waitForLoading() {
        try {
            wait.until(ExpectedConditions
                    .visibilityOf(driver.findElement(By
                            .xpath("//div[not(contains(@style, 'none'))]/i[@class='fa fa-refresh fa-spin']"))));
            wait.until(ExpectedConditions
                    .invisibilityOf(driver.findElement(By
                            .xpath("//div[not(contains(@style, 'none'))]/i[@class='fa fa-refresh fa-spin']"))));
        } catch (Exception e) {
            waitABit(3000);
        }
    }

    @Step
    public void waitForProcessing() {
        try {
            wait.until(ExpectedConditions
                    .attributeToBe(By.id("table-potential-client_processing"), "display", "none"));
        } catch (Exception ignored) {}
    }

    @Step
    public static void waitABit(int milliseconds) {
        if (milliseconds > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(milliseconds);
            } catch (InterruptedException ignored) {}
        }
    }

    @Step
    public BasePage clickWithJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        return this;
    }

    @Step
    public void closeNotification() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(successNotificationCloseButton)).click();
        } catch (Exception ignored) {}
    }
}