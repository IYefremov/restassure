package com.cyberiansoft.test.vnext.screens.wizardscreens.services;

import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class LaborServiceScreen extends VNextBaseScreen {
    @FindBy(xpath = "//*[@data-page='labor-list']")
    private WebElement rootElement;

    @FindBy(xpath = "//*[@action='available']")
    private WebElement availableServiceButton;

    @FindBy(xpath = "//*[@action='selected']")
    private WebElement selectedServiceScreen;

    @FindBy(xpath = "//*[@action='save']")
    private WebElement saveButton;

    @FindBy(xpath = "//*[@data-autotests-id='available-services-list']/div")
    private List<WebElement> serviceList;
}
