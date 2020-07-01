package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextVehicleModelsScreen extends VNextBaseScreen {

    @FindBy(xpath="//*[@data-autotests-id='makes-model-list']")
    private WebElement modelslist;

    public VNextVehicleModelsScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public VNextVehicleInfoScreen selectVehicleModel(String vehicleModel) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-model='" + vehicleModel + "']")));
        tap(modelslist.findElement(By.xpath(".//*[@data-model='" + vehicleModel + "']")));
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        //HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        return vehicleInfoScreen;
    }
}
