package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValueWithTyping;

public class APADStatementWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_BtnFind")
	private WebElement searchButton;

	@FindBy(id = "ctl00_ctl00_Content_Main_report_ctl09")
	private WebElement report;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_ddlArea")
	private ComboBox areaCombobox;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_ddlArea_DropDown")
	private DropDown areaDropDown;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_ddlTeam")
	private ComboBox teamCombobox;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_ddlTeam_DropDown")
	private DropDown teamDropDown;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_ddlEmployee_Input")
	private TextField employeeTextField;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_ddlEmployee_DropDown")
	private DropDown employeeDropDown;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_ddlClients_Input")
	private TextField customerTextField;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_filterer_ddlClients_DropDown")
	private DropDown customerDropDown;


	public APADStatementWebPage(WebDriver driver) {
		super(driver);
		try {
			wait.until(ExpectedConditions.titleContains("ReconPro :: APAD Statement"));
		} catch (Exception e) {
			Assert.fail("The APAD Statement webpage has not been opened", e);
		}
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void selectArea(String area) {
		selectComboboxValue(areaCombobox, areaDropDown, area);
	}

	public void selectTeam(String team) {
		selectComboboxValue(teamCombobox, teamDropDown, team);
	}

	public void selectEmployee(String employee) {
		selectComboboxValueWithTyping(employeeTextField, employeeDropDown, employee);
	}

	public void selectCustomer(String customer) {
		selectComboboxValueWithTyping(customerTextField, customerDropDown, customer);
	}

	public void clickSearchButton() {
		wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
		waitForLoading();
	}

	public boolean isReportGenerated() {
		try {
			wait.until(ExpectedConditions.visibilityOf(report));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean areDefaultReportValuesDisplayed(String area, String team, String customer,
												   String employee, String defaultValue) {
		return areElementsDisplayed(
				Arrays.asList(
						getDefaultParameter(area, defaultValue),
						getDefaultParameter(team, defaultValue),
						getDefaultParameter(customer, defaultValue),
						getDefaultParameter(employee, defaultValue)
				));
	}

	public boolean areReportValuesDisplayed(String areaValue, String teamValue,
											String customerValue, String employeeValue) {
		return areElementsDisplayed(
				Arrays.asList(
						getParameter(areaValue),
						getParameter(teamValue),
						getParameter(customerValue),
						getParameter(employeeValue)
				));
	}

	public boolean areReportColumnsDisplayed(String invoice, String year, String stock, String VIN, String RO,
											 String service, String amount, String billed) {
		return areElementsDisplayed(
				Arrays.asList(
						getReportColumn(invoice),
						getReportColumn(year),
						getReportColumn(stock),
						getReportColumn(VIN),
						getReportColumn(RO),
						getReportColumn(service),
						getReportColumn(amount),
						getReportColumn(billed)
				));
	}

	private boolean areElementsDisplayed(List<WebElement> elements) {
		try {
			return wait.until(ExpectedConditions
					.visibilityOfAllElements(elements))
					.stream()
					.allMatch(WebElement::isDisplayed);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private WebElement getDefaultParameter(String parameter, String defaultValue) {
		return wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[text()='"
				+ parameter + "']/../following-sibling::td/div[text()='(" + defaultValue + ")']"))));
	}

	private WebElement getParameter(String value) {
		return wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[text()='" + value + "']"))));
	}

	private WebElement getReportColumn(String column) {
		return wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='" + column + "']"))));
	}
}