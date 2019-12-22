package com.cyberiansoft.test.bo.verifications;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServiceRequestsListWebPage;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.enums.DateUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

import static com.cyberiansoft.test.baseutils.WaitUtilsWebDriver.getWait;
import static com.cyberiansoft.test.baseutils.WaitUtilsWebDriver.waitForLoading;

public class ServiceRequestsListVerifications {

    public static boolean isSearchPanelExpanded() {
        return Utils.isElementDisplayed(new ServiceRequestsListWebPage().getSearchTabExpanded(), 7);
    }

    public static void verifySearchFieldsAreVisible() {
        final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
        Assert.assertTrue(WaitUtilsWebDriver.elementShouldBeVisible(srListPage.getStatuscmb().getWrappedElement(), true));
        Assert.assertTrue(WaitUtilsWebDriver.elementShouldBeVisible(srListPage.getTeamcmb().getWrappedElement(), true));
        Assert.assertTrue(WaitUtilsWebDriver.elementShouldBeVisible(srListPage.getTechniciancmb().getWrappedElement(), true));
        Assert.assertTrue(WaitUtilsWebDriver.elementShouldBeVisible(srListPage.getTagsfld().getWrappedElement(), true));
        Assert.assertTrue(WaitUtilsWebDriver.elementShouldBeVisible(srListPage.getFreetextfld(), true));
        Assert.assertTrue(WaitUtilsWebDriver.elementShouldBeVisible(srListPage.getFindbtn(), true));
    }

    public static boolean isAcceptIconNotDisplayedForFirstServiceRequestFromList() {
        final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
        Utils.clickWithActions(srListPage.getServiceRequestsPopoverList().get(0));
        return WaitUtilsWebDriver.elementShouldBeVisible(srListPage.getServiceRequestsAcceptButton(), false);
    }

    public static boolean isAppointmentPresentForFirstServiceRequestFromList(String appointmentTime) {
        return Utils.getText(new ServiceRequestsListWebPage().getFirstSRFromList().findElement(By.xpath(".//a/span"))).equals(appointmentTime);
    }

    public static boolean isInsuranceCompanyPresentForFirstServiceRequestFromList(String insuranceCompany) {
        WaitUtilsWebDriver.waitForLoading();
        return new ServiceRequestsListWebPage().getFirstSRFromList().findElements(By.xpath(".//div[@class='" + insuranceCompany + "  ']")).size() > 0;
    }

    public static boolean verifySearchResultsByServiceName(String servicename) {
        final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
        WaitUtilsWebDriver.waitForVisibility(srListPage.getServiceRequestsList());
        return srListPage.getFirstSRFromList()
                .findElements(By.xpath(".//div[@class='name' and contains(text(), '" + servicename + "')]")).size() > 0;
    }

    public static boolean verifySearchResultsByModelIN(String _make, String _model, String _year, String vin) {
        boolean result = false;
        final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
        if (!(srListPage.getFirstSRFromList() == null)) {
            result = srListPage.getFirstSRFromList().findElements(By.xpath(
                    ".//div[@class='modelVin' and text()='" + _year + " " + _make + " " + _model + " " + vin + "']"))
                    .size() > 0;
        }
        return result;
    }

    public static boolean isCheckInButtonVisible() {
        return isDisplayed(new ServiceRequestsListWebPage().getServiceRequestCheckInButton(), true);
    }

    public static boolean isCheckInButtonInvisible() {
        return isDisplayed(new ServiceRequestsListWebPage().getServiceRequestCheckInButton(), false);
    }

    public static boolean isServiceIsPresentForForSelectedServiceRequest(String servicename) {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        driver.switchTo().frame(new ServiceRequestsListWebPage().getEditServiceRequestPanelFrame());
        return isDisplayed(driver.findElement(By.xpath("//span[contains(text(), '" + servicename + "')]")), true);
    }

    private static boolean isDisplayed(WebElement element, boolean expected) {
        verifyServiceRequestInfoFrameIsOn();
        boolean visible = WaitUtilsWebDriver.elementShouldBeVisible(element, expected);
        DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
        return visible;
    }

