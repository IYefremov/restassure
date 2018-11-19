package com.cyberiansoft.test.vnext.restclient;


import com.cyberiansoft.test.dataclasses.r360.InspectionDTO;
import com.cyberiansoft.test.dataclasses.r360.WorkOrderDTO;
import retrofit2.Call;
import retrofit2.http.*;

public interface VNextAPIService  {

    @Headers("Content-Type: application/json")
    @POST("inspections/save/{estimationId}")
    Call<BasicResponse> saveInspection(@Path("estimationId") String estimationId, @Query("licenceId") String licenceId,
                                       @Query("deviceId") String deviceId,
                                       @Query("applicationId") String applicationId,
                                       @Query("userId") String userId,
                                       @Query("json") boolean json,
                                       @Body InspectionDTO inspectionData);

    @Headers("Content-Type: application/json")
    @POST("orders/save/{OrderId}")
    Call<BasicResponse> saveWorkOrder(@Path("OrderId") String estimationId, @Query("licenceId") String licenceId,
                                       @Query("deviceId") String deviceId,
                                       @Query("applicationId") String applicationId,
                                       @Query("userId") String userId,
                                       @Query("json") boolean json,
                                       @Body WorkOrderDTO workOrderData);

    @Headers("Content-Type: application/json")
    @POST("invoices/save/{InvoiceId}")
    Call<BasicResponse> saveInvoice(@Path("InvoiceId") String estimationId, @Query("licenceId") String licenceId,
                                      @Query("deviceId") String deviceId,
                                      @Query("applicationId") String applicationId,
                                      @Query("userId") String userId,
                                      @Query("json") boolean json,
                                      @Body WorkOrderDTO workOrderData);


    @GET("inspections/")
    Call<InspectionsListResponse> getLastMyInspection(@Query("licenceId") String licenceId,
                                       @Query("deviceId") String deviceId,
                                       @Query("applicationId") String applicationId,
                                       @Query("userId") String userId,
                                       @Query("json") boolean json,
                                       @Query("pageIndex") int pageIndex,
                                       @Query("pageSize") int pageSize,
                                       @Query("estatusid") String estatusid,
                                       @Query("searchText") String searchText);

    @GET("orders/")
    Call<WorkOrdersListResponse> getLastMyWorkOrder(@Query("licenceId") String licenceId,
                                                      @Query("deviceId") String deviceId,
                                                      @Query("applicationId") String applicationId,
                                                      @Query("userId") String userId,
                                                      @Query("json") boolean json,
                                                      @Query("pageIndex") int pageIndex,
                                                      @Query("pageSize") int pageSize,
                                                      @Query("orderStatusID") String orderStatusID,
                                                      @Query("searchText") String searchText);

    @GET("invoices/")
    Call<InvoicesListResponse> getLastMyInvoice(@Query("licenceId") String licenceId,
                                                    @Query("deviceId") String deviceId,
                                                    @Query("applicationId") String applicationId,
                                                    @Query("userId") String userId,
                                                    @Query("json") boolean json,
                                                    @Query("pageIndex") int pageIndex,
                                                    @Query("pageSize") int pageSize,
                                                    @Query("status") String status,
                                                    @Query("clientId") String clientId,
                                                    @Query("searchText") String searchText);

    @GET("orders/getfull/{OrderId}")
    Call<WorkOrderForInvoiceListResponse> getFullWorkOrderForInvoiceInfo(@Path("OrderId") String estimationId, @Query("licenceId") String licenceId,
                                                    @Query("deviceId") String deviceId,
                                                    @Query("applicationId") String applicationId,
                                                    @Query("userId") String userId,
                                                    @Query("json") boolean json
                                                    );
}
