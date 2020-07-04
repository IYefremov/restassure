package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class TaskDetailsScreen extends MonitorScreen {

    @FindBy(xpath = "//div[@class='pages']/div[@data-page='task-details']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@action='select-team']/input")
    private WebElement vendorTeamField;

    @FindBy(xpath = "//div[@action='select-tech']/input")
    private WebElement technicianField;

    @FindBy(xpath = "//div[@action='select-comments']")
    private WebElement selectCommentField;

    public TaskDetailsScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }
}
