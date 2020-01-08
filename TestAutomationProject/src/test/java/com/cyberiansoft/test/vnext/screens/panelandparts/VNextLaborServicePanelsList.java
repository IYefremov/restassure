package com.cyberiansoft.test.vnext.screens.panelandparts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextLaborServicePanelsList extends VNextBasePanelPartsList {

    @FindBy(xpath="//div[@data-page='panels']")
    private WebElement laborservicedetailssscreen;

    @FindBy(xpath="//*[@data-autotests-id='panels-list']")
    private WebElement panelslist;

    public VNextLaborServicePanelsList(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='panels']")));
    }

    public VNextLaborServicePartsList selectServiceLaborPanel(String panelName) {
        tap(panelslist.findElement(By.xpath(".//*[@action='select-item']/div/div[contains(text(), '" + panelName + "')]")));
        return  new VNextLaborServicePartsList(appiumdriver);
    }

    public void clickPartsTab() {
        tap(partstab);
    }



}
