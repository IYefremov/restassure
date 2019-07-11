package com.cyberiansoft.test.vnext.screens.wizardscreens;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class VNextVehicleInfoScreen extends VNextBaseWizardScreen {

    @FindBy(xpath = "//*[@data-page='info']")
    private WebElement rootElement;

    @FindBy(xpath = "//*[contains(@data-autotests-id, '-vehicle-info')]")
    private WebElement vehicleFieldsList;

    @FindBy(xpath = "//input[@name]")
    private List<WebElement> dataFieldList;

    @FindBy(xpath = "//*[@action='select-make']")
    private WebElement makeSectionExpandButton;

    @FindBy(xpath = "//*[@action='select-color']")
    private WebElement colorSectionExpandButton;

    @FindBy(name = "Vehicle.Year")
    private WebElement yearField;

    @FindBy(xpath = "//*[@action='select-owner']")
    private WebElement selectOwnerButton;

    @FindBy(xpath = "//span[@class='client-mode']")
    private WebElement customerContextField;

    @FindBy(id = "vehicleInfoOwner")
    private WebElement ownderField;
}
