package com.cyberiansoft.test.bo.pageobjects.webpages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class WorkOrderInfoTabWebPage extends BaseWebPage {
	
	public WorkOrderInfoTabWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public boolean isServiceSelectedForWorkOrder(String service) {
		boolean exists =  driver.findElements(By.xpath(".//table/tbody/tr/td/div[text()='" + service + "']")).size() > 0;
		return exists;
	}
	
	public boolean isServicePriceCorrectForWorkOrder(String serviceprice) {
		boolean exists =  driver.findElements(By.xpath(".//table/tbody/tr/td[2]/div[text()='" + serviceprice + "']")).size() > 0;
		return exists;
	}

}
