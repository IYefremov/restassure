package com.cyberiansoft.test.vnextbo.interactions;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.VNextBOAccountInfoBlock;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class VNextBOAccountInfoBlockInteractions {

    private VNextBOAccountInfoBlock accountInfoBlock;

    public VNextBOAccountInfoBlockInteractions() {
        accountInfoBlock = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOAccountInfoBlock.class);
    }

    public void setAccountingId(String accountingId) {
        Utils.clearAndType(accountInfoBlock.getAccountingId(), accountingId);
    }

    public void setAccountingId2(String accountingId2) {
        Utils.clearAndType(accountInfoBlock.getAccountingId2(), accountingId2);
    }

    private void clickExportAsArrow() {
        Utils.clickElement(accountInfoBlock.getExportAsArrow());
    }

    public void setExportAs(String exportAs) {
        Utils.clearAndType(accountInfoBlock.getExportAsInputField(), exportAs);
        Utils.selectOptionInDropDown(accountInfoBlock.getExportAsDropDown(),
                accountInfoBlock.getExportAsListBoxOptions(), exportAs, true);
    }

    public void setClass(String classOption) {
        Utils.clickElement(accountInfoBlock.getClassArrow());
        if (classOption.equals("")) {
            try {
                WaitUtilsWebDriver.waitForVisibility(accountInfoBlock.getClassDropDownNoDataFound(), 3);
            } catch (Exception ignored) {
                Assert.fail("\"No data found message\" hasn't been displayed");
            }
            Utils.clickElement(accountInfoBlock.getClassArrow());
            WaitUtilsWebDriver.waitForInvisibility(accountInfoBlock.getClassDropDown());
        } else {
            Utils.selectOptionInDropDown(accountInfoBlock.getClassDropDown(),
                    accountInfoBlock.getClassListBoxOptions(), classOption, true);
        }
    }

    public void setQbAccount(String qbAccount) {
        Utils.clickElement(accountInfoBlock.getQbAccountArrow());
        Utils.selectOptionInDropDown(accountInfoBlock.getQbAccountDropDown(),
                accountInfoBlock.getQbAccountListBoxOptions(), qbAccount, true);
    }

    public void clickPoNumberRequiredCheckbox() {
        Utils.clickElement(accountInfoBlock.getPoNumberRequiredCheckbox());
    }

    public boolean isPoNumberUpfrontRequiredCheckboxClickable() {
        try {
            WaitUtilsWebDriver.waitForElementToBeClickable(accountInfoBlock.getPoNumberUpfrontRequiredCheckbox(), 3);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public void verifyAccountInfoBlockIsExpanded() {
        final List<WebElement> infoBlockElements = Arrays.asList(
                accountInfoBlock.getAccountingId(),
                accountInfoBlock.getAccountingId2(),
                accountInfoBlock.getExportAsArrow(),
                accountInfoBlock.getClassArrow(),
                accountInfoBlock.getQbAccountArrow());
        try {
            WaitUtilsWebDriver.getFluentWait(Duration.ofMillis(200), Duration.ofSeconds(5))
                    .until(driver -> infoBlockElements
                            .stream()
                            .anyMatch(WebElement::isDisplayed));
        } catch (Exception ignored) {
            new VNextBOClientsDetailsViewInteractions().clickAccountInfoTab();
        }
    }
}