package com.cyberiansoft.test.vnextbo.interactions.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOEditNotesDialog;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROWebPage;
import com.cyberiansoft.test.vnextbo.validations.repairOrders.VNextBOROPageValidations;
import com.google.common.base.CharMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBOROPageInteractions {

    public static void clickTechniciansFieldForWO(String woNumber) {
        Utils.clickElement(new VNextBOROWebPage().getTechniciansFieldForWO(woNumber));
    }

    public static void clickWoLink(String woNumber) {
        WaitUtilsWebDriver.waitForLoading();
        Utils.clickElement(By.xpath("//a[@class='order-no']/strong[text()='" + woNumber + "']/.."));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static WebElement clickWoLink() {
        WaitUtilsWebDriver.waitForLoading();
        final WebElement woElement = new VNextBOROWebPage().getRandomOrderNumber();
        if (woElement != null) {
            Utils.clickElement(woElement);
            WaitUtilsWebDriver.waitForLoading();
        } else {
            Assert.fail("The work orders list is empty");
        }
        return woElement;
    }

    public static void revealNoteForWorkOrder(String woNumber) {
        if (!VNextBOROPageValidations.isNoteForWorkOrderDisplayed(woNumber)) {
            clickNoteForWo(woNumber);
        }
    }

    public static void openNotesDialog() {
        Utils.clickElement(new VNextBOROWebPage().getRoNotesLink());
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(new VNextBOEditNotesDialog().getRoEditNotesModalDialog(), 5);
    }

    private static void clickNoteForWo(String woNumber) {
        Utils.clickElement(By.xpath("//strong[text()='" + woNumber
                + "']/../../../div[@data-bind='visible: orderDescriptionDisplay']"));
    }

    public static void hideNoteForWorkOrder(String woNumber) {
        if (VNextBOROPageValidations.isNoteForWorkOrderDisplayed(woNumber)) {
            WaitUtilsWebDriver.waitForLoading();
            clickNoteForWo(woNumber);
        }
    }

    public static void clickAdvancedSearchCaret() {
        Utils.clickElement(new VNextBOROWebPage().getAdvancedSearchCaret());
    }

    public static void setWoDepartment(String woNumber, String department) {
        hideNoteForWorkOrder(woNumber);
        Utils.clickElement(new VNextBOROWebPage().getTableBody().findElement(By.xpath("//a[@class='order-no']" +
                "/strong[text()='" + woNumber + "']/../following-sibling::div/i[@class='menu-trigger']")));
        final WebElement departmentDropDown = new VNextBOROWebPage().getTableBody()
                .findElement(By.xpath("//a[@class='order-no']/strong[text()='"
                + woNumber + "']/../..//div[@class='drop department-drop']"));
        WaitUtilsWebDriver.waitForVisibility(departmentDropDown);
        final List<WebElement> dropDownOptions = departmentDropDown.findElements(By.xpath(".//label"));
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(dropDownOptions);
        dropDownOptions
                .stream()
                .filter(e -> e.getText().equals(department))
                .findFirst()
                .ifPresent(WebElement::click);
        WaitUtilsWebDriver.waitForInvisibility(departmentDropDown);
        WaitUtilsWebDriver.waitABit(1000);
    }

    public static void clickXIconToCloseNoteForWorkOrder(String woNumber) {
        if (VNextBOROPageValidations.isNoteForWorkOrderDisplayed(woNumber)) {
            Utils.clickElement(By.xpath("//strong[text()='" + woNumber
                    + "']/../../../div[@data-bind='visible: orderDescriptionDisplay']//a[@class='x']"));
        }
    }

    public static void clickSavedSearchArrow() {
        Utils.clickElement(new VNextBOROWebPage().getSavedSearchArrow());
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(new VNextBOROWebPage().getSavedSearchDropDown());
    }

    public static void selectSavedSearchDropDownOption(String option) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBOROWebPage().getSavedSearchDropDownOptions());
        new VNextBOROWebPage().getSavedSearchDropDownOptions()
                .stream()
                .filter(o -> o.getText().equals(option))
                .findFirst()
                .ifPresent(Utils::clickWithActions);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static String getOrderNoteText() {
        return Utils.getText(new VNextBOROWebPage().getRoNotesLink());
    }

    public static void movePointerToSearchResultsField() {
        final WebElement searchResults = new VNextBOROWebPage().getSearchResults();
        WaitUtilsWebDriver.waitForVisibility(searchResults);
        Utils.moveToElement(searchResults);
    }

    public static List<String> getSearchResultsList() {
        return WaitUtilsWebDriver
                .waitForVisibilityOfAllOptions(new VNextBOROWebPage().getSearchOptions())
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public static void clickLocationInDropDown(String location) {
        new VNextBOROWebPage().getLocationLabels()
                .stream()
                .filter(loc -> loc.getText().contains(location))
                .findFirst()
                .ifPresent(WebElement::click);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickEditIconForSavedSearch() {
        WaitUtilsWebDriver.waitABit(1000);
        Utils.clickElement(new VNextBOROWebPage().getSavedSearchEditIcon());
    }

    public static void clickNextButton() {
        Utils.clickElement(new VNextBOROWebPage().getNextButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickPrevButton() {
        Utils.clickElement(new VNextBOROWebPage().getPrevButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    private static void makeTabActive(WebElement wideTab, WebElement wideTabActive, WebElement narrowTab, WebElement narrowTabActive) {
        try {
            WaitUtilsWebDriver.waitForVisibility(wideTabActive, 5);
        } catch (Exception ignored) {
            try {
                Utils.clickElement(wideTab);
                WaitUtilsWebDriver.waitForVisibility(wideTabActive, 5);
            } catch (Exception e) {
                Utils.clickElement(narrowTab);
                WaitUtilsWebDriver.waitForVisibility(narrowTabActive, 5);
            }
        }
    }

    public static void setDepartmentsTabActive() {
        makeTabActive(new VNextBOROWebPage().getDepartmentsWideTab(), new VNextBOROWebPage().getDepartmentsWideTabActive(),
                new VNextBOROWebPage().getDepartmentsNarrowTab(), new VNextBOROWebPage().getDepartmentsNarrowTabActive());
    }

    public static String getAllDepartmentsSum() {
        return getCalculatedSum(new VNextBOROWebPage().getAllDepartmentsWideScreenValues());
    }

    public static String getAllPhasesSum() {
        return getCalculatedSum(new VNextBOROWebPage().getAllPhasesWideScreenValues());
    }

    private static String getCalculatedSum(List<WebElement> values) {
        if (VNextBOROPageValidations.areValuesDisplayed(values)) {
            String sum = values.get(0).getText();
            System.out.println("Sum: " + sum);
            return sum;
        }
        return "";
    }

    private static String calculateSum(List<WebElement> values) {
        if (VNextBOROPageValidations.areValuesDisplayed(values)) {
            final List<WebElement> collection = values.subList(1, values.size());
            final String calculatedSum = collection
                    .stream()
                    .map(value -> {
                        if (value.getText().equals("")) {
                            return 0;
                        }
                        return Integer.valueOf(value.getText());
                    })
                    .collect(Collectors.toList())
                    .stream()
                    .reduce((val1, val2) -> val1 + val2)
                    .get()
                    .toString();
            System.out.println("Sum calculation: " + calculatedSum);
            return calculatedSum;
        }
        return "";
    }

    public static String getDepartmentsValues() {
        return calculateSum(new VNextBOROWebPage().getAllDepartmentsWideScreenValues());
    }

    public static String getPhasesValues() {
        return calculateSum(new VNextBOROWebPage().getAllPhasesWideScreenValues());
    }

    public static String getValue(List<WebElement> values, int order) {
        if (VNextBOROPageValidations.areValuesDisplayed(values)) {
            return values.get(order).getText();
        } else {
            return null;
        }
    }

    public static String getDepartmentsValue(int order) {
        return handleTabValues(order, new VNextBOROWebPage().getAllDepartmentsWideScreenValues(),
                new VNextBOROWebPage().getAllDepartmentsNarrowScreenValues(), new VNextBOROWebPage().getDepartmentsTabPane());
    }

    public static String getPhasesValue(int order) {
        return handleTabValues(order, new VNextBOROWebPage().getAllPhasesWideScreenValues(),
                new VNextBOROWebPage().getAllPhasesNarrowScreenValues(), new VNextBOROWebPage().getPhasesTabPane());
    }

    private static String handleTabValues(int order, List<WebElement> allPhasesWideScreenValues,
                                   List<WebElement> allPhasesNarrowScreenValues, WebElement phasesTabPane) {
        if (VNextBOROPageValidations.areValuesDisplayed(allPhasesWideScreenValues)) {
            final String value = VNextBOROPageInteractions.getValue(allPhasesWideScreenValues, order);
            assert value != null;
            return value.equals("") ? "0" : value;
        } else {
            try {
                WaitUtilsWebDriver.waitForVisibilityOfAllOptions(allPhasesNarrowScreenValues, 5);
            } catch (Exception ignored) {
                Utils.clickElement(phasesTabPane);
                WaitUtilsWebDriver.waitABit(1000);
            }
            String value = VNextBOROPageInteractions.getValue(allPhasesNarrowScreenValues, order);
            System.out.println("VALUE: " + value + " ORDER: " + order);
            if (value == null) {
                value = "0";
            }
            System.out.println("VALUE after replacement: " + CharMatcher.inRange('0', '9').retainFrom(value));
            value = CharMatcher.inRange('0', '9').retainFrom(value);
            Utils.clickElement(phasesTabPane);
            return value.equals("") ? "0" : value;
        }
    }

    public static void clickDepartmentForWideScreen(String department) {
        clickTabForWideScreen(department, new VNextBOROWebPage().getDepartmentsOptionsForWideScreen());
    }

    public static void clickDepartmentForNarrowScreen(String department) {
        clickOptionForNarrowScreen(department, new VNextBOROWebPage().getDepartmentsTabPane(), new VNextBOROWebPage().getDepartmentsNarrowScreenDropDownOptions());
    }

    public static void clickPhaseForWideScreen(String phase) {
        clickTabForWideScreen(phase, new VNextBOROWebPage().getPhasesOptionsForWideScreen());
    }

    public static void clickPhaseForNarrowScreen(String phase) {
        clickOptionForNarrowScreen(phase, new VNextBOROWebPage().getPhasesTabPane(), new VNextBOROWebPage().getPhasesNarrowScreenDropDownOptions());
    }

    private static void clickTabForWideScreen(String phase, List<WebElement> phasesOptionsForWideScreen) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(phasesOptionsForWideScreen);
        phasesOptionsForWideScreen
                .stream()
                .filter(ph -> ph.getText().equals(phase))
                .findFirst().ifPresent(Utils::clickElement);
        WaitUtilsWebDriver.waitForLoading();
    }

    private static void clickOptionForNarrowScreen(String phase, WebElement phasesTabPane, List<WebElement> phasesNarrowScreenDropDownOptions) {
        if (VNextBOROPageValidations.isDepartmentNarrowScreenClickable()) {
//        departmentsNarrowScreen.click();
            Utils.clickElement(phasesTabPane);
            WaitUtilsWebDriver.waitABit(1000);
        System.out.println(phase);
        phasesNarrowScreenDropDownOptions
                .stream()
                .filter(dep -> dep.getText().equals(phase))
                .findFirst().ifPresent(Utils::clickElement);
        WaitUtilsWebDriver.waitForLoading();
        }
    }

    public static int getNumOfOrdersOnPage() {
        return WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBOROWebPage().getOrdersDisplayedOnPage()).size();
    }

    public static void setPhasesTabActive() {
        makeTabActive(new VNextBOROWebPage().getPhasesWideTab(), new VNextBOROWebPage().getPhasesWideTabActive(),
                new VNextBOROWebPage().getPhasesNarrowTab(), new VNextBOROWebPage().getPhasesNarrowTabActive());
    }

    public static void openIntercom() {
        WaitUtilsWebDriver.waitForVisibility(new VNextBOROWebPage().getIntercomFrame());
        Utils.switchToFrame(new VNextBOROWebPage().getIntercomFrame());
        Utils.clickElement(new VNextBOROWebPage().getIntercom());
        WaitUtilsWebDriver.waitForVisibility(new VNextBOROWebPage().getIntercomOpen());
    }

    public static void closeIntercom() {
        Utils.clickElement(new VNextBOROWebPage().getCloseIntercomButton());
        WaitUtilsWebDriver.waitForVisibility(new VNextBOROWebPage().getIntercom());
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        driver.switchTo().window(driver.getWindowHandle());
    }

    public static void clickPhasesWide() {
        Utils.clickElement(new VNextBOROWebPage().getPhasesWideTab());
    }

    public static void searchRepairOrderByNumber(String roNumber) {
        setRepairOrdersSearchText(roNumber);
        clickSearchIcon();
    }

    public static void setRepairOrdersSearchText(String repairOrderText) {
        final WebElement repairOrdersSearchTextField = new VNextBOROWebPage().getRepairOrdersSearchTextField();
        WaitUtilsWebDriver.waitForVisibility(repairOrdersSearchTextField);
        Utils.clearAndType(repairOrdersSearchTextField, repairOrderText);
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void clickSearchIcon() {
        Utils.clickElement(new VNextBOROWebPage().getSearchIcon());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static String getTableTitleDisplayed(int titleHeaderNumber) {
        final List<WebElement> tableHeader = new VNextBOROWebPage().getTableHeader();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(tableHeader);
        return tableHeader.get(titleHeaderNumber).getText();
    }

    public static String getWoDepartment(String woNumber) {
        return WaitUtilsWebDriver.waitForVisibility(new VNextBOROWebPage().getTableBody()
                .findElement(By.xpath("//a[@class='order-no']/strong[text()='" + woNumber
                        + "']/../following-sibling::div/i[@class='menu-trigger']")))
                .getText();
    }

    public static void clickCancelSearchIcon() {
        WaitUtilsWebDriver.waitForLoading();
        Utils.clickElement(new VNextBOROWebPage().getCancelSearchIcon());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static String getWorkOrderActivePhaseValue(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        return woTableRow.findElement(By.xpath(".//td[@class='phase']")).getText().trim();
    }

    public static String getWorkOrderDaysInProgressValue(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        return woTableRow.findElement(By.xpath(".//td[@class='days']/div/p")).getText().trim();
    }

    public static void clickWorkOrderOtherMenuButton(String orderNumber) {
        WebElement wotablerow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        wotablerow.findElement(By.xpath(".//i[@data-bind='click: dropMenuClicked']")).click();
    }

    public static void clickStartRoForWorkOrder(String orderNumber) {
        clickWorkOrderOtherMenuButton(orderNumber);
        Utils.clickElement(By.xpath("//i[@class='icon-start-ro']"));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickWorkOrderOtherPhaseMenu(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        woTableRow.findElement(By.xpath(".//i[@data-bind='click: phaseMenuClicked']")).click();
    }

    public static void completeWorkOrderServiceStatus(String orderNumber, String serviceName) {
        clickWorkOrderOtherPhaseMenu(orderNumber);
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        List<WebElement> services = woTableRow.findElements(
                By.xpath(".//*[@data-bind='click: changeServiceStatus']"));
        for (WebElement srv : services)
            if (srv.getText().trim().contains(serviceName)) {
                srv.click();
                break;
            }

        WaitUtilsWebDriver.waitForLoading();
    }

    public static String getCompletedWorkOrderValue(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        return woTableRow.findElement(
                By.xpath("./td[8]/span")).getText();
    }

    public static void clickInvoiceNumberInTable(String ro, String invoice) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(ro);
        System.out.println(woTableRow);
        Utils.clickElement(By.xpath("//a[@class='stockRo__invoiceNo' and text()='" + invoice + "']"));
        WaitUtilsWebDriver.waitForLoading();
        WaitUtilsWebDriver.waitForNewTab();
    }

    public static void openOtherDropDownMenu(String wo) {
        final VNextBOROWebPage repairOrdersPage = new VNextBOROWebPage();
        WebElement roTableRow = repairOrdersPage.getTableRowWithWorkOrder(wo);
        final WebElement icon = roTableRow
                .findElement(By.xpath("//td[@class='grid__actions']//i[contains(@class, 'icon-arrow')]"));
        Utils.clickElement(icon);
        WaitUtilsWebDriver.waitForVisibility(repairOrdersPage.getOtherDropDown());
    }

    public static void openWorkOrderDetailsPage(String wo) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(wo);
        Utils.clickElement(woTableRow.findElement(By.xpath(".//a/strong[text()='" + wo + "']")));
    }

    public static void clickSearchTextToCloseLocationDropDown() {
        Utils.clickElement(new VNextBOROWebPage().getSearchText());
        WaitUtilsWebDriver.waitForInvisibility(new VNextBOROWebPage().getLocationExpanded());
    }

    public static String getTechniciansValueForWO(String woNumber) {
        return Utils.getText(new VNextBOROWebPage().getTechniciansFieldForWO(woNumber));
    }
}