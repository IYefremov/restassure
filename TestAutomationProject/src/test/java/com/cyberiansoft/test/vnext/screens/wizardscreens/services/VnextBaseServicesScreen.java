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
    private WebElement servicesScreen;

    @FindBy(xpath = "//div[@class='notifier']")
    private WebElement notificationPopup;

    @FindBy(xpath = "//*[@action='selected']")
    private WebElement selectedButton;

    @FindBy(xpath = "//*[@data-view-mode='selected']")
    private WebElement selectedView;

    public VnextBaseServicesScreen() {
    }

    public void switchToAvalableServicesView() {
        WebElement activeTab = WaitUtils.waitUntilElementIsClickable(servicesScreen.findElement(By.xpath(".//*[@action='available']")));
        if (!activeTab.getAttribute("class").contains("active")) {
            WaitUtils.getGeneralFluentWait().until(driver -> {
                servicesScreen.findElement(By.xpath(".//*[@action='available']")).click();
                return true;
            });
            WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='available' and @class='button active']")));
        }
    }

    public void switchToSelectedServicesView() {
        WaitUtils.click(selectedButton);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='selected' and @class='button active']")));
    }
}
