package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class ClientUsersWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_Content_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement adduserbtn;

	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebTable clientuserstable;

	public ClientUsersWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
		wait.until(ExpectedConditions.visibilityOf(adduserbtn));
	}

	public AddEditClientUsersDialogWebPage clickAddUserBtn() {
		clickAndWait(adduserbtn);
		return PageFactory.initElements(driver, AddEditClientUsersDialogWebPage.class);
	}

	public boolean isClientUserExistsInTable(String clientusername, String userlstname) {
		boolean exists = clientuserstable.getWrappedElement()
				.findElements(By.xpath(".//td/b[text()='" + clientusername + " " + userlstname + "']")).size() > 0;
		return exists;
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

	public AddEditClientUsersDialogWebPage clickEditClientUser(String userfstname) {
		WebElement clientstablerow = getTableRowWithUser(userfstname);
		if (clientstablerow != null) {
			clientstablerow.findElement(By.xpath(".//td[1]/input")).click();
		} else {
			Assert.assertTrue(false, "Can't find client: " + userfstname);
		}
		return PageFactory.initElements(driver, AddEditClientUsersDialogWebPage.class);
	}

	public void clickDeleteClientUser(String userfstname) {
		WebElement clientstablerow = getTableRowWithUser(userfstname);
		if (clientstablerow != null) {
			clientstablerow.findElement(By.xpath(".//td[2]/input")).click();
		} else {
			Assert.assertTrue(false, "Can't find client: " + userfstname);
		}

		driver.switchTo().alert().accept();
		waitUntilPageReloaded();

	}

	public void closePage() {
		String mainWindow = "";
		String thisWindow = driver.getWindowHandle();
		for (String window : driver.getWindowHandles()) {
			if (!window.equals(thisWindow))
				mainWindow = window;
		}
		driver.switchTo().window(thisWindow).close();
		driver.switchTo().window(mainWindow);
		driver.switchTo().defaultContent();
	}

	

}
