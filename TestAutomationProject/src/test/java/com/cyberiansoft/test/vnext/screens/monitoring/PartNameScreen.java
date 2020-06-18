package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.webelements.customers.ListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class PartNameScreen extends MonitorScreen {

    @FindBy(xpath = "//div[@class='pages']/div[@data-page='part-name']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@data-autotests-id='part-name-list']/div")
    private List<ListElement> namesRecordsList;

    public PartNameScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }
}
