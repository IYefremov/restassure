package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextWorkOrderSummaryScreen extends VNextBaseWizardScreen {

    @FindBy(xpath = "//div[@data-page='summary']")
    private WebElement woSummaryScreen;

    @FindBy(xpath = "//*[@action='auto-invoice']")
    private WebElement autoInvoiceCreateOption;

    public VNextWorkOrderSummaryScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

}
