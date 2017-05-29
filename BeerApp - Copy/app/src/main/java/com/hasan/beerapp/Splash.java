package com.hasan.beerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        gotoMainActivity();
    }

    // GOTO MAINACTIVITY
    public void gotoMainActivity() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Intent openActivity = new Intent(Splash.this, MainActivity.class);
//                            openActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(openActivity);
                            finish();
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    });

                }

            }
        }).start();

    }

}
