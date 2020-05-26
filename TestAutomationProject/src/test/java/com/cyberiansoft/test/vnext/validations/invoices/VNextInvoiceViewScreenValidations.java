package com.cyberiansoft.test.vnext.validations.invoices;

import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.invoices.VNextInvoiceViewScreen;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VNextInvoiceViewScreenValidations {

    public static void verifyInvoicePrintViewScreenIsDisplayed() {

        VNextInvoiceViewScreen invoiceViewScreen = new VNextInvoiceViewScreen();
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(invoiceViewScreen.getPageFrame());
        Assert.assertTrue(new VNextInvoiceViewScreen().getRootElement().isDisplayed(), "Invoice view screen hasn't been displayed");
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
    }

    public static void verifySignatureIsDisplayed() {

        VNextInvoiceViewScreen invoiceViewScreen = new VNextInvoiceViewScreen();
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(invoiceViewScreen.getPageFrame());
        Assert.assertTrue(new VNextInvoiceViewScreen().getSignature().isDisplayed(), "Signature hasn't been displayed");
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
    }

    public static void verifyInvoiceNumberIsDisplayed(String invoiceNumber) {

        VNextInvoiceViewScreen invoiceViewScreen = new VNextInvoiceViewScreen();
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(invoiceViewScreen.getPageFrame());
        Assert.assertTrue(new VNextInvoiceViewScreen().getInvoiceNumber().isDisplayed(), "Invoice number hasn't been displayed");
        Assert.assertEquals(new VNextInvoiceViewScreen().getInvoiceNumber().getText(), invoiceNumber,
                "Invoice number hasn't been correct");
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
    }

    public static void verifyInvoiceDateIsDisplayed() {

        VNextInvoiceViewScreen invoiceViewScreen = new VNextInvoiceViewScreen();
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(invoiceViewScreen.getPageFrame());
        Assert.assertTrue(new VNextInvoiceViewScreen().getInvoiceDate().isDisplayed(), "Invoice date hasn't been displayed");
        Assert.assertEquals(new VNextInvoiceViewScreen().getInvoiceDate().getText(), LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                "Invoice date hasn't been correct");
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
    }

    public static void verifyServiceIsDisplayed(String serviceName) {

        VNextInvoiceViewScreen invoiceViewScreen = new VNextInvoiceViewScreen();
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(invoiceViewScreen.getPageFrame());
        Assert.assertTrue(invoiceViewScreen.service(serviceName).isDisplayed(),
                serviceName + "service hasn't been displayed");
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
    }

    public static void verifyVehicleInfoIsDisplayed(VehicleInfoData vehicleInfoData) {

        VNextInvoiceViewScreen invoiceViewScreen = new VNextInvoiceViewScreen();
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(invoiceViewScreen.getPageFrame());
        Assert.assertTrue(new VNextInvoiceViewScreen().getVinNumber().isDisplayed(), "VIN# hasn't been displayed");
        Assert.assertEquals(new VNextInvoiceViewScreen().getVinNumber().getText(), vehicleInfoData.getVINNumber(),
                "VIN# hasn't been correct");
        Assert.assertTrue(new VNextInvoiceViewScreen().getMake().isDisplayed(), "Make hasn't been displayed");
        Assert.assertEquals(new VNextInvoiceViewScreen().getMake().getText(), vehicleInfoData.getVehicleMake(),
                "Make hasn't been correct");
        Assert.assertTrue(new VNextInvoiceViewScreen().getModel().isDisplayed(), "Model hasn't been displayed");
        Assert.assertEquals(new VNextInvoiceViewScreen().getModel().getText(), vehicleInfoData.getVehicleModel(),
                "Model hasn't been correct");
        Assert.assertTrue(new VNextInvoiceViewScreen().getYear().isDisplayed(), "Year hasn't been displayed");
        Assert.assertEquals(new VNextInvoiceViewScreen().getYear().getText(), vehicleInfoData.getVehicleYear(),
                "Year hasn't been correct");
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
    }

    public static void verifyCustomerInfoIsDisplayed(RetailCustomer retailCustomer) {

        VNextInvoiceViewScreen invoiceViewScreen = new VNextInvoiceViewScreen();
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().frame(invoiceViewScreen.getPageFrame());
        Assert.assertTrue(new VNextInvoiceViewScreen().getCustomerName().isDisplayed(), "Customer name hasn't been displayed");
        Assert.assertEquals(new VNextInvoiceViewScreen().getCustomerName().getText(), retailCustomer.getFullName(),
                "Customer name hasn't been correct");
        Assert.assertTrue(new VNextInvoiceViewScreen().getCustomerAddress().isDisplayed(), "Customer address hasn't been displayed");
        Assert.assertEquals(new VNextInvoiceViewScreen().getCustomerAddress().getText(), retailCustomer.getCustomerAddress1(),
                "Customer address hasn't been correct");
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().switchTo().defaultContent();
    }
}
