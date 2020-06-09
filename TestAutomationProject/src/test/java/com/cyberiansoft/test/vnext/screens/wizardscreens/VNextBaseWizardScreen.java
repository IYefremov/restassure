package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypeData;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypeData;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextTypeScreenContext;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.steps.MenuSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Getter
public class VNextBaseWizardScreen extends VNextBaseScreen {

    public static VNextTypeScreenContext typeScreenContext;
    public static InspectionTypes inspectionType;
    public static WorkOrderTypes workOrderType;

    @FindBy(xpath = "//div[@data-page]")
    private WebElement rootElement;

    @FindBy(xpath = "//div[@class='estimation-number']")
    private WebElement inspectionnumber;

    @FindBy(xpath = "//*[@action='more_actions']")
    private WebElement menubtn;

    @FindBy(xpath = "//div[@data-name='cancel']")
    private WebElement cancelinspectionmenu;

    @FindBy(xpath = "//div[@data-name='save']")
    private WebElement savemenu;

    @FindBy(xpath = "//*[@action='save']")
    private WebElement savebtn;

    @FindBy(xpath = "//div[@data-name='notes']")
    private WebElement inspectionnotesmenu;

    @FindBy(xpath = "//*[@data-automation-id='search-icon']")
    private WebElement searchIcon;

    @FindBy(xpath = "//input[@data-autotests-id='search-input']")
    private WebElement searchInput;

    @FindBy(xpath = "//*[@data-autotests-id='search-cancel']")
    private WebElement cancelSearchBtn;

    @FindBy(xpath = "//*[@id='total']")
    private WebElement totalPrice;

    public VNextBaseWizardScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
    }

    public VNextBaseWizardScreen() {
    }

    public void cancelInspection() {
        clickCancelMenuItem();
        VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
        informationdlg.clickInformationDialogYesButtonAndGetMessage();
    }


    public void clickCancelMenuItem() {
        clickMenuButton();
        tap(cancelinspectionmenu);
    }

    public void clickSaveViaMenuAsFinal() {
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickFinalButton();
    }

    public void saveInspectionAsDraft() {
        clickWizardMenuSaveButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickDraftButton();
    }

    public void saveInspectionViaMenu() {
        clickWizardMenuSaveButton();
        if (inspectionType != null)
            if (new InspectionTypeData(inspectionType).isCanBeFinalDraft())
                clickSaveViaMenuAsFinal();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        WaitUtils.elementShouldBeVisible(inspectionsScreen.getInspectionsScreen(), true);
    }

    public VNextWorkOrdersScreen saveWorkOrderViaMenu() {
        clickWizardMenuSaveButton();
        if (workOrderType != null)
            if (new WorkOrderTypeData(workOrderType).isCanBeDraft())
                clickSaveViaMenuAsFinal();
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
        WaitUtils.elementShouldBeVisible(workOrdersScreen.getRootElement(), true);
        workOrdersScreen.clearSearchField();
        return workOrdersScreen;
    }

    public VNextWorkOrdersScreen saveWorkOrderAsDraft() {
        clickWizardMenuSaveButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickDraftButton();
        return new VNextWorkOrdersScreen();
    }

    public void clickMenuButton() {
        WaitUtils.elementShouldBeVisible(menubtn, true);
        WaitUtils.waitUntilElementIsClickable(menubtn);
        tap(menubtn);
    }

    public void clickWizardMenuSaveButton() {
        clickMenuButton();
        clickSaveMenuButton();
    }

    public void clickSaveMenuButton() {
        WaitUtils.elementShouldBeVisible(savemenu, true);
        WaitUtils.waitUntilElementIsClickable(savemenu);
        tap(savemenu);
    }

    public String getNewInspectionNumber() {
        WaitUtils.waitUntilElementIsClickable(inspectionnumber);
        return inspectionnumber.getText().trim();
    }

    public VNextNotesScreen clickInspectionNotesOption() {
        clickMenuButton();
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        return new VNextNotesScreen();
    }

    public String getInspectionTotalPriceValue() {
        WaitUtils.getGeneralFluentWait().until(ExpectedConditions.textToBePresentInElement(appiumdriver.findElement(By.xpath("//*[@id='total']")),"$"));
        return appiumdriver.findElement(By.xpath("//*[@id='total']")).getText().trim();
    }

    public void clickSaveButton() {
        WaitUtils.waitUntilElementIsClickable(savebtn);
        tap(savebtn);
    }

    public void saveInvoiceAsDraft() {
        clickSaveButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickDraftButton();
    }

    public void saveInvoiceAsFinal() {
        clickSaveButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickFinalButton();
    }

    public void cancelWizard() {
        clickMenuButton();
        MenuSteps.selectMenuItem(MenuItems.CANCEL_INVOICE);
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickInformationDialogYesButton();
    }

    public boolean isSearchFilterEmpty() {
        return searchIcon.findElement(By.xpath(".//*[contains(@class, 'icon-empty-query')]")).isDisplayed();
    }

}
