package com.cyberiansoft.test.vnext.webelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TextQuestion extends GeneralQuestion {
    private String inputElementLocator = ".//textarea";

    public TextQuestion(WebElement rootElement) {
        super(rootElement);
    }

    public WebElement getTextInputElement() {
        return this.rootElement.findElement(By.xpath(inputElementLocator));
    }
}
