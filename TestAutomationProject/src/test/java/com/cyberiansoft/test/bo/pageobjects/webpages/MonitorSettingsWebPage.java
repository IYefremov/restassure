package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class MonitorSettingsWebPage  extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gvReasons_ctl00")
	private WebTable orderstatusreasonstable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gvReasons_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addorderstatusreasonbtn;
	
	//New Order Status Reason
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_reasonCard_comboStatus_Input")
	private ComboBox statuscmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_reasonCard_comboStatus_DropDown")
	private DropDown statusdd;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_reasonCard_tbReason")
	private TextField statusreasonfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement neworderstatusreasonOKBtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement neworderstatusreasoncancelBtn;
	
	public MonitorSettingsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public List<WebElement>  getOrderStatusReasonsTableRows() {
		return orderstatusreasonstable.getTableRows();
	}
	
	public WebElement getTableRowWithOrderStatusReason(String orderstatusreason) {
		List<WebElement> rows = getOrderStatusReasonsTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[4]")).getText().equals(orderstatusreason)) {
				return row;
			}
		} 
		return null;
	}
	
	public boolean isOrderStatusReasonExists(String orderstatusreason) {
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean exists =  orderstatusreasonstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + orderstatusreason + "']")).size() > 0;
		this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return exists;
	}
	
	public void deleteOrderStatusReason(String orderstatusreason) {
		WebElement row = getTableRowWithOrderStatusReason(orderstatusreason);
		if (row != null) {
			deleteTableRow(row);
		} else {
			Assert.assertTrue(false, "Can't find " + orderstatusreason + " order status reason");	
		}
	}
	
	public void clickEditOrderStatusReason(String orderstatusreason) {
		WebElement row = getTableRowWithOrderStatusReason(orderstatusreason);
		if (row != null) {
			clickEditTableRow(row);
		} else 
			Assert.assertTrue(false, "Can't find " + orderstatusreason + " order status reason");
	}
	
	public void createNewOrderStatusReason(String orderstatus, String orderstatusreason) {
		clickAddOrderStatusReasonButton();
		selectNewOrderStatus(orderstatus);
		setNewOrderStatusReason(orderstatusreason);
		clickNewOrderStatusReasonOKButton();
	}
	
	public void clickAddOrderStatusReasonButton() {
		clickAndWait(addorderstatusreasonbtn);
	}
	
	public void selectNewOrderStatus(String orderstatus) {
		selectComboboxValue(statuscmb, statusdd, orderstatus);
	}
	
	public String getNewOrderStatus() {	
		return statuscmb.getSelectedValue();
	}
	
	public void setNewOrderStatusReason(String orderstatusreason) {
		clearAndType(statusreasonfld, orderstatusreason);
	}
	
	public void clickNewOrderStatusReasonOKButton() {
		clickAndWait(neworderstatusreasonOKBtn);
	}
	
	public void clickNewOrderStatusReasonCancelButton() {
		click(neworderstatusreasoncancelBtn);
	}

}
