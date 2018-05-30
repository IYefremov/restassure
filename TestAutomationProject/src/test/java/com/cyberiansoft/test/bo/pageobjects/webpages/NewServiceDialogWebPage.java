package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class NewServiceDialogWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//input[contains(@id, 'Card_tbName')]")
	private TextField servicenamefld;

	@FindBy(xpath = "//input[contains(@id, 'Card_comboType_Input')]")
	private ComboBox servicetypecmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_comboType_DropDown')]")
	private DropDown servicetypedd;
	
	@FindBy(xpath = "//textarea[contains(@id, 'Card_tbDesc')]")
	private TextField servicedescfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboPriceType_Input")
	private ComboBox servicesPriceTypeCombobox;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_comboPriceType_DropDown")
	private DropDown servicesPriceTypeDropDown;

	@FindBy(xpath = "//input[contains(@id, 'Card_tbAccountingId')]")
	private TextField serviceaccidfld;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_tbAccountingId2')]")
	private TextField serviceaccid2fld;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_comboAssignmentTypes_Input')]")
	private ComboBox serviceAssignedToCombobox;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_comboAssignmentTypes_DropDown')]")
	private DropDown serviceAssignedToDropDown;
	
	@FindBy(xpath = "//input[contains(@id, 'ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk')]")
	private WebElement OKbtn;
	
	@FindBy(xpath = "//input[contains(@id, 'ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel')]")
	private WebElement cancelbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_cbMultiple")
	private WebElement multipleCheckBox;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_priceTypeName")
    private WebElement servicePriceType;

    @FindBy(id = "ctl00_ctl00_Content_Main_ModalDialog1")
    private WebElement newServiceDialog;
	
	public NewServiceDialogWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}

	public NewServiceDialogWebPage setNewServiceName(String servicename) {
		clearAndType(servicenamefld, servicename);
		return this;
	}
	
	public String getNewServiceName() {
		return servicenamefld.getValue();
	}
	
	public NewServiceDialogWebPage selectNewServiceType(String servicetype) {
		selectComboboxValue(servicetypecmb, servicetypedd, servicetype);
	    return this;
	}
	
	public String getNewServiceType() {
		return servicetypecmb.getSelectedValue();
	}
	
	public NewServiceDialogWebPage setNewServiceDescription(String servicedesc) {
		clearAndType(servicedescfld, servicedesc);
	    return this;
	}
	
	public String getNewServiceDescription() {
		return servicedescfld.getValue();
	}
	
	public NewServiceDialogWebPage setNewServiceAccountingID(String serviceaccid) {
		clearAndType(serviceaccidfld, serviceaccid);
	    return this;
	}
	
	public String getNewServiceAccountingID() {
		return serviceaccidfld.getValue();
	}
	
	public NewServiceDialogWebPage selectNewServiceAssignedTo(String serviceassignedto) {
		selectComboboxValue(serviceAssignedToCombobox, serviceAssignedToDropDown, serviceassignedto);
	    return this;
	}

	public NewServiceDialogWebPage selectNewServicePriceType(String priceType) {
		selectComboboxValue(servicesPriceTypeCombobox, servicesPriceTypeDropDown, priceType);
	    return this;
	}
	
	public String getNewServiceAssignedTo() {
		return serviceAssignedToCombobox.getSelectedValue();
	}
	
	public void selectNewService(String multipleCheckboxValue) {
		checkboxSelect(multipleCheckboxValue);
	}
	
	public boolean isNewServiceMultipleSelected() {
		return isCheckboxChecked(multipleCheckBox);
	}
	
	public NewServiceDialogWebPage clickOKButton() {
		clickAndWait(OKbtn);
		waitForLoading();
	    return this;
	}
	
	public void clickCancelButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelbtn)).click();
        wait.until(ExpectedConditions.attributeContains(newServiceDialog, "style", "display: none;"));
    }

    public String getSavedServicePriceType() {
        return wait.until(ExpectedConditions.visibilityOf(servicePriceType)).getText();
    }
}
