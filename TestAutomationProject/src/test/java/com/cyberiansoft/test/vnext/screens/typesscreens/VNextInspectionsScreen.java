package com.cyberiansoft.test.vnext.screens.typesscreens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.steps.SearchSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.InspectionListElement;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

@Getter
public class VNextInspectionsScreen extends VNextBaseTypeScreen {

    @FindBy(xpath = "//*[@data-autotests-id='inspections-list']")
    private WebElement rootElement;

    @FindBy(xpath = "//div[contains(@class, 'page inspections-list')]")
    private WebElement inspectionsScreen;

    @FindBy(xpath = "//*[@data-autotests-id='inspections-list']")
    private WebElement inspectionslist;

    @FindBy(xpath = "//*[@action='multiselect-actions-approve']")
    private WebElement multiSelectInspApproveBtn;

    @FindBy(xpath = "//*[@action='multiselect-actions-archive']")
    private WebElement multiselectinsparchivebtn;

    @FindBy(xpath = "//*[@action='multiselect-actions-email-inspection']")
    private WebElement multiselectInspectionEmailBtn;

    @FindBy(xpath = "//*[contains(@class,'searchlist-nothing-found')]")
    private WebElement nothingFounfPanel;

    @FindBy(xpath = "//*[@data-autotests-id='inspections-list']/div")
    private List<InspectionListElement> inspectionsList;

    final public static int MAX_NUMBER_OF_INPECTIONS = 51;

    public VNextInspectionsScreen() {
        PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
    }

    public void clickAddInspectionButton() {
        clickAddButton();
    }

    public String getFirstInspectionNumber() {
        return inspectionslist.findElement(By.xpath(".//*[@action='select']/*[@class='checkbox-item-title']")).getText();
    }

    public InspectionListElement getInspectionElement(String inspectionId) {
        WaitUtils.waitUntilElementIsClickable(inspectionslist);
        WaitUtils.getGeneralFluentWait().until((webdriver) -> inspectionsList.size() > 0);
        return inspectionsList.stream().filter(listElement -> listElement.getId().equals(inspectionId)).findFirst().orElseThrow(() -> new RuntimeException("Inspection not found " + inspectionId));
    }

    public String getInspectionCustomerValue(String inspectionNumber) {
        WebElement inspCell = getInspectionCell(inspectionNumber);
        return inspCell.findElement(By.xpath(".//*[@action='select' and @class='entity-item-title']")).getText();
    }

    public List<String> getAllInspectionsCustomers() {
        waitForInspectionsListIsVisibile();
        WaitUtils.waitUntilElementIsClickable(inspectionslist);
        List<String> inspsCustomers = new ArrayList<>();
        List<WebElement> inspections = inspectionslist.findElements(By.xpath(".//*[@action='select' and @class='entity-item-title']"));
        for (WebElement inspCell : inspections) {
            inspsCustomers.add(inspCell.getText());
        }
        return inspsCustomers;
    }

    public List<String> getAllInspectionsNumbers() {
        List<String> inspsNumbers = new ArrayList<>();
        inspectionsList.forEach(inspCell -> inspsNumbers.add(inspCell.getId()));
        return inspsNumbers;
    }

