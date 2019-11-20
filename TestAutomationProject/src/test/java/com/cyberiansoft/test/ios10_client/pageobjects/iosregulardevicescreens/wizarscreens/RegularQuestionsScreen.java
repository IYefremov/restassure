package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static io.appium.java_client.touch.offset.ElementOption.element;

public class RegularQuestionsScreen extends RegularBaseWizardScreen {
	
	public RegularQuestionsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitQuestionsScreenLoaded() {

		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Questions")));
	}

	public void clearTextQuestion() {
		appiumdriver.findElementByAccessibilityId("Clear").click();	
	}


	public void selectListQuestion(QuestionsData questionsData) {
		for (int i = 0; i < 10; i++)
			if (appiumdriver.findElementsByName(questionsData.getQuestionAnswer()).size() <= 0) {
				scrollScreenUp();
				BaseUtils.waitABit(2000);
			} else {
				i=11;
			}
		if (!appiumdriver.findElementByName(questionsData.getQuestionAnswer()).isDisplayed())
			swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + questionsData.getQuestionAnswer() + "']/..")));
		appiumdriver.findElementByName(questionsData.getQuestionAnswer()).click();

	}
	
	public void makeCaptureForQuestionRegular(String question) {
		String elementname = question + "_Image_Cell";
		appiumdriver.findElementByXPath("//UIATableCell[@name=\"" + elementname  + "\"]").click();
		Helpers.makeCapture();
		appiumdriver.findElementByAccessibilityId("Back").click();
	}

	public void drawSignature() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		WebElement signature = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Signature_Handwriting")));
		int xx = signature.getLocation().getX()/2;

		int yy = signature.getLocation().getY()/2;
		TouchAction action = new TouchAction(appiumdriver);
		action.tap(element(signature, xx, yy)).perform();
		Helpers.drawRegularQuestionsSignature();
	}
	
	public void drawQuestionFormSignature() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		WebElement signature = wait.until(ExpectedConditions.elementToBeClickable(MobileBy.className("XCUIElementTypeImage")));
		int xx = signature.getLocation().getX();

		int yy = signature.getLocation().getY();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(PointOption.point(xx + 100,yy + 100)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).
				moveTo(PointOption.point(xx + 200, yy + 200)).release().perform();
	}


	public void waitForQuestionSectionLoad(String questionSectionName) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name(questionSectionName)));
	}

	public void clickQuestionCell(String questionName) {
		appiumdriver.findElementByName(questionName).click();
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
		if (questionsData.getQuestionAnswers() != null ) {
			for (String answer : questionsData.getQuestionAnswers())
				appiumdriver.findElement(MobileBy.AccessibilityId(answer)).click();
			clickDoneButton();
		} else
			appiumdriver.findElement(MobileBy.AccessibilityId(questionsData.getQuestionAnswer())).click();
	}

	public void answerLogicalQuestion(QuestionsData questionsData) {
		appiumdriver.findElement(MobileBy.AccessibilityId(questionsData.getQuestionName())).findElement(MobileBy.className("XCUIElementTypeButton")).click();
		if (questionsData.isLogicalQuestionValue()) {
			appiumdriver.findElement(MobileBy.AccessibilityId(questionsData.getQuestionName())).findElement(MobileBy.className("XCUIElementTypeButton")).click();
		}
	}

	public void answerTextQuestion(QuestionsData questionsData) {
		appiumdriver.findElement(MobileBy.AccessibilityId(questionsData.getQuestionName())).findElement(MobileBy.className("XCUIElementTypeTextView")).clear();
		appiumdriver.findElement(MobileBy.AccessibilityId(questionsData.getQuestionName())).findElement(MobileBy.className("XCUIElementTypeTextView")).sendKeys(questionsData.getQuestionAnswer()+ "\n");
		clickDoneButton();
	}

	public void clickDoneButton() {
		appiumdriver.findElementByAccessibilityId("Done").click();
	}

	public String getQuestion2Value() {
		return appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeSelect_Question 2")).getAttribute("label");
	}

}
