package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class SelectTaskScreen extends MonitorScreen {

    @FindBy(xpath = "//div[@class='pages']/div[@data-page='all-phases-tasks']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@data-task-name='Test_manual_task']")
    private WebElement testManualTaskItem;

    public SelectTaskScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }
}
