package com.cyberiansoft.test.vnext.screens;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextEditionsScreen extends VNextBaseScreen {

    @FindBy(xpath = "//*[@data-page='editions-list']")
    private WebElement editionslistscreeen;

    @FindBy(xpath = "//*[@data-autotests-id='editions-list']")
    private WebElement editionslist;

    public VNextEditionsScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
    }

    public VNextEnvironmentSelectionScreen selectEdition(String editionName) {
        tap(editionslist.findElement(By.xpath(".//div[contains(text(), '" + editionName + "')]")));
        return new VNextEnvironmentSelectionScreen(appiumdriver);
    }
}
