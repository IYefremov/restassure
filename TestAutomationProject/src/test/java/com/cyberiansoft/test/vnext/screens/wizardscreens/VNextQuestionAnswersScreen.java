package com.cyberiansoft.test.vnext.screens.wizardscreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextQuestionAnswersScreen extends VNextBaseWizardScreen {

    @FindBy(xpath="//div[@data-page='answers']")
    private WebElement answersscreen;

    @FindBy(xpath="//div[@data-autotests-id='answers-list']")
    private WebElement answerslist;

    public VNextQuestionAnswersScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(answersscreen));
    }

    public VNextQuestionsScreen selectAnswerByIndex(int answerIndex) {
        tap(answerslist.findElements(By.xpath(".//div[@action='select-item']")).get(answerIndex));
        clickDoneButton();
        return new VNextQuestionsScreen(appiumdriver);
    }

    public void clickDoneButton() {
        tap(answersscreen.findElement(By.xpath(".//span[@action='save']")));
    }
}
