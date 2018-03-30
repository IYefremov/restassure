package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.By;
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

	public ClientUsersWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
		wait.until(ExpectedConditions.visibilityOf(adduserbtn));
	}

	public AddEditClientUsersDialogWebPage clickAddUserBtn() {
		clickAndWait(adduserbtn);
		return PageFactory.initElements(driver, AddEditClientUsersDialogWebPage.class);
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

	public void clickResendButton() throws InterruptedException {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_gv_ctl00_ctl04_lbMsg")));
		driver.findElement(By.id("ctl00_Content_gv_ctl00_ctl06_btnResend")).click();
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
		wait.until(
				ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}

}
