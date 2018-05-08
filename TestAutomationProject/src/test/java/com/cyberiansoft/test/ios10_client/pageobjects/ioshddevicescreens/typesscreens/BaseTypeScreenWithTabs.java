package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class BaseTypeScreenWithTabs extends BaseTypeScreen {

    public BaseTypeScreenWithTabs(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}
