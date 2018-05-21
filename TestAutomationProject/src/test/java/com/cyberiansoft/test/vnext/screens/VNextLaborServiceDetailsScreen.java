package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextLaborServiceDetailsScreen extends VNextBaseInspectionsScreen {

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
    }

    public void selectPanelAndPart(String panelName, String partName) {
        VNextLaborServicePanelsList laborServicePanelsList = clickSelectPanelCell();
        VNextLaborServicePartsList laborServicePartsList = laborServicePanelsList.selectServiceLaborPanel(panelName);
        laborServicePartsList.selectServiceLaborPart(partName);
    }

    public  VNextLaborServicePanelsList clickSelectPanelCell() {
        tap(selectpanelsandpartspanel);
        return new VNextLaborServicePanelsList(appiumdriver);
    }

    public VNextInspectionServicesScreen saveLaborServiceDetails() {
        tap(savebtn);
        return new VNextInspectionServicesScreen(appiumdriver);
    }
}
