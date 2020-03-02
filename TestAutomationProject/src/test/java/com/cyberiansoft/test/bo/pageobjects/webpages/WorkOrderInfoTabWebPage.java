package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class WorkOrderInfoTabWebPage extends BaseWebPage {
	
	public WorkOrderInfoTabWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public boolean isServiceSelectedForWorkOrder(String service) {
		return driver.findElements(By.xpath(".//table/tbody/tr/td/div[text()='" + service + "']")).size() > 0;
	}
	
	public boolean isServicePriceCorrectForWorkOrder(String serviceprice) {
		return driver.findElements(By.xpath(".//table/tbody/tr/td[2]/div[text()='" + serviceprice + "']")).size() > 0;
	}

}
