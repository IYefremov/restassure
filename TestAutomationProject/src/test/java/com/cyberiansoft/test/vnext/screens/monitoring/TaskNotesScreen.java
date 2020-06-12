package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class TaskNotesScreen extends MonitorScreen {

    @FindBy(xpath = "//div[@class='pages']/div[@data-page='comments-list']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@action='add-new-comment']")
    private WebElement addNewNoteButton;

    @FindBy(xpath = "//textarea[@name='comment']")
    private WebElement commentTextarea;

    @FindBy(xpath = "//div[@action='view-commnet']")
    private WebElement addedNote;

    public TaskNotesScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }
}
