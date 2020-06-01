package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class GeneralQuestion implements IWebElement {
    @Getter
    protected WebElement rootElement;
    private String questionNameLocator = ".//div[@class='question-item-header-text']";
    private String answerLocator = ".//*[@class='question-answer-bar-title']";
    private String selectedAnswerLocator = ".//div[@class='question-item-selected-answer']";
    private String selectedAnswersLocation = ".//div[contains(@class,'multi-select-answers')]/div[contains(@class,'selected-answer')]";
    private String clearButtonLocator = ".//*[@class='selected-answer-clear']";
    private String saveMultiAnswerQuestion = ".//div[contains(@class,'pply-multi-select-answers')]";
    private String cameraLocator = ".//*[contains(@class, 'add-image-from-camera')]";
    private String selectDateLocator = ".//*[@name='Question.QuestionDate']";
    private String selectTimeLocator = ".//*[@name='Question.QuestionTime']";
    private String signatureLocator = ".//*[@class='handwriting-answer-placeholder']";
    private String questionNotesIcon = ".//*[contains(@class,'selected-answer-notes ')]";
    private String addedImageLocator = ".//div[@class='image-item']";
    private String hiddenImagesCounterLocator = ".//div[@class='image-item counter-item']";

    public GeneralQuestion(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getQuestionName() {
        return rootElement.findElement(By.xpath(questionNameLocator)).getText();
    }

    public WebElement getQuestionNameElement() {
        return rootElement.findElement(By.xpath(questionNameLocator));
    }

    public WebElement getAnswerElement(String text) {
        return WaitUtils.getGeneralFluentWait().until(driver -> rootElement.findElements(By.xpath(answerLocator))
                .stream()
                .filter(WebElement::isDisplayed)
                .filter(element -> element.getText().contains(text))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Answer not found")));
    }

    public WebElement getSelectedAnswer() {
        return rootElement.findElements(By.xpath(selectedAnswerLocator))
                .stream().filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Answer not found"));
    }

    public List<WebElement> getSelectedAnswers() {
        return rootElement.findElements(By.xpath(selectedAnswersLocation))
                .stream().filter(WebElement::isDisplayed)
                .collect(Collectors.toList());
    }

    public void clearQuestion() {
        WaitUtils.click(rootElement.findElements(By.xpath(clearButtonLocator))
                .stream().filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Active clear button not found")));
    }

    public WebElement getSaveButton() {
        return rootElement.findElement(By.xpath(saveMultiAnswerQuestion));
    }

    public WebElement getQuestionCameraElement() {
        return rootElement.findElement(By.xpath(cameraLocator));
    }

    public WebElement getSelectDateElement() {
        return this.rootElement.findElement(By.xpath(selectDateLocator));
    }

    public WebElement getSelectTimeElement() {
        return this.rootElement.findElement(By.xpath(selectTimeLocator));
    }

    public WebElement getSignatureElement() {
        return this.rootElement.findElement(By.xpath(signatureLocator));
    }

    public WebElement getQuestionNotesIcon() {
        return this.rootElement.findElement(By.xpath(questionNotesIcon));
    }

    public WebElement getClearIcon() {
        return this.rootElement.findElement(By.xpath(clearButtonLocator));
    }

    public List<WebElement> getAddedImages() {
        return this.rootElement.findElements(By.xpath(addedImageLocator));
    }

    public WebElement getHiddenImagesCount() {
        return this.rootElement.findElement(By.xpath(hiddenImagesCounterLocator));
    }
}
