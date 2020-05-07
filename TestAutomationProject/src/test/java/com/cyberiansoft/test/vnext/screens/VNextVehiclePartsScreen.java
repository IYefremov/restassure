package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.PriceMatrixScreenInteractions;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextVehiclePartsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='parts']")
	private WebElement vehiclepartsscreen;

	@FindBy(xpath="//*[@data-autotests-id='matrix-parts-list']")
	private WebElement matrixpartslist;

    public VNextVehiclePartsScreen(WebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(appiumdriver, this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(vehiclepartsscreen));
        //HelpingScreenInteractions.dismissHelpingScreenIfPresent();
	}

	public VNextVehiclePartsScreen() {
	}
	
	public VNextVehiclePartInfoPage selectVehiclePart(String vehiclePartName) {
    	WaitUtils.waitUntilElementIsClickable(vehiclepartsscreen);
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		PriceMatrixScreenInteractions.selectItem(vehiclePartName);
		return new VNextVehiclePartInfoPage(appiumdriver);
	}
	
	public String getVehiclePartsScrenPriceValue() {
		WaitUtils.elementShouldBeVisible(vehiclepartsscreen, true);
		return vehiclepartsscreen.findElement(By.xpath(".//*[@class='money-wrapper']")).getText().trim();
	}

	public VNextAvailableServicesScreen clickVehiclePartsSaveButton() {
		tap(vehiclepartsscreen.findElement(By.xpath(".//*[@action='save']")));
		return new VNextAvailableServicesScreen();
	}
}
 