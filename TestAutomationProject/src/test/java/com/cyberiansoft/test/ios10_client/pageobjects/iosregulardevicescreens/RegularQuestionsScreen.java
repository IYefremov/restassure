package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularQuestionsScreen extends iOSRegularBaseScreen {
	
	public RegularQuestionsScreen(AppiumDriver driver) {
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

	public void setOwnerName(String ownername) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIAScrollView[1]/UIATableView[1]/UIATableCell[2]/UIATextView[1] ").click();
		Helpers.keyboadrType(ownername + "\n");
		appiumdriver.findElementByXPath("//UIAKeyboard[1]/UIAButton[@name=\"Hide keyboard\"]").click();
		
	}

	public void setOwnerAddress(String owneraddress) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIAScrollView[1]/UIATableView[2]/UIATableCell[2]/UIATextView[1] ").click();
		Helpers.keyboadrType(owneraddress);
		appiumdriver.findElementByXPath("//UIAKeyboard[1]/UIAButton[@name=\"Return\"]").click();
				appiumdriver.findElementByXPath("//UIAKeyboard[1]/UIAButton[@name=\"Hide keyboard\"]").click();
	}

	public void setOwnerCity(String ownercity) {
		((IOSElement) appiumdriver.findElementByXPath("//UIAScrollView[1]/UIATableView[1]/UIATableCell[2]/UIATextView[1]")).setValue(ownercity + "\n");
		appiumdriver.findElementByXPath("//UIAScrollView[1]/UIATableView[1]/UIATableCell[2]/UIATextView[1]").sendKeys("\n");
		appiumdriver.findElementByXPath("//UIAKeyboard[1]/UIAButton[@name=\"Hide keyboard\"]").click();
	}

	public void clearZip() {
		appiumdriver.findElementByAccessibilityId("Clear").click();	
	}

	public void setOwnerZip(String ownerzip)  {
		appiumdriver.findElementByXPath("//UIAScrollView[1]/UIATableView[2]/UIATableCell[2]/UIATextView[1]").sendKeys(ownerzip + "\n");
		appiumdriver.findElementByXPath("//UIAKeyboard[1]/UIAButton[@name=\"Hide keyboard\"]").click();
	}
	
	public void setOwnerInfo(String ownername, String owneraddress, String ownercity, String ownerstate,
			String ownercountry, String ownerzip) throws InterruptedException {
		scrollScreenUp();
		scrollScreenUp();
		setRegularSetFieldValue((IOSElement) appiumdriver.findElementByAccessibilityId("Owner Name_TextView"), ownername);
		scrollScreenUp();
		setRegularSetFieldValue((IOSElement) appiumdriver.findElementByAccessibilityId("Owner Address_TextView"), owneraddress);
		scrollScreenUp();
		setRegularSetFieldValue((IOSElement) appiumdriver.findElementByAccessibilityId("Owner City_TextView"), ownercity);
		scrollScreenUp();
		setOwnerState(ownerstate);
		scrollScreenUp();
		setOwnerCountry(ownercountry);
		scrollScreenUp();
		scrollScreenUp();
		scrollScreenUp();
		setRegularSetFieldValue((IOSElement) appiumdriver.findElementByAccessibilityId("Owner Zip_TextView"), ownerzip);
		Helpers.waitABit(500);
	}
	
	public void setRegularSetFieldValue(IOSElement txtfld, String txtvalue) throws InterruptedException {
		txtfld.click();
		
		((IOSDriver) appiumdriver).getKeyboard().pressKey(txtvalue);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		//appiumdriver.findElementByXPath("//UIAKeyboard[1]/UIAButton[@name=\"Return\"]").click();
		appiumdriver.findElementByAccessibilityId("Done").click();
		/*Point txtlocation = txtfld.getLocation();
		Dimension txtdimm = txtfld.getSize();
		
		int xx = txtlocation.getX() + txtdimm.getWidth() - 50;
		int yy = txtlocation.getY() + txtdimm.getHeight() - 50;
		appiumdriver.tap(1, xx, yy, 1000);*/
	}

	public void setOwnerState(String ownerstate)
			throws InterruptedException {
		if (!appiumdriver.findElementByName(ownerstate).isDisplayed())
			swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + ownerstate + "']/..")));
		appiumdriver.findElementByName(ownerstate).click();
		//Thread.sleep(1000);
		swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='Yukon']/..")));
	}

	public void setOwnerCountry(String ownercountry) {
		if (appiumdriver.findElementsByName(ownercountry).size() <= 0)
			scrollScreenUp();
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
	}
	
	public void makeCaptureForQuestionRegular(String question) throws InterruptedException {
		String elementname = question + "_Image_Cell";
		appiumdriver.findElementByXPath("//UIATableCell[@name=\"" + elementname  + "\"]").click();
		Helpers.makeCapture();
		appiumdriver.findElementByAccessibilityId("Back").click();
	}

	public void drawSignature() throws InterruptedException {
		appiumdriver.findElementByXPath("//UIAScrollView[1]/UIATableView[2]/UIATableCell[2]/UIAStaticText[1]").click();
		Helpers.drawQuestionsSignature();
	}
	
	public void drawRegularSignature() throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("Tap here to sign").click();
		Helpers.drawRegularQuestionsSignature();
	}
	
	public void selectTaxPoint(String taxpoint) {
		appiumdriver.findElementByAccessibilityId("Tax_Point_1").click();
		appiumdriver.findElementByAccessibilityId(taxpoint).click();
	}
	
	public String clickSaveWithAlert() {
		clickSaveButton();
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
		//appiumdriver.findElementByXPath("//UIAButton[@name=\"Done\"]").click();
		appiumdriver.findElementByXPath("//UIAKeyboard[1]/UIAButton[@name=\"Return\"]").click();
	}
	
	public void setBetteryTerminalsAnswer(String _answer) {
		Assert.assertTrue (appiumdriver.findElementByName("Battery Terminals / Cables / Mountings").isDisplayed());
		appiumdriver.findElementByName(_answer).click();
		//appiumdriver.findElementByXPath("//UIAScrollView[1]/UIATableView[1]/UIATableCell[@name=\"" + _answer + "\"]").click();
	}
	
	public void setCheckConditionOfBatteryAnswer(String _answer) {
		Assert.assertTrue (appiumdriver.findElementByName("Check Condition of Battery (Storage Capacity Test)").isDisplayed());
		appiumdriver.findElement(MobileBy.AccessibilityId(_answer)).click();	
	}
	
	public void setSampleQuestion(String samplequestion) {
		Assert.assertTrue (appiumdriver.findElementByName("Sample Question").isDisplayed());
		appiumdriver.findElementByName(samplequestion).click();
	}
	
	public void selectAnswerForQuestion(String question, String answer) {
		Helpers.waitABit(500);
		Assert.assertTrue (appiumdriver.findElementByName(question).isDisplayed());
		appiumdriver.findElementByName(answer).click();
	}
	
	public void answerQuestion2(String answer) {
		appiumdriver.findElementByAccessibilityId("Question 2").click();
		appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeSelect_Question 2")).click();
		Helpers.waitABit(300);
		appiumdriver.findElement(MobileBy.AccessibilityId(answer)).click();	
	}
	
	public String getQuestion2Value() {
		return appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeSelect_Question 2")).getAttribute("label");
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

}
