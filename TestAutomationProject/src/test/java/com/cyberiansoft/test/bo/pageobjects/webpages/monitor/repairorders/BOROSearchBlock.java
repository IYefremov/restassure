package com.cyberiansoft.test.bo.pageobjects.webpages.monitor.repairorders;

import com.cyberiansoft.test.bo.pageobjects.webpages.BOSearchPanel;
import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BOROSearchBlock extends BOSearchPanel {

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboLocations_Input")
    private ComboBox locationCombobox;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboLocations_DropDown")
    private DropDown locationDropDown;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboRepairStatus_Input")
    private ComboBox repairStatusCombobox;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_comboRepairStatus_DropDown")
    private DropDown repairStatusDropDown;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_ddlTimeframe_Input")
    private ComboBox timeFrameCombobox;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_ddlTimeframe_DropDown")
    private DropDown timeFrameDropDown;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_dpFrom_dateInput")
    private WebElement from;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_dpTo_dateInput")
    private WebElement to;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl02_filterer_tbWO")
    private WebElement woNum;

    public BOROSearchBlock() {
        super();
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
