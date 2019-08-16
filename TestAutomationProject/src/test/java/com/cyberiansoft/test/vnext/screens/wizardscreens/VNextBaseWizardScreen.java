package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
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
    private WebElement saveinspectionmenu;

    @FindBy(xpath = "//*[@data-name='save']")
    private WebElement saveworkordermenu;

    @FindBy(xpath = "//div[@data-name='notes']")
    private WebElement inspectionnotesmenu;

    @FindBy(xpath = "//*[@data-automation-id='search-icon']")
    private WebElement searchIcon;

    @FindBy(xpath = "//input[@data-autotests-id='search-input']")
    private WebElement searchInput;

    public VNextBaseWizardScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public VNextBaseWizardScreen() {
    }

    public VNextInspectionsScreen cancelInspection() {
        clickCancelMenuItem();
        VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
        String msg = informationdlg.clickInformationDialogYesButtonAndGetMessage();
        return new VNextInspectionsScreen(appiumdriver);
    }

    public VNextWorkOrdersScreen cancelWorkOrder() {
        clickCancelMenuItem();
        VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
        String msg = informationdlg.clickInformationDialogYesButtonAndGetMessage();
        return new VNextWorkOrdersScreen(appiumdriver);
    }

    public void clickCancelMenuItem() {
        clickMenuButton();
        tap(cancelinspectionmenu);
    }

    public void clcikSaveViaMenuAsFinal() {
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickFinalButton();
    }

    public VNextInspectionsScreen saveInspectionAsDraft() {
        clickSaveInspectionMenuButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickDraftButton();
        return new VNextInspectionsScreen(appiumdriver);
    }

    public VNextInspectionsScreen saveInspectionViaMenu() {
        clickSaveInspectionMenuButton();
        if (inspectionType != null)
            if (new InspectionTypeData(inspectionType).isCanBeFinalDraft())
                clcikSaveViaMenuAsFinal();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(appiumdriver);
        WaitUtils.elementShouldBeVisible(inspectionsScreen.getInspectionsScreen(), true);
        return inspectionsScreen;
    }

    public VNextWorkOrdersScreen saveWorkOrderViaMenu() {
        clickSaveWorkOrderMenuButton();
        if (workOrderType != null)
            if (new WorkOrderTypeData(workOrderType).isCanBeDraft())
                clcikSaveViaMenuAsFinal();
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen(appiumdriver);
        WaitUtils.elementShouldBeVisible(workOrdersScreen.getRootElement(), true);
        workOrdersScreen.clearSearchField();
        return workOrdersScreen;
    }

    public VNextWorkOrdersScreen saveWorkOrderAsDraft() {
        clickSaveWorkOrderMenuButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickDraftButton();
        return new VNextWorkOrdersScreen(appiumdriver);
    }

    public void clickMenuButton() {
        tap(menubtn);

        BaseUtils.waitABit(1000);
        if (elementExists("//*[@action='more_actions']"))
            try {
                menubtn.click();
            } catch (WebDriverException e) {

            }
    }

    public void clickSaveInspectionMenuButton() {
        clickMenuButton();
        tap(saveinspectionmenu);
    }

    public void clickSaveWorkOrderMenuButton() {
        clickMenuButton();
        WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
        wait.until(ExpectedConditions.visibilityOf(saveworkordermenu));
        BaseUtils.waitABit(1500);
        tap(saveworkordermenu);
    }

    public String getNewInspectionNumber() {
        return inspectionnumber.getText().trim();
    }

    public VNextNotesScreen clickInspectionNotesOption() {
        clickMenuButton();
        tap(inspectionnotesmenu);
        return new VNextNotesScreen();
    }

    public String getInspectionTotalPriceValue() {
        return appiumdriver.findElement(By.xpath("//*[@id='total']")).getText().trim();
    }

    public boolean isSaveButtonVisible() {
        return saveinspectionmenu.isDisplayed();
    }

    public boolean isCancelButtonVisible() {
        return cancelinspectionmenu.isDisplayed();
    }

    public boolean isNotesButtonVisible() {
        return inspectionnotesmenu.isDisplayed();
    }

}
