package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class VNextRegistrationPersonalInfoScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//select[@data-template='personal-info-country-phone-code-template']")
	private WebElement phonenumberselect;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.firstName')]")
	private WebElement firstnamefld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.lastName')]")
	private WebElement lastnamefld;
	
	@FindBy(id="personal-info-phone")
	private WebElement phonenumberfld;
	
	@FindBy(xpath="//input[@type='email']")
	private WebElement emailfld;
	
	@FindBy(xpath="//*[@data-automation-id='next']")
	private WebElement donebtn;
	
	@FindBy(xpath="//button[@class='btn btn-red']")
	private WebElement clearuserbtn;
	
	@FindBy(xpath="//*[@data-automation-id='register-with-code']")
	private WebElement ihaveregcodelink;
	
	public VNextRegistrationPersonalInfoScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		//VNextRegistrationPersonalInfoScreen.WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		//wait.until(ExpectedConditions. visibilityOf(phonenumberselect));
	}
	
	public void setUserRegistrationInfoAndSend(String firstname, String lastname, String countrycode, String phonenumber, String usermail) {
		setUserRegistrationInfo(firstname, lastname, countrycode, phonenumber, usermail);
		clickDoneButton();
	}
	
	public void setUserRegistrationInfo(String firstname, String lastname, String countrycode, String phonenumber, String usermail) {
		setFirstName(firstname);
		setLastName(lastname);
		selectPhoneNumberCountryCode(countrycode);
		setPhoneNumber(phonenumber);
		setEmail(usermail);
		//switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		//appiumdriver.hideKeyboard();
	    //switchToWebViewContext();
	}
	
	public void selectPhoneNumberCountryCode(String countrycode) {
		final String contrycodeXPath = "//li/a/span[contains(text(), '(+" + countrycode + ")')]";
		tap(appiumdriver.findElement(By.xpath("//*[@data-name='phone']/i")));
		if (!appiumdriver.findElement(By.xpath(contrycodeXPath)).isDisplayed()) {
			/*switchApplicationContext(AppContexts.NATIVE_CONTEXT);
			new TouchAction(appiumdriver).longPress(point(appiumdriver.manage().window().getSize().getWidth()-230,appiumdriver.manage().window().getSize().getHeight()-330))
			.moveTo(point(appiumdriver.manage().window().getSize().getWidth()-630,appiumdriver.manage().window().getSize().getHeight()-830)).release().perform();
			switchToWebViewContext();
			WebElement elem = appiumdriver.findElement(By.xpath("//li/a/span[contains(text(), '(+" + countrycode + ")')]"));	
			JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
			je.executeScript("arguments[0].scrollIntoView(true);",elem);*/
			String datauid = appiumdriver.findElement(By.xpath("//li/a/span[contains(text(), '(+" + countrycode + "')]/../..")).getAttribute("data-uid");
			if (appiumdriver instanceof JavascriptExecutor)
				try {
					((JavascriptExecutor)appiumdriver).executeScript("$('body').trigger('scrollto', $('[data-uid="+ datauid + "]'))");
				} catch (WebDriverException e) {
			    	//for some reason JS code is crashed but scrolled
			    }
		}
			
		tap(appiumdriver.findElement(By.xpath(contrycodeXPath)));
	}
	
	public void setFirstName(String firstname) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions. visibilityOf(firstnamefld));
		tap(firstnamefld);
		appiumdriver.getKeyboard().pressKey(firstname);
		//firstnamefld.clear();
		//firstnamefld.sendKeys(firstname);
	}
	
	public void setLastName(String lastname) {
		tap(lastnamefld);
		appiumdriver.getKeyboard().pressKey(lastname);
		//lastnamefld.clear();
		//lastnamefld.sendKeys(lastname);
	}
	
	public void setPhoneNumber(String phonenumber) {
		BaseUtils.waitABit(500);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("personal-info-phone")));
		tap(phonenumberfld);
		appiumdriver.getKeyboard().pressKey(phonenumber);
		appiumdriver.hideKeyboard();
		//phonenumberfld.clear();
		//phonenumberfld.sendKeys(phonenumber);
	}
	
	public void setEmail(String usermail) {
		tap(emailfld);
		emailfld.clear();
		appiumdriver.getKeyboard().pressKey(usermail);
		appiumdriver.hideKeyboard();
		//emailfld.clear();
		//emailfld.sendKeys(usermail);
	}
	
	public void clickDoneButton() {
		tap(donebtn);
		BaseUtils.waitABit(5000);
	}
	
	public void clickClearUserButton() {
		tap(clearuserbtn);
	}
	
	public void clickIHaveRigistrationCodeLink() {
		tap(ihaveregcodelink);
	}
}
