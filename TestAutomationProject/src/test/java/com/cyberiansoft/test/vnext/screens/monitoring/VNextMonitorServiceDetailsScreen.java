package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextMonitorServiceDetailsScreen {

    @FindBy(xpath = "//*[@action='edit-tech-split']/input")
    private WebElement TechnicianFld;

    @FindBy(xpath = "//*[@action='select-status']/input")
    private WebElement selectStatusFld;

    @FindBy(xpath = "//*[@data-action-name='completeService']")
    private WebElement completeService;

    @FindBy(xpath = "//*[@data-action-name='startService']")
    private WebElement startService;

    public VNextMonitorServiceDetailsScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }
}
