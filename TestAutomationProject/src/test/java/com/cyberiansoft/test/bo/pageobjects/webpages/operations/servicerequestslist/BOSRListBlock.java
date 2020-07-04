package com.cyberiansoft.test.bo.pageobjects.webpages.operations.servicerequestslist;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class BOSRListBlock extends BaseWebPage {

    @FindBy(xpath = "//span[@class='itemSrNo']/b")
    private List<WebElement> srNumbersList;

    @FindBy(xpath = "//a[@class='detailsPopover']")
    private List<WebElement> srPopoverList;

    public BOSRListBlock() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
