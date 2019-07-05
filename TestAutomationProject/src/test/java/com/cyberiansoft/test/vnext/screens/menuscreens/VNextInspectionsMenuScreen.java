package com.cyberiansoft.test.vnext.screens.menuscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.VNextEmailScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.VNextViewScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.steps.GeneralSteps;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextInspectionsMenuScreen extends VNextBasicMenuScreen {

    @FindBy(xpath = "//a[@data-name='view']")
    private WebElement viewinspectionbtn;

    @FindBy(xpath = "//a[@data-name='edit']")
    private WebElement editinspectionbtn;

    @FindBy(xpath = "//a[@handler='_createOrder']")
    private WebElement createwoinspectionbtn;

    @FindBy(xpath = "//a[@data-name='notes']")
    private WebElement notesinspectionbtn;

    @FindBy(xpath = "//a[@data-name='refresh']")
    private WebElement refreshpicturesinspectionbtn;

    @FindBy(xpath = "//a[@data-name='email']")
    private WebElement emailinspectionbtn;

    @FindBy(xpath = "//a[@data-name='archive']")
    private WebElement archiveinspectionbtn;

    @FindBy(xpath = "//a[@data-name='approve']")
    private WebElement approveinspectionbtn;

    @FindBy(xpath = "//a[@data-name='addSupplement']")
    private WebElement addsupplementbtn;

    @FindBy(xpath = "//a[@data-name='changeCustomer']")
    private WebElement changecustomerbtn;

    @FindBy(xpath = "//div[@class='close-popup close-actions']")
    private WebElement closebtn;

    public VNextInspectionsMenuScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public VNextInspectionsMenuScreen() {
    }

    public VNextVehicleInfoScreen clickEditInspectionMenuItem() {
        clickMenuItem(editinspectionbtn);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        return vehicleInfoScreen;
    }

    public VNextInformationDialog clickEditInspectionMenuItemWithAlert() {
        clickMenuItem(editinspectionbtn);
        return new VNextInformationDialog(appiumdriver);
    }

    public VNextEmailScreen clickEmailInspectionMenuItem() {
        clickMenuItem(emailinspectionbtn);
        return new VNextEmailScreen(appiumdriver);
    }

    public VNextNotesScreen clickNotesInspectionMenuItem() {
        clickMenuItem(notesinspectionbtn);
        return new VNextNotesScreen();
    }

    public void clickCreateWorkOrderInspectionMenuItem() {
        clickMenuItem(createwoinspectionbtn);
    }

    public boolean isCreateWorkOrderMenuPresent() {
        return createwoinspectionbtn.isDisplayed();
    }

    public void archiveInspection() {
        clickArchiveInspectionMenuItem();
        VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
        informationdlg.clickInformationDialogArchiveButton();
    }

    public void clickArchiveInspectionMenuItem() {
        clickMenuItem(archiveinspectionbtn);
    }

    public void clickApproveInspectionMenuItem() {
        clickMenuItem(approveinspectionbtn);
    }

    public boolean isApproveMenuPresent() {
        return approveinspectionbtn.isDisplayed();
    }

    public boolean isArchivwMenuPresent() {
        return archiveinspectionbtn.isDisplayed();
    }

    public boolean isViewInspectionMenuPresent() {
        return viewinspectionbtn.isDisplayed();
    }

    public boolean isNotesMenuPresent() {
        return notesinspectionbtn.isDisplayed();
    }

    public boolean isEmailInspectionMenuPresent() {
        return emailinspectionbtn.isDisplayed();
    }

    public VNextViewScreen clickViewInspectionMenuItem() {
        clickMenuItem(viewinspectionbtn);
        BaseUtils.waitABit(3000);
        return new VNextViewScreen(appiumdriver);
    }

    public VNextVehicleInfoScreen clickAddSupplementInspectionMenuItem() {
        clickMenuItem(addsupplementbtn);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        return vehicleInfoScreen;
    }

    public boolean isAddSupplementInspectionMenuItemPresent() {
        return addsupplementbtn.isDisplayed();
    }

    public boolean isChangeCustomerMenuPresent() {
        return changecustomerbtn.isDisplayed();
    }

    public boolean isEditInspectionMenuButtonExists() {
        return editinspectionbtn.isDisplayed();
    }

    public VNextInspectionsScreen clickCloseInspectionMenuButton() {
        clickCloseMenuButton();
        return new VNextInspectionsScreen(appiumdriver);
    }

    public VNextCustomersScreen clickChangeCustomerMenuItem() {
        clickMenuItem(changecustomerbtn);
        return new VNextCustomersScreen(appiumdriver);
    }
}
