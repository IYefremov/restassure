package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;

public class ClientUsersWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_Content_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement adduserbtn;

	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebTable clientuserstable;

	@FindBy(xpath = "//input[contains(@id, 'btnResend')]")
	private WebElement resendButton;

	public ClientUsersWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickAddUserBtn() {
		clickAndWait(adduserbtn);
	}

	public boolean isClientUserPresentInTable(String clientUsername, String userFirstName) {
		return clientuserstable.getWrappedElement()
				.findElements(By.xpath(".//td/b[text()='" + clientUsername + " " + userFirstName + "']")).size() > 0;
	}

	public List<WebElement> getClientUsersTableRows() {
		wait.until(ExpectedConditions.visibilityOf(clientuserstable.getWrappedElement()));
		return clientuserstable.getTableRows();
	}

	public WebElement getTableRowWithUser(String userfstname) {
		List<WebElement> clientstablerows = getClientUsersTableRows();
		for (WebElement clientstablerow : clientstablerows) {
			if (clientstablerow
					.findElement(By.xpath(".//td[" + clientuserstable.getTableColumnIndex("Full Name") + "]/b"))
					.getText().contains(userfstname)) {
				return clientstablerow;
			}
		}
		return null;
	}

	public void clickEditClientUser(String userfstname) {
		WebElement clientstablerow = getTableRowWithUser(userfstname);
		if (clientstablerow != null) {
			clientstablerow.findElement(By.xpath(".//td[1]/input")).click();
		} else {
			Assert.fail("Can't find client: " + userfstname);
		}
	}

	public void clickDeleteClientUser(String userfstname) {
		waitABit(1500);
		WebElement clientstablerow = getTableRowWithUser(userfstname);
		if (clientstablerow != null) {
			clientstablerow.findElement(By.xpath(".//td[2]/input")).click();
		} else {
			Assert.fail("Can't find client: " + userfstname);
		}
		driver.switchTo().alert().accept();
		waitForLoading();

	}

	public void closePage() {
		String mainWindow = "";
		String thisWindow = driver.getWindowHandle();
		for (String window : driver.getWindowHandles()) {
			if (!window.equals(thisWindow))
				mainWindow = window;
		}
		try {
			driver.switchTo().window(thisWindow).close();
			driver.switchTo().window(mainWindow);
			driver.switchTo().defaultContent();
		} catch (NoSuchWindowException ignored) {
		}
	}

	public void clickResendButton() {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_gv_ctl00_ctl04_lbMsg")));
		wait.until(ExpectedConditions.elementToBeClickable(resendButton)).click();
		driver.switchTo().alert().accept();
		waitForLoading();
		waitABit(1000);
	}

	public void verifyClientUserDoesNotExist(String clientUserName, String userFirstName) {
		if (isClientUserPresentInTable(clientUserName, userFirstName)) {
			clickDeleteClientUser(clientUserName);
		}
	}
}
