package com.cyberiansoft.test.vnext.screens.wizardscreens.services;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class VnextBaseServicesScreen extends VNextBaseWizardScreen {

    @FindBy(xpath = "//div[contains(@data-page,'list')]")
    private WebElement servicesscreen;

    @FindBy(xpath = "//div[@class='notifier']")
    private WebElement notificationPopup;

    @FindBy(xpath = "//*[@action='selected']")
    private WebElement selectedButton;

    @FindBy(xpath = "//*[@data-view-mode='selected']")
    private WebElement selectedView;

    public VnextBaseServicesScreen() {
    }

    public void switchToAvalableServicesView() {
        WaitUtils.waitUntilElementIsClickable(servicesscreen.findElement(By.xpath("//*[contains(@class,'services-list') and @data-view-mode]")));
        WaitUtils.getGeneralFluentWait().until(driver -> {
            tap(servicesscreen.findElement(By.xpath(".//*[@action='available']")));
            return true;
        });
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='available' and @class='button active']")));
        //wait = new WebDriverWait(appiumdriver, 5);
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class,'services-list') and @data-view-mode='available']")));
    }

    public VNextSelectedServicesScreen switchToSelectedServicesView() {
        WaitUtils.click(selectedButton);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='selected' and @class='button active']")));
        wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-view-mode='selected']")));
        return new VNextSelectedServicesScreen();
    }
}
