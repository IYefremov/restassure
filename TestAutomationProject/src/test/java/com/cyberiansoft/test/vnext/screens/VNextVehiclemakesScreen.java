package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextVehiclemakesScreen extends VNextBaseScreen {

    @FindBy(xpath="//*[@data-autotests-id='makes-list']")
    private WebElement makeslist;;

    public VNextVehiclemakesScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-page='make']")));
    }

    public VNextVehicleModelsScreen selectVehicleMake(String vehicleMake) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-make='" + vehicleMake + "']")));
        tap(makeslist.findElement(By.xpath(".//*[@data-make='" + vehicleMake + "']")));
        return new VNextVehicleModelsScreen(appiumdriver);
    }

}
