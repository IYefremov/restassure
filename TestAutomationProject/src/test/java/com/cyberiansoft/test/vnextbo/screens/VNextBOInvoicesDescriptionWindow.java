package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Set;

public class VNextBOInvoicesDescriptionWindow extends VNextBOBaseWebPage {

    @FindBy(xpath = "//title[contains(text(), 'ReconPro - Invoice')]")
    private WebElement descriptionWindowTitle;

    public VNextBOInvoicesDescriptionWindow() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public void closeNewWindow(String mainWindow) {
        final Set<String> windowHandles = driver.getWindowHandles();
        for (String window : windowHandles) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                driver.close();
                waitABit(1000);
            }
        }
    }
}
