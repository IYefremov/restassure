package com.cyberiansoft.test.vnext.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DateQuestion extends GeneralQuestion {
    private String selectDateLocator = ".//input[@name='Question.QuestionDate']";

    public DateQuestion(WebElement rootElement) {
        super(rootElement);
    }

    public WebElement getSelectDateElement() {
        return this.rootElement.findElement(By.xpath(selectDateLocator));
    }
}
