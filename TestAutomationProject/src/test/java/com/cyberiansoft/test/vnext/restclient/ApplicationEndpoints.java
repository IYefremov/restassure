package com.cyberiansoft.test.vnext.restclient;


import com.cyberiansoft.test.dataclasses.r360.Inspection;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApplicationEndpoints {

    @Headers("Content-Type: application/json")
    @POST("inspections/save/{estimationId}")
    Call<BasicResponse> saveInspection(@Path("estimationId") String estimationId, @Query("licenceId") String licenceId,
                                       @Query("deviceId") String deviceId,
                                       @Query("applicationId") String applicationId,
                                       @Query("userId") String userId,
                                       @Query("json") boolean json,
                                       @Body Inspection inspectionData);
}
