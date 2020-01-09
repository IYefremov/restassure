package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
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

    public VNextStatusScreen(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(updatemaindbbtn));
	}
	
	public VNextHomeScreen updateMainDB() {
		clickUpdateAppdata();
		BaseUtils.waitABit(2000);
		try {
            WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Start sync']")));
			wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 10);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Start sync']"))).click();
		} catch (TimeoutException e) {
			//do nothing
		}
		BaseUtils.waitABit(10000);
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 800);
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
		return loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());


	}

	public VNextHomeScreen updateMainDB(Employee employee) {
		clickUpdateAppdata();
		BaseUtils.waitABit(10000);
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 800);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='" +
				VNextAlertMessages.DATA_HAS_BEEN_DOWNLOADED_SECCESSFULY + "']")));
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();

		//VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
		//return loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());
		return clickBackButton();
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
