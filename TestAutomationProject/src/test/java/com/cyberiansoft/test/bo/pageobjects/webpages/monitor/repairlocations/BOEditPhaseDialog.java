package com.cyberiansoft.test.bo.pageobjects.webpages.monitor.repairlocations;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BOEditPhaseDialog extends BaseWebPage {

    @FindBy(id = "ctl00_Content_ctl01_pHead")
    private WebElement dialogHeader;

    @FindBy(id = "ctl00_Content_ctl01_ctl01_Card_comboWorkStatusTracking")
    private ComboBox workStatusCmb;

    @FindBy(id = "ctl00_Content_ctl01_ctl01_Card_comboWorkStatusTracking_DropDown")
    private DropDown workStatusDd;

    @FindBy(id = "ctl00_Content_ctl01_ctl02_BtnOk")
    private WebElement okButton;

    public BOEditPhaseDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
