package com.cyberiansoft.test.vnext.validations.invoices;

import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.vnext.screens.invoices.VNextInvoiceViewScreen;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VNextInvoiceViewScreenValidations {

    public static void verifyInvoicePrintViewScreenIsDisplayed() {

        Assert.assertTrue(new VNextInvoiceViewScreen().getRootElement().isDisplayed(), "Invoice view screen hasn't been displayed");
    }

    public static void verifySignatureIsDisplayed() {

        Assert.assertTrue(new VNextInvoiceViewScreen().getSignature().isDisplayed(), "Signature hasn't been displayed");
    }

    public static void verifyInvoiceNumberIsDisplayed(String invoiceNumber) {

        VNextInvoiceViewScreen invoiceViewScreen = new VNextInvoiceViewScreen();
        Assert.assertTrue(invoiceViewScreen.getInvoiceNumber().isDisplayed(), "Invoice number hasn't been displayed");
        Assert.assertEquals(invoiceViewScreen.getInvoiceNumber().getText(), invoiceNumber,
                "Invoice number hasn't been correct");
    }

    public static void verifyInvoiceDateIsDisplayed() {

        VNextInvoiceViewScreen invoiceViewScreen = new VNextInvoiceViewScreen();
        Assert.assertTrue(invoiceViewScreen.getInvoiceDate().isDisplayed(), "Invoice date hasn't been displayed");
        Assert.assertEquals(invoiceViewScreen.getInvoiceDate().getText(), LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                "Invoice date hasn't been correct");
    }

    public static void verifyServiceIsDisplayed(String serviceName) {

        Assert.assertTrue(new VNextInvoiceViewScreen().serviceCellByServiceName(serviceName).isDisplayed(),
                serviceName + "service hasn't been displayed");
    }

    public static void verifyVehicleInfoIsDisplayed(VehicleInfoData vehicleInfoData) {

        VNextInvoiceViewScreen invoiceViewScreen = new VNextInvoiceViewScreen();
        Assert.assertTrue(invoiceViewScreen.getVinNumber().isDisplayed(), "VIN# hasn't been displayed");
        Assert.assertEquals(invoiceViewScreen.getVinNumber().getText(), vehicleInfoData.getVINNumber(),
                "VIN# hasn't been correct");
        Assert.assertTrue(invoiceViewScreen.getMake().isDisplayed(), "Make hasn't been displayed");
        Assert.assertEquals(invoiceViewScreen.getMake().getText(), vehicleInfoData.getVehicleMake(),
                "Make hasn't been correct");
        Assert.assertTrue(invoiceViewScreen.getModel().isDisplayed(), "Model hasn't been displayed");
        Assert.assertEquals(invoiceViewScreen.getModel().getText(), vehicleInfoData.getVehicleModel(),
                "Model hasn't been correct");
        Assert.assertTrue(invoiceViewScreen.getYear().isDisplayed(), "Year hasn't been displayed");
        Assert.assertEquals(invoiceViewScreen.getYear().getText(), vehicleInfoData.getVehicleYear(),
                "Year hasn't been correct");
    }

    public static void verifyCustomerInfoIsDisplayed(String customerName, String customerAddress) {

        VNextInvoiceViewScreen invoiceViewScreen = new VNextInvoiceViewScreen();
        Assert.assertTrue(invoiceViewScreen.getCustomerName().isDisplayed(), "Customer name hasn't been displayed");
        Assert.assertEquals(invoiceViewScreen.getCustomerName().getText(), customerName,
                "Customer name hasn't been correct");
        Assert.assertTrue(invoiceViewScreen.getCustomerAddress().isDisplayed(), "Customer address hasn't been displayed");
        Assert.assertEquals(invoiceViewScreen.getCustomerAddress().getText(), customerAddress,
                "Customer address hasn't been correct");
    }
}
