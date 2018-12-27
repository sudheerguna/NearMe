package com.product.nearme;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.product.nearme.slidemenu.SlideHomeActivity;
import com.product.nearme.utils.SharedPref;
import com.product.nearme.utils.constant;

public class MainActivity extends AppCompatActivity {
    SharedPref mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        mSharedPref = new SharedPref(MainActivity.this);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(!constant.isEmptyString(mSharedPref.getString(constant.Login_Success))){
                    if(mSharedPref.getString(constant.Login_Success).equals("1")){
                        Intent i = new Intent(MainActivity.this, SlideHomeActivity.class);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                }else{
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }

                // close this activity
                finish();
            }
        }, 3000);


    }
}
