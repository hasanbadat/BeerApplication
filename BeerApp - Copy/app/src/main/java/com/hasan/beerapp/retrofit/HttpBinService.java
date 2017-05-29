package com.hasan.beerapp.retrofit;

import com.google.gson.JsonElement;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Url;

/**
 * Created by Hasan on 5/7/17.
 */
public interface HttpBinService {


    @GET
    Call<JsonElement> getData(@Url String url, @HeaderMap HashMap<String, String> header);
}
