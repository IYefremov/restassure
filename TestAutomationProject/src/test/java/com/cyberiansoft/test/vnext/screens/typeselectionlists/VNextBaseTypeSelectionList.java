package com.cyberiansoft.test.vnext.screens.typeselectionlists;

import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextBaseTypeSelectionList extends VNextBaseScreen {

    @FindBy(xpath="//div[@data-page='entity-types']")
    private WebElement typeslist;

    public VNextBaseTypeSelectionList(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        //PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 90);
        wait.until(ExpectedConditions.visibilityOf(typeslist));
    }

    public void selectType(String typeName) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='item-title']/div[text()='" + typeName + "']")));
        wait = new WebDriverWait(appiumdriver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='item-title']/div[text()='" + typeName + "']")));
        tap(typeslist.findElement(By.xpath(".//div[@class='item-title']/div[text()='" + typeName + "']")));
    }
}
