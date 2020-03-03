package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularInvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.ITypeScreen;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RegularTypesScreenFactory {

    private static final Map<TypeScreenContext, Supplier<ITypeScreen>> screensMap = new HashMap<>();

    private static  final Supplier<ITypeScreen> myWorkOrdersScreenSupplier = RegularMyWorkOrdersScreen::new;

    private static  final Supplier<ITypeScreen> myInspectionsScreenSupplier = RegularMyInspectionsScreen::new;

    private static  final Supplier<ITypeScreen> myInvoicesScreenSupplier = RegularMyInvoicesScreen::new;

    private static  final Supplier<ITypeScreen> serviceRequestsScreenSupplier = RegularServiceRequestsScreen::new;

    private static  final Supplier<ITypeScreen> teamWorkOrdersScreenSupplier = RegularTeamWorkOrdersScreen::new;

    private static  final Supplier<ITypeScreen> teamInspectionsScreenSupplier = RegularTeamInspectionsScreen::new;

    private static  final Supplier<ITypeScreen> invoicesInfoScreenSupplier = RegularInvoiceInfoScreen::new;

    static {
        screensMap.put(TypeScreenContext.WORKORDER, myWorkOrdersScreenSupplier);
        screensMap.put(TypeScreenContext.INSPECTION, myInspectionsScreenSupplier);
        screensMap.put(TypeScreenContext.INVOICE, myInvoicesScreenSupplier);
        screensMap.put(TypeScreenContext.SERVICEREQUEST, serviceRequestsScreenSupplier);
        screensMap.put(TypeScreenContext.TEAMWORKORDER, teamWorkOrdersScreenSupplier);
        screensMap.put(TypeScreenContext.TEAMINSPECTION, teamInspectionsScreenSupplier);
        screensMap.put(TypeScreenContext.INVOICEINFO, invoicesInfoScreenSupplier);
    }

    public static final ITypeScreen getTypeScreen(TypeScreenContext typeContext) {
        return screensMap.get(typeContext).get();
    }
}
