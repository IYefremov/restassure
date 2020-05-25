package com.cyberiansoft.test.vnextbo.screens.servicerequests;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOSRTable extends VNextBOSRPage {

    @FindBy(xpath = "//div[@class='Table']")
    private WebElement serviceRequestsTable;

    @FindBy(xpath = "//div[contains(@class, 'Table-emptyRow')]")
    private WebElement emptyTable;

    @FindBy(xpath = "//div[contains(@class, 'Table-cell') and not(@style)]//div[@class='ServiceTableTextBlock-text'][1]")
    private List<WebElement> customersList;

    @FindBy(xpath = "//div[contains(@class, 'Table-cell') and not(@style)]//div[@class='ServiceTableTextBlock-text'][3]")
    private List<WebElement> stockNumbersList;

    @FindBy(xpath = "//div[contains(@class, 'ServiceRequests-cell') and @style]//div[@class='ServiceTableTextBlock-text'][1]")
    private List<WebElement> srNumbersList;

    public VNextBOSRTable() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
