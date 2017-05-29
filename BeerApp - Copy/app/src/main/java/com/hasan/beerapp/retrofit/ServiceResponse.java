package com.hasan.beerapp.retrofit;


/**
 * Created by Hasan on 5/7/17.
 */


public interface ServiceResponse {
    void onResult(int type, HttpResponse o);
    void parseDataInBackground(int type, HttpResponse o);
    void onError(int type, HttpResponse o, Exception e);
    void noInternetAccess(int type);
    void requestHttpCall(int type, boolean isPost, String... params);


}
