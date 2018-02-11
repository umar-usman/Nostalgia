package com.example.hp.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UploadReviewActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private static final String MY_PREFS_NAME = "MyPrefsFile";
    EditText edit_review;
    Button post_review;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_review);
        mAuth = FirebaseAuth.getInstance();
        post_review = findViewById(R.id.btn_post);
        edit_review = findViewById(R.id.review_edt);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String place_name = prefs.getString("MyLocation", null);
        String place_address = prefs.getString("MyAddress", null);
        String Current_place = place_name + ", " + place_address;
        myRef = FirebaseDatabase.getInstance().getReference().child("Views").child(Current_place);
        post_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String review = edit_review.getText().toString().trim();
                post_review.setEnabled(false);
                myRef = myRef.child("Reviews").push();
                myRef.child("userID").setValue(mAuth.getCurrentUser().getUid());
                myRef.child("Review").setValue(review);
                AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                        .setTitle("Pictue uploaded successfully!")
                        .setPositiveButton("ok",dialogClickListener)
                        .show();
            }
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i){
                        case DialogInterface.BUTTON_POSITIVE:
                            Intent intent = new Intent(UploadReviewActivity.this,NavigationActivity.class );
                            startActivity(intent);
                    }
                }
            };
        });


    }
}
