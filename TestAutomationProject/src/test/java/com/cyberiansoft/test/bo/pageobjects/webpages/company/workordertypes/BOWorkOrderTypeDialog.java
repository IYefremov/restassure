package com.cyberiansoft.test.bo.pageobjects.webpages.company.workordertypes;

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
public class BOWorkOrderTypeDialog extends BaseWebPage {

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl00_labelInspectionType")
    private WebElement woTypeHeader;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_cbApprovalRequired")
    private WebElement approvalRequiredCheckbox;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboLineApproval_Input")
    private ComboBox lineApprovalCmb;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboLineApproval_DropDown")
    private DropDown lineApprovalDd;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
    private WebElement okButton;

    public BOWorkOrderTypeDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
