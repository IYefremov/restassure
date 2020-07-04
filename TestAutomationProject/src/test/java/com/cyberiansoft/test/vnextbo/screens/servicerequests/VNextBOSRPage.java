package com.cyberiansoft.test.vnextbo.screens.servicerequests;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOSRPage extends VNextBOBaseWebPage {

    @FindBy(id = "serviceRequests-view")
    private WebElement serviceRequestsView;

    @FindBy(xpath = "//div[@class='LoadMoreButton']/button")
    private WebElement loadMoreButton;

    public VNextBOSRPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