    public static void verifyServiceRequestInfoFrameIsOn() {
        DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
        try {
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                    new ServiceRequestsListWebPage().getEditServiceRequestPanelFrame()));
        } catch (TimeoutException | StaleElementReferenceException e) {
            WaitUtilsWebDriver.waitABit(2000);
        }
    }

    public static boolean checkTimeOfLastDescription() {
        verifyServiceRequestInfoFrameIsOn();
        final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
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

    public static boolean verifyTagsAreAdded(String... tags) {
        for (String tag : tags) {
            final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
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

    public static boolean isFirstTagRemoved() {
        final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
        int prevSize = srListPage.getAllAddedTags().size();
        srListPage.getAllAddedTags().get(0).findElement(By.xpath("//a[contains(@title, 'Removing tag')]")).click();
        return prevSize - srListPage.getAllAddedTags().size() == 1;
    }

    public static boolean areTagsAdded(String... tags) {
        verifyServiceRequestInfoFrameIsOn();
        final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
        System.out.println(srListPage.getAllAddedTags().stream().map(e -> e.getText()).map(t -> t.substring(0, t.length() - 3))
                .collect(Collectors.toList()));
        List tagsToCheck = new LinkedList(Arrays.asList(tags));
        tagsToCheck.remove(0);
        boolean result = srListPage.getAllAddedTags().stream().map(e -> e.getText()).map(t -> t.substring(0, t.length() - 3))
                .collect(Collectors.toList()).containsAll(tagsToCheck);
        Utils.clickElement(srListPage.getCloseServiceRequestButton());
        return result;
    }

    public static boolean isNewDescriptionAddedAndCheckedOld(String newDescription, String prevDescription) {
        verifyServiceRequestInfoFrameIsOn();
        final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
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

    public static boolean verifyServiceDescriptionIsPresent(String string) {
        WaitUtilsWebDriver.waitABit(1000);
        return WaitUtilsWebDriver.getWait().until((ExpectedCondition<Boolean>) driver -> Utils.getText(
                new ServiceRequestsListWebPage().getDescriptionTextBlock()).equals(string));
    }

    public static boolean verifyDescriptionIconsAreVisible() {
        verifyServiceRequestInfoFrameIsOn();
        final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
        boolean documentShown = WaitUtilsWebDriver.waitForAttributeToContain(srListPage.getDescriptionDocuments().findElement(By.tagName("i")), "style", "display : none;");
        boolean answerShown = WaitUtilsWebDriver.waitForAttributeToContain(srListPage.getDescriptionAnswers().findElement(By.tagName("i")), "style", "display : none;");

        return documentShown || answerShown;
    }

    public static boolean isServiceRequestDocumentIconVisible() {
        verifyServiceRequestInfoFrameIsOn();
        return WaitUtilsWebDriver.getWait().until(ExpectedConditions
                .not(ExpectedConditions
                        .attributeToBe(DriverBuilder.getInstance().getDriver().findElement(By.xpath("//div[contains(@class, 'description-reason')]"))
                                .findElement(By.tagName("i")), "style", "display : none;")));
    }

    public static boolean checkElementsInDocument() {
        try {
            final WebElement documentContent = new ServiceRequestsListWebPage().getDocumentContent();
            documentContent.findElement(By.xpath("//h2[contains(text(), 'Documents')]"));
            documentContent.findElement(By.xpath("//h3[contains(text(), 'Service Request:')]"));
            documentContent.findElement(By.className("add"));
            return documentContent.findElements(By.className("rgHeader"))
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList())
                    .containsAll(Arrays.asList("Name/Description", "Size", "Uploaded"));
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean areImageButtonsDisplayed() {
        final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
        return Utils.areElementsDisplayed(Arrays.asList(srListPage.getImagesOkButton(), srListPage.getImagesCancelButton(), srListPage.getAddFileButton()));
    }

    public static boolean isAddAppointmentFromSRListClosed() {
        return Utils.isElementNotDisplayed(new ServiceRequestsListWebPage().getAddAppointmentButton(), 10);
    }

    public static boolean checkDefaultAppointmentValuesAndAddAppointmentFomSREdit() {
        final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
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

    public static boolean isStatusDisplayed(String status) {
        DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
        try {
            return getWait().until((ExpectedCondition<Boolean>) driver -> Utils
                    .getText(By.className("serviceRequestStatus"))
                    .equals(status));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public static boolean checkDefaultAppointmentValuesFromCalendar(String subject) {
        WaitUtilsWebDriver.waitABit(1000);

        final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
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

    public static boolean checkShowHideTechs() {
        try {
            final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
            final WebDriverWait wait = WaitUtilsWebDriver.getWait();
            wait.until(ExpectedConditions.elementToBeClickable(srListPage.getTechniciansField())).click();
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("li")));
            WaitUtilsWebDriver.waitABit(2000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
                    .findElements(By.tagName("li")).get(0).click();
            WaitUtilsWebDriver.waitABit(500);
            wait.until(ExpectedConditions.elementToBeClickable(srListPage.getTechniciansField())).click();
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("li")));
            WaitUtilsWebDriver.waitABit(2000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rcbList")))
                    .findElements(By.tagName("li")).get(1).click();
            if (DriverBuilder.getInstance().getDriver().findElement(By.id("gvTechnicians")).findElements(By.tagName("tr")).size() != 4
                    && DriverBuilder.getInstance().getDriver().findElement(By.id("gvTechnicians")).findElements(By.className("datepicker-container"))
                    .size() != 4) {
                return false;
            }

            if (!wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showHideTech"))).getText()
                    .equals("Hide")) {
                return false;
            }
            WaitUtilsWebDriver.waitABit(2000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showHideTech"))).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("gvTechnicians-table")));

            if (!wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showHideTech"))).getText()
                    .equals("Show")) {
                return false;
            }
        } catch (TimeoutException e) {
            return false;
        }

        return true;
    }

    public static boolean checkDefaultAppointmentDateFromSREdit(String startDate) {
        final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
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

    public static boolean isSRLifeCycleDisplayed() {
        return Utils.isElementDisplayed(By.xpath("//table[contains(@id, 'Content_Main_report_fixedTable')]" +
                "//div[contains(text(), 'Service Request')]"));
    }

    public static void checkAcceptanceOfSRInLifeCycle() {
        checkSRInLifeCycle("//div[contains(text(), 'ServiceRequests Accepted')]");
    }

    public static void checkRejectOfSRInLifeCycle() {
        checkSRInLifeCycle("//div[contains(text(), 'ServiceRequests Rejected')]");
    }

    private static void checkSRInLifeCycle(String srState) {
        LocalDateTime dateToCheck = LocalDateTime.now(ZoneId.of("US/Pacific"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtils.THE_SHORTEST_DATE_FORMAT.getFormat());
        String dateSTR = dateToCheck.format(formatter);
        Assert.assertTrue(WaitUtilsWebDriver.elementShouldBeVisible(By.xpath(srState), true));
        Assert.assertTrue(WaitUtilsWebDriver
                .elementShouldBeVisible(By.xpath("//div[contains(text(), '" + dateSTR + "')]"), true));
    }

    public static void checkSRSearchCriteria() {
        Assert.assertTrue(Utils.isElementDisplayed(By.id("ctl00_ctl00_Content_Main_ctl01_rbxPhases_Input")));
        Assert.assertTrue(Utils.isElementDisplayed(By.id("ctl00_ctl00_Content_Main_ctl01_rbcTeamsForFilter_Input")));
        Assert.assertTrue(Utils.isElementDisplayed(By.id("ctl00_ctl00_Content_Main_ctl01_rbcTechsForFilter_Input")));
        Assert.assertTrue(Utils.isElementDisplayed(By.id("ctl00_ctl00_Content_Main_ctl01_rcbRepairLocations_Input")));
        Assert.assertTrue(Utils.isElementDisplayed(By.id("ctl00_ctl00_Content_Main_ctl01_tbSearchTags_tagsinput")));
        Assert.assertTrue(Utils.isElementDisplayed(By.id("ctl00_ctl00_Content_Main_ctl01_tbSearchFreeText")));
        Assert.assertTrue(Utils.isElementDisplayed(By.id("ctl00_ctl00_Content_Main_ctl01_btnSearch")));
    }

    public static boolean checkPresenceOfServiceAdvisersByFilter(String filter) {
        final ServiceRequestsListWebPage srListPage = new ServiceRequestsListWebPage();
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkTechniciansFromSchedulerAfterResetting() {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        if (driver.findElements(By.xpath("//div[contains(@style, 'background-color:Yellow;height:5px;')]")).size() != 0)
            return false;

        if (driver.findElements(By.xpath("//div[contains(@style, 'background-color:Blue;height:5px;')]")).size() != 0)
            return false;

        if (driver.findElements(By.xpath("//div[contains(@style, 'background-color:LimeGreen;height:5px;')]"))
                .size() != 0)
            return false;

        if (driver.findElements(By.xpath("//div[contains(@style, 'background-color:Red;height:5px;')]")).size() != 0)
            return false;

        return driver.findElements(By.xpath("//div[contains(@style, 'background-color:Violet;height:5px;')]")).size() == 0;
    }

    public static boolean checkLifeCycleDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        try {
            return WaitUtilsWebDriver.waitForVisibility(new ServiceRequestsListWebPage().getLifeCycleBlock())
                    .getText()
                    .contains(LocalDate.now(ZoneId.of("US/Pacific")).format(formatter));
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkTechniciansFromScheduler() {
        try {
            final WebDriverWait wait = WaitUtilsWebDriver.getWait();
            wait.ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(new ServiceRequestsListWebPage().getTechniciansAreasFromSchedulerArrow()));
            wait.ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(new ServiceRequestsListWebPage().getTechniciansTeamsFromScheduler()));
            wait.ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(new ServiceRequestsListWebPage().getTechniciansFromScheduler()));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public static boolean check5TechniciansFromScheduler() {
        try {
            final WebDriverWait wait = WaitUtilsWebDriver.getWait();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'sr-btn btn-apply')]")))
                    .click();
            waitForLoading();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("ctl00_ctl00_Content_Main_AppointmentsScheduler1_RadScheduler1_ctl36_pnlColor")));

            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@style, 'background-color:Yellow;height:5px;')]")));
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@style, 'background-color:Blue;height:5px;')]")));
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@style, 'background-color:LimeGreen;height:5px;')]")));
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@style, 'background-color:Red;height:5px;')]")));
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
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

    public static boolean isLifeCycleContentDisplayed() {
        WaitUtilsWebDriver.waitABit(5000);
        try {
            final WebDriverWait wait = WaitUtilsWebDriver.getWait();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'SR')]")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Year')]")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'VIN')]")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Make')]")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Stock')]")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Model')]")));

            wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_tbVin")));
            if (wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_filterer_tbSrNumber")))
                    .getAttribute("value").isEmpty()) {
                return false;
            }
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public static boolean checkLifeCycleDocumentsContent() {
        WaitUtilsWebDriver.waitABit(1000);

        try {
            final WebDriverWait wait = WaitUtilsWebDriver.getWait();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Photos')]")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Documents')]")));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//div[contains(text(), 'Service Request Type')]")));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//div[contains(text(), 'Service Request Creation')]")));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//div[contains(text(), 'Service Request Description')]")));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public static boolean checkDocumentDownloadingInLC() {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        String parentFrame = driver.getWindowHandle();
        driver.findElement(By.xpath("//a[contains(text(), 'Link to Documents')]")).click();
        Set windows = driver.getWindowHandles();
        windows.remove(parentFrame);
        driver.switchTo().window((String) windows.iterator().next());
        return true;
    }

    public static boolean areTwoWindowsOpened() {
        return DriverBuilder.getInstance().getDriver().getWindowHandles().size() == 2;
    }
}