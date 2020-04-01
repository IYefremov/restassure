package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ListPicker;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class CommonFilterScreen extends MonitorScreen {
    @FindBy(xpath = "//*[@data-autotests-id=\"search-input\"]")
    private WebElement searchInputField;

    @FindBy(xpath = "//div[contains(@class,\"common-filters-apply\")]")
    private WebElement searchButton;

    @FindBy(xpath = "//span[@data-automation-id=\"search-clear\"]")
    private WebElement clearSearchInputButton;

    @FindBy(xpath = "//div[contains(@class,'common-filters-clear')]")
    private WebElement clearFilter;

    @FindBy(xpath = "//*[@data-autotests-id=\"search-cancel\"]")
    private WebElement cancelSearchInputButton;

    @FindBy(xpath = "//*[@data-name='timeframe']/select")
    private ListPicker timeframe;

    @FindBy(xpath = "//*[@data-name='dateFrom']")
    private WebElement dateFrom;

    @FindBy(xpath = "//*[@data-name='dateTo']")
    private WebElement dateTo;

    @FindBy(xpath = "//*[@data-name='department']/select")
    private ListPicker department;

    @FindBy(xpath = "//*[@data-name='phase']/select")
    private ListPicker phase;

    @FindBy(xpath = "//*[@data-name='status']/select")
    private ListPicker status;

    @FindBy(xpath = "//*[@data-name='flag']/select")
    private ListPicker flag;

    @FindBy(xpath = "//*[@data-name='priority']/select")
    private ListPicker priority;

    @FindBy(xpath = "//div[@class=\"searchlist-nothing-found\"]")
    private WebElement nothingFoundLable;

    public CommonFilterScreen() {
        PageFactory.initElements(new FiledDecorator(webDriver), this);
    }

    public void setSearchInputField(String searchString) {
        WaitUtils.click(searchInputField);
        searchInputField.sendKeys(searchString);
    }
}
