package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.webelements.Button;
import com.cyberiansoft.test.vnext.webelements.VehicleInfoListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextVehicleInfoScreen extends VNextBaseWizardScreen {

    @FindBy(xpath = "//*[@data-page='info']")
    private WebElement rootElement;

    @FindBy(xpath = "//*[contains(@data-autotests-id, '-vehicle-info')]")
    private WebElement vehicleFieldsList;

    @FindBy(xpath = "//input[contains(@class, 'formfield-item-input')]")
    private List<VehicleInfoListElement> dataFieldList;

    @FindBy(xpath = "//*[@action='select-make']")
    private Button makeSectionExpandButton;

    @FindBy(xpath = "//*[@action='select-color']")
    private Button colorSectionExpandButton;

    @FindBy(xpath = "//*[@action='paint-codes-mode']")
    private WebElement paintCodesModeTab;

    @FindBy(name = "Vehicle.Year")
    private WebElement yearField;

    @FindBy(xpath = "//*[@action='select-owner']")
    private WebElement selectOwnerButton;

    @FindBy(xpath = "//span[@class='client-mode']")
    private WebElement customerContextField;

    @FindBy(id = "vehicleInfoOwner")
    private WebElement ownderField;

    public VNextVehicleInfoScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }
}
