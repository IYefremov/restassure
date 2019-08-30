package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class RegularEmailScreen extends iOSRegularBaseScreen {

    @iOSXCUITFindBy(accessibility = "Add")
    private IOSElement addMailBtn;

    @iOSXCUITFindBy(accessibility = "Send")
    private IOSElement sendbtn;

    @iOSXCUITFindBy(accessibility = "Single Email")
    private IOSElement singlemailbtn;

    public RegularEmailScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void clickAddMailButton() {
        WaitUtils.waitUntilElementIsClickable(addMailBtn);
        addMailBtn.click();
    }

    public void enterEmailAddress(String eMail) {
        IOSElement invoicetable = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert");
        IOSElement mailfld = ((IOSElement) invoicetable.findElementByClassName("XCUIElementTypeTextField"));
        mailfld.sendKeys(eMail);
        DriverBuilder.getInstance().getAppiumDriver().switchTo().alert().accept();
    }

    public void clickSendButton() {
        sendbtn.click();
    }

    public void clickSingleMailButton() {
        singlemailbtn.click();
    }
}
