package com.cyberiansoft.test.vnext.screens.wizardscreens.questions;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.webelements.GeneralSlideQuestion;
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

    @FindBy(xpath = "//*[@class='question-item']")
    private List<GeneralSlideQuestion> generalSlideQuestionList;

    public QuestionScreen() {
        PageFactory.initElements(new FiledDecorator(DriverBuilder.getInstance().getAppiumDriver()), this);
    }

    public GeneralSlideQuestion getGeneralQuestionByText(String questionText) {
        return generalSlideQuestionList.stream().filter((question -> question.getQuestionName().equals(questionText))).findFirst().orElseThrow(() -> new RuntimeException("Question not found " + questionText));
    }
}
