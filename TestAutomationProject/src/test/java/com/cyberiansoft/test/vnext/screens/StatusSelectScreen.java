package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class StatusSelectScreen extends VNextBaseScreen {
    @FindBy(xpath = "//div[@data-page='change-status']")
    private WebElement rootElement;

    @FindBy(xpath = "//*[@action='select']")
    private List<WebElement> statusList;

    public WebElement getStatusItemByText(String statusText){
        return statusList.stream()
                .filter(element -> element.getText().toLowerCase().contains(statusText.toLowerCase()))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("status item not found " + statusText));
    }
}
