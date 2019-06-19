package com.cyberiansoft.test.vnext.screens.menuscreens.notes;

import com.cyberiansoft.test.vnext.screens.monitoring.MonitorScreen;
import com.cyberiansoft.test.vnext.webelements.NoteListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class NoteListMenuScreen extends MonitorScreen {

    @FindBy(xpath = "//div[@data-page=\"comments-list\"]")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@action=\"add-new-comment\"]")
    private WebElement addNewNoteButton;

    @FindBy(xpath = "//div[@data-autotests-id=\"simple-comments-list\"]/div")
    private List<NoteListElement> noteListElements;

    public NoteListMenuScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }

    public NoteListElement getNoteByText(String noteText) {
        return noteListElements.stream()
                .filter(listElement -> listElement.getNoteText().equals(noteText))
                .findFirst().orElseThrow(() -> new RuntimeException("note not found " + noteText));
    }
}
