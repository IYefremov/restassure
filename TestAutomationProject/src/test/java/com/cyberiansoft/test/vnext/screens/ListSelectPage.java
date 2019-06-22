package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.vnext.screens.monitoring.MonitorScreen;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class ListSelectPage extends MonitorScreen {
    @FindBy(xpath = "//div[@data-page]")
    private WebElement rootElement;

    @FindBy(xpath = "//div[contains(@data-autotests-id,'list')]/div")
    private List<WebElement> itemList;

    @FindBy(xpath = "//span[@action='save']")
    private WebElement saveButton;

    public WebElement getItemElementByText(String text) {
        return itemList.stream()
                .filter(elem ->
                        elem.getText().equals(text)
                )
                .findFirst()
                .orElseThrow(() -> new RuntimeException("List element not found " + text));
    }
}
