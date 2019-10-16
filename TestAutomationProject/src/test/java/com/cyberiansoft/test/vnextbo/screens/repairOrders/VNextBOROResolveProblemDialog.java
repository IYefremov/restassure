package com.cyberiansoft.test.vnextbo.screens.repairOrders;

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

    public VNextBOROResolveProblemDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}