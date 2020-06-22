package com.cyberiansoft.test.vnextbo.screens.servicerequests.dialogs;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOSRWorkOrdersDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[contains(@class, 'Modal--show')]/div[text()='Work Orders']")
    private WebElement woDialog;

    @FindBy(xpath = "//div[contains(@class, 'Modal--show')]//div[@class='DocumentsDetailsBlock-modal']/div[1]")
    private List<WebElement> woNumbersList;

    public VNextBOSRWorkOrdersDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
