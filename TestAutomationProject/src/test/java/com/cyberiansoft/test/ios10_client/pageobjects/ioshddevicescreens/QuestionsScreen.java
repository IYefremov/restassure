package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class QuestionsScreen extends iOSHDBaseScreen {
	
	public QuestionsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void acceptForReminderNoDrilling() {
		Assert.assertTrue (appiumdriver.findElementByName("REMINDER NO DRILLING AND USE E-COAT").isDisplayed());
		clickAccept();
	}

	public void clickAccept() {
		appiumdriver.findElementByName("Accept").click();
	}
	
	public void clickCustomButtonEstimateConditions() {
		Assert.assertTrue (appiumdriver.findElementByName("Estimate Conditions").isDisplayed());
	}

	public void selectOtherQuestions() {
		clickCustomButtonEstimateConditions();
		appiumdriver.findElementByName("Other").click();
	}

	public void selectOutsideQuestions() {
		clickCustomButtonEstimateConditions();
		appiumdriver.findElementByName("Outside").click();
	}

	public void selectProperQuestions() {
		clickCustomButtonEstimateConditions();
		appiumdriver.findElementByName("Proper").click();
	}

	public void setOwnerName(String ownername) {
		appiumdriver.findElementByAccessibilityId("Owner Name_TextView_Cell").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(ownername);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		appiumdriver.hideKeyboard();
		
	}

	public void setOwnerAddress(String owneraddress) {
		Helpers.waitABit(2000);
		appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTable/XCUIElementTypeCell[@name='Owner Address_TextView_Cell']").click();
		//appiumdriver.findElementByAccessibilityId("Owner Address_TextView_Cell").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(owneraddress);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		appiumdriver.hideKeyboard();
	}

	public void setOwnerCity(String ownercity) {
		((IOSElement) appiumdriver.findElementByAccessibilityId("Owner City_TextView_Cell")).click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(ownercity);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		appiumdriver.hideKeyboard();
	}

	public void clearZip() {
		((IOSElement) appiumdriver.findElementsByAccessibilityId("Clear").get(1)).click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeCell[@name=\"Owner Zip\"]/XCUIElementTypeButton[@name=\"Clear\"]").click();	
	}

	public void setOwnerZip(String ownerzip)  {
		((IOSElement) appiumdriver.findElementByAccessibilityId("Owner Zip_TextView_Cell")).click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(ownerzip);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		appiumdriver.hideKeyboard();
	}
	
	public void setOwnerInfo(String ownername, String owneraddress, String ownercity, String ownerstate,
			String ownercountry, String ownerzip) throws InterruptedException {
		swipeScreenRight();
		setOwnerName(ownername);
		setOwnerAddress(owneraddress);
		swipeScreenRight();
		setOwnerCity(ownercity);
		Thread.sleep(2000);
		setOwnerState(ownerstate);
		swipeScreenRight();
		setOwnerCountry(ownercountry);
		swipeScreenRight();
		setOwnerZip(ownerzip);
	}

	public void setOwnerState(String ownerstate)
			throws InterruptedException {
		Assert.assertTrue (appiumdriver.findElementByName("Owner State").isDisplayed());
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByName(ownerstate)).waitAction(Duration.ofSeconds(1)).release().perform();
		//appiumdriver.findElementByName(ownerstate).click();
	}

	public void setOwnerCountry(String ownercountry) {
		Assert.assertTrue (appiumdriver.findElementByName("Owners Country").isDisplayed());
		appiumdriver.findElementByName(ownercountry).click();
	}
	
	public void chooseAVISCode(String aviscode) {
		Assert.assertTrue (appiumdriver.findElementByName("Choose One AVIS Code").isDisplayed());
		appiumdriver.findElementByName(aviscode).click();
	}
	
	public void chooseConsignor(String consignor) {
		Assert.assertTrue (appiumdriver.findElementByName("Consignor").isDisplayed());
		Helpers.scroolTo(consignor);
		appiumdriver.findElementByName(consignor).click();
	}
	
	public void makeCaptureForQuestion(String question) throws InterruptedException {
		String elementname = question + "_Image_Cell";
		appiumdriver.findElementByXPath("//UIATableCell[@name=\"" + elementname  + "\"]").click();
		Helpers.makeCapture();
		appiumdriver.findElementByName("Done").click();
	}

	public void drawSignature() throws InterruptedException {
		//appiumdriver.findElementByAccessibilityId("Tap here to sign").click();
		
		//MobileElement element = (MobileElement) appiumdriver.findElementByAccessibilityId("Signature_Handwriting_Cell");
		//element.click();
		WebElement signature = appiumdriver.findElementByAccessibilityId("Tap here to sign");
		int x = signature.getLocation().getX()/2;

		int y = signature.getLocation().getY()/2;
		TouchAction action = new TouchAction(appiumdriver);
		action.tap(signature, x, y) .perform();
		
		
		
		Helpers.waitABit(500);
		
		//int yy = element.getLocation().getX();

		//int xx = element.getLocation().getY();
		MobileElement element = (MobileElement) appiumdriver.findElementByAccessibilityId("Signature_Handwriting_Cell");
		int xx = element.getLocation().getX();

		int yy = element.getLocation().getY();

		action = new TouchAction(appiumdriver);
		//action.press(xx + 100,yy + 100).waitAction(duration).moveTo(xx + 200, yy + 200).release().perform();
		action.press(xx + 100,yy + 100).waitAction(Duration.ofSeconds(3)).moveTo(xx + 200, yy + 200).release().perform();
		
	
		action = new TouchAction(appiumdriver);
		//action.press(xx - 10,yy + 50).waitAction(3000).moveTo(xx + 200, yy + 200).release().perform();
		//action.tap(xx - 20, yy + 10).perform();
		action.tap(element, 30, element.getSize().getHeight() - 20).perform();
		
	}
	
	public void selectTaxPoint(String taxpoint) {
		Assert.assertTrue(appiumdriver.findElementByName("Tax_Point_1").isDisplayed());
		appiumdriver.findElementByName(taxpoint).click();
	}
	
	public String clickSaveWithAlert() {
		appiumdriver.findElementByAccessibilityId("Save").click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Save']").click();
		return Helpers.getAlertTextAndAccept();
	}
	
	public void setEngineCondition(String enginecondition) {
		Assert.assertTrue (appiumdriver.findElementByName("Engine Condition").isDisplayed());
		appiumdriver.findElementByName(enginecondition).click();
	}
	
	public void setJustOnePossibleAnswer(String justoneanswer) {
		Assert.assertTrue (appiumdriver.findElementByName("Just One Possible Answer").isDisplayed());
		appiumdriver.findElementByName(justoneanswer).click();
	}
	
	public void setMultipleAnswersCopy(String multipleanswer) {
		Assert.assertTrue (appiumdriver.findElementByName("Multiple Answers Copy").isDisplayed());
		appiumdriver.findElementByName(multipleanswer).click();
	}
	
	public void setFreeText(String freetext) {
		Assert.assertTrue (appiumdriver.findElementByName("Free Text").isDisplayed());
		((IOSElement) appiumdriver.findElementByName("Free Text_TextView")).setValue(freetext + "\n");
		((IOSDriver) appiumdriver).hideKeyboard();
	}
	
	public void setBetteryTerminalsAnswer(String _answer) {
		Assert.assertTrue (appiumdriver.findElementByName("Battery Terminals / Cables / Mountings").isDisplayed());
		appiumdriver.findElementByName(_answer).click();
		//appiumdriver.findElementByXPath("//UIAScrollView[1]/UIATableView[1]/UIATableCell[@name=\"" + _answer + "\"]").click();
	}
	
	public void setCheckConditionOfBatteryAnswer(String _answer) {
		Assert.assertTrue (appiumdriver.findElementByName("Check Condition of Battery (Storage Capacity Test)").isDisplayed());
		appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + 
				_answer + "']").click();
	}
	
	public void setSampleQuestion(String samplequestion) {
		Assert.assertTrue (appiumdriver.findElementByName("Sample Question").isDisplayed());
		appiumdriver.findElementByName(samplequestion).click();
	}
	
	public void selectAnswerForQuestion(String question, String answer) {
		Assert.assertTrue (appiumdriver.findElementByName(question).isDisplayed());
		appiumdriver.findElementByName(answer).click();
	}
	
	public void setToYesFinalQuestion() {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[2]")).click();
		Helpers.waitABit(300);
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[2]")).click();
		Helpers.waitABit(300);
	}
	
	public void setToYesCompleteQuestion() {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[4]")).click();
		Helpers.waitABit(300);
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[4]")).click();
		Helpers.waitABit(300);
	}
	
	public void answerAllIsGoodQuestion() {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeCollectionView/XCUIElementTypeCell/XCUIElementTypeTable/XCUIElementTypeCell[2]")).click();
	}
	
	public void answerTrafficLight1Question() {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell/XCUIElementTypeTable/XCUIElementTypeCell[2]")).click();
	}

}
