package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class VNextStatusScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//*[@action='update-main-db']")
	private WebElement updatemaindbbtn;
	
	@FindBy(xpath="//*[@action='feedback']")
	private WebElement feedbackbtn;
	
	@FindBy(xpath="//*[@action='back-office']")
	private WebElement gotoBObtn;
	
	public VNextStatusScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(updatemaindbbtn));
	}
	
	public void updateMainDB() {
		clickUpdateAppdata();
		BaseUtils.waitABit(10000);
		WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 800);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='" +
				VNextAlertMessages.DATA_HAS_BEEN_DOWNLOADED_SECCESSFULY + "']")));
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		Employee employee = null;
		try {
			employee = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/team-device-employee.json"), Employee.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
		loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());

	}
	
	public void clickUpdateAppdata() {
		WaitUtils.click(updatemaindbbtn);
	}
	
	public VNextHomeScreen clickBackButton() {
		clickScreenBackButton();
		return new VNextHomeScreen(appiumdriver);
	}
	
	public VNextFeedbackScreen clickFeedbackButton() {
		tap(feedbackbtn);
		return new VNextFeedbackScreen(appiumdriver);
	}
	
	public VNextEmailVerificationScreen goToBackOfficeButton() {
		tap(gotoBObtn);
		return new VNextEmailVerificationScreen(appiumdriver);
	}

}
