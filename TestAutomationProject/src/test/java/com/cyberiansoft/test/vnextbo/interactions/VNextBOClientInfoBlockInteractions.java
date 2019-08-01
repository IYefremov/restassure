package com.cyberiansoft.test.vnextbo.interactions;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.VNextBOClientInfoBlock;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class VNextBOClientInfoBlockInteractions {

    private VNextBOClientInfoBlock clientInfoBlock;

    public VNextBOClientInfoBlockInteractions() {
        clientInfoBlock = PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOClientInfoBlock.class);
    }

    public void setRetailCompanyType() {
        Utils.clickElement(clientInfoBlock.getRetailRadioButton());
    }

    public void setWholesaleCompanyType() {
        Utils.clickElement(clientInfoBlock.getWholesaleRadioButton());
    }

    public void setCompanyName(String companyName) {
        Utils.clearAndType(clientInfoBlock.getCompanyInputField(), companyName);
    }

    public void setFirstName(String firstName) {
        Utils.clearAndType(clientInfoBlock.getFirstNameInputField(), firstName);
    }

    public void setLastName(String lastName) {
        Utils.clearAndType(clientInfoBlock.getLastNameInputField(), lastName);
    }

    public void setEmail(String email) {
        Utils.clearAndType(clientInfoBlock.getEmailInputField(), email);
    }

    public void setPhone(String phone) {
        Utils.clearAndType(clientInfoBlock.getPhoneInputField(), phone);
    }

    public void verifyClientInfoBlockIsExpanded() {
        final List<WebElement> infoBlockElements = Arrays.asList(
                clientInfoBlock.getRetailRadioButton(),
                clientInfoBlock.getWholesaleRadioButton(),
                clientInfoBlock.getCompanyInputField(),
                clientInfoBlock.getFirstNameInputField(),
                clientInfoBlock.getLastNameInputField(),
                clientInfoBlock.getEmailInputField(),
                clientInfoBlock.getPhoneInputField());
        try {
            WaitUtilsWebDriver.getFluentWait(Duration.ofMillis(200), Duration.ofSeconds(5))
                    .until(driver -> infoBlockElements
                            .stream()
                            .anyMatch(WebElement::isDisplayed));
        } catch (Exception ignored) {
            new VNextBOClientsDetailsViewInteractions().clickClientsInfoTab();
        }
    }
}