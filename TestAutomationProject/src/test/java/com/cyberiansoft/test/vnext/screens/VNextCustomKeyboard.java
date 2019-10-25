package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextCustomKeyboard extends VNextBaseScreen {

    @FindBy(xpath = "//*[@data-autotests-id='keypad'][3]")
	private WebElement keyboard;
	
	public VNextCustomKeyboard(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(keyboard));
	}
	
	public void clickKeyboardBackspaceButton() {
		tap(keyboard.findElement(By.xpath(".//span[contains(@class, 'picker-keypad-delete')]")));		
	}
	
	public void clickKeyboardButton(char button) {
		tap(keyboard.findElement(By.xpath("./div[@class='picker-modal-inner picker-keypad-buttons']/span/span[text()='" + button + "']")));
	}
	
	public void clickKeyboardDoneButton() {
		tap(keyboard.findElement(By.xpath(".//*[@class='link close-picker']")));
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='keypad'][3]"));
	}
	
	public void clickKeyboardMinusButton() {
		tap(keyboard.findElement(By.xpath("./div[@class='picker-modal-inner picker-keypad-buttons']/span/span[text()='-/+']")));
	}
	
	public void typeValue(String newvalue) {
		for (int i = 0; i < newvalue.length(); i++) {
			if (Character.toString(newvalue.charAt(i)).equals("-"))
				clickKeyboardMinusButton();
			else
				clickKeyboardButton(newvalue.charAt(i));
		}
	}
	
	public void setFieldValue(String existingvalue, String newvalue) {
		clearFieldValue(existingvalue);
		typeValue(newvalue);
		clickKeyboardDoneButton();
	}

	public void clearFieldValue(String existingvalue) {
		for (int i = 0; i <= existingvalue.length()+1; i++) {
			clickKeyboardBackspaceButton();
		}
	}

}
