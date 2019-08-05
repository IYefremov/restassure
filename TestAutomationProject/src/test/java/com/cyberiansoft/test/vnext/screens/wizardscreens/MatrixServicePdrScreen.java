package com.cyberiansoft.test.vnext.screens.wizardscreens;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class MatrixServicePdrScreen extends VNextBaseWizardScreen {
    @FindBy(xpath = "//div[@action='size']")
    private WebElement sizeField;

    @FindBy(xpath = "//div[@action='severity']")
    private WebElement severityField;

    @FindBy(xpath = "//div[@action='select-item']")
    private List<WebElement> listItems;

    @FindBy(xpath = "//span[@action='available']")
    private WebElement availableServices;

    @FindBy(xpath = "//span[@action='selected']")
    private WebElement selectedServices;

    public WebElement getListItem(String itemText) {
        return listItems.stream()
                .filter(elem ->
                        elem.getText().equals(itemText)
                )
                .findFirst()
                .orElseThrow(() -> new RuntimeException("List element not found " + itemText));
    }
}
