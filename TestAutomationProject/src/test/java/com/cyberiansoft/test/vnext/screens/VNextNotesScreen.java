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

import io.appium.java_client.android.AndroidKeyCode;

public class VNextNotesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[contains(@class, 'page orders-note')]")
	private WebElement servicenotessscreen;
	
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
	
	@FindBy(id="notes-pictures")
	private WebElement notespicturesframe;
	
	public VNextNotesScreen(SwipeableWebDriver appiumdriver) {
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
		testReporter.log(LogStatus.INFO, "Add '" + quicknote + "' quick note");
	}
	
	public String getSelectedNotes() {
		return notestextfld.getAttribute("value");
	}
	
	public void setNoteText(String notetext) {
		//notestextfld.clear();
		//notestextfld.sendKeys(notetext);
		notestextfld.click();
		setValue(notestextfld, notetext);
		setValue(notestextfld, "111111");
		System.out.println("++++++++++");
		waitABit(5000);
		testReporter.log(LogStatus.INFO, "Type note text '" + notetext + "'");
	}
	
	public void clickNotesBackButton() {
		tap(notesbackbtn);
		testReporter.log(LogStatus.INFO, "Clack Notes Back button");
	}
	
	public void selectNotesPicturesTab() {
		tap(notespicturestab);
		testReporter.log(LogStatus.INFO, "Select Notes Pictures tab");
	}
	
	public void selectNotesTextTab() {
		tap(notestexttab);
		testReporter.log(LogStatus.INFO, "Select Notes Text tab");
	}
	
	public void clickCameraIcon() {
		tap(notescamerabtn);
		testReporter.log(LogStatus.INFO, "Select Notes Camera icon");
	}
	
	public void addCameraPictureToNote() {
		selectNotesPicturesTab();
		clickCameraIcon();
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		waitABit(8000);
		appiumdriver.pressKeyCode(AndroidKeyCode.KEYCODE_CAMERA);
		waitABit(8000);
		appiumdriver.findElement(By.xpath("//android.widget.ImageView[contains(@resource-id,'btn_done')]")).click();
		waitABit(4000);
		switchApplicationContext(AppContexts.WEB_CONTEXT);
		Assert.assertTrue(isPictureaddedToNote());
		testReporter.log(LogStatus.INFO, "Add Camera picture to Note");
	}

	public boolean isPictureaddedToNote() {
		return notespicturesframe.findElement(By.xpath("./ul/li[@class='picture-item']/div")).isDisplayed();
	}
	
}
