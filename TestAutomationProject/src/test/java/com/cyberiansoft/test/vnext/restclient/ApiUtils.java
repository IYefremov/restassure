package com.cyberiansoft.test.vnext.restclient;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://repair360.qc.cyberianconcepts.com/api/v1/";

    public static VNextAPIService getAPIService() {
        return RestClient.getRestClient(BASE_URL).create(VNextAPIService.class);
    }
}
