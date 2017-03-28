package com.danaleastonia.slap;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

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
    private static final String LOGGEDIN_USER_EMAIL = "LOGGEDIN_USER_EMAIL";

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

                if (mRegisterNameView.getText() != null
                        && mRegisterUserNameView.getText() != null
                        && mRegisterPasswordView.getText() != null
                        && mRegisterPasswordConfirmView.getText() != null
                        && mRegisterEmailView.getText() != null) {
                    final String nameView = mRegisterNameView.getText().toString();
                    final String userNameView = mRegisterUserNameView.getText().toString();
                    final String passwordView = mRegisterPasswordView.getText().toString();
                    final String passwordConfirmView = mRegisterPasswordConfirmView.getText().toString();
                    final String emailView = mRegisterEmailView.getText().toString();

                    DBHandler dbHandler = new DBHandler(getApplicationContext());

                    if (dbHandler.getEmailPasswordMap().keySet().contains(emailView)) {
                        showDialog("Email ID already registered!");
                    } else {
                        if (passwordView.equals(passwordConfirmView)) {
                            Log.d("Insert: ", "Inserting ..");
                            db.addUser(new User(0, nameView, userNameView, emailView, passwordView));

                            sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
                            sharedPreferences.edit().putBoolean(LOGIN_FLAG, true).apply();
                            sharedPreferences.edit().putString(LOGGEDIN_USER_EMAIL, emailView).apply();

                            Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                            RegisterActivity.this.startActivity(mainIntent);
                            finish();
                        } else {
                            showDialog("Password and confirm password do not match. Please try again.");
                        }
                    }
                } else {
                    showDialog("All fields are mandatory.");
                }
            }
        });
    }

    private void showDialog(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
