package com.cyberiansoft.test.ios10_client.pageobjects;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularBaseTypeScreen;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularServiceRequestAppointmentScreen extends RegularBaseTypeScreen {

    @iOSXCUITFindBy(accessibility = "From")
    private IOSElement fromfld;

    @iOSXCUITFindBy(accessibility = "To")
    private IOSElement tofld;

    @iOSXCUITFindBy(accessibility = "Done")
    private IOSElement donebtn;

    @iOSXCUITFindBy(accessibility = "Save")
    private IOSElement savebtn;

    public RegularServiceRequestAppointmentScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void waitForAppointmentScreenLoad() {
        FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("Appointment")));
    }

    public void clickAppointmentFromField() {
        waitForAppointmentScreenLoad();
        fromfld.click();
    }

    public void clickAppointmentToField() {
        tofld.click();
    }

    public void clickDatePickerDoneButton() {
        donebtn.click();
    }

    public void clickAppointmentSaveButton() {
        savebtn.click();
    }
}
