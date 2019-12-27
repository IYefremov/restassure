package com.cyberiansoft.test.vnextbo.screens.quicknotes;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOQuickNotesWebPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//button[@data-bind='click: onAddNewQuickNoteClick']")
    private WebElement addQuickNotesButton;

    @FindBy(xpath = "//div[@data-bind='text: noteText']")
    private List<WebElement> quickNotesList;

    public WebElement editNoteButtonByDescription(String noteDescription) {
        return DriverBuilder.getInstance().getDriver().
                findElement(By.xpath("//div[text() = '" + noteDescription + "']/ancestor::div[contains(@class, 'quick-notes-listview-item')]//span[@title='Edit']"));
    }

    public WebElement deleteNoteButtonByDescription(String noteDescription) {
        return DriverBuilder.getInstance().getDriver().
                findElement(By.xpath("//div[text() = '" + noteDescription + "']/ancestor::div[contains(@class, 'quick-notes-listview-item')]//span[@title='Delete']"));
    }

    public VNextBOQuickNotesWebPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
