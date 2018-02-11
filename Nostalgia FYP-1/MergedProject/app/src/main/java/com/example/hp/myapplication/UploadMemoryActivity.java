package com.example.hp.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class UploadMemoryActivity extends AppCompatActivity {
    ImageView imageView;
    ImageView videoView;
    Button uploadMemory;
    Button uploadVideo;
    private DatabaseReference uUserDatabaseReference;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    AlertDialog.Builder alertDialog;
    Uri downURL;
    Uri uri;
    private static final String MY_PREFS_NAME = "MyPrefsFile";
    private StorageReference mStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_memory);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String place_name = prefs.getString("MyLocation", null);
        String place_address = prefs.getString("MyAddress", null);
        String Current_place = place_name + ", " + place_address;
//        Toast.makeText(this, "" + Current_place, Toast.LENGTH_LONG).show();
        mAuth = FirebaseAuth.getInstance();
        String uniqueId = UUID.randomUUID().toString();
        mStorage = FirebaseStorage.getInstance().getReference().child(uniqueId);
        myRef = FirebaseDatabase.getInstance().getReference().child("Views").child(Current_place);
        uUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("My Memories");
        Intent intent = getIntent();
        Bitmap bitmap1 = (Bitmap) intent.getParcelableExtra("BitmapImage");
        uploadMemory = findViewById(R.id.upload_btn);
        //String filepath = getIntent().getStringExtra("Image");
        //Toast.makeText(this, filepath, Toast.LENGTH_SHORT).show();
        //Bitmap myBitmap = BitmapFactory.decodeFile(filepath);
        //Log.d("ABCD",filepath);
        ImageView myImage = (ImageView) findViewById(R.id.imgbtn);
        myImage.setImageBitmap(bitmap1);
//        Bitmap bitmap = imageView.getDrawingCache();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        final byte[] data = baos.toByteArray();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream);
        final byte[] data = stream.toByteArray();
        uploadMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                uploadMemory.setEnabled(false);
                UploadTask uploadTask = mStorage.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
//                        Toast.makeText(UploadMemoryActivity.this, "Not", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        final Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                        Toast.makeText(UploadMemoryActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                        myRef = myRef.child("Memories").push();
                        String memoryKey = myRef.getKey();
                        //String newKey = uUserDatabaseReference.push().getKey();
                        //uUserDatabaseReference.child(newKey).setValue(memoryKey);
                        myRef.runTransaction(new Transaction.Handler() {
                            @Override
                            public Transaction.Result doTransaction(MutableData mutableData) {
                                myRef.child("userID").setValue(mAuth.getCurrentUser().getUid());
                                myRef.child("Picture URL").setValue(downloadUrl.toString());
                                return null;
                            }

                            @Override
                            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
//                                Toast.makeText(UploadMemoryActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                                        .setTitle("Pictue uploaded successfully!")
                                        .setPositiveButton("ok",dialogClickListener)
                                        .show();
                            }
                        });
                    }
                });

            }
        });


    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i){
                case DialogInterface.BUTTON_POSITIVE:
                Intent intent = new Intent(UploadMemoryActivity.this,NavigationActivity.class );
                startActivity(intent);
            }
        }
    };

}




