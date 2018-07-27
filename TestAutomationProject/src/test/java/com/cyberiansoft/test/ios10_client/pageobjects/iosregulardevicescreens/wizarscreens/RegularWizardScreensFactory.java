package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RegularWizardScreensFactory {

    private static final Map<WizardScreenTypes, Supplier<IBaseWizardScreen>> screensMap = new HashMap<>();

    private static  final Supplier<IBaseWizardScreen> invoiceInfoScreenSupplier = () -> new RegularInvoiceInfoScreen();

    private static final Supplier<IBaseWizardScreen> vehicleScreenSupplier = () -> new RegularVehicleScreen();

    private static final Supplier<IBaseWizardScreen> servicesScreenSupplier = () -> new RegularServicesScreen();

    private static final Supplier<IBaseWizardScreen> claimScreenSupplier = () -> new RegularClaimScreen();

    private static final Supplier<IBaseWizardScreen> orderSummaryScreenSupplier = () -> new RegularOrderSummaryScreen();

    private static final Supplier<IBaseWizardScreen> questionsScreenSupplier = () -> new RegularQuestionsScreen();

    private static final Supplier<IBaseWizardScreen> enterpriseBeforeDamageScreenAnswerScreenSupplier = () -> new RegularEnterpriseBeforeDamageScreen();

    private static final Supplier<IBaseWizardScreen> priceMatrixScreenScreenAnswerScreenSupplier = () -> new RegularPriceMatrixScreen();

    private static final Supplier<IBaseWizardScreen> visualScreenScreenAnswerScreenSupplier = () -> new RegularVisualInteriorScreen();

    static {
        screensMap.put(WizardScreenTypes.INVOICE_INFO, invoiceInfoScreenSupplier);
        screensMap.put(WizardScreenTypes.VEHICLE_INFO, vehicleScreenSupplier);
        screensMap.put(WizardScreenTypes.SERVICES, servicesScreenSupplier);
        screensMap.put(WizardScreenTypes.CLAIM, claimScreenSupplier);
        screensMap.put(WizardScreenTypes.ORDER_SUMMARY, orderSummaryScreenSupplier);
        screensMap.put(WizardScreenTypes.QUESTIONS, questionsScreenSupplier);
        screensMap.put(WizardScreenTypes.ENTERPRISE_BEFORE_DAMAGE, enterpriseBeforeDamageScreenAnswerScreenSupplier);
        screensMap.put(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenScreenAnswerScreenSupplier);
        screensMap.put(WizardScreenTypes.VISUAL_EXTERIOR, visualScreenScreenAnswerScreenSupplier);
        screensMap.put(WizardScreenTypes.VISUAL_INTERIOR, visualScreenScreenAnswerScreenSupplier);
    }

    public static final IBaseWizardScreen getWizardScreenType(WizardScreenTypes wizardScreenType) {
        return screensMap.get(wizardScreenType).get();
    }
}
