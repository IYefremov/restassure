package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GeneralQuestion implements IWebElement {
    protected WebElement rootElement;
    private String questionNameLocator = ".//*[@class='question-answer-title']";
    private String answerLocator = ".//*[@class='question-answer-bar-title']";
    private String clearButtonLocator = ".//*[@action='clean-question']";

    public GeneralQuestion(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getQuestionName() {
        return rootElement.findElements(By.xpath(questionNameLocator)).stream().filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Active question tile not found"))
                .getText();
    }

    public WebElement getAnswerElement() {
        return rootElement.findElements(By.xpath(answerLocator))
                .stream().filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Answer not found"));
    }

    public void clearQuestion() {
        WaitUtils.click(rootElement.findElements(By.xpath(clearButtonLocator))
                .stream().filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Active clear button not found")));
    }
}
