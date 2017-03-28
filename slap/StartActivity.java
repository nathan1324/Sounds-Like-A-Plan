package com.danaleastonia.slap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String MyPreferences = "MyPrefs";
    public static final String LOGIN_FLAG = "loginFlag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        Boolean loginFlag = sharedPreferences.getBoolean(LOGIN_FLAG, false);

        if (loginFlag == false) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //finish();
        }
    }
}
