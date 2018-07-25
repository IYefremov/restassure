package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class QuestionsFormsWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_ctl00_Content_Main_gvForms_ctl00")
	private WebTable questionformstable;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvSections_ctl00")
	private WebTable questionsectionstable;

	@FindBy(id = "ctl00_ctl00_Content_Main_gvTemplates_ctl00")
	private WebTable printtemplatestable;

	////////////////////////////////////
	@FindBy(id = "ctl00_ctl00_Content_Main_gvSections_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addquestionsectionbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl03_ctl01_SectionCard_sectionName")
	private TextField newquestionsectionnamefld;

	@FindBy(xpath = "//label[contains(@for, 'SectionCard_sectionExpanded')]")
	private WebElement expandedchkbox;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl03_ctl02_BtnOk")
	private WebElement newquestionsectionnamedialogOKbtn;

	////////////////////////////////////
	@FindBy(id = "ctl00_ctl00_Content_Main_gvForms_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addquestionformbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_QuestionFormCard_formName")
	private TextField newquestionformmefld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement newquestionformnamedialogOKbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_QuestionFormCard_dlSections_lAvailable")
	private WebElement listofavailablesections;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_QuestionFormCard_dlSections_btnAddToAssigned")
	private WebElement availablesectionsaddtoassignedbtn;

	public QuestionsFormsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
		wait.until(ExpectedConditions.visibilityOf(questionformstable.getWrappedElement()));
	}

	public void verifyQuestionSectionsTableColumnsAreVisible() {
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Question Sections')]")).isDisplayed());
		Assert.assertTrue(questionformstable.tableColumnExists("Form Name"));
		Assert.assertTrue(questionformstable.tableColumnExists("Order"));
	}

	public void verifyQuestionFormsTableColumnsAreVisible() {
		wait.until(ExpectedConditions.visibilityOf(questionsectionstable.getWrappedElement()));
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Question Forms')]")).isDisplayed());
		Assert.assertTrue(questionsectionstable.tableColumnExists("Section"));
		Assert.assertTrue(questionsectionstable.tableColumnExists("Expanded"));
		Assert.assertTrue(questionsectionstable.tableColumnExists("Copy"));
	}

	public void verifyPrintTemplatesTableColumnsAreVisible() {
		wait.until(ExpectedConditions.visibilityOf(printtemplatestable.getWrappedElement()));
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Print Templates')]")).isDisplayed());
		Assert.assertTrue(printtemplatestable.tableColumnExists("Print Template"));
		Assert.assertTrue(printtemplatestable.tableColumnExists("Description"));
	}

	public void clickAddQuestionSectionButton() {
		clickAndWait(addquestionsectionbtn);
	}

	public void createQuestionSection(String questionsectionname) {
		if (isQuestionSectionExists(questionsectionname)) {
			deleteQuestionSections(questionsectionname);
		}
		clickAddQuestionSectionButton();
		clearAndType(newquestionsectionnamefld, questionsectionname);
		clickAndWait(newquestionsectionnamedialogOKbtn);
    }

	public void createQuestionSection(String questionsectionname, boolean expanded) {
		if (isQuestionSectionExists(questionsectionname)) {
			deleteQuestionSections(questionsectionname);
		};
		clickAddQuestionSectionButton();
		clearAndType(newquestionsectionnamefld, questionsectionname);
		if (expanded) {
			checkboxSelect(expandedchkbox);
		}

		clickAndWait(newquestionsectionnamedialogOKbtn);
	}

	public QuestionsSectionDialogWebPage clickQuestionsLinkForQuestionSection(String questionsectionname) {
		List<WebElement> questionsectionsrows = getQuestionSectionTableRows();
		for (WebElement questionsectionsrow : questionsectionsrows) {
			if (questionsectionsrow.getText().contains(questionsectionname)) {
				questionsectionsrow.findElement(By.xpath(".//td/a[contains(text(), 'Questions')]")).click();
				waitForNewTab();
				break;
			}
		}
		return PageFactory.initElements(
				driver, QuestionsSectionDialogWebPage.class);
	}

	public void addQuestionForQuestionSection(String questionsectionname, String questionname) {
		QuestionsSectionDialogWebPage questionsectiondialog = clickQuestionsLinkForQuestionSection(questionsectionname);
		final String mainWindowHandle = driver.getWindowHandle();
		// iterate through your windows
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		questionsectiondialog.createNewQuestionForQuestionSection(questionname);
		closeNewTab(mainWindowHandle);
	}

	public void clickAddQuestionFormButton() {
		clickAndWait(addquestionformbtn);
	}

	public void createQuestionForm(String questionformname) {
		clickAddQuestionFormButton();
		wait.until(ExpectedConditions.elementToBeClickable(newquestionformmefld.getWrappedElement()));
		setNewQuestionFormName(questionformname);
		clickNewQuestionFormOKButton();
	}

	public void createQuestionFormAndAssignSection(String questionformname, String sectionname) {
		clickAddQuestionFormButton();
		wait.until(ExpectedConditions.elementToBeClickable(newquestionformmefld.getWrappedElement()));
		setNewQuestionFormName(questionformname);
		assignSectionToQuestionForm(sectionname);
		clickNewQuestionFormOKButton();
	}

	public void setNewQuestionFormName(String questionformname) {
		clearAndType(newquestionformmefld, questionformname);
	}

	public void clickNewQuestionFormOKButton() {
		clickAndWait(newquestionformnamedialogOKbtn);
	}

	public void clickEditButtonForQuestionForm(String questionformname) {
		List<WebElement> questionformsrows = getQuestionFormTableRows();
		for (WebElement questionformsrow : questionformsrows) {
			if (questionformsrow.getText().contains(questionformname)) {
				questionformsrow.findElement(By.xpath(".//td/input[@title='Edit']")).click();
				waitForLoading();
				break;
			}
		}
	}

	public void clickPreviewLinkForQuestionForm(String questionformname) {
		List<WebElement> questionformsrows = getQuestionFormTableRows();
		for (WebElement questionformsrow : questionformsrows) {
			if (questionformsrow.getText().contains(questionformname)) {
				questionformsrow.findElement(By.xpath(".//td/a[contains(text(), 'Preview')]")).click();
				break;
			}
		}
	}

	public void editAndAssignSectionToQuestionForm(String questionformname, String questionsectionname) {
		clickEditButtonForQuestionForm(questionformname);
		assignSectionToQuestionForm(questionsectionname);
		clickNewQuestionFormOKButton();
	}

	public void assignSectionToQuestionForm(String questionsectionname) {
		wait.until(ExpectedConditions.elementToBeClickable(listofavailablesections));
		Select availablesections = new Select(listofavailablesections);
		availablesections.selectByVisibleText(questionsectionname);
		click(availablesectionsaddtoassignedbtn);
	}

	public void verifyQuestionIsAssignedToQuestionFormViaPreview(String questionformname, String questionname) {
		clickPreviewLinkForQuestionForm(questionformname);
		waitForNewTab();
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it = handles.iterator();
		// iterate through your windows
		while (it.hasNext()) {
			String parent = it.next();
			String newwin = it.next();
			driver.switchTo().window(newwin);
			waitABit(1500);
			WebElement questionformcontent =  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//td[@id='ctl00_tdContent']"))));
			waitABit(1500);
			Assert.assertTrue(questionformcontent.findElement(By.xpath(".//td/b[contains(text(), '" + questionname + "')]")).isDisplayed());
			driver.close();
			driver.switchTo().window(parent);
		}
	}

	public List<WebElement> getQuestionSectionTableRows() {
		return questionsectionstable.getTableRows();
	}

	public List<WebElement> getQuestionFormTableRows() {
		return questionformstable.getTableRows();
	}

	public void deleteQuestionForm(String questionformname) {
		List<WebElement> questionformsrows = getQuestionFormTableRows();
		for (WebElement questionformsrow : questionformsrows) {
			if (questionformsrow.getText().contains(questionformname)) {
				questionformsrow.findElement(By.xpath(".//td/input[@title='Delete']")).click();
				try {
					wait.until(ExpectedConditions.alertIsPresent());
					Alert alert = driver.switchTo().alert();
					alert.accept();
				}catch(Exception e){}
				waitForLoading();
				break;
			}
		}
	}

	public void deleteQuestionSections(String questionsectionname) {
		List<WebElement> questionsectionsrows = getQuestionSectionTableRows();
		for (WebElement questionsectionsrow : questionsectionsrows) {
			if (questionsectionsrow.getText().contains(questionsectionname)) {
				questionsectionsrow.findElement(By.xpath(".//td/input[@title='Delete']")).click();
				try {
					wait.until(ExpectedConditions.alertIsPresent());
					Alert alert = driver.switchTo().alert();
					alert.accept();
				}catch(Exception e){}
				waitForLoading();
				break;
			}
		}
	}

	public boolean isQuestionFormExists(String questionformname) {
		boolean exists =  questionformstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + questionformname + "']")).size() > 0;
		return exists;
	}

	public boolean isQuestionSectionExists(String questionsectionname) {
		boolean exists =  questionsectionstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + questionsectionname + "']")).size() > 0;
		return exists;
	}
}
