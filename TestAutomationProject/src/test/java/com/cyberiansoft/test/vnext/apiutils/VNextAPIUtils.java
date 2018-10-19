package com.cyberiansoft.test.vnext.apiutils;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.r360.WorkOrderDTO;
import com.cyberiansoft.test.dataprovider.JsonUtils;
import com.cyberiansoft.test.objectpoolsi.WOPool;
import com.cyberiansoft.test.objectpoolsi.WorkOrderFactory;
import com.cyberiansoft.test.vnext.config.VNextDataInfo;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class VNextAPIUtils {

    private static VNextAPIUtils instance = null;

    private VNextAPIUtils() {
    }

    public static VNextAPIUtils getInstance() {
        if ( instance == null ) {
            instance = new VNextAPIUtils();
        }

        return instance;
    }

    public List<WorkOrderDTO> generateWorkOrders(String WO_DATA_FILE, WorkOrderTypes workOrderType, RetailCustomer testcustomer,
                                                 Employee employee, String licenseID, String deviceID, String appID,
                                                 String appLicenseEntity, int numberOfWorkOrdresToCreate) throws Exception {
        final String apiPath = getAPIDataFilesPath();

        List<WorkOrderDTO> workOrderDTOS = new ArrayList<WorkOrderDTO>();
        WOPool woPool = null;

        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(1);
        config.setMaxTotal(numberOfWorkOrdresToCreate);


        WorkOrderDTO workOrderDTO = JsonUtils.getWorkOrderDTO(new File(apiPath + WO_DATA_FILE),
                new File(apiPath + VNextDataInfo.getInstance().getDefaultVehicleInfoDataFileName()),
                new File(apiPath + VNextDataInfo.getInstance().getDefaultDeviceInfoDataFileName()));
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        woPool = new WOPool(new WorkOrderFactory(workOrderDTO, workOrderType, testcustomer.getClientId(),
                employee.getEmployeeID(), licenseID, deviceID, appID, appLicenseEntity), config);
        for (int i = 0; i < woPool.getMaxTotal(); i++) {
            try {
                WorkOrderDTO workOrder = woPool.borrowObject();
                workOrderDTOS.add(workOrder);
                woPool.invalidateObject(workOrder);
            } catch (SocketTimeoutException tm) {
                System.out.println("SocketTimeoutException happen");
            }
        }
        return workOrderDTOS;
    }

    private String getAPIDataFilesPath() {
        return VNextDataInfo.getInstance().getPathToAPIDataFiles();
    }
}
