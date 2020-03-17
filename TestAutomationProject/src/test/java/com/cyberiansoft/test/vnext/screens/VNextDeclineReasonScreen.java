package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
@Getter
public class VNextDeclineReasonScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='services-add']")
	private WebElement approveServicesScreen;

	public VNextDeclineReasonScreen() {
		PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
	}

	public void selectDeclineReason(String declineReason) {
		tap(approveServicesScreen.findElement(By.
				xpath(".//ul/li[@action='select-item']/span[text()='" + declineReason + "']")));
	}


	public String getScreenText () {
		return approveServicesScreen.findElement(By.
				xpath("//div[@class='navbar-panel-inner']")).getText();
	}

}
