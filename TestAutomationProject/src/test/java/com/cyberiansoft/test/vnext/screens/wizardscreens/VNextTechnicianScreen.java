package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.webelements.TechnicialListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextTechnicianScreen extends VNextBaseScreen {

    @FindBy(xpath = "//*[@data-page='tech-split']")
    private WebElement rootElement;

    @FindBy(xpath = "//*[@data-autotests-id='tech-split-list']//div[@class='entity-item']")
    private List<TechnicialListElement> techList;

    @FindBy(xpath = "//*[@action='save']")
    private WebElement acceptButton;

    @FindBy(xpath = "//*[@action='evenly']")
    private WebElement evenlyButton;

    @FindBy(xpath = "//*[@action='custom']")
    private WebElement customButton;

    @FindBy(xpath = "//*[@action='default']")
    private WebElement defaultButton;

    public VNextTechnicianScreen() {
        PageFactory.initElements(new FiledDecorator(DriverBuilder.getInstance().getAppiumDriver()), this);
    }
}
