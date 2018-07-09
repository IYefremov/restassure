package com.cyberiansoft.test.ibsBo;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.ibs.pageobjects.webpages.BasePage;
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

    @FindBy(xpath = "//input[contains(@id, 'EditFormControl_cbIsActive')]")
    private WebElement activeCheckbox;

    @FindBy(xpath = "//span[text()='Billing Profile successfully updated.']")
    private WebElement billingProfileUpdateNotification;

    @FindBy(xpath = "//span[text()='Agreement successfully updated.']")
    private WebElement clientAgreementsUpdateNotification;

    @FindBy(xpath = "//span/input[contains(@id, 'gridBillingProfiles') and (@checked='checked')]")
    private WebElement billingProfileActivated;

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

    public IbsBoEditClientPage verifyBillingProfileIsNotActivated(String billingProfileItemsSection) {
        if (isBillingProfileActivated()) {
            clickBillingProfilesEditButton();
            scrollDownToText(billingProfileItemsSection);
            clickIsActiveCheckbox();
            clickBillingProfilesUpdateButton();
            Assert.assertTrue(isBillingProfileUpdated(), "The Billing Profile has not been updated");
            Assert.assertFalse(isBillingProfileActivated(),
                    "The Billing Profile is activated, but has to be deactivated");
        }
        return this;
    }
}