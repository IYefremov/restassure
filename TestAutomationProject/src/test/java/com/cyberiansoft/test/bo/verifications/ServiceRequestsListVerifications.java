package com.cyberiansoft.test.bo.verifications;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServiceRequestsListWebPage;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cyberiansoft.test.baseutils.WaitUtilsWebDriver.waitForLoading;

public class ServiceRequestsListVerifications {

    private ServiceRequestsListWebPage srListPage;

    public ServiceRequestsListVerifications() {
        srListPage = new ServiceRequestsListWebPage();
    }

    public boolean isSearchPanelExpanded() {
        return Utils.isElementDisplayed(srListPage.getSearchTabExpanded(), 7);
    }

    public void verifySearchFieldsAreVisible() {
        Assert.assertTrue(srListPage.getStatuscmb().isDisplayed());
        Assert.assertTrue(srListPage.getTeamcmb().isDisplayed());
        Assert.assertTrue(srListPage.getTechniciancmb().isDisplayed());
        Assert.assertTrue(srListPage.getTagsfld().isDisplayed());
        Assert.assertTrue(srListPage.getFreetextfld().isDisplayed());
        Assert.assertTrue(srListPage.getFindbtn().isDisplayed());
    }

    public boolean isAcceptIconPresentForFirstServiceRequestFromList() {
        Utils.getActions().moveToElement(srListPage.getFirstSRFromList(), 10, 10).click().perform();
        return Utils.isElementDisplayed(srListPage.getFirstSRFromList().findElement(By.xpath(".//a[@title='Accept']")));
    }

    public boolean appointmentExistsForFirstServiceRequestFromList(String appointmentTime) {
        return Utils.getText(srListPage.getFirstSRFromList().findElement(By.xpath(".//a/span"))).equals(appointmentTime);
    }

    public boolean isInsuranceCompanyPresentForFirstServiceRequestFromList(String insuranceCompany) {
        WaitUtilsWebDriver.waitForLoading();
        return srListPage.getFirstSRFromList().findElements(By.xpath(".//div[@class='" + insuranceCompany + "  ']")).size() > 0;
    }

    public boolean verifySearchResultsByServiceName(String servicename) {
        WaitUtilsWebDriver.waitForVisibility(srListPage.getServiceRequestsList());
        return srListPage.getFirstSRFromList()
                .findElements(By.xpath(".//div[@class='name' and contains(text(), '" + servicename + "')]")).size() > 0;
    }

    public boolean verifySearchResultsByModelIN(String _make, String _model, String _year, String vin) {
        boolean result = false;
        if (!(srListPage.getFirstSRFromList() == null)) {
            result = srListPage.getFirstSRFromList().findElements(By.xpath(
                    ".//div[@class='modelVin' and text()='" + _year + " " + _make + " " + _model + " " + vin + "']"))
                    .size() > 0;
        }
        return result;
    }

    public boolean isCheckInButtonVisible() {
        return isDisplayed(srListPage.getServiceRequestCheckInButton());
    }

    public boolean isServiceIsPresentForForSelectedServiceRequest(String servicename) {
        DriverBuilder.getInstance().getDriver().switchTo().frame(srListPage.getEditServiceRequestPanelFrame());
        return isDisplayed(DriverBuilder.getInstance().getDriver().findElement(By.xpath("//span[contains(text(), '" + servicename + "')]")));
    }

    private boolean isDisplayed(WebElement element) {
        verifyServiceRequestInfoFrameIsOn();
        boolean visible = Utils.isElementDisplayed(element);
        DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
        return visible;
    }

