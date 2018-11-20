package com.cyberiansoft.test.objectpoolsi;

import com.cyberiansoft.test.dataclasses.r360.InvoiceDTO;
import com.cyberiansoft.test.dataclasses.r360.WorkOrderForInvoiceDTO;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.globalutils.GlobalUtils;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypeData;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.restclient.ApiUtils;
import com.cyberiansoft.test.vnext.restclient.BasicResponse;
import com.google.gson.Gson;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import retrofit2.Response;

public class InvoiceFactory extends BasePooledObjectFactory<InvoiceDTO> {

    private InvoiceDTO invoioceDTO;
    private InvoiceTypes invoiceTypes;
    private WorkOrderForInvoiceDTO workOrderForInvoiceDTO;
    private int newInvoiceNumber;

    public InvoiceFactory(InvoiceDTO invoioceDTO, InvoiceTypes invoiceTypes,
                          WorkOrderForInvoiceDTO workOrderForInvoiceDTO, int newInvoiceNumber) {
        super();

        this.invoioceDTO = invoioceDTO;
        this.invoiceTypes = invoiceTypes;
        this.workOrderForInvoiceDTO = workOrderForInvoiceDTO;
        this.newInvoiceNumber = newInvoiceNumber;

    }

    @Override
    public InvoiceDTO create() throws Exception {
        //int inspnumber = Integer.valueOf(getLastInspectionNumber(licenseID, deviceID, appID,
          //      employeeID, true, GlobalUtils.getInspectionSymbol() + appLicenseEntity).getLocalNo());

        Gson gson = new Gson();
        JtwigTemplate template = JtwigTemplate.inlineTemplate(gson.toJson(invoioceDTO));
        JtwigModel model = JtwigModel.newModel()
                .with("InvoiceId", GlobalUtils.getUUID())
                .with("ClientId", workOrderForInvoiceDTO.getClientId())
                .with("EmployeeId", workOrderForInvoiceDTO.getEmployeeId())
                .with("LicenceId", workOrderForInvoiceDTO.getLicenceId())
                .with("DeviceId", workOrderForInvoiceDTO.getDeviceLicences().getDeviceID())
                .with("InvoiceTypeId", new InvoiceTypeData(invoiceTypes).getInvoiceTypeID())
                .with("UTCTime", GlobalUtils.getVNextInspectionCreationTime())
                .with("InvoiceDate", GlobalUtils.getVNextInspectionDate())
                .with("LicenceID", workOrderForInvoiceDTO.getDeviceLicences().getLicenceId())
                .with("OrderNo", workOrderForInvoiceDTO.getOrderNo())
                .with("OrderId", workOrderForInvoiceDTO.getOrderId())
                .with("OrderTypeId", workOrderForInvoiceDTO.getOrderTypeId())
                .with("OrderDate", workOrderForInvoiceDTO.getOrderDate())
                .with("InvoiceOrderId", GlobalUtils.getUUID());


        String json = template.render(model);
        InvoiceDTO newInvoice = JSonDataParser.getTestDataFromJson(json, InvoiceDTO.class);
        newInvoice.setLocalNo(newInvoiceNumber);
        Response<BasicResponse> res = ApiUtils.getAPIService().saveInvoice(newInvoice.getInvoiceId(),
                newInvoice.getLicenceID(),newInvoice.getDevice().getDeviceId(),
                workOrderForInvoiceDTO.getClient().getApplicationId(),
                workOrderForInvoiceDTO.getEmployeeId(), true, newInvoice).execute();


        return newInvoice;
    }

    @Override
    public PooledObject<InvoiceDTO> wrap(InvoiceDTO invoiceDTO) {
        // TODO Auto-generated method stub
        return new DefaultPooledObject<InvoiceDTO>(invoiceDTO);
    }
}
