package com.hasan.beerapp.retrofit;

import android.os.AsyncTask;

import com.google.gson.JsonElement;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hasan on 5/7/17.
 */

public class CallBackRetrofit implements Callback<JsonElement> {
    ServiceResponse response;
    int type;

    public CallBackRetrofit(int type, ServiceResponse response) {
        this.response = response;
        this.type = type;
    }

    @Override
    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

        if (response != null) {
            HttpResponse httpResponse = new HttpResponse();
            if (response.body() != null)
                httpResponse.setResponseData(response.body().toString());
            else try {
                httpResponse.setResponsMessage(response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            httpResponse.setResponseCode(response.code());
            if (response.isSuccessful()) {
                asyncTask(httpResponse);
            } else this.response.onError(type, httpResponse, null);
        }
    }

    @Override
    public void onFailure(Call<JsonElement> call, Throwable t) {
        t.printStackTrace();
        this.response.onError(type, null, new Exception());
    }


    public void asyncTask(final HttpResponse res) {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                response.parseDataInBackground(type, res);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (res.getResponseCode() == 200) {
                    response.onResult(type, res);
                } else response.onError(type, res, null);


            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
