package com.cyberiansoft.test.vnext.screens.wizardscreens.questions;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.GeneralQuestion;
import com.cyberiansoft.test.vnext.webelements.TextQuestion;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class QuestionScreen extends VNextBaseWizardScreen {
    @FindBy(xpath = "//*[@class='questions-list-block']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@class='questions-list']/div")
    private List<GeneralQuestion> generalQuestionList;

    @FindBy(xpath = "//div[contains(@class,'nswered-question')]")
    private List<GeneralQuestion> answeredQuestions;

    @FindBy(xpath = "//div[@class='questions-list']/div")
    private List<TextQuestion> textQuestionList;

    public QuestionScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public GeneralQuestion getGeneralQuestionByText(String questionText) {
        return WaitUtils.getGeneralFluentWait()
                .until(driver -> generalQuestionList.stream().filter((question -> question.getQuestionName().equals(questionText))).findFirst().orElseThrow(() -> new RuntimeException("Question not found " + questionText)));
    }

    public GeneralQuestion getAnsweredQuestionByText(String questionText) {
        return WaitUtils.getGeneralFluentWait()
                .until(driver -> answeredQuestions.stream().filter((question -> question.getQuestionName().equals(questionText))).findFirst().orElseThrow(() -> new RuntimeException("Question not found " + questionText)));
    }

    public TextQuestion getTextQuestionByText(String questionText) {
        return textQuestionList.stream().filter((
                question -> question.getQuestionName().equals(questionText)
        )).findFirst().orElseThrow(() -> new RuntimeException("Question not found " + questionText));
    }
}
