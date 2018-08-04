package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextVehicleModelsScreen extends VNextBaseScreen {

    @FindBy(xpath="//*[@data-autotests-id='models-list']")
    private WebElement modelslist;

    public VNextVehicleModelsScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-page='model']")));
    }

    public VNextVehicleInfoScreen selectVehicleModel(String vehicleModel) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-model='" + vehicleModel + "']")));
        tap(modelslist.findElement(By.xpath(".//*[@data-model='" + vehicleModel + "']")));
        return new VNextVehicleInfoScreen(appiumdriver);
    }
}