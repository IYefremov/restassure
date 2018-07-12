package com.cyberiansoft.test.inhouse.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TeamPortalHeader extends BasePage {

    @FindBy(id = "logoutForm")
    List<WebElement> logoutBTN;

    public TeamPortalHeader(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step
    public void clickLogoutButton(){
        if (driver.getWindowHandles().size() > 1) {
            driver.close();
            for (String activeHandle : driver.getWindowHandles())
                driver.switchTo().window(activeHandle);
        }
        driver.switchTo().defaultContent();
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,-400)", "");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        logoutBTN.get(1).click();
    }
} 
