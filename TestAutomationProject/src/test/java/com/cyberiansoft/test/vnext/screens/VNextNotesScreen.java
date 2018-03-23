package com.cyberiansoft.test.vnext.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobilePlatform;

public class VNextNotesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='notes']")
	private WebElement servicenotessscreen;
	
	@FindBy(xpath="//span[@action='clear']")
	private WebElement clearnotesbtn;
	
	@FindBy(xpath="//textarea[@name='notes']")
	private WebElement notestextfld;
	
	@FindBy(xpath="//div[contains(@class, 'accordion-item accordion-item-quick-notes')]")
	private WebElement quicknotescontent;
	
	@FindBy(xpath="//a[@action='select-text']")
	private WebElement notestexttab;
	
	@FindBy(xpath="//div[@action='take-lib']")
	private WebElement notespicturestab;
	
	@FindBy(xpath="//*[@action='take-camera']")
	private WebElement notescamerabtn;
	
	@FindBy(xpath="//*[@action='take-lib']")
	private WebElement notesgallerybtn;
	
	@FindBy(id="notes-pictures")
	private WebElement notespicturesframe;
	
	public VNextNotesScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='notes']")));
		BaseUtils.waitABit(1000);
	}
	
	public List<WebElement> getListOfQuickNotes() {
		return quicknotescontent.findElements(By.xpath(".//div[@action='quick-note']"));
	}
	
	public void addQuickNote(String quicknote) {
		if (!quicknotescontent.getAttribute("class").contains("accordion-item-expanded"))
			tap(quicknotescontent);
		tap(quicknotescontent.findElement(By.xpath(".//div[@action='quick-note' and contains(text(), '" + quicknote + "')]")));
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
		clickScreenBackButton();
		log(LogStatus.INFO, "Clack Notes screen Back button");
	}
	
	public void selectNotesPicturesTab() {
		//tap(notespicturestab);
		tap(notescamerabtn);
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
		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		BaseUtils.waitABit(8000);
		//appiumdriver.pressKeyCode(AndroidKeyCode.KEYCODE_CAMERA);
		BaseUtils.waitABit(8000);
		if (appiumdriver.findElements(By.xpath("//android.widget.ImageView[contains(@resource-id,'btn_done')]")).size() > 0)
			appiumdriver.findElement(By.xpath("//android.widget.ImageView[contains(@resource-id,'btn_done')]")).click();
		else
			appiumdriver.findElement(By.xpath("//android.widget.ImageView[contains(@resource-id,'ok')]")).click();
		BaseUtils.waitABit(4000);
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		Assert.assertTrue(isPictureaddedToNote());
		log(LogStatus.INFO, "Add Camera picture to Note");
	}

	public boolean isPictureaddedToNote() {
		return notespicturesframe.findElement(By.xpath("./ul/li[@class='picture-item']/div")).isDisplayed();
	}
	
	public void addFakeImageNote() {
		if (appiumdriver instanceof JavascriptExecutor)
		    ((JavascriptExecutor)appiumdriver).executeScript("$('[action=take-camera]').trigger('tap:fake')");
	}
	
	public void addImageToNotesFromGallery() {
		if (DriverBuilder.getInstance().getMobilePlatform().equals(MobilePlatform.ANDROID)) {
			AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
			BaseUtils.waitABit(3000);
			if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.Button' and @text='Allow']")).size() > 0)
				appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.Button' and @text='Allow']")).click();
			else if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.Button' and @text='ALLOW']")).size() > 0)
				appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.Button' and @text='ALLOW']")).click();
		
			BaseUtils.waitABit(1000);
			if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.Button' and @text='Allow']")).size() > 0)
				appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.Button' and @text='Allow']")).click();
			else if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.Button' and @text='ALLOW']")).size() > 0)
				appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.Button' and @text='ALLOW']")).click();
			BaseUtils.waitABit(4000);

			appiumdriver.findElement(MobileBy.xpath("//*[@class='GLButton' and @text='Shutter']")).click();
			BaseUtils.waitABit(10000);
			appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.TextView' and @text='OK']")).click();
			
			AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
			BaseUtils.waitABit(3000);
		} else {
			addFakeImageNote();
			BaseUtils.waitABit(3000);
		}
	}
	
	public int getNumberOfAddedNotesPictures() {
		if (!(servicenotessscreen.findElements(By.xpath(".//div[@class='accordion-item accordion-item-pictures accordion-item-expanded']")).size() > 0))
			tap(servicenotessscreen.findElement(By.xpath(".//div[@class='accordion-item accordion-item-pictures']")));
		return servicenotessscreen.findElement(By.xpath(".//div[@class='images-row']")).findElements(By.xpath(".//div[contains(@class, 'img-item') and @action='fullscreen']")).size();
	}
	
	public void deletePictureFromNotes() {
		if (!(servicenotessscreen.findElements(By.xpath(".//div[@class='accordion-item accordion-item-pictures accordion-item-expanded']")).size() > 0))
			tap(servicenotessscreen.findElement(By.xpath(".//div[@class='accordion-item accordion-item-pictures']")));
		tap(servicenotessscreen.findElement(By.xpath(".//div[@class='images-row']")).findElement(By.xpath(".//div[contains(@class, 'img-item') and @action='fullscreen']")));
		tap(servicenotessscreen.findElement(By.xpath(".//*[@action='remove']")));
		VNextInformationDialog informationdialog = new VNextInformationDialog(appiumdriver);
		informationdialog.clickInformationDialogRemoveButton();
	}
	
	public ArrayList<String> addNumberOfQuickNotes(int quickNotesNumber) {
		ArrayList<String> addednotes = new ArrayList<String>();
		for (int i = 0; i < quickNotesNumber; i++) {
			if (!quicknotescontent.getAttribute("class").contains("accordion-item-expanded"))
				tap(quicknotescontent);
			appiumdriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			if (servicenotessscreen.findElements(By.xpath("//*[@action='show-more']")).size() > 0)
				tap(servicenotessscreen.findElement(By.xpath("//*[@action='show-more']")));
			appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			List<WebElement> quicknotes = servicenotessscreen.findElements(By.xpath("//*[@action='quick-note']"));
			if (i < quicknotes.size()) {
				JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
				je.executeScript("arguments[0].scrollIntoView(true);",quicknotes.get(i));
				tap(quicknotes.get(i));
				addednotes.add(quicknotes.get(i).getText().trim());
			}
		}
		return addednotes;
	}
	
}