    //todo rewrite
    public String getInspectionStatusValue(String inspectionNumber) {
        WaitUtils.elementShouldBeVisible(rootElement,true);
        if (!WaitUtils.isElementPresent(By.xpath("//div[contains(@class, 'checkbox-item-title') and text()='" + inspectionNumber + "']")))
            clearSearchField();
        WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(inspectionslist, By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + inspectionNumber + "']")));
        WebElement inspCell = getInspectionCell(inspectionNumber);
        return inspCell.findElement(By.xpath(".//*[@action='select']/div/*[contains(@class, 'entity-item-status-')]")).getText();
    }

    public void clickOnFirstInspectionWithStatus(String inspStatus) {
        clearSearchField();
        tap(inspectionslist.findElement(By.xpath(".//*[contains(@class, 'entity-item-status-') and text()='" + inspStatus + "']")));
    }

    public String getFirstInspectionPrice() {
        return inspectionslist.findElement(By.xpath(".//div[@class='checkbox-item-title checkbox-item-price']")).getText();
    }

    public String getInspectionPriceValue(String inspectionNumber) {
        WebElement inspCell = getInspectionCell(inspectionNumber);
        return inspCell.findElement(By.xpath(".//div[@class='checkbox-item-title checkbox-item-price']")).getText();
    }

    public String getInspectionApprovedPriceValue(String inspectionNumber) {
        WebElement inspCell = getInspectionCell(inspectionNumber);
        return inspCell.findElement(By.xpath(".//div[@class='entity-item-approved-amount']")).getText();
    }

    public WebElement getInspectionCell(String inspectionNumber) {
        waitForInspectionsListIsVisibile();
        return getListCell(inspectionslist, inspectionNumber);
    }

    public void waitForInspectionsListIsVisibile() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.visibilityOf(inspectionslist));
    }

    public boolean isNotesIconPresentForInspection(String inspectionNumber) {
        WebElement inspCell = getInspectionCell(inspectionNumber);
        return inspCell.findElements(By.xpath(".//*[@data-autotests-id='estimation_notes']")).size() > 0;
    }

    public boolean isEmailSentIconPresentForInspection(String inspectionNumber) {
        WebElement inspCell = getInspectionCell(inspectionNumber);
        return inspCell.findElements(By.xpath(".//*[@data-autotests-id='estimation_email_sent']")).size() > 0;
    }

    public int getNumberOfInspectionsInList() {
        return inspectionsList.size();
    }

    public boolean isInspectionExists(String inspectionNumber) {
        WaitUtils.elementShouldBeVisible(inspectionsScreen,true);
        if (nothingFounfPanel.isDisplayed())
            return false;
        WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.visibilityOf(inspectionslist));
        return inspectionslist.findElements(By.xpath(".//div[@class='checkbox-item-title' and text()='" + inspectionNumber + "']")).size() > 0;
    }

    public void switchToTeamInspectionsView() {
        switchToTeamView();
    }

    public boolean isTeamInspectionsViewActive() {
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
        WaitUtils.elementShouldBeVisible(inspectionsScreen,true);
        return isTeamViewActive();
    }

    public void switchToMyInspectionsView() {
        switchToMyView();
    }

    public boolean isMyInspectionsViewActive() {
        return isMyViewActive();
    }

    //todo make a step!!!
    public void searchInpectionByFreeText(String searchtext) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.visibilityOf(inspectionsScreen));
        WaitUtils.waitUntilElementIsClickable(inspectionsScreen.findElement(By.xpath(".//*[@class='page-content']")));
        SearchSteps.searchByText(searchtext);
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Downloading data']"));
    }

    public void selectInspection(String inspectionNumber) {
        WebElement inspcell = getInspectionCell(inspectionNumber);
        if (inspcell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") == null)
            tap(inspcell.findElement(By.xpath(".//input[@type='checkbox']")));
    }

    public void unselectInspection(String inspectionNumber) {
        WebElement inspcell = getInspectionCell(inspectionNumber);
        if (inspcell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") != null)
            tap(inspcell.findElement(By.xpath(".//input[@type='checkbox']")));
    }

    public void clickMultiselectInspectionsArchiveButton() {
        tap(multiselectinsparchivebtn);
        WaitUtils.waitLoadDialogDisappears();
    }

    public void waitNotificationMessageDissapears() {
        WaitUtils.getGeneralFluentWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='notifier-contaier']")));
        WaitUtils.getGeneralFluentWait().until(ExpectedConditions.invisibilityOf(
                ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(By.xpath("//div[@class='notifier-contaier']"))
        ));
    }
}
