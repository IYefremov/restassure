package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public abstract class BasePage {

    public WebDriver driver;
    public static WebDriverWait wait;
    public WebDriverWait updateWait;

    private static final long SLEEP_TIMEOUT_IN_SEC = 15;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(SLEEP_TIMEOUT_IN_SEC, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(SLEEP_TIMEOUT_IN_SEC * 4, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(SLEEP_TIMEOUT_IN_SEC * 2, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 20, 250);
    }

    public BasePage goToAgreemntApprovmentPageFromEmail(String link) {
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
}