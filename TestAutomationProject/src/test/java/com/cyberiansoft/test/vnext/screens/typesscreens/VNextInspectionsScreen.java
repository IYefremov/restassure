package com.cyberiansoft.test.vnext.screens.typesscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
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
    private WebElement multiselectinspapprovebtn;

    @FindBy(xpath = "//*[@action='multiselect-actions-archive']")
    private WebElement multiselectinsparchivebtn;

    final public static int MAX_NUMBER_OF_INPECTIONS = 50;

    public VNextInspectionsScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public VNextInspectionsScreen() {
    }

    public VNextCustomersScreen clickAddInspectionButton() {
        clickAddButton();
        return new VNextCustomersScreen(appiumdriver);
    }

    public VNextInspectionTypesList clickAddInspectionWithPreselectedCustomerButton() {
        clickAddButton();
        return new VNextInspectionTypesList(appiumdriver);
    }

    public boolean isAddInspectionButtonVisible() {
        return inspectionsScreen.findElement(By.xpath(".//*[@action='add']")).isDisplayed();
    }

    public VNextHomeScreen clickBackButton() {
        clickScreenBackButton();
        return new VNextHomeScreen(appiumdriver);
    }

    public String getFirstInspectionNumber() {
        return inspectionslist.findElement(By.xpath(".//*[@action='select']/*[@class='checkbox-item-title']")).getText();
    }

    public String getInspectionNumberValue(WebElement inspCell) {
        return inspCell.findElement(By.xpath(".//*[@action='select']/*[@class='checkbox-item-title']")).getText();
    }

    public String getInspectionCustomerValue(String inspectionNumber) {
        WebElement inspCell = getInspectionCell(inspectionNumber);
        return inspCell.findElement(By.xpath(".//*[@action='select' and @class='entity-item-title']")).getText();
    }

    public String getInspectionCustomerValue(WebElement inspCell) {
        return inspCell.findElement(By.xpath(".//*[@action='select' and @class='entity-item-title']")).getText();
    }

    public List<String> getAllInspectionsCustomers() {
        List<String> inspsCustomers = new ArrayList<>();
        List<WebElement> inspections = getInspectionsList();
        for (WebElement inspCell : inspections) {
            inspsCustomers.add(getInspectionCustomerValue(inspCell));
        }
        return inspsCustomers;
    }

    public List<String> getAllInspectionsNumbers() {
        List<String> inspsNumbers = new ArrayList<>();
        List<WebElement> inspections = getInspectionsList();
        for (WebElement inspCell : inspections) {
            inspsNumbers.add(getInspectionNumberValue(inspCell));
        }
        return inspsNumbers;
    }

    public String getInspectionStatusValue(String inspectionNumber) {
        WaitUtils.elementShouldBeVisible(rootElement,true);
        if (!elementExists("//div[contains(@class, 'checkbox-item-title') and text()='" + inspectionNumber + "']"))
            clearSearchField();
        WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(inspectionslist, By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + inspectionNumber + "']")));
        WebElement inspCell = getInspectionCell(inspectionNumber);
        return inspCell.findElement(By.xpath(".//div[@action='select']/div/*[contains(@class, 'entity-item-status-')]")).getText();
    }

    public VNextInspectionsMenuScreen clickOnFirstInspectionWithStatus(String inspStatus) {
        clearSearchField();
        tap(inspectionslist.findElement(By.xpath(".//*[contains(@class, 'entity-item-status-') and text()='" + inspStatus + "']")));
        return new VNextInspectionsMenuScreen(appiumdriver);
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

    public VNextInspectionsMenuScreen clickOnInspectionByInspNumber(String inspectionNumber) {
        WaitUtils.elementShouldBeVisible(rootElement,true);
        if (!elementExists("//div[contains(@class, 'checkbox-item-title') and text()='" + inspectionNumber + "']"))
            clearSearchField();
        WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(inspectionslist, By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + inspectionNumber + "']")));
        try {
            wait = new WebDriverWait(appiumdriver, 60);
            wait.until(ExpectedConditions.elementToBeClickable(inspectionslist.findElement(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + inspectionNumber + "']")))).click();
        } catch (WebDriverException e) {
            BaseUtils.waitABit(500);
            tap(inspectionslist.findElement(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + inspectionNumber + "']")));
        }
        //tap(inspectionslist.findElement(By.xpath(".//div[contains(@class, 'checkbox-item-title') and text()='" + inspectionNumber + "']")));
        return new VNextInspectionsMenuScreen(appiumdriver);
    }

    public VNextVehicleInfoScreen clickOpenInspectionToEdit(String inspectionNumber) {
        VNextInspectionsMenuScreen InspectionsMenuScreen = clickOnInspectionByInspNumber(inspectionNumber);
        return InspectionsMenuScreen.clickEditInspectionMenuItem();
    }

    public VNextEmailScreen clickOnInspectionToEmail(String inspectionNumber) {
        VNextInspectionsMenuScreen InspectionsMenuScreen = clickOnInspectionByInspNumber(inspectionNumber);
        return InspectionsMenuScreen.clickEmailInspectionMenuItem();
    }

    public VNextNotesScreen openInspectionNotes(String inspectionNumber) {
        VNextInspectionsMenuScreen InspectionsMenuScreen = clickOnInspectionByInspNumber(inspectionNumber);
        return InspectionsMenuScreen.clickNotesInspectionMenuItem();
    }

    private List<WebElement> getInspectionsList() {
        return inspectionslist.findElements(By.xpath("./div[@class='entity-item accordion-item']"));
    }

    public int getNumberOfInspectionsInList() {
        return getInspectionsList().size();
    }

    public int getNumberOfInspectionsOnTheScreen() {
        return getInspectionsList().size();
    }

    public boolean waitUntilInspectionDisappears(String inspectionNumber) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath(".//div[@class='checkbox-item-title' and text()='" + inspectionNumber + "']")));
    }

    public boolean isInspectionExists(String inspectionNumber) {
        WaitUtils.elementShouldBeVisible(rootElement,true);
        if (!inspectionslist.isDisplayed())
            return false;
        WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.visibilityOf(inspectionslist));
        wait = new WebDriverWait(appiumdriver, 30);
        //return !wait.until(ExpectedConditions.invisibilityOf(inspectionslist.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + inspectionNumber + "']"))));
        return inspectionslist.findElements(By.xpath(".//div[@class='checkbox-item-title' and text()='" + inspectionNumber + "']")).size() > 0;
    }

    public void hidePickerWheel() {
        if (appiumdriver.findElements(By.xpath("//div[@class='picker-item']")).size() > 0) {
            tap(appiumdriver.findElement(By.xpath("//div[@class='toolbar-panel-right ']/a[@class='link close-picker']")));
        }
    }


    public void switchToTeamInspectionsView() {
        switchToTeamView();
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading inspections']"));
    }

    public boolean isTeamInspectionsViewActive() {
        WaitUtils.elementShouldBeVisible(inspectionsScreen,true);
        return isTeamViewActive();
    }

    public void switchToMyInspectionsView() {
        switchToMyView();
    }

    public boolean isMyInspectionsViewActive() {
        return isMyViewActive();
    }

    public void searchInpectionByFreeText(String searchtext) {
        searchByFreeText(searchtext);
    }

    public void selectInspection(String inspectionNumber) {
        WebElement workordercell = getInspectionCell(inspectionNumber);
        if (workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") == null)
            tap(workordercell.findElement(By.xpath(".//input[@type='checkbox']")));
    }

    public void unselectInspection(String inspectionNumber) {
        WebElement workordercell = getInspectionCell(inspectionNumber);
        if (workordercell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") != null)
            tap(workordercell.findElement(By.xpath(".//input[@type='checkbox']")));
    }

    public VNextApproveInspectionsScreen clickMultiselectInspectionsApproveButton() {
        tap(multiselectinspapprovebtn);
        return new VNextApproveInspectionsScreen(appiumdriver);
    }

    public void clickMultiselectInspectionsArchiveButton() {
        tap(multiselectinsparchivebtn);
    }

    public VNextApproveInspectionsScreen clickMultiselectInspectionsApproveButtonAndSelectCustomer(AppCustomer customer) {
        tap(multiselectinspapprovebtn);
        VNextCustomersScreen customersScreen = new VNextCustomersScreen(appiumdriver);
        customersScreen.selectCustomer(customer);
        return new VNextApproveInspectionsScreen(appiumdriver);
    }

    public VNextInspectionsScreen changeCustomerForInspection(String inspectionNumber, AppCustomer newCustomer) {
        VNextInspectionsMenuScreen inspectionsMenuScreen = clickOnInspectionByInspNumber(inspectionNumber);
        VNextCustomersScreen customersScreen = inspectionsMenuScreen.clickChangeCustomerMenuItem();
        customersScreen.selectCustomer(newCustomer);
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickInformationDialogYesButton();
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Saving Inspection customer...']"));
        return this;
    }

    public VNextInspectionsScreen changeCustomerForWorkOrderViaSearch(String inspectionNumber, AppCustomer newCustomer) {
        VNextInspectionsMenuScreen inspectionsMenuScreen = clickOnInspectionByInspNumber(inspectionNumber);
        VNextCustomersScreen customersScreen = inspectionsMenuScreen.clickChangeCustomerMenuItem();
        customersScreen.switchToRetailMode();
        customersScreen.searchCustomerByName(newCustomer.getFullName());
        customersScreen.selectCustomer(newCustomer);
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickInformationDialogYesButton();
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Saving Inspection customer...']"));
        return this;
    }

    public VNextInspectionsScreen changeCustomerToWholesailForInspection(String inspectionNumber, AppCustomer newWholesailCustomer) {
        VNextInspectionsMenuScreen inspectionsMenuScreen = clickOnInspectionByInspNumber(inspectionNumber);
        VNextCustomersScreen customersScreen = inspectionsMenuScreen.clickChangeCustomerMenuItem();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(newWholesailCustomer);
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickInformationDialogYesButton();
        return this;
    }
}
