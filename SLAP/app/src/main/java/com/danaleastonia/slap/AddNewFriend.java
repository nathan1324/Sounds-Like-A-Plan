package com.danaleastonia.slap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewFriend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_friend);


        final EditText friendNameET = (EditText) findViewById(R.id.addFriendName);
        Button addButton = (Button) findViewById(R.id.addFriendButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String friendName = friendNameET.getText().toString();
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                long count = dbHandler.addFriend(friendName);

                if (count > 0)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Friend added successfully", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
            }
        });

    }

}
