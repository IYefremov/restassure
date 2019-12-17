package com.cyberiansoft.test.vnextbo.screens.clients.clientdetails;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOPreferencesBlock extends VNextBOClientsDetailsViewAccordion {

    @FindBy(xpath = "//div[@id='client-details-view-headingPreferences']/div/div")
    private WebElement preferencesPanel;

    @FindBy(id = "vehicleSingleWOType")
    private WebElement useSingleWoTypeCheckbox;

    @FindBy(id = "vehicleHistoryEnforced")
    private WebElement vehicleHistoryEnforcedCheckbox;

    @FindBy(xpath = "//span[@aria-owns='clientEditForm-client-default-area_listbox']//span[contains(@class, 'k-input')]")
    private WebElement defaultAreaField;

    @FindBy(xpath = "//span[@aria-owns='clientEditForm-client-default-area_listbox']//span[contains(@class, 'k-icon')]")
    private WebElement defaultAreaArrow;

    @FindBy(id = "clientEditForm-client-default-area-list")
    private WebElement defaultAreaDropDown;

    @FindBy(xpath = "//ul[@id='clientEditForm-client-default-area_listbox']/li")
    private List<WebElement> defaultAreaListBoxOptions;

    public WebElement getDefaultAreaListBoxOptionByText(String option) {

        return driver.findElement(By.xpath("//ul[@id='clientEditForm-client-default-area_listbox']/li[text()='"+
                option + "']"));
    }

    public VNextBOPreferencesBlock() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}