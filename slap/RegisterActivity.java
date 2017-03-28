package com.danaleastonia.slap;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import static android.Manifest.permission.READ_CONTACTS;


//begin activity
public class RegisterActivity extends AppCompatActivity {
    
    // member variables declaration
    private EditText mRegisterNameView;
    private EditText mRegisterUserNameView;
    private EditText mRegisterPasswordView;
    private EditText mRegisterPasswordConfirmView;
    private EditText mRegisterEmailView;

    private SharedPreferences sharedPreferences;
    private static final String MyPreferences = "MyPrefs";
    private static final String LOGIN_FLAG = "loginFlag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // create database
        final DBHandler db = new DBHandler(this);

        // member variable assignment
        mRegisterNameView = (EditText) findViewById(R.id.etName);
        mRegisterUserNameView = (EditText) findViewById(R.id.etUsername);
        mRegisterPasswordView = (EditText) findViewById(R.id.etPassword);
        mRegisterPasswordConfirmView = (EditText) findViewById(R.id.etConfirmPassword);
        mRegisterEmailView = (EditText) findViewById(R.id.etEmail);

        Button mRegisterButton = (Button) findViewById(R.id.bRegister);
        mRegisterButton.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View view) {

                final String nameView = mRegisterNameView.getText().toString();
                final String userNameView = mRegisterUserNameView.getText().toString();
                final String passwordView = mRegisterPasswordView.getText().toString();
                final String passwordConfirmView = mRegisterPasswordConfirmView.getText().toString();
                final String emailView = mRegisterEmailView.getText().toString();

                db.addUser(new User(0, nameView, userNameView, emailView, passwordView));
       			Log.d("Insert: ", "Inserting ..");
        		Log.d("Reading: ", "Reading all users..");
        		List<User> users = db.getAllUsers();

                for (User user : users) {
                    String log = "Id: " + user.getId() + ", Name: " + user.getName() + ", Username: " +  user.getUserName() + ", Email: " + user.getEmail() + ", Password: " + user.getPassword();
                    // Writing users to log
                    Log.d("User: : ", log);
                }

                sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean(LOGIN_FLAG, true).apply();

        		Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(mainIntent);
        	}
        });
    }
}
