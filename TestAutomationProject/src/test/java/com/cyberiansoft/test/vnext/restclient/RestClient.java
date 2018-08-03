package com.cyberiansoft.test.vnext.restclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public ApplicationEndpoints createClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://repair360.cyberianconcepts.com/api/v1/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        return retrofit.create(ApplicationEndpoints.class);
    }
}
