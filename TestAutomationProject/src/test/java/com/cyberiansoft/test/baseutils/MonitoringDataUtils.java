package com.cyberiansoft.test.baseutils;

import com.cyberiansoft.test.dataclasses.ServiceData;

import java.util.ArrayList;
import java.util.List;

public class MonitoringDataUtils {
    //TODO: Temp solution to provide test data to @BeforeMethod method
    public static List<ServiceData> getTestSerivceData() {
        List<ServiceData> serviceData = new ArrayList<>();
        ServiceData service = new ServiceData();
        service.setServiceName("Expenses_money (AM)");
        serviceData.add(service);
        service = new ServiceData();
        service.setServiceName("Labor AM");
        serviceData.add(service);
        return serviceData;
    }
}
