package com.cyberiansoft.test.vnextbo.screens.repairorders;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOROResolveProblemDialog extends VNextBOBaseWebPage {

    @FindBy(id = "resolve-problem-popup")
    private WebElement resolveProblemDialog;

    @FindBy(xpath = "//button[contains(@data-bind, 'resolveProblemAction')]")
    private WebElement resolveProblemButton;

    @FindBy(xpath = "//div[@id='resolve-problem-popup']//button[@class='close']")
    private WebElement resolveProblemCloseButton;

    public VNextBOROResolveProblemDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}