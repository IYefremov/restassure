package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextVehiclemakesScreen extends VNextBaseScreen {

    @FindBy(xpath="//*[@data-autotests-id='makes-model-list']")
    private WebElement makeslist;

    public VNextVehiclemakesScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public void selectVehicleMake(String vehicleMake) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-make='" + vehicleMake + "']")));
        tap(makeslist.findElement(By.xpath(".//*[@data-make='" + vehicleMake + "']")));
    }

}
