package com.hasan.beerapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hasan on 5/7/17.
 */

public class RetrofitFactory {
    public static final int POST = 1, GET = 2, PUT = 3;
    public static String apiKey= "?key=e9bbb587c9f8059acbfb584d250a5501&availableId=1";
    Retrofit retrofit;
    public static String MainApiUrl = "http://api.brewerydb.com/v2/beers/";

    public void requestService(int methodType, HashMap map, String postURL, JsonObject jsonElement, CallBackRetrofit callback) {
        HttpBinService service = getServiceInstance(postURL + "/");
        Call<JsonElement> j = null;
        if (methodType == GET) {
            j = service.getData(postURL, map);
            j.enqueue(callback);
        }
    }

    public HttpBinService getServiceInstance(String postURL) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        retrofit = new Retrofit.Builder()

                .baseUrl(MainApiUrl)
                .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient)
                .build();

//        }
        HttpBinService service = retrofit.create(HttpBinService.class);
        return service;
    }
}
