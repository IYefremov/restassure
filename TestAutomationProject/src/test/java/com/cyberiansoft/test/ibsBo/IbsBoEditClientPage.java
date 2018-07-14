package com.cyberiansoft.test.ibsBo;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.ibs.pageobjects.webpages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

public class IbsBoEditClientPage extends BasePage {

    @FindBy(xpath = "//h1/span")
    private WebElement pageHeader;

    @FindBy(xpath = "//h2[text()='Billing Profiles']")
    private WebElement billingProfilesHeader;

    @FindBy(xpath = "//tr[contains(@id, 'gridBillingProfiles')]//input[contains(@id, 'EditButton')]")
    private WebElement billingProfilesEditButton;

    @FindBy(xpath = "//input[contains(@id, 'EditFormControl_ddlBillingBase_Input')]")
    private ComboBox billingProfilesBaseCombobox;

    @FindBy(xpath = "//div[contains(@id, 'EditFormControl_ddlBillingBase_DropDown')]")
    private DropDown billingProfilesBaseDropDown;

    @FindBy(xpath = "//tr[contains(@id, 'gridAgreements')]//input[contains(@id, 'EditButton')]")
    private WebElement clientAgreementsEditButton;

    @FindBy(xpath = "//input[contains(@id, 'EditFormControl_ddlStatus_Input')]")
    private ComboBox clientAgreementsStatusCombobox;

    @FindBy(xpath = "//div[contains(@id, 'EditFormControl_ddlStatus_DropDown')]")
    private DropDown clientAgreementsStatusDropDown;

    @FindBy(xpath = "//input[contains(@id, 'gridBillingProfiles') and contains(@id, 'EditFormControl_btnUpdate')]")
    private WebElement billingProfilesUpdateButton;

    @FindBy(xpath = "//input[contains(@id, 'gridAgreements') and contains(@id, 'EditFormControl_btnUpdate')]")
    private WebElement clientAgreementsUpdateButton;

    @FindBy(xpath = "//input[contains(@id, 'gridBillingProfiles') and contains(@id, 'AddNewRecordButton')]")
    private WebElement newBillingProfilesButton;

    @FindBy(xpath = "//input[contains(@id, 'EditFormControl_cbIsActive')]")
    private WebElement activeCheckbox;

    @FindBy(xpath = "//span[text()='Billing Profile successfully updated.']")
    private WebElement billingProfileUpdateNotification;

    @FindBy(xpath = "//span[text()='Agreement successfully updated.']")
    private WebElement clientAgreementsUpdateNotification;

    @FindBy(xpath = "//span/input[contains(@id, 'gridBillingProfiles') and (@checked='checked')]")
    private WebElement billingProfileActivated;

    @FindBy(xpath = "//span/input[contains(@id, 'gridBillingProfiles') and not (@checked='checked')]")
    private WebElement billingProfileDeactivated;

    @FindBy(xpath = "//td[contains(text(), 'Payment Term')]")
    private WebElement paymentTerm;

    @FindBy(xpath = "//input[contains(@id, 'ContentPlaceHolder1_gridBillingProfiles') and contains(@id, 'EditFormControl_ddlPaymentTerms_Input')]")
    private ComboBox paymentTermCombobox;

    @FindBy(xpath = "//input[contains(@id, 'ContentPlaceHolder1_gridBillingProfiles') and contains(@id, 'EditFormControl_ddlPaymentTerms_Input')]")
    private WebElement paymentTermInput;

    @FindBy(xpath = "//div[contains(@id, 'ContentPlaceHolder1_gridBillingProfiles') and contains(@id, 'EditFormControl_ddlPaymentTerms_DropDown')]")
    private DropDown paymentTermDropDown;

    @FindBy(xpath = "//input[contains(@id, 'ContentPlaceHolder1_gridBillingProfiles') and contains(@id, 'EditFormControl_btnInsert')]")
    private WebElement billingProfilesInsertButton;

