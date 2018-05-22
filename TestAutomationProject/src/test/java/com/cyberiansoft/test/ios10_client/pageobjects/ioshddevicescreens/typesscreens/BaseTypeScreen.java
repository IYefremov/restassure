package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.BaseAppScreen;
import io.appium.java_client.AppiumDriver;

public abstract class BaseTypeScreen extends BaseAppScreen implements ITypeScreen {

    public BaseTypeScreen(AppiumDriver driver) {
        super(driver);
        //PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        //appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}
