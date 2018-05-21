package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextLaborServicePanelsList extends VNextBaseInspectionsScreen {

    @FindBy(xpath="//div[@data-page='panels']")
    private WebElement laborservicedetailssscreen;

    @FindBy(xpath="//*[@data-autotests-id='panels-list']")
    private WebElement panelslist;

    public VNextLaborServicePanelsList(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='panels']")));
    }

    public VNextLaborServicePartsList selectServiceLaborPanel(String panelName) {
        tap(panelslist.findElement(By.xpath(".//*[@action='select-item']/span[text()='" + panelName + "']")));
        return  new VNextLaborServicePartsList(appiumdriver);
    }
}