    @FindBy(xpath = "//input[contains(@id, 'ContentPlaceHolder1_gridBillingProfiles') and contains(@id, 'EditFormControl_btnCancel')]")
    private WebElement billingProfilesCancelButton;

    @FindBy(xpath = "//input[contains(@id, 'ContentPlaceHolder1_gridBillingProfiles') and contains(@id, 'gbcDeleteColumn')]")
    private WebElement billingProfilesDeleteButton;

    @FindBy(xpath = "//td[contains(text(), 'Old Invoices')]")
    private WebElement oldInvoicesService;

    @FindBy(xpath = "//input[contains(@id, 'ContentPlaceHolder1_gridBillingProfiles') and contains(@id, 'EditFormControl_rmypEfectiveBPeriod_dateInput') and @type='text']")
    private WebElement effectiveBillingProfilePeriodField;

    @FindBy(xpath = "//input[contains(@id, 'ContentPlaceHolder1_gridBillingProfiles') and contains(@id, 'EditFormControl_rmypExpirationBPeriod_dateInput') and @type='text']")
    private WebElement expirationBillingProfilePeriodField;

    public IbsBoEditClientPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public boolean isEditClientPageOpened() {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(pageHeader, "AMT Demo Sites>>ReconPro Demo Application"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public IbsBoEditClientPage clickBillingProfilesEditButton() {
        clickButton(billingProfilesEditButton);
        return this;
    }

    public IbsBoEditClientPage clickClientAgreementsEditButton() {
        clickButton(clientAgreementsEditButton);
        return this;
    }

    private void clickButton(WebElement button) {
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        waitIbsBoForUpdate();
    }

    public IbsBoEditClientPage clickIsActiveCheckbox() {
        wait.until(ExpectedConditions.elementToBeClickable(activeCheckbox)).click();
        waitABit(300);
        return this;
    }

    public IbsBoEditClientPage selectClientAgreementsActiveStatus() {
        selectComboboxValue(clientAgreementsStatusCombobox, clientAgreementsStatusDropDown, "Active");
        return this;
    }

    public IbsBoEditClientPage selectClientAgreementsSuspendedStatus() {
        selectComboboxValue(clientAgreementsStatusCombobox, clientAgreementsStatusDropDown, "Suspended");
        return this;
    }

    public IbsBoEditClientPage selectBillingProfilesActivatedBase() {
        selectComboboxValue(billingProfilesBaseCombobox, billingProfilesBaseDropDown, "Activated Devices");
        return this;
    }

    public IbsBoEditClientPage selectBillingProfilesLicenseUsageBase() {
        selectComboboxValue(billingProfilesBaseCombobox, billingProfilesBaseDropDown, "License Usage");
        return this;
    }

    public IbsBoEditClientPage clickBillingProfilesUpdateButton() {
        clickButton(billingProfilesUpdateButton);
        return this;
    }

    public IbsBoEditClientPage clickClientAgreementsUpdateButton() {
        clickButton(clientAgreementsUpdateButton);
        return this;
    }

    public boolean isBillingProfileUpdated() {
        return isUpdated(billingProfileUpdateNotification);
    }

    public boolean areClientAgreementsUpdated() {
        return isUpdated(clientAgreementsUpdateNotification);
    }

    private boolean isUpdated(WebElement notification) {
        try {
            wait.until(ExpectedConditions.visibilityOf(notification));
        return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isBillingProfileActivated() {
        try {
            wait.until(ExpectedConditions.visibilityOf(billingProfileActivated));
        return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBillingProfileDeactivated() {
        try {
            wait.until(ExpectedConditions.visibilityOf(billingProfileDeactivated));
        return true;
        } catch (Exception e) {
            return false;
        }
    }

    public IbsBoEditClientPage verifyBillingProfileIsDeactivated(String sectionBillingProfileItems, String effectiveDate, String expirationDate) {
        if (isBillingProfileActivated()) {
            deactivateBillingProfile(sectionBillingProfileItems, effectiveDate, expirationDate);
            Assert.assertTrue(isBillingProfileUpdated(), "The Billing Profile has not been updated");
            Assert.assertTrue(isBillingProfileDeactivated(),
                    "The Billing Profile is activated, but has to be deactivated");
        }
        return this;
    }

    public IbsBoEditClientPage verifyBillingProfileIsActivated(String sectionBillingProfileItems, String effectiveDate, String expirationDate) {
        if (isBillingProfileDeactivated()) {
            activateBillingProfile(sectionBillingProfileItems, effectiveDate, expirationDate);
            Assert.assertTrue(isBillingProfileUpdated(), "The Billing Profile has not been updated");
            Assert.assertTrue(isBillingProfileActivated(),
                    "The Billing Profile is deactivated, but has to be activated");
        }
        return this;
    }

    public void activateBillingProfile(String sectionBillingProfileItems, String effectiveDate, String expirationDate) {
        clickBillingProfilesEditButton();
        scrollDownToText(sectionBillingProfileItems);
        clickIsActiveCheckbox();
        fillEffectiveBillingProfilePeriodField(effectiveDate);
        fillExpirationBillingProfilePeriodField(expirationDate);
        selectBillingProfilesActivatedBase();
        clickBillingProfilesUpdateButton();
    }

    public void deactivateBillingProfile(String sectionBillingProfileItems, String effectiveDate, String expirationDate) {
        clickBillingProfilesEditButton();
        scrollDownToText(sectionBillingProfileItems);
        clickIsActiveCheckbox();
        fillEffectiveBillingProfilePeriodField(effectiveDate);
        fillExpirationBillingProfilePeriodField(expirationDate);
        selectBillingProfilesLicenseUsageBase();
        clickBillingProfilesUpdateButton();
    }

    public IbsBoEditClientPage clickAddNewBillingProfilesButton() {
        clickButton(newBillingProfilesButton);
        wait.until(ExpectedConditions.visibilityOf(paymentTerm));
        return this;
    }

    public IbsBoEditClientPage selectPaymentTerm(String paymentType) {
        selectComboboxValue(paymentTermCombobox, paymentTermDropDown, paymentType);
        return this;
    }

    public IbsBoEditClientPage clickBillingProfilesInsertButton() {
        clickButton(billingProfilesInsertButton);
        return this;
    }

    public boolean verifyPaymentTermIsYearly() {
        return wait.until(ExpectedConditions.attributeContains(paymentTermInput, "value", "Yearly"));
    }

    public boolean verifyPaymentTermIsMonthly() {
        return wait.until(ExpectedConditions.attributeContains(paymentTermInput, "value", "Monthly"));
    }

    public IbsBoEditClientPage clickCancelButton() {
        clickButton(billingProfilesCancelButton);
        return this;
    }

    public IbsBoEditClientPage deleteBillingProfilesWithOldInvoicesService() {
        try {
            while (wait.until(ExpectedConditions.visibilityOf(oldInvoicesService)).isDisplayed()) {
                clickButton(billingProfilesDeleteButton);
                handleAlert();
                waitIbsBoForUpdate();
            }
        } catch (Exception ignored) {}
        return this;
    }

    public IbsBoEditClientPage fillExpirationBillingProfilePeriodField(String date) {
        wait.until(ExpectedConditions.visibilityOf(expirationBillingProfilePeriodField)).clear();
        expirationBillingProfilePeriodField.sendKeys(date);
        expirationBillingProfilePeriodField.sendKeys(Keys.ENTER);
        return this;
    }

    public IbsBoEditClientPage fillEffectiveBillingProfilePeriodField(String date) {
        wait.until(ExpectedConditions.visibilityOf(effectiveBillingProfilePeriodField)).clear();
        effectiveBillingProfilePeriodField.sendKeys(date);
        effectiveBillingProfilePeriodField.sendKeys(Keys.ENTER);
        return this;
    }
}