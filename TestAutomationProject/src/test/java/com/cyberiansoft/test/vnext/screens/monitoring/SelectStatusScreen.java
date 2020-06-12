package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class SelectStatusScreen extends MonitorScreen {

    @FindBy(xpath = "//div[@class='pages']/div[@data-page='change-status']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@data-name='Active']")
    private WebElement activeStatus;

    public SelectStatusScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }
}
