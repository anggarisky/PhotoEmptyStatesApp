package com.example.anggarisky.photoemptystatesapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChooseUserNameAct extends AppCompatActivity {

    EditText userNameStory;
    Button btnsaveusername;

    DatabaseReference reference;

    String USER_NAME_STORY = "usernamestory";
    String userNameStoryLocal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_name);

        userNameStory = findViewById(R.id.userNameStory);
        btnsaveusername = findViewById(R.id.btnsaveusername);

        btnsaveusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(userNameStory.getText().toString());

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // save to Firebase
                        reference.getRef().child("username").setValue(userNameStory.getText().toString());

                        // save username to the local storage to keep as a key whenever we get the data from Firebase
                        SharedPreferences sharedPreferences = getSharedPreferences(USER_NAME_STORY, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(userNameStoryLocal, userNameStory.getText().toString());
                        editor.apply();

                        // give a notice
                        Toast.makeText(getApplicationContext(), "Ok Done", Toast.LENGTH_SHORT).show();

                        // pass into another activity
                        
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