    public void verifyServiceRequestInfoFrameIsOn() {
        DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
        try {
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(srListPage.getEditServiceRequestPanelFrame()));
        } catch (TimeoutException | StaleElementReferenceException e) {
            WaitUtilsWebDriver.waitABit(2000);
        }
    }

    public boolean checkTimeOfLastDescription() {
        verifyServiceRequestInfoFrameIsOn();
        Utils.clickElement(srListPage.getInfoBlockEditButton());
        WaitUtilsWebDriver.waitForElementToBeClickable(srListPage.getAddSRDescription().getWrappedElement());
        try {
            new SimpleDateFormat("dd yyyy hh:mm").parse(srListPage.getDescriptionTime().getText().substring(4, 17));
            Utils.clickElement(srListPage.getCloseServiceRequestButton());
            return true;
        } catch (ParseException e) {
            Utils.clickElement(srListPage.getCloseServiceRequestButton());
            return false;
        }
    }

    public boolean verifyTagsAreAdded(String... tags) {
        for (String tag : tags) {
            if (srListPage.getAllAddedTags().stream().map(e -> e.getText()).map(t -> t.substring(0, t.length() - 3))
                    .collect(Collectors.toList()).contains(tag)) {
                srListPage.getTagField().sendKeys(tag);
                srListPage.getTagField().sendKeys(Keys.ENTER);
                return Utils.isElementDisplayed(By.xpath("//input[contains (@class, 'ui-autocomplete-input not_valid')]"));
            } else {
                srListPage.getTagField().sendKeys(tag);
                srListPage.getTagField().sendKeys(Keys.ENTER);
            }
        }
        return true;
    }

    public boolean isFirstTagRemoved() {
        int prevSize = srListPage.getAllAddedTags().size();
        srListPage.getAllAddedTags().get(0).findElement(By.xpath("//a[contains(@title, 'Removing tag')]")).click();
        return prevSize - srListPage.getAllAddedTags().size() == 1;
    }

    public boolean areTagsAdded(String... tags) {
        verifyServiceRequestInfoFrameIsOn();
        System.out.println(srListPage.getAllAddedTags().stream().map(e -> e.getText()).map(t -> t.substring(0, t.length() - 3))
                .collect(Collectors.toList()));
        List tagsToCheck = new LinkedList(Arrays.asList(tags));
        tagsToCheck.remove(0);
        boolean result = srListPage.getAllAddedTags().stream().map(e -> e.getText()).map(t -> t.substring(0, t.length() - 3))
                .collect(Collectors.toList()).containsAll(tagsToCheck);
        Utils.clickElement(srListPage.getCloseServiceRequestButton());
        return result;
    }

    public boolean isNewDescriptionAddedAndCheckedOld(String newDescription, String prevDescription) {
        verifyServiceRequestInfoFrameIsOn();
        Utils.clickElement(srListPage.getInfoBlockEditButton());
        WaitUtilsWebDriver.waitForElementToBeClickable(srListPage.getAddSRDescription().getWrappedElement());
        Utils.clearAndType(srListPage.getAddSRDescription().getWrappedElement(), newDescription);
        WebElement lastDescription = WaitUtilsWebDriver.waitForVisibilityOfAllOptions(srListPage.getOldDescriptions()).get(0);
        if (!lastDescription.findElement(By.tagName("span")).getText().equals(prevDescription)) {
            return false;
        }
        srListPage.getAddSRDescription().clear();
        Utils.clickElement(srListPage.getInfoBlockDoneButton());

        return DriverBuilder.getInstance().getDriver().findElement(By.className("description-content")).findElement(By.className("infoBlock-valContainer"))
                .getAttribute("style").equals("display: none;");
    }

    public boolean verifyServiceDescriptionIsPresent(String string) {
        verifyServiceRequestInfoFrameIsOn();
        WaitUtilsWebDriver.waitABit(3000);
        Utils.clickElement(srListPage.getInfoBlockEditButton());
        WaitUtilsWebDriver.waitForElementToBeClickable(srListPage.getAddSRDescription().getWrappedElement());
        WebElement lastDescription = srListPage.getOldDescriptions().get(0);
        return lastDescription.findElement(By.tagName("span")).getText().equals(string);
    }

    public boolean verifyDescriptionIconsAreVisible() {
        verifyServiceRequestInfoFrameIsOn();
        boolean documentShown = WaitUtilsWebDriver.waitForAttributeToContain(srListPage.getDescriptionDocuments().findElement(By.tagName("i")), "style", "display : none;");
        boolean answerShown = WaitUtilsWebDriver.waitForAttributeToContain(srListPage.getDescriptionAnswers().findElement(By.tagName("i")), "style", "display : none;");

        return documentShown || answerShown;
    }

    public boolean isServiceRequestDocumentIconVisible() {
        verifyServiceRequestInfoFrameIsOn();
        return WaitUtilsWebDriver.getWait().until(ExpectedConditions
                .not(ExpectedConditions
                        .attributeToBe(DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[contains(@class, 'description-reason')]"))
                                .findElement(By.tagName("i")), "style", "display : none;")));
    }

    public boolean checkElementsInDocument() {
        try {
            srListPage.getDocumentContent().findElement(By.xpath("//h2[contains(text(), 'Documents')]"));
            srListPage.getDocumentContent().findElement(By.xpath("//h3[contains(text(), 'Service Request:')]"));
            srListPage.getDocumentContent().findElement(By.className("add"));
            return srListPage.getDocumentContent().findElements(By.className("rgHeader"))
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList())
                    .containsAll(Arrays.asList("Name/Description", "Size", "Uploaded"));
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean areImageButtonsDisplayed() {
        return Utils.areElementsDisplayed(Arrays.asList(srListPage.getImagesOkButton(), srListPage.getImagesCancelButton(), srListPage.getAddFileButton()));
    }

    public boolean isAddAppointmentFromSRListClosed() {
        return Utils.isElementNotDisplayed(srListPage.getAddAppointmentButton(), 10);
    }

    public boolean checkDefaultAppointmentValuesAndAddAppointmentFomSREdit() {
        if (!(srListPage.getAppointmentFromDateSRedit().getText().isEmpty() && srListPage.getAppointmentToDateSRedit().getText().isEmpty()
                && srListPage.getAppointmentFromTimeSRedit().getText().isEmpty() && srListPage.getAppointmentToTimeSRedit().getText().isEmpty())) {
            return false;
        }

        Utils.clickElement(srListPage.getLocationTypeField());
        if (!DriverBuilder.getInstance().getDriver().findElement(By.className("rcbHovered")).getText().equals("Custom") && srListPage.getAppointmentContent()
                .findElement(By.id("Card_rcbAppointmentLocations_Input")).getAttribute("disabled").equals("disabled")) {
            return false;
        }

        if (!srListPage.getTechniciansField().getAttribute("value").equals("All")
                && srListPage.getAppointmentContent().findElement(By.id("Card_rcbStates_Input")).getAttribute("value").equals("All")) {
            return false;
        }

        if (!srListPage.getAppointmentContent().findElement(By.id("Card_tbxAddress")).getText().isEmpty()
                && srListPage.getAppointmentContent().findElement(By.id("Card_tbxCity")).getText().isEmpty()
                && srListPage.getAppointmentContent().findElement(By.id("Card_tbxZip")).getText().isEmpty()) {
            return false;
        }

        if (!srListPage.getAppointmentContent().findElement(By.id("Card_tbAppointmentClientName")).getText().equals("Alex SASHAZ")
                && srListPage.getAppointmentContent().findElement(By.id("Card_tbAppointmentClientAddress")).getText()
                .equals("407 SILVER SAGE DR., NewYork, 10001")
                && srListPage.getAppointmentContent().findElement(By.id("Card_tbAppointmentClientPhone")).getText()
                .equals("14043801674")
                && srListPage.getAppointmentContent().findElement(By.id("Card_tbAppointmentClientEmail")).getText()
                .equals("ALICIA.VILLALOBOS@KCC.COM")) {
            return false;
        }
        Utils.clickElement(srListPage.getArrow());
        WaitUtilsWebDriver.waitABit(2000);
        try {
            Utils.clickElement(srListPage.getTechniciansField());
            Utils.clickElement(DriverBuilder.getInstance().getDriver().findElement(By.className("rcbList")).findElements(By.className("rcbItem")).get(0));
            WaitUtilsWebDriver.waitABit(500);
            Utils.clickElement(srListPage.getTechniciansField());
            Utils.clickElement(DriverBuilder.getInstance().getDriver().findElement(By.className("rcbList")).findElements(By.className("rcbItem")).get(1));
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }
        WaitUtilsWebDriver.waitABit(1000);
        if (DriverBuilder.getInstance().getDriver().findElement(By.id("gvTechnicians")).findElements(By.tagName("tr")).size() != 4 && DriverBuilder.getInstance().getDriver()
                .findElement(By.id("gvTechnicians")).findElements(By.className("datepicker-container")).size() != 4) {
            return false;
        }
        Utils.clickElement(srListPage.getAddAppButton());
        return true;
    }

    public boolean isStatusDisplayed(String status) {
        DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
        try {
            final String srStatus = WaitUtilsWebDriver.waitForVisibility(By.className("serviceRequestStatus")).getText();
            System.out.println(srStatus);
            return srStatus.equals(status);
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkDefaultAppointmentValuesFromCalendar(String subject) {
        WaitUtilsWebDriver.waitABit(1000);

        Utils.clickElement(srListPage.getAppointmentContentFromCalendar()
                .findElement(By.id("ctl00_ctl00_Content_Main_rcbAppLocations_Input")));

        WaitUtilsWebDriver.waitABit(1000);
        if (!DriverBuilder.getInstance().getDriver().findElement(By.className("rcbHovered")).getText().equals("Custom") && srListPage.getAppointmentContentFromCalendar()
                .findElement(By.id("ctl00_ctl00_Content_Main_rcbAppointmentLocations_Input")).getAttribute("disabled")
                .equals("disabled")) {
            return false;
        }

        WaitUtilsWebDriver.waitABit(1000);
        if (!srListPage.getAppointmentContentFromCalendar().findElement(By.id("ctl00_ctl00_Content_Main_rcbTechnician_Input"))
                .getAttribute("value").equals("All")) {
            return false;
        }

        WaitUtilsWebDriver.waitABit(1000);
        System.out.println(
                DriverBuilder.getInstance().getDriver().findElement(By.id("ctl00_ctl00_Content_Main_rcbStates_Input")).getAttribute("disabled"));
        if (!DriverBuilder.getInstance().getDriver().findElement(By.id("ctl00_ctl00_Content_Main_rcbStates_Input")).getAttribute("disabled")
                .equals("true")) {
            return false;
        }

        WaitUtilsWebDriver.waitABit(1000);
        if (!srListPage.getAppointmentContentFromCalendar().findElement(By.id("ctl00_ctl00_Content_Main_tbxAddress")).getText()
                .isEmpty()
                && srListPage.getAppointmentContentFromCalendar().findElement(By.id("ctl00_ctl00_Content_Main_tbxCity")).getText()
                .equals("L.A.")
                && srListPage.getAppointmentContentFromCalendar().findElement(By.id("ctl00_ctl00_Content_Main_tbxZip")).getText()
                .equals("78523")) {
            return false;
        }

        WaitUtilsWebDriver.waitABit(1000);
        if (!srListPage.getAppointmentContentFromCalendar().findElement(By.id("ctl00_ctl00_Content_Main_tbAppointmentClientName"))
                .getText().equals("Johon Connor")
                && srListPage.getAppointmentContentFromCalendar()
                .findElement(By.id("ctl00_ctl00_Content_Main_tbAppointmentClientAddress")).getText()
                .equals("..., L.A., CA, 78523")
                && srListPage.getAppointmentContentFromCalendar()
                .findElement(By.id("ctl00_ctl00_Content_Main_tbAppointmentClientPhone")).getText()
                .equals("77877")
                && srListPage.getAppointmentContentFromCalendar()
                .findElement(By.id("ctl00_ctl00_Content_Main_tbAppointmentClientEmail")).getText()
                .equals("T@K.A")) {
            return false;
        }
        DriverBuilder.getInstance().getDriver().findElement(By.id("ctl00_ctl00_Content_Main_rcbAppLocations_Arrow")).click();
        WaitUtilsWebDriver.waitABit(1000);
        try {
            srListPage.getAppointmentContentFromCalendar().findElement(By.id("ctl00_ctl00_Content_Main_rcbTechnician_Input")).click();
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
                    .findElements(By.className("rcbItem")).get(0).click();
            WaitUtilsWebDriver.waitABit(500);
            srListPage.getAppointmentContentFromCalendar().findElement(By.id("ctl00_ctl00_Content_Main_rcbTechnician_Input")).click();
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
                    .findElements(By.className("rcbItem")).get(1).click();
        } catch (TimeoutException e) {
            return false;
        }

        WaitUtilsWebDriver.waitABit(1000);
        if (DriverBuilder.getInstance().getDriver().findElement(By.id("gvTechnicians")).findElements(By.tagName("tr")).size() != 4 && DriverBuilder.getInstance().getDriver()
                .findElement(By.id("gvTechnicians")).findElements(By.className("datepicker-container")).size() != 4) {
            return false;
        }
        DriverBuilder.getInstance().getDriver().findElement(By.id("ctl00_ctl00_Content_Main_tbxSubject")).sendKeys(subject);
        DriverBuilder.getInstance().getDriver().findElement(By.id("ctl00_ctl00_Content_Main_btnAddApp")).click();
        return true;
    }

    public boolean checkShowHideTechs() {
        try {
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.elementToBeClickable(srListPage.getTechniciansField())).click();
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("li")));
            WaitUtilsWebDriver.waitABit(2000);
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
                    .findElements(By.tagName("li")).get(0).click();
            WaitUtilsWebDriver.waitABit(500);
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.elementToBeClickable(srListPage.getTechniciansField())).click();
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("li")));
            WaitUtilsWebDriver.waitABit(2000);
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
                    .findElements(By.tagName("li")).get(1).click();
            if (DriverBuilder.getInstance().getDriver().findElement(By.id("gvTechnicians")).findElements(By.tagName("tr")).size() != 4
                    && DriverBuilder.getInstance().getDriver().findElement(By.id("gvTechnicians")).findElements(By.className("datepicker-container"))
                    .size() != 4) {
                return false;
            }

            if (!WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("showHideTech"))).getText()
                    .equals("Hide")) {
                return false;
            }
            WaitUtilsWebDriver.waitABit(2000);
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("showHideTech"))).click();
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.className("gvTechnicians-table")));

            if (!WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("showHideTech"))).getText()
                    .equals("Show")) {
                return false;
            }
        } catch (TimeoutException e) {
            return false;
        }

        return true;
    }

    public boolean checkDefaultAppointmentDateFromSREdit(String startDate) {
        if (!WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(srListPage.getPhaseField())).getAttribute("value").equals("Estimating"))
            return false;

        WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(srListPage.getAppointmentFromDateSRedit()));
        if (!(srListPage.getAppointmentFromDateSRedit().getAttribute("value").equals(startDate)
                && srListPage.getAppointmentToDateSRedit().getAttribute("value").equals(startDate))) {
            return false;
        }

        Utils.clickElement(srListPage.getLocationTypeField());

        if (!WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(DriverBuilder.getInstance().getDriver().findElement(By.className("rcbHovered")))).getText().equals("Custom") && srListPage.getAppointmentContent()
                .findElement(By.id("Card_rcbAppointmentLocations_Input")).getAttribute("disabled").equals("disabled")) {
            return false;
        }

        if (!WaitUtilsWebDriver.getWait().until(ExpectedConditions.elementToBeClickable(srListPage.getTechniciansField())).getAttribute("value").equals("All")
                && srListPage.getAppointmentContent().findElement(By.id("Card_rcbStates_Input")).getAttribute("value").equals("All")) {
            return false;
        }

        if (!WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(srListPage.getAppointmentContent().findElement(By.id("Card_tbxAddress"))))
                .getText().isEmpty()
                && srListPage.getAppointmentContent().findElement(By.id("Card_tbxCity")).getText().isEmpty()
                && srListPage.getAppointmentContent().findElement(By.id("Card_tbxZip")).getText().isEmpty()) {
            return false;
        }
        Utils.clickElement(srListPage.getAddAppButton());
        return true;
    }

    public boolean isSRLifeCycleDisplayed() {
        return Utils.isElementDisplayed(By.xpath("//table[contains(@id, 'Content_Main_report_fixedTable')]" +
                "//div[contains(text(), 'Service Request')]"));
    }

    public void checkAcceptanceOfSRInLifeCycle() {
        checkSRInLifeCycle("//div[contains(text(), 'ServiceRequests Accepted')]");
    }

    public void checkRejectOfSRInLifeCycle() {
        checkSRInLifeCycle("//div[contains(text(), 'ServiceRequests Rejected')]");
    }

    private void checkSRInLifeCycle(String srState) {
        LocalDateTime dateToCheck = LocalDateTime.now(ZoneId.of("US/Pacific")).minusHours(10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String dateSTR = dateToCheck.format(formatter);
        Assert.assertTrue(Utils.isElementDisplayed(By.xpath(srState)));
        Assert.assertTrue(Utils.isElementDisplayed(By.xpath("//div[contains(text(), '" + dateSTR + "')]")));
    }

    public void checkSRSearchCriteria() {
        Assert.assertTrue(Utils.isElementDisplayed(By.id("ctl00_ctl00_Content_Main_ctl01_rbxPhases_Input")));
        Assert.assertTrue(Utils.isElementDisplayed(By.id("ctl00_ctl00_Content_Main_ctl01_rbcTeamsForFilter_Input")));
        Assert.assertTrue(Utils.isElementDisplayed(By.id("ctl00_ctl00_Content_Main_ctl01_rbcTechsForFilter_Input")));
        Assert.assertTrue(Utils.isElementDisplayed(By.id("ctl00_ctl00_Content_Main_ctl01_rcbRepairLocations_Input")));
        Assert.assertTrue(Utils.isElementDisplayed(By.id("ctl00_ctl00_Content_Main_ctl01_tbSearchTags_tagsinput")));
        Assert.assertTrue(Utils.isElementDisplayed(By.id("ctl00_ctl00_Content_Main_ctl01_tbSearchFreeText")));
        Assert.assertTrue(Utils.isElementDisplayed(By.id("ctl00_ctl00_Content_Main_ctl01_btnSearch")));
    }

    public boolean checkPresenceOfServiceAdvisersByFilter(String filter) {
        Utils.clearAndType(srListPage.getAddsrvcustomercmb().getWrappedElement(), filter);
        int index = RandomUtils.nextInt(0, srListPage.getClients().size());
        try {
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(
                    By.xpath("//div[@id='Card_ddlClients_DropDown']//li"), 1));
        } catch (TimeoutException e) {
            Assert.fail("The drop down list has not been displayed.", e);
        }
        try {
            boolean result = srListPage.getClients().stream()
                    .map(WebElement::getText).map(String::toLowerCase).allMatch(t -> t.contains(filter));
            Utils.clickElement(srListPage.getClients().get(index));
            WaitUtilsWebDriver.waitForInvisibility(srListPage.getAddsrvcustomerdd().getWrappedElement());
            return result;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkTechniciansFromSchedulerAfterResetting() {
        if (DriverBuilder.getInstance().getDriver().findElements(By.xpath("//div[contains(@style, 'background-color:Yellow;height:5px;')]")).size() != 0)
            return false;

        if (DriverBuilder.getInstance().getDriver().findElements(By.xpath("//div[contains(@style, 'background-color:Blue;height:5px;')]")).size() != 0)
            return false;

        if (DriverBuilder.getInstance().getDriver().findElements(By.xpath("//div[contains(@style, 'background-color:LimeGreen;height:5px;')]"))
                .size() != 0)
            return false;

        if (DriverBuilder.getInstance().getDriver().findElements(By.xpath("//div[contains(@style, 'background-color:Red;height:5px;')]")).size() != 0)
            return false;

        return DriverBuilder.getInstance().getDriver().findElements(By.xpath("//div[contains(@style, 'background-color:Violet;height:5px;')]")).size() == 0;
    }

    public boolean checkLifeCycleDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        try {
            return WaitUtilsWebDriver.waitForVisibility(srListPage.getLifeCycleBlock())
                    .getText()
                    .contains(LocalDate.now(ZoneId.of("US/Pacific")).format(formatter));
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkTechniciansFromScheduler() {
        try {
            WaitUtilsWebDriver.getWait().ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(srListPage.getTechniciansAreasFromSchedulerArrow()));
            WaitUtilsWebDriver.getWait().ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(srListPage.getTechniciansTeamsFromScheduler()));
            WaitUtilsWebDriver.getWait().ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(srListPage.getTechniciansFromScheduler()));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean check5TechniciansFromScheduler() {
        try {
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'sr-btn btn-apply')]")))
                    .click();
            waitForLoading();
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("ctl00_ctl00_Content_Main_AppointmentsScheduler1_RadScheduler1_ctl36_pnlColor")));

            WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@style, 'background-color:Yellow;height:5px;')]")));
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@style, 'background-color:Blue;height:5px;')]")));
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@style, 'background-color:LimeGreen;height:5px;')]")));
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@style, 'background-color:Red;height:5px;')]")));
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@style, 'background-color:Violet;height:5px;')]")));

            if (DriverBuilder.getInstance().getDriver().findElements(By.xpath("//div[contains(@style, 'background-color:Yellow;height:5px;')]"))
                    .size() != 5
                    && DriverBuilder.getInstance().getDriver().findElements(By.xpath("//div[contains(@style, 'background-color:Blue;height:5px;')]"))
                    .size() != 5
                    && DriverBuilder.getInstance().getDriver()
                    .findElements(By.xpath("//div[contains(@style, 'background-color:LimeGreen;height:5px;')]"))
                    .size() != 5
                    && DriverBuilder.getInstance().getDriver().findElements(By.xpath("//div[contains(@style, 'background-color:Red;height:5px;')]"))
                    .size() != 5
                    && DriverBuilder.getInstance().getDriver().findElements(By.xpath("//div[contains(@style, 'background-color:Violet;height:5px;')]"))
                    .size() != 5) {
                return false;
            }

        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isLifeCycleContentDisplayed() {
        WaitUtilsWebDriver.waitABit(5000);
        try {
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'SR')]")));
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Year')]")));
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'VIN')]")));
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Make')]")));
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Stock')]")));
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Model')]")));

            WaitUtilsWebDriver.getWait().until(ExpectedConditions
                    .presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_tbVin")));
            if (WaitUtilsWebDriver.getWait().until(ExpectedConditions
                    .presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_tbSrNumber")))
                    .getAttribute("value").isEmpty()) {
                return false;
            }
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean checkLifeCycleDocumentsContent() {
        WaitUtilsWebDriver.waitABit(1000);

        try {
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Photos')]")));
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Documents')]")));
            WaitUtilsWebDriver.getWait().until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//div[contains(text(), 'Service Request Type')]")));
            WaitUtilsWebDriver.getWait().until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//div[contains(text(), 'Service Request Creation')]")));
            WaitUtilsWebDriver.getWait().until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//div[contains(text(), 'Service Request Description')]")));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean checkDocumentDownloadingInLC() {
        String parentFrame = DriverBuilder.getInstance().getDriver().getWindowHandle();
        DriverBuilder.getInstance().getDriver().findElement(By.xpath("//a[contains(text(), 'Link to Documents')]")).click();
        Set windows = DriverBuilder.getInstance().getDriver().getWindowHandles();
        windows.remove(parentFrame);
        DriverBuilder.getInstance().getDriver().switchTo().window((String) windows.iterator().next());
        return true;
    }

    public boolean areTwoWindowsOpened() {
        return DriverBuilder.getInstance().getDriver().getWindowHandles().size() == 2;
    }
}