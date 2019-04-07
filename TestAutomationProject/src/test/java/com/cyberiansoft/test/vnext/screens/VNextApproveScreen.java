package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class VNextApproveScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-automations-id='approve']")
	private WebElement approcescreen;
	
	@FindBy(id="approve-signature-canvas")
	private WebElement drawcanvas;
	
	@FindBy(xpath="//*[@action='clear']")
	private WebElement clearsignaturebtn;
	
	@FindBy(xpath="//*[@action='save']")
	private WebElement savebtn;
	
	public VNextApproveScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.visibilityOf(approcescreen));
	}
	
	public void clickSignatureCanvas() {
		tap(drawcanvas);
	}
	
	public void clickClearSignatureButton() {
		tap(clearsignaturebtn);
	}
	
	public void drawSignature() {
		BaseUtils.waitABit(5000);
		clickSignatureCanvas();
		int xx = drawcanvas.getLocation().getX();
		
		int yy = drawcanvas.getLocation().getY();
		//TouchActions action = new TouchActions(appiumdriver);
		//action.down(xx + 100,yy + 100).perform();
		
		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);

		new TouchAction(appiumdriver).tap(PointOption.point(appiumdriver.manage().window().getSize().width/2, appiumdriver.manage().window().getSize().height/2)).perform();
		//new TouchAction(appiumdriver).tap(PointOption.point(xx+200, yy+200)).perform();
		
		TouchAction action = new TouchAction(appiumdriver);
		action.press(PointOption.point(xx + 100,yy + 100)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(300))).moveTo(PointOption.point(xx + 200, yy + 200)).release().perform();
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		/**TouchActions action = new TouchActions(appiumdriver);
		try {
		action.down(xx + 100,yy + 100).perform();
		} catch (NullPointerException e) {
			//do nothing
		}*/

		//TouchAction action = new TouchAction(appiumdriver);
		//action.press(xx + 100,yy + 100).waitAction(300).moveTo(xx + 200, yy + 200).release().perform();
		
	}
	
	public void clickSaveButton() {
		tap(savebtn);
	}
	
	public void saveApprovedInspection() {
		clickSaveButton();
	}
	
	public boolean isClearButtonVisible() {
		return clearsignaturebtn.isDisplayed();
	}

	public String getApprovePriceValue() {
		return appiumdriver.findElement(By.xpath("//div[@class='approve-page-placeholder approve-page-row']")).
				findElement(By.xpath(".//div[@class='text-bold approve-total']")).getText();
	}


}
