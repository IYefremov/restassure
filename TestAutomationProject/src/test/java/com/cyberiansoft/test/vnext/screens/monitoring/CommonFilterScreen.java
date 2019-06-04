package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.webelements.ListPicker;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class CommonFilterScreen extends MonitorScreen {
    @FindBy(xpath = "//input[@data-autotests-id=\"search-input\"]")
    private WebElement searchInputField;

    @FindBy(xpath = "//div[contains(@class,\"searchlist-filters-apply\")]")
    private WebElement searchButton;

    @FindBy(xpath = "//span[@data-automation-id=\"search-clear\"]")
    private WebElement clearSearchInputButton;

    @FindBy(xpath = "//div[contains(@class,\"searchlist-filters-clear\")]")
    private WebElement clearFilter;

    @FindBy(id = "filter_department")
    private ListPicker department;

    @FindBy(id = "filter_phase")
    private ListPicker phase;

    @FindBy(id = "filter_status")
    private ListPicker status;

    @FindBy(id = "filter_flag")
    private ListPicker flag;

    @FindBy(id = "filter_priority")
    private ListPicker priority;

    public CommonFilterScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }

    public void setSearchInputField(String searchString) {
        searchInputField.click();
        searchInputField.sendKeys(searchString);
    }

    public void search() {
        searchButton.click();
    }
}