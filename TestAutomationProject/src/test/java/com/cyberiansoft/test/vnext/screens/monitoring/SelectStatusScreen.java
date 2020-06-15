package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.enums.TaskStatus;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class SelectStatusScreen extends MonitorScreen {

    @FindBy(xpath = "//div[@class='pages']/div[@data-page='change-status']")
    private WebElement rootElement;

    public WebElement getStatusRecord(TaskStatus status) {

        return webDriver.findElement(By.xpath("//div[@data-name='" + status.getStatusName() + "']"));
    }

    public SelectStatusScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }
}
