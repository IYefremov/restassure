package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

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
	
	public boolean isOrderStatusReasonPresent(String orderstatusreason) {
        return orderstatusreasonstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + orderstatusreason + "']")).size() > 0;
	}
	
	public void deleteOrderStatusReason(String orderstatusreason) {
		WebElement row = getTableRowWithOrderStatusReason(orderstatusreason);
		if (row != null) {
			deleteTableRow(row);
		} else {
			Assert.fail("Can't find " + orderstatusreason + " order status reason");
		}
	}
	
	public void clickEditOrderStatusReason(String orderstatusreason) {
		WebElement row = getTableRowWithOrderStatusReason(orderstatusreason);
		if (row != null) {
			clickEditTableRow(row);
		} else
			Assert.fail("Can't find " + orderstatusreason + " order status reason");
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

	public boolean checkPresenceOfTabs() {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_gvReasons")));
            } catch (Exception e) {
                return false;
            }
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_gvFlags")));
            } catch (Exception e) {
                return false;
            }
        return true;
    }

	public boolean checkEmployeeRoleSettingsGridColumnsAndRows() {		
		try{			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//th[text()='Role']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//th[text()='Adding Services']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//th[text()='Editing Services']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//th[text()='Removing Services']")));
			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//td[text()='Employee']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//td[text()='Inspector']")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//td[text()='Manager']")));
		}catch(Exception e){
			return false;
		}
		return true;
	}

	public boolean checkEmployeeRoleSettingsGridOnOfFieldsAbility() {
		try{
			try{
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl04_btnEnableMonitorCanAddService")));
			}catch(TimeoutException e){
				wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl04_btnDisableMonitorCanAddService")));
			}
			try{
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl04_btnEnableMonitorCanEditService")));
			}catch(TimeoutException e){
				wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl04_btnDisableMonitorCanEditService")));
			}
			try{
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl04_btnEnableMonitorCanRemoveService")));
			}catch(TimeoutException e){
				wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl04_btnDisableMonitorCanRemoveService")));
			}
			try{
				wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl06_btnDisableMonitorCanAddService")));
			}catch(TimeoutException e){
				wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl06_btnEnableMonitorCanAddService")));
			}
			try{
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl06_btnDisableMonitorCanEditService")));
			}catch(TimeoutException e){
				wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl06_btnEnableMonitorCanEditService")));
			}
			try{
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl06_btnEnableMonitorCanRemoveService")));
			}catch(TimeoutException e){
				wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl06_btnDisableMonitorCanRemoveService")));
			}
			try{
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl08_btnDisableMonitorCanAddService")));
			}catch(TimeoutException e){
				wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl08_btnEnableMonitorCanAddService")));
			}
			try{
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl08_btnEnableMonitorCanEditService")));
			}catch(TimeoutException e){
				wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl08_btnDisableMonitorCanEditService")));
			}
			try{
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl08_btnEnableMonitorCanRemoveService")));
			}catch(TimeoutException e){
				wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_ctl00_Content_Main_gvAppEmployeeRoleSettingsPresenter_ctl00_ctl08_btnDisableMonitorCanRemoveService")));
			}

		}catch(TimeoutException e){
			return false;
		}
		return true;
	}

    public void verifyOrderStatusReasonIsNotPresent(String reason) {
        if (isOrderStatusReasonPresent(reason)) {
            deleteOrderStatusReason(reason);
        }
    }
}
