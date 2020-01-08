package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class VNextQuestionsScreen extends VNextBaseWizardScreen {

    @FindBy(xpath="//div[@data-page='questions-list']")
    private WebElement questionsscreen;

    @FindBy(xpath="//*[@data-autotests-id='all-questions']")
    private WebElement questionslist;

    public VNextQuestionsScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(questionsscreen));
    }

    public void selectAllRequiredQuestions(int answerIndex) {
        List<WebElement> selectQstns = questionslist.findElements(By.xpath(".//div[@class='question-answer-bar required-question']"));
        for (WebElement selectQuestionCell : selectQstns) {
            tap(questionslist.findElement(By.xpath(".//div[@class='question-answer-bar required-question']")));
            VNextQuestionAnswersScreen questionAnswersScreen = new VNextQuestionAnswersScreen(appiumdriver);
            questionAnswersScreen.selectAnswerByIndex(answerIndex);
        }
    }

    public void selectRequiredQuestion() {
        tap(questionslist.findElement(By.xpath(".//div[@class='question-answer-bar required-question']")));
    }

    public void setAllRequiredQuestions(String answerText) {
        List<WebElement> selectQstns = questionslist.findElements(By.xpath(".//*[@class='question-answer-bar form-fieldset required-question']"));
        for (WebElement selectQuestionCell : selectQstns) {
            selectQuestionCell.findElement(By.xpath(".//*[@class='question-answer-bar-title']")).click();
            selectQuestionCell.findElement(By.xpath(".//textarea")).clear();
            selectQuestionCell.findElement(By.xpath(".//textarea")).sendKeys(answerText);
            //appiumdriver.hideKeyboard();
        }
    }

    public void clickDoneButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='save']")));
        tap(questionsscreen.findElement(By.xpath(".//*[@action='save']")));
    }

    public VNextSelectedServicesScreen saveQuestions() {
        clickDoneButton();
        return new VNextSelectedServicesScreen(appiumdriver);
    }
}
