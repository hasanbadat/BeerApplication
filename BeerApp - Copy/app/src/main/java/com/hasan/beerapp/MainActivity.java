package com.hasan.beerapp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hasan.beerapp.adapter.AdapterPager;
import com.hasan.beerapp.models.Datum;
import com.hasan.beerapp.models.ModelMain;
import com.hasan.beerapp.retrofit.CallBackRetrofit;
import com.hasan.beerapp.retrofit.HttpResponse;
import com.hasan.beerapp.retrofit.RetrofitFactory;
import com.hasan.beerapp.retrofit.ServiceResponse;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ServiceResponse {
    AdapterPager adapterBears;
    CustomViewPager viewPager;
    ProgressBar pb;
    int pageNumber = 1;
    private ModelMain modelResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        requestHttpCall(0, false, null);
    }

    private void initViews() {
        viewPager = (CustomViewPager) findViewById(R.id.vpager);

        viewPager.setPagingEnabled(false);
        pb = (ProgressBar) findViewById(R.id.pb);
        viewPager.setVisibility(View.GONE);

    }

    /**
     * this will populate upper vertical list of Bears
     *
     * @param isReplace
     * @param list
     */
    private void populateValuesBears(boolean isReplace, List<Datum> list) {
        if (adapterBears == null) {
            adapterBears = new AdapterPager(this, list, viewPager);
            viewPager.setAdapter(adapterBears);
            if (adapterBears.getCount() > 0) {
                pb.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
            }
            return;
        }
        if (isReplace) {
            adapterBears.replaceData(list);
        } else adapterBears.addToList(list);



    }

    @Override
    public void onResult(int type, HttpResponse o) {
        if (modelResponse != null) {
            populateValuesBears(false, modelResponse.getData());
        }

    }

    @Override
    public void parseDataInBackground(int type, HttpResponse o) {

        if (o != null && !TextUtils.isEmpty(o.getResponseData())) {
            try {
                modelResponse = new Gson().fromJson(o.getResponseData(), ModelMain.class);
            } catch (Exception e) {
                modelResponse = null;
            }
        } else {
            modelResponse = null;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onError(int type, HttpResponse o, Exception e) {
        if (modelResponse == null)
            Toast.makeText(this, "There is an error occured while getting data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noInternetAccess(int type) {
        Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestHttpCall(int type, boolean isPost, String... params) {
        FrontEngine.getInstance().getRetrofitFactory().requestService(RetrofitFactory.GET, new HashMap<>(), RetrofitFactory.MainApiUrl + "" + RetrofitFactory.apiKey + "&p=" + pageNumber, new JsonObject(), new CallBackRetrofit(1, this));

    }

}
