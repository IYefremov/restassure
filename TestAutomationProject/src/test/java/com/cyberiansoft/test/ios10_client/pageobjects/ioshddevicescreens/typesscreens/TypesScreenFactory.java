package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.ITypeScreen;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TypesScreenFactory {

    private static final Map<TypeScreenContext, Supplier<ITypeScreen>> screensMap = new HashMap<>();

    private static  final Supplier<ITypeScreen> myWorkOrdersScreenSupplier = () -> new MyWorkOrdersScreen();

    private static  final Supplier<ITypeScreen> myInspectionsScreenSupplier = () -> new MyInspectionsScreen();

    private static  final Supplier<ITypeScreen> myInvoicesScreenSupplier = () -> new MyInvoicesScreen();

    private static  final Supplier<ITypeScreen> serviceRequestsScreenSupplier = () -> new ServiceRequestsScreen();

    private static  final Supplier<ITypeScreen> teamWorkOrdersScreenSupplier = () -> new TeamWorkOrdersScreen();

    private static  final Supplier<ITypeScreen> teamInspectionsScreenSupplier = () -> new TeamInspectionsScreen();

    private static  final Supplier<ITypeScreen> invoicesInfoScreenSupplier = () -> new InvoiceInfoScreen();

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
