package com.cyberiansoft.test.vnext.screens.customers;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import retrofit2.http.GET;

@Getter
public class VNextCustomersScreen extends VNextBaseCustomersScreen {
	
	@FindBy(xpath="//div[@data-page='customers-list']")
	private WebElement customersscreen;


	
	public VNextCustomersScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);	
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.visibilityOf(customersscreen));
		BaseUtils.waitABit(1500);
		if (checkHelpPopupPresence())		
			tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']"));
		if (elementExists("//*[@data-autotests-id='search-cancel']"))
			if (cancelsearchbtn.isDisplayed())
				tap(cancelsearchbtn);
	}

	public VNextCustomersScreen() {
	}

	public VNextNewCustomerScreen clickAddCustomerButton() {
		tap(customersscreen.findElement(By.xpath(".//a[@class='floating-button color-red']")));
		tap(customersscreen.findElement(By.xpath(".//*[@action='add_customer' and @class='customers-button']")));
		//tap(customersscreen.findElement(By.xpath(".//*[@action='add' and @class='customers-button']")));
		return new VNextNewCustomerScreen(appiumdriver);
	}

	public boolean isAddCustomerButtonDisplayed() {
		return customersscreen.findElements(By.xpath(".//*[@action='add']")).size() > 0;
	}

	public boolean isNothingFoundCaptionDisplayed() {
		return customersscreen.findElement(By.xpath(".//b[text()='Nothing found']")).isDisplayed();
	}
}
