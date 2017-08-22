package com.cyberiansoft.test.vnext.screens;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileBy;

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
	
	public VNextNotesScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='notes']")));
	}
	
	public List<WebElement> getListOfQuickNotes() {
		return quicknotescontent.findElements(By.xpath(".//div[@class='list-block']/div[@action='quick-note']"));
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
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		waitABit(8000);
		//appiumdriver.pressKeyCode(AndroidKeyCode.KEYCODE_CAMERA);
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
	
	public void addFakeImageNote() {
		if (appiumdriver instanceof JavascriptExecutor)
		    ((JavascriptExecutor)appiumdriver).executeScript("$('[action=take-camera]').trigger('tap:fake')");
	}
	
	public void addImageToNotesFromGallery() {
		//clickGalleryIcon();
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		waitABit(3000);
		if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.Button' and @text='Allow']")).size() > 0)
			appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.Button' and @text='Allow']")).click();
		else if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.Button' and @text='ALLOW']")).size() > 0)
			appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.Button' and @text='ALLOW']")).click();
		
		waitABit(1000);
		if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.Button' and @text='Allow']")).size() > 0)
			appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.Button' and @text='Allow']")).click();
		else if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.Button' and @text='ALLOW']")).size() > 0)
			appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.Button' and @text='ALLOW']")).click();
		waitABit(4000);
		/*if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.TextView' and @text='Recent']")).size() > 0)
			appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.TextView' and @text='Recent']")).click();
		//else if (appiumdriver.findElements(MobileBy.xpath("//*[@class='android.widget.TextView' and @text='RECENT']")).size() > 0)
		//	appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.TextView' and @text='RECENT']")).click();
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		//wait.until(ExpectedConditions.elementToBeClickable(MobileBy.xpath("//*[@class='android.widget.TextView' and @text='Recent']"))).click();
		waitABit(1000);
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		//wait.until(ExpectedConditions.elementToBeClickable(MobileBy.xpath("//android.widget.GridView/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ImageView"))).click();
		
		if (appiumdriver.findElements(MobileBy.xpath("//android.widget.GridView/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ImageView")).size() > 0) {
			new TouchAction(appiumdriver).press(appiumdriver.findElement(MobileBy.xpath("//android.widget.GridView/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ImageView"))).waitAction(500).release().perform();
			System.out.println("+++++" + appiumdriver.findElements(MobileBy.xpath("//android.widget.GridView/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ImageView")).size());
			appiumdriver.findElement(MobileBy.xpath("//android.widget.GridView/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ImageView")).click();
			
		
		}
		else if (appiumdriver.findElements(MobileBy.xpath("//android.support.v7.widget.RecyclerView/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.ImageView")).size() > 0) {
			List<WebElement> imgs = appiumdriver.findElements(MobileBy.xpath("//android.support.v7.widget.RecyclerView/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.ImageView"));
			for (WebElement im : imgs)
				new TouchAction(appiumdriver).press(im).waitAction(500).release().perform();
			System.out.println("+++++" + appiumdriver.findElements(MobileBy.xpath("//android.support.v7.widget.RecyclerView/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.ImageView")).size());
			appiumdriver.findElement(MobileBy.xpath("//android.support.v7.widget.RecyclerView/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.ImageView")).click();
			
		}
		waitABit(3000);
		*/

		appiumdriver.findElement(MobileBy.xpath("//*[@class='GLButton' and @text='Shutter']")).click();
		waitABit(10000);
		appiumdriver.findElement(MobileBy.xpath("//*[@class='android.widget.TextView' and @text='OK']")).click();
		
		switchToWebViewContext();
		waitABit(3000);
	}
	
	public int getNumberOfAddedNotesPictures() {
		if (!(servicenotessscreen.findElements(By.xpath(".//div[@class='accordion-item accordion-item-pictures accordion-item-expanded']")).size() > 0))
			tap(servicenotessscreen.findElement(By.xpath(".//div[@class='accordion-item accordion-item-pictures']")));
		return servicenotessscreen.findElement(By.xpath(".//div[@class='images-row']")).findElements(By.xpath(".//div[@class='img-item' and @action='fullscreen']")).size();
	}
	
}
