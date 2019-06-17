package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static io.appium.java_client.touch.offset.ElementOption.element;

public class RegularQuestionsScreen extends RegularBaseWizardScreen {
	
	public RegularQuestionsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitQuestionsScreenLoaded(String screenName) {

		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name = 'viewPrompt' and label = '" + screenName+ "'")));
	}

	public void acceptForReminderNoDrilling() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("REMINDER NO DRILLING AND USE E-COAT")));
		clickAccept();
	}

	public void clickAccept() {
		appiumdriver.findElementByName("Accept").click();
	}
	
	public void clickCustomButtonEstimateConditions() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Estimate Conditions")));
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
		appiumdriver.findElementByXPath("//UIAScrollView[1]/UIATableView[1]/UIATableCell[2]/UIATextView[1] ").click();
		appiumdriver.getKeyboard().sendKeys(ownername + "\n");
		//Helpers.keyboadrType(ownername + "\n");
		appiumdriver.findElementByXPath("//UIAKeyboard[1]/UIAButton[@name=\"Hide keyboard\"]").click();
		
	}

	public void setOwnerAddress(String owneraddress) {
		appiumdriver.findElementByXPath("//UIAScrollView[1]/UIATableView[2]/UIATableCell[2]/UIATextView[1] ").click();
		appiumdriver.getKeyboard().sendKeys(owneraddress);
		//Helpers.keyboadrType(owneraddress);
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
			String ownercountry, String ownerzip) {
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
	}
	
	public void setRegularSetFieldValue(IOSElement txtfld, String txtvalue) {
		txtfld.click();
		txtfld.sendKeys(txtvalue + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey(txtvalue);
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		//appiumdriver.findElementByXPath("//UIAKeyboard[1]/UIAButton[@name=\"Return\"]").click();
		appiumdriver.findElementByAccessibilityId("Done").click();
		/*Point txtlocation = txtfld.getLocation();
		Dimension txtdimm = txtfld.getSize();
		
		int xx = txtlocation.getX() + txtdimm.getWidth() - 50;
		int yy = txtlocation.getY() + txtdimm.getHeight() - 50;
		appiumdriver.tap(1, xx, yy, 1000);*/
	}

	public void setOwnerState(String ownerstate) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(ownerstate)));
		if (!appiumdriver.findElementByName(ownerstate).isDisplayed())
			swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + ownerstate + "']/..")));
		appiumdriver.findElementByName(ownerstate).click();
		//Thread.sleep(1000);
		swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='Yukon']/..")));
	}

	public void setOwnerCountry(String ownercountry) {
		Helpers.waitABit(2000);
		if (appiumdriver.findElementsByName(ownercountry).size() <= 0)
			scrollScreenUp();
		appiumdriver.findElementByName(ownercountry).click();
	}
	
	public void chooseAVISCode(String aviscode) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Choose One AVIS Code")));
		appiumdriver.findElementByName(aviscode).click();
	}
	
	public void chooseConsignor(String consignor) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Consignor")));
		Helpers.scroolTo(consignor);
		appiumdriver.findElementByName(consignor).click();
	}
	
	public void makeCaptureForQuestion(String question) {
		String elementname = question + "_Image_Cell";
		appiumdriver.findElementByXPath("//UIATableCell[@name=\"" + elementname  + "\"]").click();
		Helpers.makeCapture();
	}
	
	public void makeCaptureForQuestionRegular(String question) {
		String elementname = question + "_Image_Cell";
		appiumdriver.findElementByXPath("//UIATableCell[@name=\"" + elementname  + "\"]").click();
		Helpers.makeCapture();
		appiumdriver.findElementByAccessibilityId("Back").click();
	}

	public void drawSignature() {
		appiumdriver.findElementByXPath("//UIAScrollView[1]/UIATableView[2]/UIATableCell[2]/UIAStaticText[1]").click();
		Helpers.drawQuestionsSignature();
	}
	
	public void drawRegularSignature() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		WebElement signature = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Signature_Handwriting")));
		int xx = signature.getLocation().getX()/2;

		int yy = signature.getLocation().getY()/2;
		TouchAction action = new TouchAction(appiumdriver);
		action.tap(element(signature, xx, yy)).perform();
		Helpers.drawRegularQuestionsSignature();
	}
	
	public void selectTaxPoint(String taxpoint) {
		appiumdriver.findElementByAccessibilityId("Tax_Point_1").click();
		appiumdriver.findElementByAccessibilityId(taxpoint).click();
	}
	
	public String clickSaveWithAlert() {
		clickSave();
		return Helpers.getAlertTextAndAccept();
	}
	
	public void setEngineCondition(String enginecondition) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Engine Condition")));
		appiumdriver.findElementByName(enginecondition).click();
	}

	public void waitForQuestionSectionLoad(String questionSectionName) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name(questionSectionName)));
	}
	
	public void setJustOnePossibleAnswer(String justoneanswer) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Just One Possible Answer")));
		appiumdriver.findElementByName(justoneanswer).click();
	}
	
	public void setMultipleAnswersCopy(String multipleanswer) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Multiple Answers Copy")));
		appiumdriver.findElementByName(multipleanswer).click();
	}
	
	public void setFreeText(String freetext) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Free Text")));
		((IOSElement) appiumdriver.findElementByName("Free Text_TextView")).setValue(freetext + "\n");
		//appiumdriver.findElementByXPath("//UIAButton[@name=\"Done\"]").click();
		appiumdriver.findElementByXPath("//UIAKeyboard[1]/UIAButton[@name=\"Return\"]").click();
	}
	
	public void setBetteryTerminalsAnswer(String _answer) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Battery Terminals / Cables / Mountings")));
		appiumdriver.findElementByName(_answer).click();
		//appiumdriver.findElementByXPath("//UIAScrollView[1]/UIATableView[1]/UIATableCell[@name=\"" + _answer + "\"]").click();
	}
	
	public void setCheckConditionOfBatteryAnswer(String _answer) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Check Condition of Battery (Storage Capacity Test)")));
		wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElement(MobileBy.AccessibilityId(_answer)))).click();
	}
	
	public void setSampleQuestion(String samplequestion) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Sample Question")));
		appiumdriver.findElementByName(samplequestion).click();
	}
	
	public void selectAnswerForQuestion(String question, String answer) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(question)));
		appiumdriver.findElementByName(answer).click();
	}

	public void selectAnswerForQuestion(QuestionsData questionsData) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(questionsData.getQuestionName())));
		appiumdriver.findElementByName(questionsData.getQuestionAnswer()).click();
	}

	public void selectAnswerForQuestionWithAdditionalConditions(String question, String answer, String question2answer, String vehiclePart) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(question)));
		appiumdriver.findElementByName(answer).click();
		RegularSelectedServiceDetailsScreen serviceDetailsScreen = new RegularSelectedServiceDetailsScreen();
		appiumdriver.findElementByName("Questions").click();
		serviceDetailsScreen.answerQuestion2(question2answer);
		serviceDetailsScreen.saveSelectedServiceDetails();
		serviceDetailsScreen.selectVehiclePart(vehiclePart);
		serviceDetailsScreen.saveSelectedServiceDetails();
		serviceDetailsScreen.saveSelectedServiceDetails();
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(question)));

	}
	
	public void answerQuestion2(String answer) {
		appiumdriver.findElementByAccessibilityId("Question 2").click();
		appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeSelect_Question 2")).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(answer)).click();	
	}

	public void answerQuestion(QuestionsData questionsData) {
		if (questionsData.getQuestionName() != null)
			appiumdriver.findElement(MobileBy.AccessibilityId(questionsData.getQuestionName())).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(questionsData.getQuestionAnswer())).click();
	}
	
	public String getQuestion2Value() {
		return appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeSelect_Question 2")).getAttribute("label");
	}

	
	public void setToYesFinalQuestion() {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[2]")).click();
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[2]")).click();
	}
	
	public void setToYesCompleteQuestion() {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[4]")).click();
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[4]")).click();
	}

}
