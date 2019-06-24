package com.cyberiansoft.test.vnext.screens.panelandparts;

import com.cyberiansoft.test.vnext.screens.VNextLaborServiceDetailsScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextLaborServicePartsList extends VNextBasePanelPartsList {

    @FindBy(xpath="//div[@data-page='parts']")
    private WebElement laborservicedetailssscreen;

    @FindBy(xpath="//*[@data-autotests-id='parts-list']")
    private WebElement partslist;

    public VNextLaborServicePartsList(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='parts']")));
    }

    public void selectServiceLaborPart(String partName) {
        tap(partslist.findElement(By.xpath(".//*[@action='select-item']/div/div[contains(text(), '" + partName + "')]")));
    }

    public void saveLaborServiceParts() {
        clickSaveButton();
    }

}
