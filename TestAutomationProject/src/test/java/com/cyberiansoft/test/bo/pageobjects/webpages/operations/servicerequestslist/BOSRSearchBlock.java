package com.cyberiansoft.test.bo.pageobjects.webpages.operations.servicerequestslist;

import com.cyberiansoft.test.bo.pageobjects.webpages.BOSearchPanel;
import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BOSRSearchBlock extends BOSearchPanel {

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ddlTimeframe_Input")
    private ComboBox timeFrameCbx;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ddlTimeframe_DropDown")
    private DropDown timeFrameDd;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_calDateFrom_dateInput")
    private WebElement from;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_calDateTo_dateInput")
    private WebElement to;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_tbSearchFreeText")
    private WebElement freeText;

    @FindBy(xpath = "//input[contains(@id, 'btnSearch')]")
    private WebElement searchButton;

    public BOSRSearchBlock() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
