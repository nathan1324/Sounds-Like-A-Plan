package com.danaleastonia.slap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.AlertDialog;

public class UpdateStatus extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String MyPreferences = "MyPrefs";
    private static final String LOGGEDIN_USER_EMAIL = "LOGGEDIN_USER_EMAIL";
    private String mLoggedInUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);

        Spinner spinner = (Spinner) findViewById(R.id.status_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        String selectedStatus = spinner.getSelectedItem().toString();
        final EditText statusEditText = (EditText) findViewById(R.id.statusUpdateEditText);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();

                if ("Custom".equals(item)) {
                    statusEditText.setText("");
                    statusEditText.setHint("Type Status");
                } else {
                    statusEditText.setText(item);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button button = (Button) findViewById(R.id.statusUpdateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = statusEditText.getText().toString();
                showDialog(status);
            }
        });

        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        mLoggedInUserEmail = sharedPreferences.getString(LOGGEDIN_USER_EMAIL, null);
    }

    private void showDialog(final String status) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Setting your status to: " + status);

        alertDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getApplicationContext(), "Updating status.", Toast.LENGTH_SHORT).show();
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                dbHandler.addFeedItem(status, mLoggedInUserEmail);
                finish();
            }
        });
        
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
