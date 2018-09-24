package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.dataclasses.LaborServiceData;
import com.cyberiansoft.test.vnext.screens.panelandparts.VNextLaborServicePanelsList;
import com.cyberiansoft.test.vnext.screens.panelandparts.VNextLaborServicePartsList;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextLaborServiceDetailsScreen extends VNextBaseScreen {

    @FindBy(xpath="//div[@data-page='details']")
    private WebElement laborservicedetailssscreen;

    @FindBy(xpath="//input[@data-name='Amaunt']")
    private WebElement laborrate;

    @FindBy(xpath="//input[@data-name='QuantityFloat']")
    private WebElement labortime;

    @FindBy(xpath="//div[@action='select-panel']")
    private WebElement selectpanelsandpartspanel;

    @FindBy(xpath="//span[@action='save']")
    private WebElement savebtn;

    public VNextLaborServiceDetailsScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='details']")));
        wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-page='details']")));
    }

    public VNextLaborServiceDetailsScreen selectPanelAndPart(String panelName, String partName) {
        VNextLaborServicePanelsList laborServicePanelsList = clickSelectPanelCell();
        VNextLaborServicePartsList laborServicePartsList = laborServicePanelsList.selectServiceLaborPanel(panelName);
        laborServicePartsList.selectServiceLaborPart(partName);
        return laborServicePartsList.saveLaborServiceParts();
    }

    public  VNextLaborServicePanelsList clickSelectPanelCell() {
        tap(selectpanelsandpartspanel);
        return new VNextLaborServicePanelsList(appiumdriver);
    }

    public void saveLaborServiceDetails() {
        tap(savebtn);
    }

    public VNextAvailableServicesScreen clickBackButton(){
        clickScreenBackButton();
        return new VNextAvailableServicesScreen(appiumdriver);
    }

    public VNextLaborServicePartsList clickSelectPanelsAndPartsForLaborService(LaborServiceData laborService) {

        tap(appiumdriver.findElement(By.xpath("//div[@action='select-panel']")));
        return new VNextLaborServicePartsList(appiumdriver);
    }

    public String getLaborServiceRate(LaborServiceData laborService) {
        return appiumdriver.findElement(By.xpath(".//input[@data-name='Amount']")).getAttribute("value").trim();
    }

    public String getLaborServiceTime(LaborServiceData laborService) {
        return appiumdriver.findElement(By.xpath(".//input[@data-name='QuantityFloat']")).getAttribute("value").trim();
    }

    public String getLaborServiceNotes(LaborServiceData laborService) {
        return appiumdriver.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).getText().trim();
    }
}
