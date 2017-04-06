package com.cyberiansoft.test.vnext.screens;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

public class VNextNotesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='notes']")
	private WebElement servicenotessscreen;
	
	@FindBy(xpath="//a[@action='clear']")
	private WebElement clearnotesbtn;
	
	@FindBy(xpath="//textarea[@name='notes']")
	private WebElement notestextfld;
	
	@FindBy(name="quick_notes")
	private WebElement quicknotescontent;
	
	@FindBy(xpath="//a[@action='save']/i")
	private WebElement notesbackbtn;
	
	@FindBy(xpath="//a[@action='select-text']")
	private WebElement notestexttab;
	
	@FindBy(xpath="//a[@action='select-pictures']")
	private WebElement notespicturestab;
	
	@FindBy(xpath="//i[@action='take-camera']")
	private WebElement notescamerabtn;
	
	@FindBy(xpath="//i[@action='take-lib']")
	private WebElement notesgallerybtn;
	
	@FindBy(id="notes-pictures")
	private WebElement notespicturesframe;
	
	public VNextNotesScreen(AppiumDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(quicknotescontent));
	}
	
	public List<WebElement> getListOfQuickNotes() {
		return quicknotescontent.findElements(By.xpath(".//ul/li[@action='quick-note']"));
	}
	
	public void addQuickNote(String quicknote) {
		tap(quicknotescontent.findElement(By.xpath(".//ul/li[@action='quick-note']/div/div[text()='" + quicknote + "']")));
		log(LogStatus.INFO, "Add '" + quicknote + "' quick note");
	}
	
	public String getSelectedNotes() {
		return notestextfld.getAttribute("value");
	}
	
	public void setNoteText(String notetext) {
		//notestextfld.clear();
		//notestextfld.sendKeys(notetext);
		notestextfld.click();
		setValue(notestextfld, notetext);
		log(LogStatus.INFO, "Type note text '" + notetext + "'");
	}
	
	public void clickNotesBackButton() {
		tap(notesbackbtn);
		log(LogStatus.INFO, "Clack Notes Back button");
	}
	
	public void selectNotesPicturesTab() {
		tap(notespicturestab);
		log(LogStatus.INFO, "Select Notes Pictures tab");
	}
	
	public void selectNotesTextTab() {
		tap(notestexttab);
		log(LogStatus.INFO, "Select Notes Text tab");
	}
	
	public void clickCameraIcon() {
		tap(notescamerabtn);
		log(LogStatus.INFO, "Select Notes Camera icon");
	}
	
	public void clickGalleryIcon() {
		tap(notesgallerybtn);
		log(LogStatus.INFO, "Select Notes Camera icon");
	}
	
	public void clickClearNotesButton() {
		tap(clearnotesbtn);
		log(LogStatus.INFO, "Tap Clear Notes button");
	}
	
	public void addCameraPictureToNote() {
		selectNotesPicturesTab();
		clickCameraIcon();
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		waitABit(8000);
		((AndroidDriver) appiumdriver).pressKeyCode(AndroidKeyCode.KEYCODE_CAMERA);
		waitABit(8000);
		if (appiumdriver.findElements(By.xpath("//android.widget.ImageView[contains(@resource-id,'btn_done')]")).size() > 0)
			appiumdriver.findElement(By.xpath("//android.widget.ImageView[contains(@resource-id,'btn_done')]")).click();
		else
			appiumdriver.findElement(By.xpath("//android.widget.ImageView[contains(@resource-id,'ok')]")).click();
		waitABit(4000);
		switchApplicationContext(AppContexts.WEB_CONTEXT);
		Assert.assertTrue(isPictureaddedToNote());
		log(LogStatus.INFO, "Add Camera picture to Note");
	}

	public boolean isPictureaddedToNote() {
		return notespicturesframe.findElement(By.xpath("./ul/li[@class='picture-item']/div")).isDisplayed();
	}
	
	public void addImageToNotesFromGallery() {
		clickGalleryIcon();
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		waitABit(1000);
		if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.Button' and @text='Allow']")).size() > 0)
			appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.Button' and @text='Allow']")).click();
		waitABit(4000);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.xpath("//*[@class='android.widget.TextView' and @text='Recent']"))).click();
		waitABit(2000);
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.xpath("//android.widget.GridView/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ImageView"))).click();
		
		waitABit(3000);
		switchToWebViewContext();
		waitABit(3000);
	}
	
	public int getNumberOfAddedNotesPictures() {
		return servicenotessscreen.findElement(By.id("notes-pictures")).findElements(By.xpath(".//li[@class='picture-item']")).size();
	}
	
}
