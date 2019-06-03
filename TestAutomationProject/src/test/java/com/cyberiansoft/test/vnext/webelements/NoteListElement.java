package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class NoteListElement implements IWebElement {
    private WebElement rootElement;
    private String noteTextLocator = ".//p[contains(@class,\"list-item-name\")]";
    private String footerLocator = ".//p[@class=\"list-item-text\"]";

    public NoteListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getNoteText() {
        return rootElement.findElement(By.xpath(noteTextLocator)).getText();
    }

    public String getFooter() {
        return rootElement.findElement(By.xpath(footerLocator)).getText();
    }
}
