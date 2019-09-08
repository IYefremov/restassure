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
import static com.cyberiansoft.test.inhouse.testcases.BaseTestCase.browsertype;

public class ClientContactsWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_Content_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement adduserbtn;

	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebTable clientcontactstable;

	public ClientContactsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
		wait.until(ExpectedConditions.visibilityOf(adduserbtn));
	}


	public void clickAddUserBtn() {
		try {
			driver.switchTo().alert().accept();
		} catch (Exception e) {
		}
		clickAndWait(adduserbtn);
	}


	public boolean isClientContactPresentInTable(String contactfstname, String contactlstname) {
		boolean exists = clientcontactstable.getWrappedElement().
				findElements(By.xpath(".//td/b[text()='" + contactfstname + " " + contactlstname + "']")).size() > 0;
		return exists;
	}


	public List<WebElement> getClientContactsTableRows() {
		wait.until(ExpectedConditions.visibilityOf(clientcontactstable.getWrappedElement()));
		return clientcontactstable.getTableRows();
	}


	public WebElement getTableRowWithContact(String contactfstname) {
		List<WebElement> clientstablerows = getClientContactsTableRows();
		for (WebElement clientstablerow : clientstablerows) {
			if (clientstablerow.findElement(By.xpath(".//td[" + clientcontactstable.getTableColumnIndex("Full Name / Company") + "]/b")).getText().contains(contactfstname)) {
				return clientstablerow;
			}
		}
		return null;
	}


	public void clickEditContactUser(String contactfstname) {
		WebElement clientstablerow = getTableRowWithContact(contactfstname);
		if (clientstablerow != null) {
			clientstablerow.findElement(By.xpath(".//td[1]/input")).click();
		} else {
			Assert.assertTrue(false, "Can't find client: " + contactfstname);
		}
	}


	public void clickDeleteClientContact(String contactfstname) {
		WebElement clientstablerow = getTableRowWithContact(contactfstname);
		if (clientstablerow != null) {
			clientstablerow.findElement(By.xpath(".//td[2]/input")).click();
		} else {
			Assert.assertTrue(false, "Can't find client: " + contactfstname);
		}
		try {
			Thread.sleep(3000);
			driver.switchTo().alert().accept();
			waitForLoading();
		} catch (Exception e) {
		}

	}

	public String getClientStatusText(String contactfstname) {
		WebElement clientstablerow = getTableRowWithContact(contactfstname);
		if (clientstablerow != null) {
			return clientstablerow.findElement(By.xpath(".//td[5]/span")).getText();
		} else {
			Assert.assertTrue(false, "Can't find client: " + contactfstname);
		}
		return null;
	}

	public String getClientStatusTextColor(String contactfstname) {
		WebElement clientstablerow = getTableRowWithContact(contactfstname);
		if (clientstablerow != null) {
			String value = clientstablerow.findElement(By.xpath(".//td[5]/span")).getAttribute("style").toLowerCase();
			return value.substring(value.indexOf(' ') + 1, value.indexOf(';'));
		} else {
			Assert.assertTrue(false, "Can't find client: " + contactfstname);
		}
		return null;
	}


	public void waitForMessageChange(String contactfstname, String message) {
		while (!getClientStatusText(contactfstname).equals(message)) {
			driver.navigate().refresh();
		}
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

	public void verifyColorForContact(ClientContactsWebPage clientContactsWebPage, String colorForEdge, String color, String contact) {
		if (browsertype.getBrowserTypeString().contains("Edge")) {
			Assert.assertEquals(colorForEdge, clientContactsWebPage.getClientStatusTextColor(contact));
		} else {
			Assert.assertEquals(color, clientContactsWebPage.getClientStatusTextColor(contact));
		}
	}
}
