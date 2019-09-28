package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class PriceMatrixListPage {

    @FindBy(xpath = "//div[@data-page='info']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@data-autotests-id='matrices-list']//li")
    private List<WebElement> elements;

    public PriceMatrixListPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverBuilder.getInstance().getAppiumDriver()), this);
    }
}
