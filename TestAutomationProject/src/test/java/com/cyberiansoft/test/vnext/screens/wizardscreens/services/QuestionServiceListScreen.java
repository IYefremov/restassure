package com.cyberiansoft.test.vnext.screens.wizardscreens.services;

import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class QuestionServiceListScreen extends VNextBaseScreen {
    @FindBy(xpath = "//*[@data-page='services-list']")
    private WebElement rootElement;

    @FindBy(xpath = "//*[@action='available']")
    private WebElement needToSetupServiceButton;

    @FindBy(xpath = "//*[@action='selected']")
    private WebElement selectedServiceScreen;

    @FindBy(xpath = "//*[@action='save']")
    private WebElement saveButton;

    @FindBy(xpath = "//*[@data-entity-service-id]")
    private List<WebElement> serviceList;
}
