package com.cyberiansoft.test.vnext.restclient;


import com.cyberiansoft.test.dataclasses.r360.InspectionDTO;
import com.cyberiansoft.test.dataclasses.r360.InvoiceDTO;
import com.cyberiansoft.test.dataclasses.r360.WorkOrderDTO;
import com.cyberiansoft.test.vnext.restclient.monitorrolessettings.EmployeeRoleDTO;
import com.cyberiansoft.test.vnext.restclient.monitorrolessettings.EmployeeRoleSettingsDTO;
import com.cyberiansoft.test.vnext.restclient.monitorrolessettings.RoleSettingsDTO;
import com.cyberiansoft.test.vnext.restclient.monitorrolessettings.UserAuthorizationDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface VNextAPIService {

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
                                    @Body InvoiceDTO invoiceData);


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
                                                                         @Query("json") boolean json);

    @Headers({"Content-Type: application/json",
            "Authorization: Basic ewp1c2VybmFtZTogIm9sZXhhbmRyLmtyYW1hckBjeWJlcmlhbnNvZnQuY29tIiwKZW1haWw6ICJvbGV4YW5kci5rcmFtYXJAY3liZXJpYW5zb2Z0LmNvbSIsCnBhc3N3b3JkOiAidGVzdDEyMzQ1IiwKdXJsOiAiY29tcGFuaW9uYXBwLnFjLmN5YmVyaWFuY29uY2VwdHMuY29tIgp9"})
    @POST("account/actions/login")
    Call<UserAuthorizationDTO> accountLogin();

    @Headers({"Content-Type: application/json"})
    @GET("employees/roles")
    Call<List<EmployeeRoleDTO>> getEmployeesRoles(@Header("Authorization") String userKey);

    @Headers({"Content-Type: application/json"})
    @GET("employees/roles/settings")
    Call<List<EmployeeRoleSettingsDTO>> getEmployeesRolesSettings(@Header("Authorization") String userKey);

    @Headers({"Content-Type: application/json",})
    @GET("employees/roles/{roleId}/settings")
    Call<RoleSettingsDTO> getRoleSettingsByRoleId(@Header("Authorization") String userKey,
                                                  @Path("roleId") String roleId);

    @Headers({"Content-Type: application/json"})
    @PUT("employees/roles/{roleId}/settings")
    Call<Void> updateEmployeeRoleSettings(@Header("Authorization") String userKey,
                                          @Path("roleId") String roleId,
                                          @Body RoleSettingsDTO roleSettings);
}
