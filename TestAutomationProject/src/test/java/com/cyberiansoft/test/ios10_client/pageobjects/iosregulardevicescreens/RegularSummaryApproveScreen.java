package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class RegularSummaryApproveScreen extends iOSRegularBaseScreen {

    @iOSXCUITFindBy(accessibility ="Approve")
    private IOSElement approvebtn;

    public RegularSummaryApproveScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void clickApproveButton() {
        WaitUtils.elementShouldBeVisible(approvebtn, true);
        approvebtn.click();
    }


}
