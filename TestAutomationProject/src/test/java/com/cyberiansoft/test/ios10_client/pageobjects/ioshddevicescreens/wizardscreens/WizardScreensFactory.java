package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class WizardScreensFactory {

    private static final Map<WizardScreenTypes, Supplier<IBaseWizardScreen>> screensMap = new HashMap<>();

    private static  final Supplier<IBaseWizardScreen> invoiceInfoScreenSupplier = InvoiceInfoScreen::new;

    private static final Supplier<IBaseWizardScreen> vehicleScreenSupplier = VehicleScreen::new;

    private static final Supplier<IBaseWizardScreen> servicesScreenSupplier = ServicesScreen::new;

    private static final Supplier<IBaseWizardScreen> claimScreenSupplier = ClaimScreen::new;

    private static final Supplier<IBaseWizardScreen> orderSummaryScreenSupplier = OrderSummaryScreen::new;

    private static final Supplier<IBaseWizardScreen> questionsScreenSupplier = QuestionsScreen::new;

    private static final Supplier<IBaseWizardScreen> questionAnswerScreenSupplier = QuestionAnswerScreen::new;

    private static final Supplier<IBaseWizardScreen> enterpriseBeforeDamageScreenAnswerScreenSupplier = EnterpriseBeforeDamageScreen::new;

    private static final Supplier<IBaseWizardScreen> priceMatrixScreenScreenAnswerScreenSupplier = PriceMatrixScreen::new;

    private static final Supplier<IBaseWizardScreen> visualScreenScreenAnswerScreenSupplier = VisualInteriorScreen::new;

    static {
        screensMap.put(WizardScreenTypes.INVOICE_INFO, invoiceInfoScreenSupplier);
        screensMap.put(WizardScreenTypes.VEHICLE_INFO, vehicleScreenSupplier);
        screensMap.put(WizardScreenTypes.SERVICES, servicesScreenSupplier);
        screensMap.put(WizardScreenTypes.CLAIM, claimScreenSupplier);
        screensMap.put(WizardScreenTypes.ORDER_SUMMARY, orderSummaryScreenSupplier);
        screensMap.put(WizardScreenTypes.QUESTIONS, questionsScreenSupplier);
        screensMap.put(WizardScreenTypes.QUESTION_ANSWER, questionAnswerScreenSupplier);
        screensMap.put(WizardScreenTypes.ENTERPRISE_BEFORE_DAMAGE, enterpriseBeforeDamageScreenAnswerScreenSupplier);
        screensMap.put(WizardScreenTypes.PRICE_MATRIX, priceMatrixScreenScreenAnswerScreenSupplier);
        screensMap.put(WizardScreenTypes.VISUAL_EXTERIOR, visualScreenScreenAnswerScreenSupplier);
        screensMap.put(WizardScreenTypes.VISUAL_INTERIOR, visualScreenScreenAnswerScreenSupplier);
    }

    public static final IBaseWizardScreen getWizardScreenType(WizardScreenTypes wizardScreenType) {
        return screensMap.get(wizardScreenType).get();
    }
}
